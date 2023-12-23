package io.github.hiiragi283.material.api.material.property

import io.github.hiiragi283.material.api.material.HTMaterialNew
import java.util.function.Consumer

class HTMaterialProperties : MutableMap<HTPropertyKey<*>, HTMaterialProperty<*>> by hashMapOf() {

    private operator fun <T : HTMaterialProperty<T>> plusAssign(property: T) {
        this.add(property)
    }

    fun <T : HTMaterialProperty<T>> add(property: T) {
        this.putIfAbsent(property.key, property)
    }

    fun verify(material: HTMaterialNew) {
        this.values.forEach { it.verify(material) }
    }

    //    Util    //

    fun setFluid(consumer: Consumer<HTFluidProperty>) {
        this.add(HTFluidProperty().also(consumer::accept))
    }

    fun setGem(type: HTGemProperty.Type) {
        this.add(HTGemProperty(type))
    }

    fun setMetal() {
        this.add(HTMetalProperty())
    }

    fun setSolid() {

    }

    fun setStone() {

        this.add(HTMetalProperty())
    }

    fun setWood() {

        this.add(HTMetalProperty())
    }

    fun setHarvestLevel(level: Int) {

    }

    //    Any    //

    override fun toString(): String = this.keys.joinToString(separator = ", ")

}