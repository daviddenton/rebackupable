package daviddenton.app

import daviddenton.adapter.HttpRemarkable
import daviddenton.adapter.InMemoryBackup
import daviddenton.contents
import daviddenton.domain.BackupReport
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

    private val hub = RebackupableHub(
        backup,
        HttpRemarkable(FakeRemarkable(contents).debug(), Uri.of("http://remarkable")))

    @Test
    fun `backs up no content`() {
        expectThat(hub.backup()).isEqualTo(Success(BackupReport(File("."), 0)))
    }
}