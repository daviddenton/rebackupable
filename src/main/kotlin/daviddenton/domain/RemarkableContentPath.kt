package daviddenton.domain

import dev.forkhandles.values.StringValue
import dev.forkhandles.values.StringValueFactory

class RemarkableContentPath private constructor(value: String) : StringValue(value) {
    val root get() = RemarkableFileId.parse(value.substringBefore('/'))

    fun dropRoot() = when {
        value.contains('/') -> RemarkableContentPath.of(value.substringAfter('/'))
        else -> ROOT
    }

    fun child(next: RemarkableFileId) = when (ROOT) {
        this -> RemarkableContentPath.of(next.value.toString())
        else -> RemarkableContentPath.of("$value/$next")
    }

    companion object : StringValueFactory<RemarkableContentPath>(::RemarkableContentPath) {
        val ROOT = RemarkableContentPath.of("")
    }
}
