package io.github.hiiragi283.material.api.material.flag

import io.github.hiiragi283.material.api.material.HTMaterialNew

class HTMaterialFlags : MutableCollection<HTMaterialFlag> by mutableSetOf() {

    fun addAll(vararg flag: HTMaterialFlag) {
        flag.forEach(this::add)
    }

    fun removeAll(vararg flag: HTMaterialFlag) {
        flag.forEach(this::remove)
    }

    fun verify(material: HTMaterialNew) {
        this.forEach { it.verify(material) }
    }

    //    Any    //

    override fun toString(): String = this.joinToString(separator = ", ")

}