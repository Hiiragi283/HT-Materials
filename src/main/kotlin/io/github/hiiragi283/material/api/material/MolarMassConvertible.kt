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

    }

    class Child(val map: Map<HTMaterialKey, Int>) : MolarMassConvertible {

        constructor(vararg pair: Pair<HTMaterialKey, Int>) : this(pair.toMap())

        override fun asMolarMass(): Double {
            var result = 0.0
            for ((key: HTMaterialKey, weight: Int) in map) {
                val molar: Double = HTMaterial.getMaterialOrNull(key)?.info?.molarMass ?: 0.0
                result += molar * weight
            }
            return "%.1f".format(result).toDouble()
        }

    }

}