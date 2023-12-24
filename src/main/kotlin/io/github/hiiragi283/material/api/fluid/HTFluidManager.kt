package io.github.hiiragi283.material.api.fluid

import com.google.common.collect.HashMultimap
import com.google.common.collect.ImmutableMultimap
import com.google.common.collect.Multimap
import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.api.material.HTMaterialKey
import io.github.hiiragi283.material.api.material.materials.HTVanillaMaterialsNew
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

    private val fluidToMaterial: MutableMap<Fluid, HTMaterialKey> = mutableMapOf()

    @JvmStatic
    fun getFluidToMaterialMap(): Map<Fluid, HTMaterialKey> = fluidToMaterial

    @JvmStatic
    fun getMaterialKey(fluid: Fluid): HTMaterialKey? = fluidToMaterial[fluid]

    @JvmStatic
    fun hasMaterialKey(fluid: Fluid): Boolean = fluid in fluidToMaterial

    //    HTMaterialKey -> Fluid    //

    private val materialToFluid: MutableMap<HTMaterialKey, Fluid> = mutableMapOf()

    @JvmStatic
    fun getDefaultFluidMap(): Map<HTMaterialKey, Fluid> = materialToFluid

    @JvmStatic
    fun getDefaultFluid(material: HTMaterialKey): Fluid? = materialToFluid[material]

    @JvmStatic
    fun hasDefaultFluid(material: HTMaterialKey): Boolean = material in materialToFluid

    //   HTMaterialKey -> Collection<Fluid>    //

    private val materialToFluids: Multimap<HTMaterialKey, Fluid> = HashMultimap.create()

    @JvmStatic
    fun getMaterialToFluidsMap(): ImmutableMultimap<HTMaterialKey, Fluid> =
        ImmutableMultimap.copyOf(materialToFluids)

    @JvmStatic
    fun getFluids(material: HTMaterialKey): Collection<Fluid> = materialToFluids[material] ?: setOf()

    //    Registration    //

    private fun checkFluidNotEmpty(fluid: Fluid) {
        check(fluid != Fluids.EMPTY) { "The Entry is empty!" }
    }

    @JvmStatic
    fun register(material: HTMaterialKey, fluid: Fluid) {
        checkFluidNotEmpty(fluid)
        if (fluid is FlowableFluid) {
            registerInternal(material, fluid.still)
            registerInternal(material, fluid.flowing)
        } else {
            registerInternal(material, fluid)
        }
    }

    @JvmSynthetic
    private fun registerInternal(material: HTMaterialKey, fluid: Fluid) {
        //Fluid -> HTMaterialKey
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
    internal fun forceRegister(material: HTMaterialKey, fluid: Fluid) {
        //Fluid -> HTMaterialKey
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

    init {
        //Water
        forceRegister(HTVanillaMaterialsNew.WATER, Fluids.WATER)
        forceRegister(HTVanillaMaterialsNew.WATER, Fluids.FLOWING_WATER)
        //Lava
        forceRegister(HTVanillaMaterialsNew.LAVA, Fluids.LAVA)
        forceRegister(HTVanillaMaterialsNew.LAVA, Fluids.FLOWING_LAVA)
    }

    @JvmStatic
    internal fun registerAllFluids() {
        getAllModId().forEach { modid: String ->
            HTMaterial.REGISTRY.keys.forEach { key ->
                Registry.FLUID.get(Identifier(modid, key.name)).run {
                    if (this != Fluids.EMPTY) {
                        register(key, this)
                    }
                }
            }
        }
    }

}