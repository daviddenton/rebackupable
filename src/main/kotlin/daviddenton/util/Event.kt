package daviddenton.util

import org.http4k.events.Event
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class Event(val message: String) : Event
