package daviddenton.domain

import daviddenton.domain.RemarkableContentPath.Companion.ROOT
import dev.forkhandles.values.of
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class RemarkableContentPathTest {

    @Test
    fun `can construct tree`() {
        expectThat(ROOT.child(RemarkableFileId.of(0, 1)).child(RemarkableFileId.of(2, 3)))
            .isEqualTo(RemarkableContentPath.of("00000000-0000-0000-0000-000000000001/00000000-0000-0002-0000-000000000003"))
    }

    @Test
    fun `can drill down tree`() {
        expectThat(ROOT.child(RemarkableFileId.of(0, 1)).child(RemarkableFileId.of(2, 3)).dropRoot())
            .isEqualTo(RemarkableContentPath.of("00000000-0000-0002-0000-000000000003"))
    }
}