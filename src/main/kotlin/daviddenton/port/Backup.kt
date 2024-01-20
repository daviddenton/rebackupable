package daviddenton.port

import daviddenton.domain.RemarkableContentPath
import daviddenton.domain.RemarkableFileName
import dev.forkhandles.result4k.Result4k
import java.io.InputStream

interface Backup {
    fun write(path: RemarkableContentPath, file: RemarkableFileName, data: InputStream): Result4k<Unit, Exception>
}