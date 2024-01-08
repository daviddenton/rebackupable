package daviddenton.adapter

import daviddenton.domain.RemarkableContentPath
import daviddenton.port.Backup
import dev.forkhandles.result4k.Result4k
import dev.forkhandles.result4k.Success
import java.io.InputStream

class InMemoryBackup : Backup {
    val content = mutableMapOf<RemarkableContentPath, String>()

    override fun write(path: RemarkableContentPath, data: InputStream): Result4k<Unit, Exception> {
        content[path] = data.reader().readText()
        return Success(Unit)
    }
}