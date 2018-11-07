package org.lzh.shipan.daluren

import java.time.LocalDateTime
import org.lzh.calendar.SolarToLunar
import org.lzh.calendar.solarTermInfo
import org.lzh.lunar.GetMoonEclipticLongitudeEC
import org.lzh.util.GetJulianDayFromDateTime
import org.lzh.ganzhiwuxing.DiZhi
import org.lzh.ganzhiwuxing.GanZhi
import org.lzh.ganzhiwuxing.TianGan
import org.lzh.lunar.GetEarthEclipticLongitudeForSun

fun getSiZhu(year: Int, month: Int, day: Int, hour: Int, minutes: Int, second: Int): SiZHu {
    val t = LocalDateTime.of(year, month, day, hour, minutes, second)
    val lunarDayInfo = SolarToLunar(year, month, day)
    val yGanZhi = lunarDayInfo.GanZhiYear
    var mGanZhi = lunarDayInfo.GanZhiMonth
    val dGanZhi = lunarDayInfo.GanZhiDay
//    SolarToLunar方法的月份干支是以00:00计算的，
//    因此，如果还没到交节时间，仍使用前一月干支
    if (t.isBefore(lunarDayInfo.SolarTermFist.t)) mGanZhi = mGanZhi + (-1)
    val shiChen = getShiChen(hour)
    val zhiShiGan = when (lunarDayInfo.GanZhiDay.gan) {//计算子时天干
        TianGan("甲"), TianGan("己") -> TianGan("甲")
        TianGan("乙"), TianGan("庚") -> TianGan("丙")
        TianGan("丙"), TianGan("辛") -> TianGan("戊")
        TianGan("丁"), TianGan("壬") -> TianGan("庚")
        else -> TianGan("壬")
//        TianGan("戊") ,TianGan("癸")->TianGan("壬")

    }
    val shiZhu = GanZhi(zhiShiGan + (shiChen - DiZhi("子")), shiChen)
    return SiZHu(
            yGanZhi,
            mGanZhi,
            if (shiChen == DiZhi("子")) dGanZhi + 1 else dGanZhi,
            shiZhu
    )

}

fun getSolarTerms(year: Int, month: Int, day: Int, hour: Int, minutes: Int, second: Int): Array<solarTermInfo> {
    val t = LocalDateTime.of(year, month, day, hour, minutes, second)
    var lunarDayInfo = SolarToLunar(year, month, day)
//    SolarToLunar方法的月份干支是以00:00计算的，
//    因此，如果还没到交节时间，仍使用前一月干支
    if (t.isBefore(lunarDayInfo.SolarTermFist.t)) {
        val t0 = t.minusDays(1)
        lunarDayInfo = SolarToLunar(t0.year, t0.monthValue, t0.dayOfMonth)
    }

    return arrayOf(lunarDayInfo.SolarTermFist, lunarDayInfo.SolarTermSecond)

}

fun getSunMansion(year: Int, month: Int, day: Int, hour: Int, minutes: Int, second: Int): DiZhi {
//    将北京时间转换成UTC时间
    val t = LocalDateTime.of(year, month, day, hour, minutes, second).minusHours(8)
    val jd = GetJulianDayFromDateTime(t)
    var longitude = GetEarthEclipticLongitudeForSun(jd)
    longitude = Math.toDegrees(longitude)
    val DEGREES = 30
    when {
        longitude < DEGREES -> return DiZhi("戌")
        longitude < DEGREES * 2 -> return DiZhi("酉")
        longitude < DEGREES * 3 -> return DiZhi("申")
        longitude < DEGREES * 4 -> return DiZhi("未")
        longitude < DEGREES * 5 -> return DiZhi("午")
        longitude < DEGREES * 6 -> return DiZhi("巳")
        longitude < DEGREES * 7 -> return DiZhi("辰")
        longitude < DEGREES * 8 -> return DiZhi("卯")
        longitude < DEGREES * 9 -> return DiZhi("寅")
        longitude < DEGREES * 10 -> return DiZhi("丑")
        longitude < DEGREES * 11 -> return DiZhi("子")
        else -> return DiZhi("亥")
    }
}

fun getMoonMansion(year: Int, month: Int, day: Int, hour: Int, minutes: Int, second: Int): DiZhi {
//    将北京时间转换成UTC时间
    val t = LocalDateTime.of(year, month, day, hour, minutes, second).minusHours(8)
//    t -= datetime.timedelta(hours=8)
    val jd = GetJulianDayFromDateTime(t)
    var longitude = GetMoonEclipticLongitudeEC(jd)
    longitude = Math.toDegrees(longitude)
    val DEGREES = 30
    when {
        longitude < DEGREES -> return DiZhi("戌")
        longitude < DEGREES * 2 -> return DiZhi("酉")
        longitude < DEGREES * 3 -> return DiZhi("申")
        longitude < DEGREES * 4 -> return DiZhi("未")
        longitude < DEGREES * 5 -> return DiZhi("午")
        longitude < DEGREES * 6 -> return DiZhi("巳")
        longitude < DEGREES * 7 -> return DiZhi("辰")
        longitude < DEGREES * 8 -> return DiZhi("卯")
        longitude < DEGREES * 9 -> return DiZhi("寅")
        longitude < DEGREES * 10 -> return DiZhi("丑")
        longitude < DEGREES * 11 -> return DiZhi("子")
        else -> return DiZhi("亥")
    }
}

private fun getShiChen(h: Int): DiZhi {
//    int((h + 1) / 2) + 1 可以得到时辰数
//    当h = 23，得到13, 即第二日子时
    val s = (h + 1) / 2 + 1
//    if (s == 13)s = 1
    return DiZhi("亥") + s//不必再将13换算为1，亥加上13即为子
}

//fun TianGan.jiGoang():DiZhi {
////    return when(it){
////        TianGan("甲")-> DiZhi("寅")
////        TianGan("乙")-> DiZhi("辰")
////        TianGan("丙")-> DiZhi("巳")
////        TianGan("丁")-> DiZhi("未")
////        TianGan("戊")-> DiZhi("巳")
////        TianGan("己")-> DiZhi("未")
////        TianGan("庚")-> DiZhi("申")
////        TianGan("辛")-> DiZhi("戌")
////        TianGan("壬")-> DiZhi("亥")
////        else-> DiZhi("丑")
////    }
////}
val TianGan.jiGong: DiZhi
    get() {
        return when (this) {
            TianGan("甲") -> DiZhi("寅")
            TianGan("乙") -> DiZhi("辰")
            TianGan("丙") -> DiZhi("巳")
            TianGan("丁") -> DiZhi("未")
            TianGan("戊") -> DiZhi("巳")
            TianGan("己") -> DiZhi("未")
            TianGan("庚") -> DiZhi("申")
            TianGan("辛") -> DiZhi("戌")
            TianGan("壬") -> DiZhi("亥")
            else -> DiZhi("丑")
        }
    }
val DiZhi.jiGongGan: Array<TianGan>
    get() {
        return when (this) {
            DiZhi("丑") -> arrayOf(TianGan("癸"))
            DiZhi("寅") -> arrayOf(TianGan("甲"))
            DiZhi("辰") -> arrayOf(TianGan("乙"))
            DiZhi("巳") -> arrayOf(TianGan("丙"),TianGan("戌"))
            DiZhi("未") -> arrayOf(TianGan("丁"),TianGan("己"))
            DiZhi("申") -> arrayOf(TianGan("庚"))
            DiZhi("戌") -> arrayOf(TianGan("酉"))
            DiZhi("亥") -> arrayOf(TianGan("壬"))
            else -> emptyArray()
        }
    }

fun GanZhi.isBaZhuan(): Boolean {
    val baZhuan = arrayOf(
            GanZhi(TianGan("甲"), DiZhi("寅")),
            GanZhi(TianGan("庚"), DiZhi("申")),
            GanZhi(TianGan("丁"), DiZhi("未")),
            GanZhi(TianGan("己"), DiZhi("未")))
    return this in baZhuan
}