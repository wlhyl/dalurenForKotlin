import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import org.lzh.ganzhiwuxing.DiZhi
import org.lzh.shipan.daluren.ShiPan
import org.lzh.shipan.daluren.toJSON

fun main(args: Array<String>) {
//    val t=LocalDateTime.of(2018,10,8,16,14,29)
//    println(t.plusHours(1))
//    println(t.monthValue)
    val s = ShiPan(
            2018, 4, 3, 22, 3, 53,
            DiZhi("戌"),
            DiZhi("酉"),
            false,
            2018)
//    val result = Klaxon().toJsonString(s)
//    s.sheSha.forEach { println(it) }
//    s.factors.distinct().forEach { println(it) }
}