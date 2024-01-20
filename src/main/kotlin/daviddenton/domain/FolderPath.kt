package daviddenton.domain

import dev.forkhandles.values.StringValue
import dev.forkhandles.values.StringValueFactory

class FolderPath private constructor(value: String) : StringValue(value) {

    fun child(next: RemarkableFile) = when (ROOT) {
        this -> FolderPath.of(next.VissibleName.value)
        else -> FolderPath.of("$value/${next.VissibleName}")
    }

    companion object : StringValueFactory<FolderPath>(::FolderPath) {
        val ROOT = FolderPath.of("")
    }
}