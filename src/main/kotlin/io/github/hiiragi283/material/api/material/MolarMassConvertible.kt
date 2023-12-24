package io.github.hiiragi283.material.api.material

fun interface MolarMassConvertible {

    fun asMolarMass(): Double

    companion object {

        @JvmField
        val EMPTY = MolarMassConvertible { 0.0 }

        @JvmStatic
        fun of(vararg pair: Pair<MolarMassConvertible, Int>) = of(pair.toMap())

        @JvmStatic
        fun of(map: Map<MolarMassConvertible, Int>) = MolarMassConvertible {
            var result = 0.0
            for ((molar: MolarMassConvertible, weight: Int) in map) {
                result += molar.asMolarMass() * weight
            }
            "%.1f".format(result).toDouble()
        }

        @JvmStatic
        fun of(registry: Map<HTMaterialKey, MolarMassConvertible>, vararg pair: Pair<HTMaterialKey, Int>) =
            of(registry, pair.toMap())

        @JvmStatic
        fun of(registry: Map<HTMaterialKey, MolarMassConvertible>, map: Map<HTMaterialKey, Int>) =
            MolarMassConvertible {
                var result = 0.0
                for ((key: HTMaterialKey, weight: Int) in map) {
                    result += registry.getOrDefault(key, EMPTY).asMolarMass() * weight
                }
                "%.1f".format(result).toDouble()
            }

    }

}