package daviddenton.port

import daviddenton.domain.FolderPath
import dev.forkhandles.result4k.Result4k
import java.io.InputStream

interface Backup {
    fun location(backupId: String): String
    fun write(path: FolderPath, data: InputStream): Result4k<Unit, Exception>
}