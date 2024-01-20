import daviddenton.Rebackupable
import daviddenton.contents
import daviddenton.fake.FakeRemarkable

fun main() {
    Rebackupable(rawHttp = FakeRemarkable(contents)).main(listOf("backup"))
}