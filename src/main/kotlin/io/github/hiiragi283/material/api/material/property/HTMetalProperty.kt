package io.github.hiiragi283.material.api.material.property

import io.github.hiiragi283.material.api.material.HTMaterialNew

class HTMetalProperty : HTMaterialProperty<HTMetalProperty> {

    override val key: HTPropertyKey<HTMetalProperty> = HTPropertyKey.METAL

    override fun verify(material: HTMaterialNew) {
        if (material.hasProperty(HTPropertyKey.GEM)) {
            throw IllegalStateException("Material: has both Metal and Gem Property, which is not allowed!")
        }
    }

}