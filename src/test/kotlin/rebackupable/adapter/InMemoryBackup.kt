package rebackupable.adapter

import dev.forkhandles.result4k.Result4k
import dev.forkhandles.result4k.Success
import rebackupable.domain.LocalFilePath
import rebackupable.port.Backup
import java.io.InputStream

class InMemoryBackup : Backup {
    private val content = mutableMapOf<String, String>()

    fun allSaved() = content.toMap()

    override fun location(backupId: String) = "memory/$backupId"

    override fun write(path: LocalFilePath, data: InputStream): Result4k<Unit, Exception> {
        content[path.value] = data.reader().readText()
        return Success(Unit)
    }
}