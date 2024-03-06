package io.github.hiiragi283.api.material

import com.google.common.collect.ImmutableMap
import io.github.hiiragi283.api.material.composition.HTMaterialComposition
import io.github.hiiragi283.api.material.element.HTElement

interface HTMaterialRegistry {
    val registry: ImmutableMap<HTMaterialKey, HTMaterial>

    val keys: Collection<HTMaterialKey>
        get() = registry.keys

    val values: Collection<HTMaterial>
        get() = registry.values

    fun getByName(key: String) = get(HTMaterialKey(key))

    operator fun get(key: HTMaterialKey) = registry[key]

    operator fun contains(key: HTMaterialKey) = key in registry

    //    Builder    //

    class Builder {
        val registry: ImmutableMap<HTMaterialKey, HTMaterial>
            get() = _registry.values.map(::HTMaterial).associateBy(HTMaterial::key).let { ImmutableMap.copyOf(it) }
        private val _registry: MutableMap<String, HTMaterial.Builder> = mutableMapOf()

        fun get(key: HTMaterialKey): HTMaterial.Builder = get(key.name)

        fun get(name: String): HTMaterial.Builder = _registry.computeIfAbsent(name) { HTMaterial.Builder(name) }

        //    Simple    //

        fun setSimple(key: HTMaterialKey, elements: Pair<HTElement, Int>): HTMaterial.Builder =
            setSimple(key, HTMaterialComposition.molecular(elements))

        fun setSimple(key: HTMaterialKey, composition: HTMaterialComposition) = get(key).apply {
            this.composition = composition
        }

        //    Gem    //

        fun setGem(key: HTMaterialKey, element: HTElement): HTMaterial.Builder = setSimple(key, element to 1)

        fun setGem(key: HTMaterialKey, composition: HTMaterialComposition): HTMaterial.Builder = setSimple(key, composition)

        //    Metal    //

        fun setMetal(key: HTMaterialKey, element: HTElement): HTMaterial.Builder = setSimple(key, element to 1)

        fun setMetal(key: HTMaterialKey, composition: HTMaterialComposition): HTMaterial.Builder = setSimple(key, composition)

        //    Stone    //

        fun setStone(key: HTMaterialKey, element: HTElement): HTMaterial.Builder = setSimple(key, element to 1)

        fun setStone(key: HTMaterialKey, composition: HTMaterialComposition): HTMaterial.Builder = setSimple(key, composition)

        //    Wood    //

        fun setWood(key: HTMaterialKey, element: HTElement): HTMaterial.Builder = setSimple(key, element to 1)

        fun setWood(key: HTMaterialKey, composition: HTMaterialComposition): HTMaterial.Builder = setSimple(key, composition)
    }
}
