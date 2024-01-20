package rebackupable.fake

import rebackupable.domain.RemarkableFile
import rebackupable.domain.RemarkableFileId
import rebackupable.domain.RemarkableFileName
import rebackupable.domain.RemarkableFileType.Companion.CollectionType
import rebackupable.domain.RemarkableFileType.Companion.DocumentType

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