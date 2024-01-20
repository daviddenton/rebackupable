package daviddenton.fake

import daviddenton.domain.RemarkableContentPath
import daviddenton.domain.RemarkableFile
import daviddenton.fake.RemarkableFsEntry.File
import daviddenton.fake.RemarkableFsEntry.Folder
import daviddenton.util.Json
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.NOT_FOUND
import org.http4k.core.Status.Companion.OK
import org.http4k.core.with
import org.http4k.lens.Path
import org.http4k.lens.value
import org.http4k.routing.bind
import org.http4k.routing.path
import org.http4k.routing.routes

fun FakeRemarkable(contents: List<RemarkableFsEntry>) = routes(
    "/download/{uuid}/placeholder" bind GET to { req: Request ->
        when (val result = contents.allFiles()
            .firstOrNull { it.ID.toString() == req.path("uuid") }) {
            null -> Response(NOT_FOUND)
            else -> Response(OK).body(result.ID.toString())
        }
    },
    "/documents/{path:.*}" bind GET to { req ->
        when(val result = contents.find(Path.value(RemarkableContentPath).of("path")(req))) {
           null -> Response(NOT_FOUND)
           else -> result.list()
        }
    },
    "/documents" bind GET to { contents.list() }
)

private fun List<RemarkableFsEntry>.list() = Response(OK).with(
    Json.autoBody<List<RemarkableFile>>().toLens() of map(RemarkableFsEntry::toRemarkableFile)
)

fun List<RemarkableFsEntry>.find(path: RemarkableContentPath): List<RemarkableFsEntry>? {
//    firstOrNull { it.id == path.root }
//        ?.let {
//            when(it) {
//
//            }
//            if()
//        }

    TODO("Not yet implemented")
}

private fun List<RemarkableFsEntry>.allFiles(): List<RemarkableFile> = flatMap {
    when (it) {
        is File -> listOf(it.toRemarkableFile())
        is Folder -> it.contents.allFiles()
    }
}

