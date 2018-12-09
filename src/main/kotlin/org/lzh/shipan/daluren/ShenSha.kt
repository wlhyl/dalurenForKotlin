package org.lzh.shipan.daluren

import org.lzh.ganzhiwuxing.DiZhi
import org.lzh.ganzhiwuxing.TianGan

object ShenSha {

    @ShenShaAndGuaTi("shenSha")
    fun 旬奇(sp: ShiPan): ShenShaData {
        val __kw = sp.kongWang
        val __xs = __kw[1] + 1
        val __旬奇 =
                when (__xs) {
                    DiZhi("戌"), DiZhi("子") -> DiZhi("丑")
                    DiZhi("申"), DiZhi("午") -> DiZhi("子")
                    else -> DiZhi("亥")
                }

        return ShenShaData("日", "旬奇", __旬奇)
//    sp.sheSha["旬"].put("旬奇",__旬奇)

    }

    @ShenShaAndGuaTi("shenSha")
    fun 日奇(sp: ShiPan): ShenShaData {
        val sk = sp.siKe
        val g = sk.gan
        val d = g - TianGan("甲")
        val 日奇 = if (d <= 5) DiZhi("午") + (-1 * d)
        else DiZhi("未") + (g - TianGan("庚"))
        return ShenShaData("日", "日奇", 日奇)
    }

    @ShenShaAndGuaTi("shenSha")
    fun 旬仪(sp: ShiPan): ShenShaData {
        val kw = sp.kongWang
        val xs = kw[1] + 1
        return ShenShaData("日", "旬仪", xs)
    }

    @ShenShaAndGuaTi("shenSha")
    fun 支仪(sp: ShiPan): ShenShaData {
        val sk = sp.siKe
        val __g = sk.zhi
        val d = __g - DiZhi("子")
        val 支仪 = if (d <= 5) DiZhi("午") + (-1 * d)
        else DiZhi("未") + (__g - DiZhi("午"))
        return ShenShaData("日", "支仪", 支仪)
    }

}