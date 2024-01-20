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
        HttpRemarkable(FakeRemarkable(contents).debug(), Uri.of("http://remarkable"))
    )

    @Test
    fun `backs up all content`() {
        expectThat(hub.backup()).isEqualTo(Success(BackupReport(File("."), 3)))
        expectThat(backup.allSaved().toList().joinToString("\n")).isEqualTo(
            listOf(
                "rootFile" to "00000000-0000-0000-0000-000000000001",
                "00000000-0000-0002-0000-000000000003/childFile" to "00000000-0000-0004-0000-000000000005",
                "00000000-0000-0002-0000-000000000003/00000000-0000-0006-0000-000000000007/grandchildFile" to "00000000-0000-0008-0000-000000000009",
            ).joinToString("\n")
        )
    }
}