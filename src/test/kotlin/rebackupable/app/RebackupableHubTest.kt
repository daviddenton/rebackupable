package rebackupable.app

import dev.forkhandles.result4k.Success
import org.http4k.core.Uri
import org.junit.jupiter.api.Test
import rebackupable.adapter.HttpRemarkable
import rebackupable.adapter.InMemoryBackup
import rebackupable.contents
import rebackupable.domain.BackupReport
import rebackupable.fake.FakeRemarkable
import strikt.api.expectThat
import strikt.assertions.isEqualTo
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
        expectThat(hub.backup()).isEqualTo(Success(BackupReport("memory/1970/01/01/0000", 3)))
        expectThat(backup.allSaved().toList().joinToString("\n")).isEqualTo(
            listOf(
                "1970/01/01/0000/rootFile.pdf" to "00000000-0000-0000-0000-000000000001",
                "1970/01/01/0000/childFolder/childFile.pdf" to "00000000-0000-0004-0000-000000000005",
                "1970/01/01/0000/childFolder/grandchildFolder/grandchildFile.pdf" to "00000000-0000-0008-0000-000000000009",
            ).joinToString("\n")
        )
    }
}