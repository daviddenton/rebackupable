package daviddenton.app

import daviddenton.adapter.HttpRemarkable
import daviddenton.adapter.InMemoryBackup
import daviddenton.domain.BackupReport
import daviddenton.domain.RemarkableFile
import daviddenton.fake.FakeRemarkable
import dev.forkhandles.result4k.Success
import org.http4k.core.Uri
import org.http4k.filter.debug
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import java.io.File

class RebackupableHubTest {

    private val backup = InMemoryBackup()
    private val remarkableContent = emptyMap<String, RemarkableFile>()

    private val hub = RebackupableHub(
        backup,
        HttpRemarkable(FakeRemarkable(remarkableContent).debug(), Uri.of("http://remarkable")))

    @Test
    fun `backs up no content`() {
        expectThat(hub.backup()).isEqualTo(Success(BackupReport(File("."), 0)))
    }
}