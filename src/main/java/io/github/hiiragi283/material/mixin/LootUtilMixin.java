package io.github.hiiragi283.material.mixin;

import io.github.hiiragi283.material.HTMaterialsCommon;
import net.fabricmc.fabric.api.loot.v2.LootTableSource;
import net.fabricmc.fabric.impl.loot.LootUtil;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LootUtil.class)
public class LootUtilMixin {

    @Inject(method = "determineSource", at = @At(value = "HEAD"), cancellable = true)
    private static void ht_materials$determineSource(Identifier lootTableId, ResourceManager resourceManager, CallbackInfoReturnable<LootTableSource> cir) {
        if (lootTableId.getNamespace().equals(HTMaterialsCommon.MOD_ID)) {
            cir.setReturnValue(LootTableSource.MOD);
        }
    }

}