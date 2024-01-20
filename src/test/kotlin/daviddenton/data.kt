package daviddenton

import daviddenton.domain.RemarkableFileId
import daviddenton.fake.RemarkableFsEntry
import dev.forkhandles.values.random

val contents = listOf(
    RemarkableFsEntry.File(RemarkableFileId.random(), "rootFile"),
    RemarkableFsEntry.Folder(
        RemarkableFileId.random(), "childFolder",
        listOf(
            RemarkableFsEntry.File(RemarkableFileId.random(), "childFile"),
            RemarkableFsEntry.Folder(
                RemarkableFileId.random(), "grandchildFolder",
                listOf(
                    RemarkableFsEntry.File(RemarkableFileId.random(), "grandchildFile")
                )
            )

        )
    )
)