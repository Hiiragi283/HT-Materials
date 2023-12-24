package io.github.hiiragi283.material.api.shape

import io.github.hiiragi283.material.api.material.HTMaterial
import java.util.function.Predicate

class HTShapePredicate : Predicate<HTMaterial> {

    override fun test(material: HTMaterial): Boolean = false

    class Builder

}