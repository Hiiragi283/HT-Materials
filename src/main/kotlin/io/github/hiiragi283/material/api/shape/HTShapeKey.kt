package io.github.hiiragi283.material.api.shape

import io.github.hiiragi283.material.api.registry.HTObjectKey

data class HTShapeKey(override val name: String) : HTObjectKey<HTShape> {

    override val objClass: Class<HTShape> = HTShape::class.java

}