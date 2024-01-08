package daviddenton.fake

import daviddenton.domain.RemarkableFile
import daviddenton.domain.RemarkableFileId
import daviddenton.domain.RemarkableFileName
import daviddenton.domain.RemarkableFileType

sealed interface RemarkableFsEntry {
    val id: RemarkableFileId
    val name: String

    fun toRemarkableFile(): RemarkableFile

    data class File(
        override val id: RemarkableFileId,
        override val name: String
    ) : RemarkableFsEntry {
        override fun toRemarkableFile() =
            RemarkableFile(id, RemarkableFileType.DocumentType, RemarkableFileName.of(name))
    }

    data class Folder(
        override val id: RemarkableFileId,
        override val name: String,
        val contents: Map<RemarkableFileId, RemarkableFsEntry>
    ) : RemarkableFsEntry {
        override fun toRemarkableFile() =
            RemarkableFile(id, RemarkableFileType.CollectionType, RemarkableFileName.of(name))
    }
}