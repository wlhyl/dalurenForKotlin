package org.lzh.shipan.daluren

import org.lzh.ganzhiwuxing.DiZhi
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

        return ShenShaData("旬", "旬奇", __旬奇)
//    sp.sheSha["旬"].put("旬奇",__旬奇)

    }
}