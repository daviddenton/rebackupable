package rebackupable.adapter

import dev.forkhandles.result4k.orThrow
import org.junit.jupiter.api.Test
import rebackupable.domain.LocalFilePath
import rebackupable.port.Backup
import strikt.api.expectThat
import strikt.assertions.isEqualTo

interface BackupContract {

    val backup: Backup

    fun getFile(path: String): String?

    @Test
    fun `backs up content`() {
        backup.write(LocalFilePath.of("foo/moo.pdf"), "hello1".byteInputStream()).orThrow()
        backup.write(LocalFilePath.of("foo/alt/bar.pdf"), "hello2".byteInputStream()).orThrow()

        expectThat(getFile("foo/moo.pdf")).isEqualTo("hello1")
        expectThat(getFile("foo/alt/bar.pdf")).isEqualTo("hello2")
    }
}