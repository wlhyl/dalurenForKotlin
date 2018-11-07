package org.lzh.shipan.daluren

import org.lzh.ganzhiwuxing.GanZhi

enum class Sex {
    MAN, WOMEN
}

data class SiZHu(val yearGanZhi: GanZhi, val monthGanZhi: GanZhi, val dayGanZhi: GanZhi, val hourGanZhi: GanZhi)