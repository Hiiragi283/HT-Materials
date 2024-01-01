package io.github.hiiragi283.material.api.material.property

import io.github.hiiragi283.material.api.material.HTMaterial

object HTStoneProperty : HTMaterialProperty<HTStoneProperty> {

    override val key: HTPropertyKey<HTStoneProperty> = HTPropertyKey.STONE

    override fun verify(material: HTMaterial) {

    }

}