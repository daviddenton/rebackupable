package rebackupable

import com.github.ajalt.clikt.core.CliktCommand
import dev.forkhandles.result4k.Failure
import dev.forkhandles.result4k.Success
import okhttp3.OkHttpClient.Builder
import org.http4k.client.OkHttp
import org.http4k.config.Environment
import org.http4k.config.Environment.Companion.ENV
import org.http4k.config.Environment.Companion.JVM_PROPERTIES
import org.http4k.core.HttpHandler
import org.http4k.core.then
import rebackupable.Settings.HOME_DIR
import rebackupable.Settings.SERVER_URL
import rebackupable.adapter.HttpRemarkable
import rebackupable.adapter.SystemTerminal
import rebackupable.adapter.UserHomeDirBackup
import rebackupable.app.RebackupableHub
import rebackupable.util.Debug
import java.time.Clock.systemDefaultZone
import java.time.Duration
import java.util.concurrent.atomic.AtomicReference

fun Rebackupable(
    env: Environment = ENV overrides JVM_PROPERTIES,
    rawHttp: HttpHandler = OkHttp(
        Builder()
            .callTimeout(Duration.ofMinutes(1))
            .readTimeout(Duration.ofMinutes(1))
            .connectTimeout(Duration.ofSeconds(1))
            .followRedirects(false)
            .build()
    )
) = object : CliktCommand(name = "rebackupable") {
    val terminal = SystemTerminal()
    val debug = AtomicReference(false)
    val clock = systemDefaultZone()
    val backup = UserHomeDirBackup(HOME_DIR(env))
    val remarkable = HttpRemarkable(Debug(debug).then(rawHttp), SERVER_URL(env))
    val hub = RebackupableHub(clock, backup, remarkable, terminal)
    override fun run() {
        terminal("Backing up Remarkable\n")
        when (val result = hub.backup()) {
            is Success -> terminal("\nSuccessfully backed up ${result.value.count} files to ${result.value.location}\n")
            is Failure -> terminal("\nFailed to backup\n${result.reason.localizedMessage}\n")
        }
    }
}
