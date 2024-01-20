package daviddenton.adapter

import daviddenton.domain.RemarkableContentPath
import daviddenton.domain.RemarkableFileName
import daviddenton.port.Backup
import dev.forkhandles.result4k.Result4k
import java.io.File
import java.io.InputStream
import java.time.Clock

fun UserHomeDirBackup(clock: Clock, homeDir: File) = object : Backup {
    override fun write(path: RemarkableContentPath, file: RemarkableFileName, data: InputStream): Result4k<Unit, Exception> {
        clock.also {  }
        homeDir.also {  }
        TODO("Not yet implemented")
    }
}