package rebackupable.adapter

import dev.forkhandles.result4k.Failure
import dev.forkhandles.result4k.Result4k
import dev.forkhandles.result4k.Success
import org.http4k.cloudnative.RemoteRequestFailed
import org.http4k.core.Filter
import org.http4k.core.HttpHandler
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Uri
import org.http4k.core.then
import org.http4k.filter.ClientFilters.SetBaseUriFrom
import org.http4k.format.invoke
import org.http4k.lens.Header
import org.http4k.lens.regexGroup
import rebackupable.domain.RemarkableContentPath
import rebackupable.domain.RemarkableFile
import rebackupable.domain.RemarkableFileId
import rebackupable.port.Remarkable
import rebackupable.util.Json
import java.io.InputStream

class HttpRemarkable(http: HttpHandler, serverUrl: Uri) : Remarkable {
    private val http = SetBaseUriFrom(serverUrl)
        .then(AlwaysCloseConnection())
        .then(http)

    override fun list(path: RemarkableContentPath): Result4k<List<RemarkableFile>, RemoteRequestFailed> {
        val uri = Uri.of("/documents/$path")
        val result = http(Request(GET, uri))
        return when {
            result.status.successful -> Success(Json<Array<RemarkableFile>>(result).toList())
            else -> Failure(RemoteRequestFailed(result.status, "request failed ${result.status}", uri))
        }
    }

    override fun download(id: RemarkableFileId): Result4k<Pair<String, InputStream>, Exception> {
        val uri = Uri.of("/download/$id/placeholder")
        val name = Header.regexGroup(".*filename=\"(.+).+").required("Content-Disposition")
        val result = http(Request(GET, uri))
        return when {
            result.status.successful -> Success(name(result) to result.body.stream)
            else -> {
                Failure(RemoteRequestFailed(result.status, "request failed ${result.status}", uri))
            }
        }
    }
}

private fun AlwaysCloseConnection() = Filter { next ->
    {
        next(it.header("Connection", "close"))
    }
}
