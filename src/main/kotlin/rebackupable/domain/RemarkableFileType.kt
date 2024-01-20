package rebackupable.domain

import dev.forkhandles.values.NonBlankStringValueFactory
import dev.forkhandles.values.StringValue

class RemarkableFileType private constructor(value: String) : StringValue(value) {
    companion object : NonBlankStringValueFactory<RemarkableFileType>(::RemarkableFileType) {
        val CollectionType = RemarkableFileType.of("CollectionType")
        val DocumentType = RemarkableFileType.of("DocumentType")
    }
}
