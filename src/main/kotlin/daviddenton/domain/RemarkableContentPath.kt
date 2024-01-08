package daviddenton.domain

import dev.forkhandles.values.StringValue
import dev.forkhandles.values.StringValueFactory

class RemarkableContentPath private constructor(value: String) : StringValue(value) {
    val root get() = RemarkableFileId.parse(value.substringBefore('/'))
    val isFile get() = value.count { it == '/' }
    fun child(next: RemarkableFileId) = RemarkableContentPath.of("$value/$next")

    companion object : StringValueFactory<RemarkableContentPath>(::RemarkableContentPath) {
        val ROOT = RemarkableContentPath.of("")
    }
}
