package io.github.hiiragi283.material.api.shape

import io.github.hiiragi283.material.HTMaterialsCommon
import io.github.hiiragi283.material.api.material.HTMaterialKey
import io.github.hiiragi283.material.api.registry.HTObjectKey
import io.github.hiiragi283.material.util.commonId
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.resource.language.I18n
import net.minecraft.item.Item
import net.minecraft.tag.TagKey
import net.minecraft.text.TranslatableText
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

data class HTShapeKey(
    override val name: String,
    val idPath: String = "%s_$name",
    val forgePath: String = getForgePath(name),
    val commonPath: String = "${idPath}s"
) : HTObjectKey<HTShape> {

    override val objClass: Class<HTShape> = HTShape::class.java

    fun getShape(): HTShape = HTShape.getShape(this)

    fun getShapeOrNull(): HTShape? = HTShape.getShapeOrNull(this)

    //    Identifier    //

    fun getIdentifier(material: HTMaterialKey, namespace: String = HTMaterialsCommon.MOD_ID): Identifier =
        Identifier(namespace, idPath.replace("%s", material.name))

    //    TagKey    //

    fun getForgeTag(material: HTMaterialKey): TagKey<Item> =
        TagKey.of(Registry.ITEM_KEY, commonId(forgePath.replace("%s", material.name)))

    fun getCommonTag(material: HTMaterialKey): TagKey<Item> =
        TagKey.of(Registry.ITEM_KEY, commonId(commonPath.replace("%s", material.name)))

    //    Translation    //

    private val translationKey: String = "ht_shape.$name"

    @Environment(EnvType.CLIENT)
    fun getTranslatedName(materialKey: HTMaterialKey): String =
        I18n.translate(translationKey, materialKey.getTranslatedName())

    fun getTranslatedText(materialKey: HTMaterialKey): TranslatableText =
        TranslatableText(translationKey, materialKey.getTranslatedName())

    //    Any    //

    override fun equals(other: Any?): Boolean = when (other) {
        null -> false
        !is HTShapeKey -> false
        else -> other.name == this.name
    }

    override fun hashCode(): Int = name.hashCode()

    override fun toString(): String = name

    companion object {

        private fun getForgePath(name: String): String {
            val split: List<String> = name.split("_")
            return when (split.size) {
                1 -> "${name}s/%s"
                2 -> "${split[0]}s/${split[1]}/%s"
                else -> throw IllegalStateException("Cannot decompose name: $name into Forge Tag format!")
            }
        }

    }

}