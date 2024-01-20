package rebackupable.util

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi.Builder
import org.http4k.format.ConfigurableMoshi
import org.http4k.format.ListAdapter
import org.http4k.format.MapAdapter
import org.http4k.format.asConfigurable
import org.http4k.format.value
import org.http4k.format.withStandardMappings
import rebackupable.domain.RemarkableFileId
import rebackupable.domain.RemarkableFileName
import se.ansman.kotshi.KotshiJsonAdapterFactory

object Json : ConfigurableMoshi(
    Builder()
        .add(RebackupableJsonAdapterFactory)
        .addLast(MapAdapter)
        .addLast(ListAdapter)
        .asConfigurable()
        .withStandardMappings()
        .value(RemarkableFileId)
        .value(RemarkableFileName)
        .done()
)

@KotshiJsonAdapterFactory
object RebackupableJsonAdapterFactory : JsonAdapter.Factory by KotshiRebackupableJsonAdapterFactory
