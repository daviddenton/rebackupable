package daviddenton.fake

import daviddenton.adapter.HttpRemarkable
import daviddenton.contents
import daviddenton.domain.RemarkableContentPath
import dev.forkhandles.result4k.Success
import org.http4k.core.Uri
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class FakeRemarkableTest {
    private val remarkable = HttpRemarkable(FakeRemarkable(contents), Uri.of("http://remarkable"))

    @Test
    fun `get files`() {
        expectThat(remarkable.list(RemarkableContentPath.ROOT))
            .isEqualTo(
                Success(listOf())
            )
    }
}