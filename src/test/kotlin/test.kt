import org.lzh.ganzhiwuxing.DiZhi
import org.lzh.shipan.daluren.ShiPan
import org.lzh.shipan.daluren.toJSON

fun main(args: Array<String>) {
//    val t=LocalDateTime.of(2018,10,8,16,14,29)
//    println(t.plusHours(1))
//    println(t.monthValue)
    val s = ShiPan(
            2018, 10, 13, 17, 14, 40,
            DiZhi("辰"),
            DiZhi("巳"),
            true,
            2018)
//    val result = Klaxon().toJsonString(s)
    println(s.toJSON())
//    for(i in s.solarTerms)println(i)
//    for(i in s.kongWang)println(i)
//    println(s.siKe)
//    println(s.siKe.gan)
//    println(s.siKe.zhi)
//    println(s.siKe.ganYang)
//    println(s.siKe.ganYing)
//    println(s.siKe.zhiYang)
//    println(s.siKe.zhiYing)
//    println(s.sanChuan.chu)
//    println(s.sanChuan.zhong)
//    println(s.sanChuan.mo)
//    val t1=LocalDateTime.of(2018,10,8,16,14,40,268)
//println(t1.minusDays(1))
//    println(t.isBefore(t1))
}