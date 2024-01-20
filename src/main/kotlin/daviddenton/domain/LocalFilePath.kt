package daviddenton.domain

import dev.forkhandles.values.StringValue
import dev.forkhandles.values.StringValueFactory

class LocalFilePath private constructor(value: String) : StringValue(value) {

    fun child(next: RemarkableFile) = when (ROOT) {
        this -> LocalFilePath.of(next.VissibleName.value)
        else -> LocalFilePath.of("$value/${next.VissibleName}")
    }

    companion object : StringValueFactory<LocalFilePath>(::LocalFilePath) {
        val ROOT = LocalFilePath.of("")
    }
}