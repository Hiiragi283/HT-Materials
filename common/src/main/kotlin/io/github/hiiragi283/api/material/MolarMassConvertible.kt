package io.github.hiiragi283.api.material

fun interface MolarMassConvertible {
    fun asMolarMass(): Double

    companion object {

        @JvmStatic
        fun calculate(molars: Iterable<Double>) = calculate(molars.associateWith { 1 })

        @JvmStatic
        fun calculate(map: Map<Double, Int>): Double = map.map { (molar: Double, weight: Int) -> molar * weight }.sum()
    }
}