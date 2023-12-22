package io.github.hiiragi283.material.api.fluid

import com.google.common.collect.HashMultimap
import com.google.common.collect.ImmutableMultimap
import com.google.common.collect.Multimap
import io.github.hiiragi283.material.api.material.HTMaterialNew
import io.github.hiiragi283.material.util.getAllModId
import net.minecraft.fluid.FlowableFluid
import net.minecraft.fluid.Fluid
import net.minecraft.fluid.Fluids
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object HTFluidManager {

    private val LOGGER: Logger = LoggerFactory.getLogger(this::class.java)

    //    Fluid -> HTMaterial    //

    private val fluidToMaterial: MutableMap<Fluid, HTMaterialNew> = mutableMapOf()

    @JvmStatic
    fun getFluidToMaterialMap(): Map<Fluid, HTMaterialNew> = fluidToMaterial

    @JvmStatic
    fun getMaterial(fluid: Fluid): HTMaterialNew? = fluidToMaterial[fluid]

    @JvmStatic
    fun hasMaterial(fluid: Fluid): Boolean = fluid in fluidToMaterial

    //    HTMaterialNew -> Fluid    //

    private val materialToFluid: MutableMap<HTMaterialNew, Fluid> = mutableMapOf()

    @JvmStatic
    fun getDefaultFluidMap(): Map<HTMaterialNew, Fluid> = materialToFluid

    @JvmStatic
    fun getDefaultFluid(material: HTMaterialNew): Fluid? = materialToFluid[material]

    @JvmStatic
    fun hasDefaultFluid(material: HTMaterialNew): Boolean = material in materialToFluid

    //   HTMaterialNew -> Collection<Fluid>    //

    private val materialToFluids: Multimap<HTMaterialNew, Fluid> = HashMultimap.create()

    @JvmStatic
    fun getMaterialToFluidsMap(): ImmutableMultimap<HTMaterialNew, Fluid> =
        ImmutableMultimap.copyOf(materialToFluids)

    @JvmStatic
    fun getFluids(material: HTMaterialNew): Collection<Fluid> = materialToFluids[material] ?: setOf()

    //    Registration    //

    private fun checkFluidNotEmpty(fluid: Fluid) {
        check(fluid != Fluids.EMPTY) { "The Entry is empty!" }
    }

    @JvmStatic
    fun register(material: HTMaterialNew, fluid: Fluid) {
        checkFluidNotEmpty(fluid)
        if (fluid is FlowableFluid) {
            registerInternal(material, fluid.still)
            registerInternal(material, fluid.flowing)
        } else {
            registerInternal(material, fluid)
        }
    }

    @JvmSynthetic
    private fun registerInternal(material: HTMaterialNew, fluid: Fluid) {
        //Fluid -> HTMaterialNew
        fluidToMaterial.putIfAbsent(fluid, material)
        //HTMaterial -> Fluid
        if (!hasDefaultFluid(material)) {
            materialToFluid[material] = fluid
            LOGGER.info("The Fluid: ${Registry.FLUID.getId(fluid)} registered as Default Fluid for Material: $material!!")
        }
        //HTMaterial -> Collection<Fluid>
        materialToFluids.put(material, fluid)
        //print info
        LOGGER.info("The Fluid: ${Registry.FLUID.getId(fluid)} linked to Material: $material!")
    }

    @JvmStatic
    @JvmSynthetic
    internal fun forceRegister(material: HTMaterialNew, fluid: Fluid) {
        //Fluid -> HTMaterialNew
        fluidToMaterial.putIfAbsent(fluid, material)
        //HTMaterial -> Fluid
        materialToFluid[material] = fluid
        LOGGER.info("The Fluid: ${Registry.FLUID.getId(fluid)} registered as Default Fluid for Material: $material!!")
        //HTMaterial -> Collection<Fluid>
        materialToFluids.put(material, fluid)
        //print info
        LOGGER.info("The Fluid: ${Registry.FLUID.getId(fluid)} linked to Material: $material!")
    }

    //    Initialization    //

    /*init {
        //Water
        forceRegister(HTVanillaMaterials.WATER, Fluids.WATER)
        forceRegister(HTVanillaMaterials.WATER, Fluids.FLOWING_WATER)
        //Lava
        forceRegister(HTVanillaMaterials.LAVA, Fluids.LAVA)
        forceRegister(HTVanillaMaterials.LAVA, Fluids.FLOWING_LAVA)
    }*/

    @JvmStatic
    internal fun registerAllFluids() {
        getAllModId().forEach { modid: String ->
            HTMaterialNew.REGISTRY.forEach { material ->
                Registry.FLUID.get(Identifier(modid, material.getName())).run {
                    if (this != Fluids.EMPTY) {
                        register(material, this)
                    }
                }
            }
        }
    }

}