package io.github.hiiragi283.api.material

import java.awt.Color

fun interface ColorConvertible {
    fun asColor(): Color

    companion object {

        @JvmStatic
        fun average(vararg colors: Color): Color = average(colors.associateWith { 1 })

        @JvmStatic
        fun average(colors: Iterable<Color>): Color = average(colors.associateWith { 1 })

        @JvmStatic
        fun average(vararg pairs: Pair<Color, Int>): Color = average(mapOf(*pairs))

        @JvmStatic
        fun average(map: Map<Color, Int>): Color {
            var redSum = 0
            var greenSum = 0
            var blueSum = 0
            var weightSum = 0
            map.forEach { (color: Color, weight: Int) ->
                // RGB値にweightをかけた値を加算していく
                color.run {
                    redSum += this.red * weight
                    greenSum += this.green * weight
                    blueSum += this.blue * weight
                }
                weightSum += weight
            }
            return if (weightSum > 0) {
                Color(redSum / weightSum, greenSum / weightSum, blueSum / weightSum)
            } else {
                Color.WHITE
            }
        }
    }
}