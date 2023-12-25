package io.github.hiiragi283.material.api.material

fun interface FormulaConvertible {

    fun asFormula(): String

    companion object {

        @JvmField
        val EMPTY = FormulaConvertible { "" }

        @JvmStatic
        fun of(vararg pair: Pair<FormulaConvertible, Int>) = of(pair.toMap())

        @JvmStatic
        fun of(map: Map<FormulaConvertible, Int>) = FormulaConvertible {
            val builder = StringBuilder()
            for ((formula: FormulaConvertible, weight: Int) in map) {
                builder.append(formula.asFormula())
                //値が1の場合はパス
                if (weight == 1) continue
                //化学式の下付き数字の桁数調整
                val subscript1: Char = '\u2080' + (weight % 10)
                val subscript10: Char = '\u2080' + (weight / 10)
                //2桁目が0でない場合，下付き数字を2桁にする
                builder.append(StringBuilder().run {
                    if (subscript10 != '\u2080') this.append(subscript10)
                    this.append(subscript1)
                })
            }
            builder.toString()
        }

    }

    class Child(val map: Map<HTMaterialKey, Int>) : FormulaConvertible {

        constructor(vararg pair: Pair<HTMaterialKey, Int>) : this(pair.toMap())

        override fun asFormula(): String {
            val builder = StringBuilder()
            for ((key: HTMaterialKey, weight: Int) in map) {
                builder.append(HTMaterial.getMaterialOrNull(key)?.info?.formula ?: "X")
                //値が1の場合はパス
                if (weight == 1) continue
                //化学式の下付き数字の桁数調整
                val subscript1: Char = '\u2080' + (weight % 10)
                val subscript10: Char = '\u2080' + (weight / 10)
                //2桁目が0でない場合，下付き数字を2桁にする
                builder.append(StringBuilder().run {
                    if (subscript10 != '\u2080') this.append(subscript10)
                    this.append(subscript1)
                })
            }
            return builder.toString()
        }

    }

}