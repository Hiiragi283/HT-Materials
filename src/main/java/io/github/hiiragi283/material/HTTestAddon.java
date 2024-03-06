package io.github.hiiragi283.material;

import com.google.common.collect.ImmutableMap;
import io.github.hiiragi283.api.HTMaterialsAPI;
import io.github.hiiragi283.api.HTMaterialsAddon;
import io.github.hiiragi283.api.extension.HTColor;
import io.github.hiiragi283.api.fluid.HTFluidManager;
import io.github.hiiragi283.api.material.HTMaterialKey;
import io.github.hiiragi283.api.material.HTMaterialRegistry;
import io.github.hiiragi283.api.material.composition.HTMaterialComposition;
import io.github.hiiragi283.api.material.element.HTElement;
import io.github.hiiragi283.api.part.HTPartManager;
import io.github.hiiragi283.api.shape.HTShape;
import io.github.hiiragi283.api.shape.HTShapes;
import io.github.hiiragi283.api.shape.HTShapeRegistry;
import net.fabricmc.api.EnvType;
import net.minecraft.item.Items;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class HTTestAddon implements HTMaterialsAddon {

    @NotNull
    @Override
    public String getModId() {
        return HTMaterialsAPI.MOD_ID;
    }

    @Override
    public int getPriority() {
        return 0;
    }

    //    HTShape    //

    public static final HTShape DIRTY_DUST = new HTShape("dirty_dust");

    @Override
    public void modifyShapeRegistry(@NotNull HTShapeRegistry.Builder builder) {
        builder.add(DIRTY_DUST);
    }

    //    HTMaterial    //

    public static final HTMaterialKey INFINITY_KEY = new HTMaterialKey("infinity");
    public static final HTElement INFINITY_ELEMENT = HTElement.of(HTMaterialsAPI.id("infinity"), HTColor.WHITE, "Inf.", Double.MAX_VALUE);

    @Override
    public void modifyMaterialRegistry(@NotNull HTMaterialRegistry.Builder builder) {
        builder.setMetal(
                INFINITY_KEY,
                HTMaterialComposition.molecular(
                        ImmutableMap.<HTElement, Integer>builder()
                                .put(INFINITY_ELEMENT, 1)
                                .build()
                )
        );
    }

    //    Post Init    //

    @Override
    public void modifyFluidManager(@NotNull HTFluidManager.Builder builder) {

    }

    @Override
    public void modifyPartManager(@NotNull HTPartManager.Builder builder) {
        builder.add(INFINITY_KEY, HTShapes.GEM, Items.NETHER_STAR);
    }

    @Override
    public void postInitialize(@NotNull EnvType envType) {
        HTMaterialsAPI.getINSTANCE().shapeRegistry().getKeys().forEach(key -> HTMaterialsAPI.log("Shape: " + key));
        HTMaterialsAPI.getINSTANCE().materialRegistry().getKeys().forEach(key -> HTMaterialsAPI.log("Material: " + key));
    }

}