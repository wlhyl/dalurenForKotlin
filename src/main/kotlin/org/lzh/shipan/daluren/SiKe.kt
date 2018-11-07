package org.lzh.shipan.daluren
import com.beust.klaxon.Klaxon
import org.lzh.ganzhiwuxing.GanZhi

class SiKe(val tianPan: TianPan, val divinationDay: GanZhi) {
    val gan = divinationDay.gan
    val ganYang = tianPan.up(gan.jiGong)
    val ganYing = tianPan.up(ganYang)

    val zhi = divinationDay.zhi
    val zhiYang = tianPan.up(zhi)
    val zhiYing = tianPan.up(zhiYang)
    override fun toString(): String {
        return "${zhiYing} ${zhiYang} ${ganYing} ${ganYang}\n${zhiYang} ${zhi} ${ganYang} ${gan}"
    }
}

fun SiKe.toJSON():String{
val j = mapOf(
        "gan" to gan.toString(),
        "ganYang" to ganYang.toString(),
        "ganYing" to ganYing.toString(),
        "zhi" to zhi.toString(),
        "zhiYang" to zhiYang.toString(),
        "zhiYing" to zhiYing.toString())
    return  Klaxon().toJsonString(j)
}