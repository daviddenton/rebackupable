package rebackupable

import dev.forkhandles.values.of
import rebackupable.domain.RemarkableFileId
import rebackupable.fake.RemarkableFsEntry

private var index = 0L

val contents = listOf(
    RemarkableFsEntry.File(RemarkableFileId.of(index++, index++), "rootFile"),
    RemarkableFsEntry.Folder(
        RemarkableFileId.of(index++, index++), "childFolder",
        listOf(
            RemarkableFsEntry.File(RemarkableFileId.of(index++, index++), "childFile"),
            RemarkableFsEntry.Folder(
                RemarkableFileId.of(index++, index++), "grandchildFolder",
                listOf(
                    RemarkableFsEntry.File(RemarkableFileId.of(index++, index++), "grandchildFile")
                )
            )
        )
    )
)