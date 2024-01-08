package daviddenton.port

import daviddenton.domain.RemarkableContentPath
import daviddenton.domain.RemarkableFile
import daviddenton.domain.RemarkableFileId
import dev.forkhandles.result4k.Result4k
import java.io.InputStream

interface Remarkable {
    fun list(path: RemarkableContentPath): Result4k<List<RemarkableFile>, Exception>
    fun download(id: RemarkableFileId): Result4k<InputStream, Exception>
}
