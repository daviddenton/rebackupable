package daviddenton.app

import daviddenton.domain.BackupReport
import daviddenton.domain.RemarkableContentPath
import daviddenton.domain.RemarkableContentPath.Companion.ROOT
import daviddenton.domain.RemarkableFileType.CollectionType
import daviddenton.domain.RemarkableFileType.DocumentType
import daviddenton.port.Backup
import daviddenton.port.Remarkable
import dev.forkhandles.result4k.Result
import dev.forkhandles.result4k.allValues
import dev.forkhandles.result4k.flatMap
import dev.forkhandles.result4k.map
import java.io.File

class RebackupableHub(
    private val backup: Backup,
    private val remarkable: Remarkable
) {
    fun backup() = ROOT.backup()

    private fun RemarkableContentPath.backup(): Result<BackupReport, Exception> = backupFolder()
        .map { BackupReport(File("."), it) }

    private fun RemarkableContentPath.backupFolder(): Result<Int, Exception> = remarkable.list(this)
        .flatMap {
            it.map { remarkableFile ->
                when (remarkableFile.Type) {
                    CollectionType -> child(remarkableFile.ID).backupFolder()
                    DocumentType -> remarkable.download(remarkableFile.ID)
                        .flatMap { backup.write(this, remarkableFile.VissibleName, it) }
                        .map { 1 }
                }
            }
                .allValues()
                .map { it.sum() }
        }
}