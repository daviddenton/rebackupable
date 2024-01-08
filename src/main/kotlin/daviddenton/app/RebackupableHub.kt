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

    private fun RemarkableContentPath.backup(): Result<BackupReport, Exception> = remarkable.list(this)
        .flatMap {
            it
                .map { remarkableFile ->
                    when (remarkableFile.Type) {
                        CollectionType -> child(remarkableFile.ID).backup()
                        DocumentType -> remarkable.download(remarkableFile.ID)
                            .flatMap {
                                backup
                                    .write(RemarkableContentPath.of(remarkableFile.VissibleName.value), it)
                            }
                    }
                }
                .allValues()
                .map { BackupReport(File("."), it.size) }
        }
}