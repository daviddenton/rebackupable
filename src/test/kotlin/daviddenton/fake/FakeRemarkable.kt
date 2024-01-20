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
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import org.http4k.routing.path
import org.http4k.routing.routes

fun FakeRemarkable(rootContents: List<RemarkableFsEntry>): RoutingHttpHandler {




    return routes(
        "/download/{uuid}/placeholder" bind GET to { req: Request ->
            when (val result = rootContents.allFiles()
                .firstOrNull { it.ID.toString() == req.path("uuid") }) {
                null -> Response(NOT_FOUND)
                else -> Response(OK).body(result.ID.toString())
            }
        },
        "/documents/{path:.*}" bind GET to { req ->
            when (val result = rootContents.find(Path.value(RemarkableContentPath).of("path")(req))) {
                null -> Response(NOT_FOUND)
                else -> result.list()
            }
        },
        "/documents" bind GET to { rootContents.list() }
    )
}

private fun List<RemarkableFsEntry>.list() = Response(OK).with(
    Json.autoBody<List<RemarkableFile>>().toLens() of map(RemarkableFsEntry::toRemarkableFile)
)

// path = 123/546
// list = 1 123 (456, 345)

fun List<RemarkableFsEntry>.find(path: RemarkableContentPath): List<RemarkableFsEntry>? {
    filterIsInstance<Folder>()
        .firstOrNull { it.toRemarkableFile().ID == path.root }
        ?.contents
    TODO()
}

private fun List<RemarkableFsEntry>.allFiles(): List<RemarkableFile> = flatMap {
    when (it) {
        is File -> listOf(it.toRemarkableFile())
        is Folder -> it.contents.allFiles()
    }
}

