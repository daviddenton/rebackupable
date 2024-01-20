package rebackupable.domain

import dev.forkhandles.values.UUIDValue
import dev.forkhandles.values.UUIDValueFactory
import java.util.UUID

class RemarkableFileId private constructor(value: UUID) : UUIDValue(value) {
    companion object : UUIDValueFactory<RemarkableFileId>(::RemarkableFileId)
}
