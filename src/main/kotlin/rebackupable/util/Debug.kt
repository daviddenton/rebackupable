package rebackupable.util

import org.http4k.core.Filter
import org.http4k.core.NoOp
import org.http4k.core.then
import org.http4k.filter.DebuggingFilters
import java.util.concurrent.atomic.AtomicReference

fun Debug(verbose: AtomicReference<Boolean>) = Filter { next ->
    {
        (if (verbose.get()) DebuggingFilters.PrintRequestAndResponse() else Filter.NoOp).then(next)(it)
    }
}