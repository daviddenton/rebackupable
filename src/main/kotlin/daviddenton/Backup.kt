package daviddenton

import com.github.ajalt.clikt.core.CliktCommand
import daviddenton.app.RebackupableHub
import daviddenton.port.Terminal
import dev.forkhandles.result4k.Failure
import dev.forkhandles.result4k.Success

fun Backup(
    hub: RebackupableHub, terminal: Terminal
) = object : CliktCommand(
    name = "backup",
    help = "Generate backup of all files from your Remarkable."
) {
    override fun run() {
        terminal("Backing up Remarkable\n")
        when (val result = hub.backup()) {
            is Success -> terminal("Successfully backed up ${result.value.count} files to ${result.value.dir.absolutePath}")
            is Failure -> terminal(result.reason.stackTraceToString())
        }
    }
}

