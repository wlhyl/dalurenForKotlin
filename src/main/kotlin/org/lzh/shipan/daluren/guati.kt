package org.lzh.shipan.daluren

object GuaTi {
    @ShenShaAndGuaTi("guaTi")
    fun 伏呤(sp: ShiPan): String? {
        val sk = sp.siKe
        val zhi = sk.zhi
        val zhiYang = sk.zhiYang
        if (zhi == zhiYang) return "伏呤卦"
        return null
    }

    @ShenShaAndGuaTi("guaTi")
    fun 返呤(sp: ShiPan): String? {
        val sk = sp.siKe
        val zhi = sk.zhi
        val zhiYang = sk.zhiYang
        if ((zhi + 6) == zhiYang) return "返呤卦"
        return null
    }

    @ShenShaAndGuaTi("guaTi")
    fun 龙德(sp: ShiPan): String? {
        val __太岁 = sp.siZhu.yearGanZhi.zhi
        val __月将 = sp.sunMansion
        val __三传 = sp.sanChuan
        val sc = arrayOf(__三传.chu, __三传.zhong, __三传.mo)
        if (sc[0] == __月将 && __太岁 in sc) {
            sp.factors.add("发用为月将")
            sp.factors.add("太岁入传")
            return "龙德卦"
        }
        return null
    }

    @ShenShaAndGuaTi("guaTi")
    fun 三光(sp: ShiPan): String? {
        // 2015 - 08-12 巳将卯时
        val __月建 = sp.siZhu.monthGanZhi.zhi
        val __三传 = sp.sanChuan
        val __初 = __三传.chu
        val __中 = __三传.zhong
        val __末 = __三传.mo
        when (__初.wuXing.wangShuai(__月建)) {
            "旺" -> sp.factors.add("用旺")
            "相" -> sp.factors.add("用相")
            else -> return null
        }

        val sk = sp.siKe
        val __干 = sk.gan
        val __支 = sk.zhi
        when (__干.wuXing.wangShuai(__月建)) {
            "旺" -> sp.factors.add("日干旺")
            "相" -> sp.factors.add("日干相")
            else -> return null
        }
        when (__支.wuXing.wangShuai(__月建)) {
            "旺" -> sp.factors.add("日支旺")
            "相" -> sp.factors.add("日支相")
            else -> return null
        }

        val tj = sp.tianJiang
        val c = tj.up(__初)
        val z = tj.up(__中)
        val m = tj.up(__末)
        if (c.good or z.good or m.good) {
            sp.factors.add("吉将入传")
            return "三光卦"
        }
        return null
    }

}