package daviddenton

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import daviddenton.Settings.HOME_DIR
import daviddenton.Settings.SERVER_URL
import daviddenton.adapter.HttpRemarkable
import daviddenton.adapter.SystemTerminal
import daviddenton.adapter.UserHomeDirBackup
import daviddenton.app.RebackupableHub
import daviddenton.util.Debug
import org.http4k.client.JavaHttpClient
import org.http4k.cloudnative.env.Environment
import org.http4k.cloudnative.env.Environment.Companion.ENV
import org.http4k.cloudnative.env.Environment.Companion.JVM_PROPERTIES
import org.http4k.core.HttpHandler
import org.http4k.core.then
import java.time.Clock
import java.util.concurrent.atomic.AtomicReference

fun Rebackupable(
    env: Environment = ENV overrides JVM_PROPERTIES,
    rawHttp: HttpHandler = JavaHttpClient()
): CliktCommand {
    val terminal = SystemTerminal()
    val debug = AtomicReference(false)
    val clock = Clock.systemDefaultZone()
    val backup = UserHomeDirBackup(HOME_DIR(env))
    val remarkable = HttpRemarkable(Debug(debug).then(rawHttp), SERVER_URL(env))
    val hub = RebackupableHub(clock, backup, remarkable, terminal)

    return object : CliktCommand(name = "rebackupable") {
        val verbose by option(help = "Set verbose mode.").flag()

        override fun run() = debug.set(verbose)
    }.subcommands(
        Backup(hub, terminal)
    )
}

