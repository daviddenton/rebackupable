package daviddenton.port

import daviddenton.domain.RemarkableContentPath
import dev.forkhandles.result4k.Result4k
import java.io.InputStream

interface Backup {
    fun write(path: RemarkableContentPath, data: InputStream): Result4k<Unit, Exception>
}