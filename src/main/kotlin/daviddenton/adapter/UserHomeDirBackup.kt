package daviddenton.adapter

import daviddenton.domain.LocalFilePath
import daviddenton.port.Backup
import dev.forkhandles.result4k.map
import dev.forkhandles.result4k.resultFrom
import java.io.File
import java.io.InputStream

fun UserHomeDirBackup(homeDir: File) = object : Backup {
    private val rebackupableHome = File(homeDir, "Rebackupable")
    override fun location(backupId: String) = File(rebackupableHome, backupId).absolutePath

    override fun write(path: LocalFilePath, data: InputStream) = resultFrom {
        val target = File(rebackupableHome, path.value).apply { if (!parentFile.exists()) require(parentFile.mkdirs()) }
        data.copyTo(target.outputStream())
    }.map { }
}