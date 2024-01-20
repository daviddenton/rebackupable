package rebackupable.fake

import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.NOT_FOUND
import org.http4k.core.Status.Companion.OK
import org.http4k.core.with
import org.http4k.lens.Path
import org.http4k.lens.value
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.SunHttp
import org.http4k.server.asServer
import rebackupable.contents
import rebackupable.domain.RemarkableContentPath
import rebackupable.domain.RemarkableContentPath.Companion.ROOT
import rebackupable.domain.RemarkableFile
import rebackupable.domain.RemarkableFileId
import rebackupable.fake.RemarkableFsEntry.File
import rebackupable.fake.RemarkableFsEntry.Folder
import rebackupable.util.Json

fun FakeRemarkable(rootContents: List<RemarkableFsEntry>) = routes(
    "/download/{uuid}/placeholder" bind GET to { req: Request ->
        when (val result = rootContents.allFiles()
            .firstOrNull { it.ID == Path.value(RemarkableFileId).of("uuid")(req) }) {
            null -> Response(NOT_FOUND)
            else -> Response(OK)
                .header("Content-Disposition", "attachment; filename=\"${result.VissibleName}.pdf\"")
                .body(result.ID.toString())
        }
    },
    "/documents/{path:.*}" bind GET to { req ->
        val toFind = Path.value(RemarkableContentPath).of("path")(req)
        when (val result = rootContents.find(toFind)) {
            null -> Response(NOT_FOUND)
            else -> result.list()
        }
    },
    "/documents" bind GET to { rootContents.list() }
)

private fun List<RemarkableFsEntry>.list() = Response(OK).with(
    Json.autoBody<List<RemarkableFile>>().toLens() of map(RemarkableFsEntry::toRemarkableFile)
)

private fun List<RemarkableFsEntry>.find(path: RemarkableContentPath): List<RemarkableFsEntry>? = when (path) {
    ROOT -> this
    else -> filterIsInstance<Folder>()
        .firstOrNull { it.toRemarkableFile().ID == path.root }
        ?.contents?.find(path.dropRoot())
}

private fun List<RemarkableFsEntry>.allFiles(): List<RemarkableFile> = flatMap {
    when (it) {
        is File -> listOf(it.toRemarkableFile())
        is Folder -> it.contents.allFiles()
    }
}

fun main() {
    FakeRemarkable(contents).asServer(SunHttp(9000)).start()
}