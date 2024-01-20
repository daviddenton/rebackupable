package rebackupable.domain

import dev.forkhandles.values.of
import org.junit.jupiter.api.Test
import rebackupable.domain.RemarkableContentPath.Companion.ROOT
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class RemarkableContentPathTest {

    private val child = ROOT.child(RemarkableFileId.of(0, 1))
    private val grandchild = child.child(RemarkableFileId.of(2, 3))

    @Test
    fun `can report total path`() {
        expectThat(ROOT).isEqualTo(RemarkableContentPath.of(""))
        expectThat(child).isEqualTo(RemarkableContentPath.of("00000000-0000-0000-0000-000000000001"))
        expectThat(grandchild).isEqualTo(RemarkableContentPath.of("00000000-0000-0000-0000-000000000001/00000000-0000-0002-0000-000000000003"))
    }

    @Test
    fun `can drill down tree`() {
        expectThat(child.dropRoot()).isEqualTo(RemarkableContentPath.of(""))
        expectThat(grandchild.dropRoot()).isEqualTo(RemarkableContentPath.of("00000000-0000-0002-0000-000000000003"))
    }
}