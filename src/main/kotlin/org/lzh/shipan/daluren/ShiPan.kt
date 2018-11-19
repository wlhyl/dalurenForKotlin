package org.lzh.shipan.daluren

import com.beust.klaxon.*
import org.lzh.ganzhiwuxing.DiZhi
import org.lzh.ganzhiwuxing.GanZhi
import org.lzh.ganzhiwuxing.TianGan
import kotlin.reflect.full.declaredFunctions


class ShiPan(
        val year: Int,
        val month: Int,
        val day: Int,
        val hour: Int,
        val minutes: Int,
        val second: Int,
        val sunMansion: DiZhi,
        val divinationTime: DiZhi,//占测时间
        val daytime: Boolean,//昼占
        val yearOfBirth: Int,//出生年份
        val description: String = "", //占测的事
        val sex: Sex = Sex.MAN
) {
    val siZhu = getSiZhu(year, month, day, hour, minutes, second)
    val solarTerms = getSolarTerms(year, month, day, hour, minutes, second)
    val moonMansion = getMoonMansion(year, month, day, hour, minutes, second) //TODO getMoonMansion(year, month, day, hour, minutes, second)
    val divinationDay = siZhu.dayGanZhi
    val kongWang = getKongWang(divinationDay)
    val tianPan = TianPan(sunMansion, divinationTime)
    val siKe = SiKe(tianPan, divinationDay)
    val sanChuan = SanChuan(tianPan, siKe)
    val tianJiang = TianJiangPan(tianPan, siKe, daytime)
    val benMing = getSiZhu(yearOfBirth, 5, 20, 12, 0, 0).yearGanZhi
    val xingNian = getxingNian()
    val guaTi = getguaTi()

    private fun getxingNian(): GanZhi {
        if (sex == Sex.MAN) return GanZhi(TianGan("丙"), DiZhi("寅")) + (year - yearOfBirth)
        else return GanZhi(TianGan("壬"), DiZhi("申")) + (yearOfBirth - year)
    }
    private fun getguaTi(): Array<String>{
        val gt = sanChuan.guaTi.toMutableList()
        for (f in GuaTi::class.declaredFunctions) {
            f.annotations.forEach {
                if (it is ShenShaAndGuaTi) {
                    // call的返回值为Any?，
                    // 可以用it is String作自动转换
                    // 这里使用it.toString()转换，因为it的值是确定的
                    if(it.type=="guaTi") {
                         f.call(GuaTi, this)?.let{gt.add(it.toString())}
                    }
                }
            }
        }
        return gt.toTypedArray()
    }

}

private fun getKongWang(d: GanZhi): Array<DiZhi> {
    val gan = d.gan
    val zhi = d.zhi
    val jia = TianGan("甲")

    val delta = gan - jia

    val xunShou = zhi + (-1 * delta)

    return arrayOf(xunShou + (-2), xunShou + (-1))
}

fun ShiPan.toJSON(): String {
    val j = mapOf(
//        obj(
            "year" to year,
            "month" to month,
            "day" to day,
            "hour" to hour,
            "minutes" to minutes,
            "second" to second,
            "sunMansion" to sunMansion.toString(),
            "divinationTime" to divinationTime.toString(),
            "daytime" to daytime,
            "yearOfBirth" to yearOfBirth,//出生年份
            "description" to description, //占测的事
            "sex" to if (sex == Sex.MAN) "男" else "女",
//        "siZhu" to mapOf("yearGanZhi" to siZhu.yearGanZhi.toString(),
//                "monthGanZhi" to siZhu.monthGanZhi.toString(),
//                "dayGanZhi" to siZhu.dayGanZhi.toString(),
//                "hourGanZhi" to siZhu.hourGanZhi.toString()),
            "siZhu" to listOf(siZhu.yearGanZhi.toString(),
                    siZhu.monthGanZhi.toString(),
                    siZhu.dayGanZhi.toString(),
                    siZhu.hourGanZhi.toString()),
            "solarTerms" to solarTerms.toList(),
            "moonMansion" to moonMansion.toString(),
            "divinationDay" to divinationDay.toString(),
            "kongWang" to Array(kongWang.size) { kongWang[it].toString() }.toList(),
            "tianPan" to Parser().parse(StringBuilder(tianPan.toJSON())) as JsonArray<JsonObject>,
            "siKe" to Parser().parse(StringBuilder(siKe.toJSON())) as JsonObject,
            "sanChuan" to Parser().parse(StringBuilder(sanChuan.toJSON())) as JsonObject,
            "tianJiang" to Parser().parse(StringBuilder(tianJiang.toJSON())) as JsonArray<JsonObject>,
            "benMing" to benMing.toString(),
            "xingNian" to xingNian.toString(),
            "guaTi" to guaTi
    )//}
    return Klaxon().toJsonString(j)
//    return j.toJsonString()
}