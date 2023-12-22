package io.github.hiiragi283.material.api.material

import io.github.hiiragi283.material.HTMaterialsCommon
import io.github.hiiragi283.material.api.material.flag.HTMaterialFlag
import io.github.hiiragi283.material.api.material.flag.HTMaterialFlags
import io.github.hiiragi283.material.api.material.materials.HTAtomicGroups
import io.github.hiiragi283.material.api.material.materials.HTCommonMaterials
import io.github.hiiragi283.material.api.material.materials.HTElementMaterials
import io.github.hiiragi283.material.api.material.materials.HTVanillaMaterials
import io.github.hiiragi283.material.api.material.property.HTMaterialProperties
import io.github.hiiragi283.material.api.material.property.HTMaterialProperty
import io.github.hiiragi283.material.api.material.property.HTPropertyKey
import io.github.hiiragi283.material.api.shape.HTShape
import io.github.hiiragi283.material.api.shape.HTShapes
import io.github.hiiragi283.material.util.commonId
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants
import net.minecraft.client.resource.language.I18n
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.Identifier
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.awt.Color

@Suppress("UnstableApiUsage")
class HTMaterial private constructor(
    private val info: Info,
    private val properties: HTMaterialProperties,
    private val flags: HTMaterialFlags
) : ColorConvertible, FormulaConvertible, MolarMassConvertible {

    fun verify() {
    }

    //    Properties    //

    fun getProperties(): Collection<HTMaterialProperty<*>> = properties.values

    fun <T : HTMaterialProperty<T>> getProperty(key: HTPropertyKey<T>): T? = properties.getAs(key)

    fun <T : HTMaterialProperty<T>> hasProperty(key: HTPropertyKey<T>): Boolean = key in properties

    fun modifyProperties(init: HTMaterialProperties.() -> Unit) {
        check(canModify) { "Cannot modify material properties after Initialization!!" }
        properties.init()
    }

    fun getDefaultShape(): HTShape? = when {
        hasProperty(HTPropertyKey.METAL) -> HTShapes.INGOT
        hasProperty(HTPropertyKey.GEM) -> HTShapes.GEM
        else -> null
    }

    //    Flags    //

    fun hasFlag(flag: HTMaterialFlag): Boolean = flag in flags

    fun modifyFlags(init: HTMaterialFlags.() -> Unit) {
        check(canModify) { "Cannot modify material flags after Initialization!!" }
        flags.init()
    }

    //    Info    //

    fun getName(): String = info.name

    fun getIdentifier(namespace: String = HTMaterialsCommon.MOD_ID): Identifier = Identifier(namespace, getName())

    fun getCommonId(): Identifier = commonId(getName())

    fun getIngotCountPerBlock(): Int = info.ingotPerBlock

    fun getFluidAmountPerIngot(): Long = FluidConstants.BLOCK / getIngotCountPerBlock()

    @Environment(EnvType.CLIENT)
    fun getTranslatedName(): String = I18n.translate(info.translationKey)

    fun getTranslatedText(): TranslatableText = TranslatableText(info.translationKey)

    fun modifyInfo(init: Info.() -> Unit) {
        check(canModify) { "Cannot modify material infos after Initialization!!" }
        info.init()
    }

    //    ColorConvertible    //

    private lateinit var colorCache: Color

    override fun asColor(): Color {
        check(!canModify) { "Cannot call #asFormula before Initialization!!" }
        if (!this::colorCache.isInitialized) {
            colorCache = info.color.asColor()
            info.color = ColorConvertible.EMPTY
        }
        return colorCache
    }

    //    FormulaConvertible    //

    private lateinit var formulaCache: String

    override fun asFormula(): String {
        check(!canModify) { "Cannot call #asFormula before Initialization!!" }
        if (!this::formulaCache.isInitialized) {
            formulaCache = info.formula.asFormula()
            info.formula = FormulaConvertible.EMPTY
        }
        return formulaCache
    }

    //    MolarMassConvertible    //

    private var molarCache: Double = 0.0

    override fun asMolarMass(): Double {
        check(!canModify) { "Cannot call #asMolarMass before Initialization!!" }
        if (molarCache <= 0.0) {
            molarCache = info.molarMass.asMolarMass()
            info.molarMass = MolarMassConvertible.EMPTY
        }
        return molarCache
    }

    data class Info(
        @JvmField val name: String,
        @JvmField var color: ColorConvertible = ColorConvertible.EMPTY,
        @JvmField var formula: FormulaConvertible = FormulaConvertible.EMPTY,
        @JvmField var ingotPerBlock: Int = 9,
        @JvmField var molarMass: MolarMassConvertible = MolarMassConvertible.EMPTY,
        @JvmField var translationKey: String = "ht_material.$name"
    )

    //    Any    //

    override fun equals(other: Any?): Boolean = when (other) {
        null -> false
        !is HTMaterial -> false
        else -> other.getName() == this.getName()
    }

    override fun hashCode(): Int = getName().hashCode()

    override fun toString(): String = getName()

    companion object {

        private val LOGGER: Logger = LoggerFactory.getLogger(this::class.java)

        internal var canModify: Boolean = true

        private val map: MutableMap<String, HTMaterial> = mutableMapOf()

        @JvmField
        val REGISTRY: Collection<HTMaterial> = map.values

        @JvmStatic
        fun create(name: String, init: HTMaterial.() -> Unit = {}) =
            HTMaterial(Info(name), HTMaterialProperties(), HTMaterialFlags())
                .also { check(canModify) { "Cannot register material after Initialization!!" } }
                .apply(init)
                .also { material ->
                    map.putIfAbsent(name, material)
                    LOGGER.info("The Material: $material registered!")
                }

        @JvmStatic
        fun getMaterial(name: String): HTMaterial? = map[name]

        init {
            HTElementMaterials
            HTAtomicGroups
            HTVanillaMaterials
            HTCommonMaterials
        }

    }

    fun appendFluidTooltip(stack: ItemStack, lines: MutableList<Text>) {

    }

}