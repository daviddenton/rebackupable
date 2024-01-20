package rebackupable

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import okhttp3.OkHttpClient
import org.http4k.client.OkHttp
import org.http4k.cloudnative.env.Environment
import org.http4k.cloudnative.env.Environment.Companion.ENV
import org.http4k.cloudnative.env.Environment.Companion.JVM_PROPERTIES
import org.http4k.core.HttpHandler
import org.http4k.core.then
import rebackupable.Settings.HOME_DIR
import rebackupable.Settings.SERVER_URL
import rebackupable.adapter.HttpRemarkable
import rebackupable.adapter.SystemTerminal
import rebackupable.adapter.UserHomeDirBackup
import rebackupable.app.RebackupableHub
import rebackupable.util.Debug
import java.time.Clock
import java.time.Duration
import java.util.concurrent.atomic.AtomicReference

fun Rebackupable(
    env: Environment = ENV overrides JVM_PROPERTIES,
    rawHttp: HttpHandler = OkHttp(
        OkHttpClient.Builder()
            .callTimeout(Duration.ofMinutes(1))
            .readTimeout(Duration.ofMinutes(1))
            .connectTimeout(Duration.ofMinutes(1))
            .followRedirects(false)
            .build()
    )
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

