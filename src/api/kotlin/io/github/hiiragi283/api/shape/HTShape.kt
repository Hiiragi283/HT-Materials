package io.github.hiiragi283.api.shape

import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.material.HTMaterialKey
import io.github.hiiragi283.formatter.HTTagFormatRule
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.resource.language.I18n
import net.minecraft.text.Text
import net.minecraft.util.Identifier

data class HTShape(val name: String) {
    fun getShapeId() = Identifier("shape", name)

    //    Formatter    //

    fun getFormatter(): HTTagFormatRule? = HTTagFormatRule.registry[name]

    //    Identifier    //

    private val idPath: String = "%s_$name"

    fun getId(materialKey: HTMaterialKey, namespace: String = HTMaterialsAPI.MOD_ID) = getId(materialKey.name, namespace)

    fun getId(path: String, namespace: String = HTMaterialsAPI.MOD_ID) = Identifier(namespace, idPath.replace("%s", path))

    fun getCommonId(material: HTMaterialKey): Identifier = getCommonId(material.name)

    fun getCommonId(path: String) = getId(path, "c")

    //    Translation    //

    private val translationKey: String = "ht_shape.$name"

    @Environment(EnvType.CLIENT)
    fun getTranslatedName(materialKey: HTMaterialKey): String = I18n.translate(translationKey, materialKey.getTranslatedName())

    fun getTranslatedText(materialKey: HTMaterialKey): Text = Text.translatable(translationKey, materialKey.getTranslatedName())

    //    Any    //

    override fun toString(): String = name
}
