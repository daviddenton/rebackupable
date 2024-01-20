package daviddenton.adapter

import daviddenton.port.Terminal

fun SystemTerminal() = object : Terminal {
    override fun invoke(string: String) = with(System.out) { print(string) }
}
