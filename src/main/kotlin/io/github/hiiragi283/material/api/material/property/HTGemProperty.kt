package io.github.hiiragi283.material.api.material.property

import io.github.hiiragi283.material.api.material.HTMaterialNew

class HTGemProperty(val type: Type) : HTMaterialProperty<HTGemProperty> {

    override val key: HTPropertyKey<HTGemProperty> = HTPropertyKey.GEM

    override fun verify(material: HTMaterialNew) {
        if (material.hasProperty(HTPropertyKey.METAL)) {
            throw IllegalStateException("Material: has both Metal and Gem Property, which is not allowed!")
        }
    }

    enum class Type {
        AMETHYST,
        COAL,
        CUBIC,
        DIAMOND,
        EMERALD,
        LAPIS,
        QUARTZ,
        RUBY;
    }

}