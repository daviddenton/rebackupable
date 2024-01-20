package daviddenton.adapter

import daviddenton.domain.FolderPath
import daviddenton.port.Backup
import dev.forkhandles.result4k.map
import dev.forkhandles.result4k.resultFrom
import java.io.File
import java.io.InputStream

fun UserHomeDirBackup(homeDir: File) = object : Backup {
    override fun location(backupId: String) = File(homeDir.absolutePath, backupId).absolutePath

    override fun write(path: FolderPath, data: InputStream) = resultFrom {
        val target = File(homeDir, path.value).apply { if (!parentFile.exists()) require(parentFile.mkdirs()) }
        data.copyTo(target.outputStream())
    }.map { }
}