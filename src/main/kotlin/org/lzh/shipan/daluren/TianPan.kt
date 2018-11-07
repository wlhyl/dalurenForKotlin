package org.lzh.shipan.daluren

import com.beust.klaxon.Klaxon
import org.lzh.ganzhiwuxing.DiZhi

class TianPan(val sunMansion: DiZhi, val divinationTime: DiZhi) {
    //    用于取得支上神
    fun up(diZhi: DiZhi): DiZhi = sunMansion + (diZhi - divinationTime)

    //    地支所临的地盘支
    fun down(diZhi: DiZhi): DiZhi = divinationTime + (diZhi - sunMansion)
}

fun TianPan.toJSON():String{
    val t = Array(12){
        up(DiZhi("子")+it).toString()
    }
    return  Klaxon().toJsonString(t.toList())
}