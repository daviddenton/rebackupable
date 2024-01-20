package daviddenton.fake

import daviddenton.adapter.HttpRemarkable
import daviddenton.contents
import daviddenton.domain.RemarkableContentPath.Companion.ROOT
import daviddenton.domain.RemarkableFile
import daviddenton.domain.RemarkableFileId
import daviddenton.domain.RemarkableFileName
import daviddenton.domain.RemarkableFileType.CollectionType
import daviddenton.domain.RemarkableFileType.DocumentType
import dev.forkhandles.result4k.Success
import dev.forkhandles.values.of
import org.http4k.core.Uri
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class FakeRemarkableTest {
    private val remarkable = HttpRemarkable(FakeRemarkable(contents), Uri.of("http://remarkable"))

    @Test
    fun `get root files`() {
        expectThat(remarkable.list(ROOT))
            .isEqualTo(
                Success(
                    listOf(
                        RemarkableFile(
                            RemarkableFileId.of(0, 1),
                            DocumentType,
                            RemarkableFileName.of("rootFile"),
                            "pdf"
                        ),
                        RemarkableFile(
                            RemarkableFileId.of(2, 3),
                            CollectionType,
                            RemarkableFileName.of("childFolder")
                        )
                    )
                )
            )
    }

    @Test
    fun `get child files`() {
        expectThat(remarkable.list(ROOT.child(RemarkableFileId.of(2, 3))))
            .isEqualTo(
                Success(
                    listOf(
                        RemarkableFile(
                            RemarkableFileId.of(4, 5),
                            DocumentType,
                            RemarkableFileName.of("childFile"),
                            "pdf"
                        ),
                        RemarkableFile(
                            RemarkableFileId.of(6, 7),
                            CollectionType,
                            RemarkableFileName.of("grandchildFolder")
                        )
                    )
                )
            )
    }

    @Test
    fun `get grandchild files`() {
        expectThat(remarkable.list(ROOT.child(RemarkableFileId.of(2, 3)).child(RemarkableFileId.of(6, 7))))
            .isEqualTo(
                Success(
                    listOf(
                        RemarkableFile(
                            RemarkableFileId.of(8, 9),
                            DocumentType,
                            RemarkableFileName.of("grandchildFile"),
                            "pdf"
                        )
                    )
                )
            )
    }
}