package io.github.hiiragi283.api.part

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import io.github.hiiragi283.api.extension.Encodable
import io.github.hiiragi283.api.material.HTMaterial
import io.github.hiiragi283.api.material.HTMaterialKey
import io.github.hiiragi283.api.shape.HTShape
import net.minecraft.item.Item
import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.tag.TagKey
import net.minecraft.util.Identifier

data class HTPart(
    val materialKey: HTMaterialKey,
    val shapeKey: HTShape,
) : Encodable<HTPart> {
    override val codec: Codec<HTPart>
        get() = CODEC

    companion object {
        @JvmField
        val CODEC: Codec<HTPart> = RecordCodecBuilder.create { instance ->
            instance.group(
                Codec.STRING.fieldOf("material").forGetter { it.materialKey.name },
                Codec.STRING.fieldOf("shape").forGetter { it.shapeKey.name },
            ).apply(instance, ::HTPart)
        }
    }

    constructor(material: HTMaterial, shapeKey: HTShape) : this(material.key, shapeKey)

    constructor(material: String, shape: String) : this(HTMaterialKey(material), HTShape(shape))

    fun getMaterial(): HTMaterial = materialKey.getMaterial()

    fun getPartId(): Identifier = Identifier("part", "$shapeKey/$materialKey")

    fun getPartTag(): TagKey<Item> = TagKey.of(RegistryKeys.ITEM, getPartId())
}
