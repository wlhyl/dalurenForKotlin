package org.lzh.shipan.daluren

object GuaTi{
    @ShenShaAndGuaTi("guaTi")
    fun 伏呤(sp: ShiPan): String? {
        val sk = sp.siKe
        val zhi = sk.zhi
        val zhiYang = sk.zhiYang
        if (zhi ==zhiYang) return "伏呤卦"
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

//    def do_龙德(sp):
//    __太岁 = sp.四柱与节气[0]
//    __月将 = sp.yueJiang
//    __三传 = sp.三传
//    sc = [__三传.初, __三传.中, __三传.末]
//    if sc[0] == __月将 and __太岁.支 in sc:
//    sp.setGuaTi("龙德卦")
}