import com.github.ajalt.clikt.core.main
import rebackupable.Rebackupable
import rebackupable.contents
import rebackupable.fake.FakeRemarkable

fun main() {
    Rebackupable(rawHttp = FakeRemarkable(contents)).main(listOf())
}