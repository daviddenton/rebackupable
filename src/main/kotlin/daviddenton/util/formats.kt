package daviddenton.util

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi.Builder
import org.http4k.format.ConfigurableMoshi
import org.http4k.format.ListAdapter
import org.http4k.format.MapAdapter
import org.http4k.format.asConfigurable
import org.http4k.format.withStandardMappings
import se.ansman.kotshi.KotshiJsonAdapterFactory

object Json : ConfigurableMoshi(
    Builder()
        .add(RebackupableJsonAdapterFactory)
        .addLast(MapAdapter)
        .addLast(ListAdapter)
        .asConfigurable()
        .withStandardMappings()
        .done()
)

@KotshiJsonAdapterFactory
object RebackupableJsonAdapterFactory : JsonAdapter.Factory by RebackupableJsonAdapterFactory
