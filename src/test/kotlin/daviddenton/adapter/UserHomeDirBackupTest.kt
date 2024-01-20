package daviddenton.adapter

import org.junit.jupiter.api.AfterEach
import java.io.File
import java.nio.file.Files

class UserHomeDirBackupTest : BackupContract {

    private val home = Files.createTempDirectory("UserHomeDirBackupTest").toFile()

    override val backup = UserHomeDirBackup(home)

    override fun getFile(path: String) = File(File(home, "Rebackupable"), path).readText()

    @AfterEach
    fun removeTempDir() {
        home.deleteRecursively()
    }
}