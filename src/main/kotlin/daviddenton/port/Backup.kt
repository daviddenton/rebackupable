package daviddenton.port

import daviddenton.domain.LocalFilePath
import dev.forkhandles.result4k.Result4k
import java.io.InputStream

interface Backup {
    fun write(path: LocalFilePath, data: InputStream): Result4k<Unit, Exception>
}