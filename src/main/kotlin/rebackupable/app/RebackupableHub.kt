package rebackupable.app

import dev.forkhandles.result4k.Result
import dev.forkhandles.result4k.allValues
import dev.forkhandles.result4k.flatMap
import dev.forkhandles.result4k.map
import rebackupable.domain.BackupReport
import rebackupable.domain.LocalFilePath
import rebackupable.domain.RemarkableContentPath
import rebackupable.domain.RemarkableContentPath.Companion.ROOT
import rebackupable.domain.RemarkableFileType.Companion.CollectionType
import rebackupable.domain.RemarkableFileType.Companion.DocumentType
import rebackupable.port.Backup
import rebackupable.port.Remarkable
import rebackupable.port.Terminal
import java.time.Clock
import java.time.format.DateTimeFormatter

class RebackupableHub(
    private val clock: Clock,
    private val backup: Backup,
    private val remarkable: Remarkable,
    private val terminal: Terminal
) {
    fun backup(): Result<BackupReport, Exception> {
        val backupTime = clock.instant().atZone(clock.zone)
        val rootFolder = DateTimeFormatter.ofPattern("yyyy/MM/dd/HHmm").format(backupTime)

        return ROOT.backupFolder(LocalFilePath.of(rootFolder)).map { BackupReport(backup.location(rootFolder), it) }
    }

    private fun RemarkableContentPath.backupFolder(fsPath: LocalFilePath): Result<Int, Exception> =
        remarkable.list(this)
            .flatMap {
                it.mapNotNull { remarkableFile ->
                    when (remarkableFile.Type) {
                        CollectionType -> child(remarkableFile.ID).backupFolder(fsPath.child(remarkableFile))
                        DocumentType -> {
                            terminal(".")
                            remarkable.download(remarkableFile.ID)
                                .flatMap { backup.write(fsPath.file(it.first), it.second) }
                                .map { 1 }
                        }
                        else -> null
                    }
                }
                    .allValues()
                    .map(List<Int>::sum)
            }
}