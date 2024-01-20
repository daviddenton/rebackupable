package rebackupable.fake

import dev.forkhandles.result4k.Success
import dev.forkhandles.values.of
import org.http4k.core.Uri
import org.junit.jupiter.api.Test
import rebackupable.adapter.HttpRemarkable
import rebackupable.contents
import rebackupable.domain.RemarkableContentPath.Companion.ROOT
import rebackupable.domain.RemarkableFile
import rebackupable.domain.RemarkableFileId
import rebackupable.domain.RemarkableFileName
import rebackupable.domain.RemarkableFileType.CollectionType
import rebackupable.domain.RemarkableFileType.DocumentType
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
                            RemarkableFileName.of("rootFile")
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
                            RemarkableFileName.of("childFile")
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
                            RemarkableFileName.of("grandchildFile")
                        )
                    )
                )
            )
    }
}