package daviddenton.adapter

import daviddenton.domain.FolderPath
import daviddenton.port.Backup
import dev.forkhandles.result4k.orThrow
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

interface BackupContract {

    val backup: Backup

    fun getFile(path: String): String?

    @Test
    fun `backs up content`() {
        backup.write(FolderPath.of("foo/moo"), "hello1".byteInputStream()).orThrow()
        backup.write(FolderPath.of("foo/alt/bar"), "hello2".byteInputStream()).orThrow()

        expectThat(getFile("foo/moo")).isEqualTo("hello1")
        expectThat(getFile("foo/alt/bar")).isEqualTo("hello2")
    }
}