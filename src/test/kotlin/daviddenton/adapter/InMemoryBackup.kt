package daviddenton.adapter

import daviddenton.domain.RemarkableContentPath
import daviddenton.domain.RemarkableContentPath.Companion.ROOT
import daviddenton.domain.RemarkableFileName
import daviddenton.port.Backup
import dev.forkhandles.result4k.Result4k
import dev.forkhandles.result4k.Success
import java.io.InputStream

class InMemoryBackup : Backup {
    private val content = mutableMapOf<String, String>()

    fun allSaved() = content.toMap()

    override fun write(path: RemarkableContentPath, file: RemarkableFileName, data: InputStream): Result4k<Unit, Exception> {
        val actualPath = if(path == ROOT) file.value else path.value + "/" + file
        content[actualPath] = data.reader().readText()
        return Success(Unit)
    }
}