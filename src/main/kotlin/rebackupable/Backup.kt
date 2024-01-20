package rebackupable

import com.github.ajalt.clikt.core.CliktCommand
import dev.forkhandles.result4k.Failure
import dev.forkhandles.result4k.Success
import rebackupable.app.RebackupableHub
import rebackupable.port.Terminal

fun Backup(hub: RebackupableHub, terminal: Terminal) = object : CliktCommand(
    name = "backup",
    help = "Generate backup of all files from your Remarkable."
) {
    override fun run() {
        terminal("Backing up Remarkable\n")
        when (val result = hub.backup()) {
            is Success -> terminal("\nSuccessfully backed up ${result.value.count} files to ${result.value.location}")
            is Failure -> terminal(result.reason.stackTraceToString())
        }
    }
}

