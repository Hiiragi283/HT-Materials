package io.github.hiiragi283.material.api.material.flag

import io.github.hiiragi283.material.api.material.HTMaterialNew

class HTMaterialFlagsNew private constructor(val set: Set<HTMaterialFlag>) : Set<HTMaterialFlag> by set {

    fun verify(material: HTMaterialNew) {
        this.forEach { it.verify(material) }
    }

    //    Any    //

    override fun toString(): String = this.joinToString(separator = ", ")

    //    Builder    //

    class Builder : MutableSet<HTMaterialFlag> by hashSetOf() {

        internal fun build(): HTMaterialFlagsNew = HTMaterialFlagsNew(this)

    }

}