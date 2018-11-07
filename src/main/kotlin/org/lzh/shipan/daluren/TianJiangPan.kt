package org.lzh.shipan.daluren

import com.beust.klaxon.Klaxon
import org.lzh.ganzhiwuxing.DiZhi
import org.lzh.ganzhiwuxing.TianGan

class TianJiang(val name: String) {
    private val numToName = arrayOf("贵", "蛇", "雀", "合", "勾", "龙", "空", "虎", "常", "玄", "阴", "后")
    val num: Int = numToName.indexOf(name) + 1
    val good = when (num) {
        1, 4, 6, 9, 11, 12 -> true
        else -> false
    }

    operator fun plus(other: Int): TianJiang {
        var tmp = (num + other + 12) % 12
        if (tmp == 0) tmp = 12
        return TianJiang(numToName[tmp - 1])
    }

    operator fun minus(other: TianJiang): Int {
        return num - other.num
    }

    override fun equals(other: Any?): Boolean {
        if (other !is TianJiang) return false
        return num == other.num
    }

    override fun toString(): String {
        return name
    }
}

class TianJiangPan(val tianPan: TianPan, val siKe: SiKe, val daytime: Boolean) {
    private val tianYiDiZhi = getTianYiDiZhi()//天乙所在的地支
    val inverse = isInverse() // 逆布为true
    private fun getTianYiDiZhi(): DiZhi {
        if (daytime) {
            return when (siKe.gan) {
                TianGan("甲") -> DiZhi("未")
                TianGan("乙") -> DiZhi("申")
                TianGan("丙") -> DiZhi("酉")
                TianGan("丁") -> DiZhi("亥")
                TianGan("戊") -> DiZhi("丑")
                TianGan("己") -> DiZhi("子")
                TianGan("庚") -> DiZhi("丑")
                TianGan("辛") -> DiZhi("寅")
                TianGan("壬") -> DiZhi("卯")
                else -> DiZhi("巳")
            }
        }
        return when (siKe.gan) {
            TianGan("甲") -> DiZhi("丑")
            TianGan("乙") -> DiZhi("子")
            TianGan("丙") -> DiZhi("亥")
            TianGan("丁") -> DiZhi("酉")
            TianGan("戊") -> DiZhi("未")
            TianGan("己") -> DiZhi("申")
            TianGan("庚") -> DiZhi("未")
            TianGan("辛") -> DiZhi("午")
            TianGan("壬") -> DiZhi("巳")
            else -> DiZhi("卯")
        }
    }

    private fun isInverse(): Boolean {
        val guiRenDiPan = DiZhi("子") + (tianYiDiZhi - tianPan.up(DiZhi("子"))) // 贵人地盘之支

        val si = DiZhi("巳")
        val xu = DiZhi("戌")
        if (guiRenDiPan - si >= 0 && xu - guiRenDiPan >= 0) return true
        return false
    }

    // 获取某地支的天将
    fun up(diZhi: DiZhi): TianJiang {
        if (inverse) return TianJiang("贵") + (tianYiDiZhi - diZhi)
        else return TianJiang("贵") + (diZhi - tianYiDiZhi)
    }

    fun down(tianJiang: TianJiang): DiZhi {
        if (inverse) return tianYiDiZhi + (TianJiang("贵") - tianJiang)
        else return tianYiDiZhi + (tianJiang - TianJiang("贵"))
    }

}
fun TianJiangPan.toJSON():String{
    val t = Array(12){
        up(DiZhi("子")+it).toString()
    }
    return  Klaxon().toJsonString(t.toList())
}