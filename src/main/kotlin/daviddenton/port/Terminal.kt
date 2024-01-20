package daviddenton.port

fun interface Terminal {
    operator fun invoke(string: String)
}
