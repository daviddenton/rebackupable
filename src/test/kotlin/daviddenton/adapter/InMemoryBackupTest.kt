package daviddenton.adapter

class InMemoryBackupTest : BackupContract {
    override val backup = InMemoryBackup()

    override fun getFile(path: String) = backup.allSaved()[path]
}
