package daviddenton.adapter

import daviddenton.domain.LocalFilePath
import daviddenton.port.Backup
import dev.forkhandles.result4k.Result4k
import java.io.File
import java.io.InputStream
import java.time.Clock

fun UserHomeDirBackup(clock: Clock, homeDir: File) = object : Backup {
    override fun write(path: LocalFilePath, data: InputStream): Result4k<Unit, Exception> {
        clock.also {  }
        homeDir.also {  }
        TODO("Not yet implemented")
    }
}