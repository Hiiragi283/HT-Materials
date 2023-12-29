package io.github.hiiragi283.material;

import io.github.hiiragi283.material.api.HTMaterialsAddon;
import io.github.hiiragi283.material.api.material.*;
import io.github.hiiragi283.material.api.material.flag.HTMaterialFlag;
import io.github.hiiragi283.material.api.material.flag.HTMaterialFlagSet;
import io.github.hiiragi283.material.api.material.property.HTFluidProperty;
import io.github.hiiragi283.material.api.material.property.HTMaterialPropertyMap;
import io.github.hiiragi283.material.api.material.property.HTMetalProperty;
import io.github.hiiragi283.material.api.registry.HTDefaultedMap;
import io.github.hiiragi283.material.api.registry.HTDefaultedTable;
import io.github.hiiragi283.material.api.registry.HTObjectKeySet;
import io.github.hiiragi283.material.api.shape.HTShape;
import io.github.hiiragi283.material.api.shape.HTShapeKey;
import io.github.hiiragi283.material.api.shape.HTShapePredicate;
import io.github.hiiragi283.material.api.shape.HTShapes;
import io.github.hiiragi283.material.util.HTColor;
import kotlin.Unit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Map;

public class HTTestAddon implements HTMaterialsAddon {

    @NotNull
    @Override
    public String getModId() {
        return HTMaterialsCommon.MOD_ID;
    }

    @Override
    public int getPriority() {
        return 0;
    }

    //    HTShape    //

    public static final HTShapeKey DIRTY_DUST = new HTShapeKey("dirty_dust");

    @Override
    public void registerShape(@NotNull HTObjectKeySet<HTShapeKey> registry) {
        registry.add(DIRTY_DUST);
    }

    @Override
    public void modifyShapePredicate(@NotNull HTDefaultedMap<HTShapeKey, HTShapePredicate.Builder> registry) {
        var builder = registry.getOrCreate(DIRTY_DUST);
        builder.setDisabled(false);
        builder.requiredFlags.add(HTMaterialFlag.GENERATE_DUST);
    }

    //    HTMaterial    //

    public static final HTMaterialKey INFINITY = new HTMaterialKey("infinity");

    @Override
    public void registerMaterialKey(@NotNull HTObjectKeySet<HTMaterialKey> registry) {
        registry.add(INFINITY);
    }

    @Override
    public void modifyMaterialProperty(@NotNull HTDefaultedMap<HTMaterialKey, HTMaterialPropertyMap.Builder> registry) {
        var builder = registry.getOrCreate(INFINITY);
        builder.add(HTMetalProperty.INSTANCE);
        builder.add(new HTFluidProperty(), prop -> {
            prop.setTemperature(32768);
            return Unit.INSTANCE;
        });
    }

    @Override
    public void modifyMaterialFlag(@NotNull HTDefaultedMap<HTMaterialKey, HTMaterialFlagSet.Builder> registry) {
        var builder = registry.getOrCreate(INFINITY);
        builder.add(HTMaterialFlag.GENERATE_DUST);
        builder.add(HTMaterialFlag.GENERATE_GEAR);
        builder.add(HTMaterialFlag.GENERATE_INGOT);
        builder.add(HTMaterialFlag.GENERATE_NUGGET);
        builder.add(HTMaterialFlag.GENERATE_PLATE);
        builder.add(HTMaterialFlag.GENERATE_ROD);
    }

    @Override
    public void modifyMaterialColor(@NotNull Map<HTMaterialKey, ColorConvertible> registry) {
        registry.put(INFINITY, () -> HTColor.WHITE);
    }

    @Override
    public void modifyMaterialFormula(@NotNull Map<HTMaterialKey, FormulaConvertible> registry) {
        registry.put(INFINITY, () -> "INFINITY");
    }

    @Override
    public void modifyMaterialMolar(@NotNull Map<HTMaterialKey, MolarMassConvertible> registry) {
        registry.put(INFINITY, () -> Double.MAX_VALUE);
    }

    //    Post Init    //

    @Override
    public void bindFluidToPart(@NotNull HTDefaultedMap<HTMaterialKey, Collection<Fluid>> registry) {

    }

    @Override
    public void bindItemToPart(@NotNull HTDefaultedTable<HTMaterialKey, HTShapeKey, Collection<ItemConvertible>> registry) {
        registry.getOrCreate(INFINITY, HTShapes.GEM).add(Items.NETHER_STAR);
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(HTTestAddon.class);

    @Override
    public void commonSetup() {
        HTShape.REGISTRY.keySet().forEach(key -> LOGGER.info("Shape: " + key));
        HTMaterial.REGISTRY.keySet().forEach(key -> LOGGER.info("Material: " + key));
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void clientSetup() {

    }

}