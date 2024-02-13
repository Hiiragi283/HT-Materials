package io.github.hiiragi283.fabric.mixin;

import io.github.hiiragi283.api.HTMaterialsAPI;
import io.github.hiiragi283.api.util.resource.HTResourcePackProvider;
import net.fabricmc.fabric.impl.resource.loader.ModResourcePackCreator;
import net.minecraft.resource.ResourcePackProfile;
import net.minecraft.resource.ResourceType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(ModResourcePackCreator.class)
public class ModResourcePackCreatorMixin {

    @Unique
    private ResourceType type;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void onConstruction(ResourceType type, CallbackInfo ci) {
        this.type = type;
    }

    @Inject(method = "register(Ljava/util/function/Consumer;Lnet/minecraft/resource/ResourcePackProfile$Factory;)V", at = @At("RETURN"))
    private void ht_materials$register(Consumer<ResourcePackProfile> consumer, ResourcePackProfile.Factory factory, CallbackInfo ci) {
        switch (type) {
            case CLIENT_RESOURCES -> {
                HTResourcePackProvider.CLIENT.register(consumer, factory);
                HTMaterialsAPI.log("Registered runtime resource pack!");
            }
            case SERVER_DATA -> {
                HTResourcePackProvider.SERVER.register(consumer, factory);
                HTMaterialsAPI.log("Registered runtime data pack!");
            }
        }
    }

}