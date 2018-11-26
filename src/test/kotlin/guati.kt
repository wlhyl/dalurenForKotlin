import org.lzh.shipan.daluren.*

import org.joda.time.*
import org.lzh.ganzhiwuxing.DiZhi


fun main(args: Array<String>) {


    var t = DateTime(2018, 11, 26, 22, 36, 0, 0);


//    Duration(1,)
//    val t1 = t + Duration(1)
//    println(t)

//    val t0 = t.plusDays(1)
//    println("${t0.year}, ${t0.monthOfYear}, ${t0.dayOfMonth}, ${t0.hourOfDay}, ${t0.minuteOfHour}, ${t0.secondOfMinute}")
//    println(Period(2,3,PeriodType.hours()))
    for (k in (0..50)) {
        val t0 = t.plusDays(k)
        for (i in (0..11)) {
            val __月将 = getSunMansion(t0.year, t0.monthOfYear, t0.dayOfMonth, t0.hourOfDay, t0.minuteOfHour, t0.secondOfMinute)
            val s = ShiPan(t0.year, t0.monthOfYear, t0.dayOfMonth, t0.hourOfDay, t0.minuteOfHour, t0.secondOfMinute,
                    __月将, DiZhi("子") + i, true, 2018, "abc", Sex.MAN)
            if ("龙德卦" in s.guaTi) println("${t0} ${DiZhi("子") + i}")
        }
    }
}