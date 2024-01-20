package rebackupable.util

import org.http4k.core.Response
import rebackupable.port.Terminal

sealed class RebackupableResult(fn: (Terminal) -> Unit) : (Terminal) -> Unit by fn {

    class OK(vararg messages: String) : RebackupableResult({
        messages.forEach { message -> it.invoke(message) }
    })

    class Failed(vararg messages: String) : RebackupableResult({
        messages.forEach { message -> it.invoke(message) }
    })

    class RemoteError(operation: String, response: Response) : RebackupableResult({
        it.invoke(operation.styledBoldText() + " failed with ${response.status}, returned body:\n" + response.bodyString())
    })
}