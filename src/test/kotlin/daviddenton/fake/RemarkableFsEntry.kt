package daviddenton.fake

import daviddenton.domain.RemarkableFile
import daviddenton.domain.RemarkableFileId
import daviddenton.domain.RemarkableFileName
import daviddenton.domain.RemarkableFileType.CollectionType
import daviddenton.domain.RemarkableFileType.DocumentType

sealed interface RemarkableFsEntry {
    val id: RemarkableFileId
    val name: String

    fun toRemarkableFile(): RemarkableFile

    data class File(
        override val id: RemarkableFileId,
        override val name: String
    ) : RemarkableFsEntry {
        override fun toRemarkableFile() =
            RemarkableFile(id, DocumentType, RemarkableFileName.of(name))
    }

    data class Folder(
        override val id: RemarkableFileId,
        override val name: String,
        val contents: List<RemarkableFsEntry>
    ) : RemarkableFsEntry {
        override fun toRemarkableFile() =
            RemarkableFile(id, CollectionType, RemarkableFileName.of(name))
    }
}