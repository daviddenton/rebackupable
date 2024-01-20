package daviddenton.domain

import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class RemarkableFile(
    val ID: RemarkableFileId,
    val Type: RemarkableFileType,
    val VissibleName: RemarkableFileName,
    val fileType: String? = null
)