package rebackupable.adapter

import rebackupable.port.Terminal

fun SystemTerminal() = object : Terminal {
    override fun invoke(string: String) = with(System.out) { print(string) }
}
