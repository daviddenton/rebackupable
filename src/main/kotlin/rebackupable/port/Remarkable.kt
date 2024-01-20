package rebackupable.port

import dev.forkhandles.result4k.Result4k
import rebackupable.domain.RemarkableContentPath
import rebackupable.domain.RemarkableFile
import rebackupable.domain.RemarkableFileId
import java.io.InputStream

interface Remarkable {
    fun list(path: RemarkableContentPath): Result4k<List<RemarkableFile>, Exception>
    fun download(id: RemarkableFileId): Result4k<Pair<String, InputStream>, Exception>
}
