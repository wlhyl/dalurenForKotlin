package org.lzh.shipan.daluren

import com.beust.klaxon.json
import org.lzh.ganzhiwuxing.DiZhi
import org.lzh.ganzhiwuxing.TianGan

class SanChuan(val tianPan: TianPan, val siKe: SiKe) {
    private val guaTiList: MutableList<String> = mutableListOf()
    private val sanChuan = getSanChuan()
    val chu = sanChuan[0]
    val zhong = sanChuan[1]
    val mo = sanChuan[2]
    val dunGan = getDunTianGan()
    val guaTi = guaTiList.toTypedArray()
    val liuQing = get六亲()

    private fun getSanChuan(): Array<DiZhi> {
        if (siKe.zhiYang == siKe.zhi) return 伏呤()
        if (siKe.zhiYang.liuChong(siKe.zhi)) return 返呤()
//        var sc: Array<DiZhi>? = null
//        var sc = 贼克()
        贼克()?.let { return it }
//        if (sc !== null) return sc
//        sc?.let{return it}
        遥克()?.let { return it }
        昂星()?.let { return it }
        别责()?.let { return it }
        八专()?.let { return it }
        return emptyArray()// 这行实际永远不会执行
    }

    private fun getShangShen(it: Int): DiZhi {
        return when (it) {
            1 -> siKe.ganYang
            2 -> siKe.ganYing
            3 -> siKe.zhiYang
            else -> siKe.zhiYing
        }
    }

    private fun 有贼(): Array<Int>? {
        val keList: MutableList<Int> = mutableListOf()

        if (siKe.gan.wuXing.ke(siKe.ganYang.wuXing)) keList.add(1)
        if (siKe.ganYang.ke(siKe.zhiYing)) keList.add(2)
        if (siKe.zhi.ke(siKe.zhiYang)) keList.add(3)
        if (siKe.zhiYang.ke(siKe.zhiYing)) keList.add(4)


        if (keList.size == 0) return null //return keList.toTypedArray()

        // 删除重复课
        val shangShenList: MutableList<DiZhi> = mutableListOf()// = Array(keList.size) { i: Int -> getShangShen(keList[i]) }.distinct()
        val keListTmp: MutableList<Int> = mutableListOf()
        keList.forEach {
            if (getShangShen(it) !in shangShenList) {
                shangShenList.add(getShangShen(it))
                keListTmp.add(it)
            }
        }
//        for (i in keList) if (getShangShen(i) in shangShenList) keListTmp.add(i)
        return keListTmp.toTypedArray()

    }

    private fun 有克(): Array<Int>? {
        val keList: MutableList<Int> = mutableListOf()

        if (siKe.ganYang.wuXing.ke(siKe.gan.wuXing)) keList.add(1)
        if (siKe.ganYing.ke(siKe.ganYang)) keList.add(2)
        if (siKe.zhiYang.ke(siKe.zhi)) keList.add(3)
        if (siKe.zhiYing.ke(siKe.zhiYang)) keList.add(4)

        if (keList.size == 0) return null//keList.toTypedArray()
        //    删除重复课
        val shangShenList: MutableList<DiZhi> = mutableListOf()// = Array(keList.size) { i: Int -> getShangShen(keList[i]) }.distinct()
        val keListTmp: MutableList<Int> = mutableListOf()
        keList.forEach {
            if (getShangShen(it) !in shangShenList) {
                shangShenList.add(getShangShen(it))
                keListTmp.add(it)
            }
        }
//        for (i in keList) if (getShangShen(i) in shangShenList) keListTmp.add(i)
        return keListTmp.toTypedArray()
    }

    private fun 贼克(): Array<DiZhi>? {
        val 贼 = 有贼()
        val 克 = 有克()

//        if (贼.isEmpty() && 克.isEmpty())return emptyArray()
//        if (贼 == null && 克 == null) return null
        if (贼?.size ?: 0 == 1) {
            val __贼 = 贼 as Array<Int>
            val __初 = getShangShen(__贼[0])
            val __中 = tianPan.up(__初)
            val __末 = tianPan.up(__中)
            guaTiList.add("重审卦")
            return arrayOf(__初, __中, __末)
        }

        if (贼?.size ?: 0 > 1) return 比用(贼 as Array<Int>)

        if (克?.size ?: 0 == 1) {
            val __克 = 克 as Array<Int>
            val __初 = getShangShen(__克[0])
            val __中 = tianPan.up(__初)
            val __末 = tianPan.up(__中)
            guaTiList.add("元首卦")
            return arrayOf(__初, __中, __末)
        }
        if (贼?.size ?: 0 > 1) return 比用(克 as Array<Int>)
        return null
    }

    private fun 比用(keList: Array<Int>): Array<DiZhi> {
        val result: MutableList<Int> = mutableListOf()
        keList.forEach {
            if (getShangShen(it).yang == siKe.gan.yang) result.add(it)
        }
        if (result.size == 1) {
            val __初 = getShangShen(result[0])
            val __中 = tianPan.up(__初)
            val __末 = tianPan.up(__中)
            guaTiList.add("知一卦")
            return arrayOf(__初, __中, __末)
        }
        if (result.isEmpty()) return 涉害(keList) //俱不比
        return 涉害(result.toTypedArray())  //多个俱比
    }

    private fun 涉害(keList: Array<Int>): Array<DiZhi> {

        /*
        格式
        [
            [[干阳神, 干], 2],
        ]
        */
        val is贼 = { it: Int ->
            when (it) {
                1 -> siKe.gan.wuXing.ke(siKe.ganYang.wuXing)
                2 -> siKe.ganYang.ke(siKe.ganYing)
                3 -> siKe.zhi.ke(siKe.zhiYang)
                else -> siKe.zhiYang.ke(siKe.zhiYing)
            }
        }
        val __课的涉害深度: MutableList<Array<Int>> = mutableListOf()

        keList.forEach {
            val __ke = getShangShen(it)
            val 临地盘 = tianPan.down(getShangShen(it))
            var count = 0
            for (i in 0 until 12) {
                val __d = 临地盘 + i
                if (__d == getShangShen(it)) break
                val ganList = __d.jiGongGan
                if (is贼(it)) { //贼
                    if (__d.wuXing.ke(__ke.wuXing)) {
                        count++
                        ganList.forEach {
                            if (it.wuXing.ke(__ke.wuXing)) count++
                        }
                    }
                } else {
                    if (__ke.wuXing.ke(__d.wuXing)) {
                        count++
                        ganList.forEach {
                            if (__ke.wuXing.ke(it.wuXing)) count++
                        }
                    }
                }
                __课的涉害深度.add(arrayOf(it, count))
            }
        }
//        var __最大涉害深度 = 0
        val __最大涉害深度 = Array<Int>(__课的涉害深度.size) { it: Int -> __课的涉害深度[it][1] }.max()
        val __有最大涉害深度的支组: MutableList<Int> = mutableListOf()

        __课的涉害深度.forEach {
            if (it[1] == __最大涉害深度) __有最大涉害深度的支组.add(it[0])
        }


        if (__有最大涉害深度的支组.size == 1) {
            val __初 = getShangShen(__有最大涉害深度的支组[0])
            val __中 = tianPan.up(__初)
            val __末 = tianPan.up(__中)
            guaTiList.add("涉害卦")
            return arrayOf(__初, __中, __末)
        }

        // 涉害深度相同
        __有最大涉害深度的支组.forEach {
            val 临地盘 = tianPan.down(getShangShen(it))
            // 从孟发用
            //if 临地盘 in [支("寅"), 支("巳"), 支("申"), 支("亥")]:
            if ((临地盘 - DiZhi("寅")) % 3 == 0) {
                val __初 = getShangShen(it)
                val __中 = tianPan.up(__初)
                val __末 = tianPan.up(__中)
                guaTiList.add("见机卦")
                return arrayOf(__初, __中, __末)
            }
        }


        // 从仲发用
        __有最大涉害深度的支组.forEach {
            val 临地盘 = tianPan.down(getShangShen(it))
            if ((临地盘 - DiZhi("子")) % 3 == 0) {
                val __初 = getShangShen(it)
                val __中 = tianPan.up(__初)
                val __末 = tianPan.up(__中)
                guaTiList.add("察微卦")
                return arrayOf(__初, __中, __末)
            }
        }

        if (siKe.gan.yang) {
            val __初 = siKe.ganYang
            val __中 = tianPan.up(__初)
            val __末 = tianPan.up(__中)
            guaTiList.add("复等卦")
            return arrayOf(__初, __中, __末)
        } else {
            val __初 = siKe.zhiYang
            val __中 = tianPan.up(__初)
            val __末 = tianPan.up(__中)
            guaTiList.add("复等卦")
            return arrayOf(__初, __中, __末)
        }

        // 俱是季
//        raise NoSanchuan ('所临皆四季，不能用涉害取三传')
    }

    private fun 遥克(): Array<DiZhi>? {

        if (siKe.divinationDay.isBaZhuan()) return null
        val ke: MutableList<Int> = mutableListOf()
        if (siKe.ganYing.wuXing.ke(siKe.gan.wuXing)) ke.add(2)
        if (siKe.zhiYang.wuXing.ke(siKe.gan.wuXing)) ke.add(3)
        if (siKe.zhiYing.wuXing.ke(siKe.gan.wuXing)) ke.add(4)

        if (ke.isEmpty()) {
            if (siKe.gan.wuXing.ke(siKe.ganYing.wuXing)) ke.add(2)
            if (siKe.gan.wuXing.ke(siKe.zhiYang.wuXing)) ke.add(3)
            if (siKe.gan.wuXing.ke(siKe.zhiYing.wuXing)) ke.add(4)
        }
        if (ke.isEmpty()) return null

        // 删除重复课
        val keDiZhiList = Array(ke.size) { it: Int -> getShangShen(it) }.distinct()
        if (keDiZhiList.size == 1) {
            val __初 = keDiZhiList[0]
            val __中 = tianPan.up(__初)
            val __末 = tianPan.up(__中)
            guaTiList.add("遥克卦")
            return arrayOf(__初, __中, __末)
        } else {
            guaTiList.add("遥克卦")
            return 比用(ke.toTypedArray())
        }
    }

    private fun 昂星(): Array<DiZhi>? {
        // 课不备，不能用昂星取三传
        if (arrayOf(siKe.ganYang, siKe.ganYing, siKe.zhiYang, siKe.zhiYing).distinct().size != 4) return null

        if (siKe.gan.yang) {
            val chu = tianPan.up(DiZhi("酉"))
            val zhong = siKe.zhiYang
            val mo = siKe.ganYang
            guaTiList.add("虎视卦")
            return arrayOf(chu, zhong, mo)
        } else {
            val chu = tianPan.down(DiZhi("酉"))
            val zhong = siKe.ganYang
            val mo = siKe.zhiYang
            guaTiList.add("冬蛇掩目")
            return arrayOf(chu, zhong, mo)
        }
    }

    private fun 别责(): Array<DiZhi>? {
        val ke = arrayOf(siKe.ganYang, siKe.ganYing, siKe.zhiYang, siKe.zhiYing).distinct()
        // 四课全备，不能用别责取三传
        if (ke.size == 4) return null
        // 用别责用于三课备取三传
        if (ke.size != 3) return null
        val chu = if (siKe.gan.yang) tianPan.up((siKe.gan + 5).jiGong) else siKe.zhi + 4
        val zhong = siKe.ganYang
        val mo = zhong
        guaTiList.add("别责卦")
        return arrayOf(chu, zhong, mo)
    }

    private fun 八专(): Array<DiZhi>? {
        // 不是八传日
        if (!siKe.divinationDay.isBaZhuan()) return null

        if (siKe.gan.yang) {
            val chu = siKe.ganYang + 2
            guaTiList.add("八专卦")
            return arrayOf(chu, siKe.ganYang, siKe.ganYang)
        } else {
            val chu = siKe.zhiYing + (-2)
            guaTiList.add("八专卦")
            return arrayOf(chu, siKe.ganYang, siKe.ganYang)
        }
    }

    private fun 伏呤(): Array<DiZhi> {
        val chu =
        // 六乙、六癸日
                if (siKe.gan in arrayOf(TianGan("乙"), TianGan("癸")) || siKe.gan.yang) {
                    siKe.ganYang
                } else {
                    // 阴日，非六乙日、六癸
                    siKe.zhiYang
                }
        lateinit var zhong: DiZhi
        for (i in 0..11) {
            val d = chu + i
            if (chu.xing(d)) {
                zhong = d
                break
            }
        }

        //初为自刑，阳日、六乙日、六癸日取支上神为中传
        if (chu == zhong) {
            if (siKe.gan in arrayOf(TianGan("乙"), TianGan("癸")) || siKe.gan.yang) {
                zhong = siKe.zhiYang
            } else {
                zhong = siKe.ganYang
            }
        }

        lateinit var mo: DiZhi
        for (i in 0..11) {
            val d = chu + i
            if (zhong.xing(d)) {
                mo = d
                break
            }
        }
        // 中传自刑，取中所冲之神
        if (zhong == mo) mo = zhong + 6
        // 初、中互刑，如：子、卯，末取中所冲之神
        if (zhong.xing(chu)) mo = zhong + 6
        if (chu.xing(chu)) {
            guaTiList.add("杜传卦")
        } else {
            if (siKe.gan.yang) guaTiList.add("自任卦")
            else guaTiList.add("自信卦")
        }
        return arrayOf(chu, zhong, mo)
    }

    private fun 返呤(): Array<DiZhi> {
        val sc = 贼克()
        sc?.let { return it }
        // 驿马计算
        var zhi = siKe.zhi
        lateinit var yiMa: DiZhi
        for (i in 0 until 3) {
            if ((zhi - DiZhi("寅")) % 3 == 0) {
                yiMa = zhi + 6
                break
            }
            zhi += 4
        }
        val chu = yiMa
        val zhong = siKe.zhiYang
        val mo = siKe.ganYang
        guaTiList.add("无依卦")
        return arrayOf(chu, zhong, mo)
    }

    private fun getDunTianGan(): Array<TianGan?> {
        val d: MutableList<TianGan?> = mutableListOf()
        val gan = siKe.gan
        val zhi = siKe.zhi
        val jia = TianGan("甲")

        val delta = gan - jia

        val xunShou = zhi + (-1 * delta)

        for (i in arrayOf(chu, zhong, mo)) {
            val zhiDelta = (i - xunShou + 12) % 12
            if (zhiDelta == 10 || zhiDelta == 11) d.add(null)
            else d.add(jia + zhiDelta)
        }
        return d.toTypedArray()
    }

    private fun get六亲(): Array<String> {
        val luQing: MutableList<String> = mutableListOf()
        val gan = siKe.gan
        val sc = arrayOf(chu, zhong, mo)
        sc.forEach {
            when {
                gan.wuXing.ke(it.wuXing) -> luQing.add("财")
                it.wuXing.ke(gan.wuXing) -> luQing.add("官")
                gan.wuXing.sheng(it.wuXing) -> luQing.add("子")
                it.wuXing.sheng(gan.wuXing) -> luQing.add("父")
                else -> luQing.add("兄")
            }
        }
        return luQing.toTypedArray()
    }
}

fun SanChuan.toJSON(): String {
    val sc = listOf(chu.toString(), zhong.toString(), mo.toString())
    val dg = Array(dunGan.size) { dunGan[it].toString() }.toList()
    val lq = liuQing.toList()
    val j = json {
        obj("sanChuan" to array(sc),
                "dunGan" to array(dg),
                "liuQing" to array(lq)
        )
    }
    return j.toJsonString()
}