package daviddenton

import daviddenton.domain.RemarkableFileId
import daviddenton.fake.RemarkableFsEntry
import dev.forkhandles.values.of

private var index = 0L

val contents = listOf(
    RemarkableFsEntry.File(RemarkableFileId.of(index++, index++), "rootFile", "pdf"),
    RemarkableFsEntry.Folder(
        RemarkableFileId.of(index++, index++), "childFolder",
        listOf(
            RemarkableFsEntry.File(RemarkableFileId.of(index++, index++), "childFile", "pdf"),
            RemarkableFsEntry.Folder(
                RemarkableFileId.of(index++, index++), "grandchildFolder",
                listOf(
                    RemarkableFsEntry.File(RemarkableFileId.of(index++, index++), "grandchildFile", "pdf")
                )
            )
        )
    )
)