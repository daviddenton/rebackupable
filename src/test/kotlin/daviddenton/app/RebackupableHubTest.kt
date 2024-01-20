package daviddenton.app

import daviddenton.adapter.HttpRemarkable
import daviddenton.adapter.InMemoryBackup
import daviddenton.contents
import daviddenton.domain.BackupReport
import daviddenton.fake.FakeRemarkable
import dev.forkhandles.result4k.Success
import org.http4k.core.Uri
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import java.io.File
import java.time.Clock
import java.time.Instant.EPOCH
import java.time.ZoneId

class RebackupableHubTest {
    private val backup = InMemoryBackup()

    private val hub = RebackupableHub(
        Clock.fixed(EPOCH, ZoneId.of("UTC")),
        backup,
        HttpRemarkable(FakeRemarkable(contents), Uri.of("http://remarkable")),
        ::println
    )

    @Test
    fun `backs up all content`() {
        expectThat(hub.backup()).isEqualTo(Success(BackupReport(File("1970/01/01/0000"), 3)))
        expectThat(backup.allSaved().toList().joinToString("\n")).isEqualTo(
            listOf(
                "1970/01/01/0000/rootFile" to "00000000-0000-0000-0000-000000000001",
                "1970/01/01/0000/childFolder/childFile" to "00000000-0000-0004-0000-000000000005",
                "1970/01/01/0000/childFolder/grandchildFolder/grandchildFile" to "00000000-0000-0008-0000-000000000009",
            ).joinToString("\n")
        )
    }
}