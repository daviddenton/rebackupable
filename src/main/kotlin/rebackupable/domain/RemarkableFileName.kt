package rebackupable.domain

import dev.forkhandles.values.NonBlankStringValueFactory
import dev.forkhandles.values.StringValue

class RemarkableFileName private constructor(value: String) : StringValue(value) {
    companion object : NonBlankStringValueFactory<RemarkableFileName>(::RemarkableFileName)
}