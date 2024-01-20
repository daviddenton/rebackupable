package rebackupable.port

import dev.forkhandles.result4k.Result4k
import rebackupable.domain.LocalFilePath
import java.io.InputStream

interface Backup {
    fun location(backupId: String): String
    fun write(path: LocalFilePath, data: InputStream): Result4k<Unit, Exception>
}