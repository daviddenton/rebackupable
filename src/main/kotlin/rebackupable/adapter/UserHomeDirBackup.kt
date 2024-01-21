package rebackupable.adapter

import dev.forkhandles.result4k.map
import dev.forkhandles.result4k.resultFrom
import rebackupable.domain.LocalFilePath
import rebackupable.port.Backup
import java.io.File
import java.io.InputStream

fun UserHomeDirBackup(homeDir: File) = object : Backup {
    private val rebackupableHome = File(homeDir, "/Documents/Rebackupable")
    override fun location(backupId: String) = File(rebackupableHome, backupId).absolutePath

    override fun write(path: LocalFilePath, data: InputStream) = resultFrom {
        val target = File(rebackupableHome, path.value).apply { if (!parentFile.exists()) require(parentFile.mkdirs()) }
        data.copyTo(target.outputStream())
    }.map { }
}