package daviddenton.util

import daviddenton.util.Colour.black
import daviddenton.util.Colour.blue
import daviddenton.util.Colour.cyan
import daviddenton.util.Colour.green
import daviddenton.util.Colour.purple
import daviddenton.util.Colour.red
import daviddenton.util.Colour.white
import daviddenton.util.Colour.yellow
import daviddenton.util.Emphasis.bold
import daviddenton.util.Emphasis.normal
import daviddenton.util.Emphasis.reversed
import daviddenton.util.Emphasis.underlined

fun String.styledBoldText() = this.styled(bold, white)
fun String.styledHeader() = this.styled(underlined, cyan)
fun String.styledLink() = this.styled(underlined, cyan)
fun String.styledSuccess() = this.styled(normal, green)
fun String.styledError() = this.styled(normal, red)

private enum class Colour { black, red, green, yellow, blue, purple, cyan, white }
private enum class Emphasis { normal, bold, underlined, reversed }

private const val MARKER = "\u001b"
private const val RESET = "$MARKER[0m"

private fun String.styled(emphasis: Emphasis? = null, text: Colour? = null) =
    (emphasis?.let(::emphasisMarkerOf) ?: "") + (text?.let(::colourMarkerOf) ?: "") + this + RESET

private fun colourMarkerOf(colour: Colour) = when (colour) {
    black -> "$MARKER[30m"
    red -> "$MARKER[31m"
    green -> "$MARKER[32m"
    yellow -> "$MARKER[33m"
    blue -> "$MARKER[34m"
    purple -> "$MARKER[35m"
    cyan -> "$MARKER[36m"
    white -> "$MARKER[37m"
}

private fun emphasisMarkerOf(emphasis: Emphasis) = when (emphasis) {
    normal -> RESET
    bold -> "$MARKER[1m"
    underlined -> "$MARKER[4m"
    reversed -> "$MARKER[7m"
}

fun String.styledCommand() = "`$this`".styledBoldText()

object Messages {
    val MONOPOLIS = "Monopolis".styledBoldText()
    val LOGIN_MESSAGE = "Please login to the $MONOPOLIS CLI with ${"daviddenton login".styledCommand()}"
    val REPO_ARG = "Full VCS repository path in the format '${"org/repo".styledBoldText()}'"
}
