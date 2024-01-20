package rebackupable

import org.http4k.cloudnative.env.EnvironmentKey
import org.http4k.core.Uri
import org.http4k.lens.of
import org.http4k.lens.uri
import java.io.File

object Settings {
    val HOME_DIR = EnvironmentKey.map(::File, File::getPath)
        .defaulted(
            "HOME",
            EnvironmentKey.map(::File).defaulted(
                "USERPROFILE",
                EnvironmentKey.map(::File).required("user.home")
            )
        )

    val SERVER_URL by EnvironmentKey.uri().of().defaulted(Uri.of("http://10.11.99.1"))
}
