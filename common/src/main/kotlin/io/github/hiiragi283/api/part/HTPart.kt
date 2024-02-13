package io.github.hiiragi283.api.part

import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.material.HTMaterial
import io.github.hiiragi283.api.material.HTMaterialKey
import io.github.hiiragi283.api.shape.HTShape
import io.github.hiiragi283.api.shape.HTShapeKey
import net.minecraft.item.Item
import net.minecraft.tag.TagKey
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

data class HTPart(
    val materialKey: HTMaterialKey,
    val shapeKey: HTShapeKey,
) {
    constructor(material: HTMaterial, shape: HTShape) : this(material.key, shape.key)

    fun getMaterial(): HTMaterial = materialKey.getMaterial()

    fun getShape(): HTShape = shapeKey.getShape()

    fun getPartId(): Identifier = Identifier("part", "$shapeKey/$materialKey")

    fun getPartTag(): TagKey<Item> = TagKey.of(Registry.ITEM_KEY, getPartId())

    companion object {
        @JvmStatic
        private lateinit var cache: Map<String, HTPart>

        @JvmStatic
        fun initCache() {
            val map: MutableMap<String, HTPart> = hashMapOf()
            HTMaterialsAPI.INSTANCE.shapeRegistry().getValues().forEach { shape ->
                HTMaterialsAPI.INSTANCE.materialRegistry().getKeys().forEach { material ->
                    map[shape.getFabricId(material).path] = HTPart(material, shape.key)
                    map[shape.getForgeId(material).path] = HTPart(material, shape.key)
                }
            }
            cache = map
        }

        @JvmStatic
        fun fromTag(tag: TagKey<*>): HTPart? = fromId(tag.id)

        @JvmStatic
        fun fromId(id: Identifier): HTPart? = fromPath(id.path).takeIf { id.namespace in listOf("c", "forge") }

        @JvmStatic
        fun fromPath(path: String): HTPart? = cache[path]
    }
}
