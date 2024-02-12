package io.github.hiiragi283.fabric.mixin;

import io.github.hiiragi283.api.util.resource.HTResourcePackProvider;
import net.fabricmc.fabric.impl.resource.loader.ModResourcePackCreator;
import net.minecraft.resource.ResourcePackProfile;
import net.minecraft.resource.ResourcePackProvider;
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
    private ResourcePackProvider packProvider;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void onConstruction(ResourceType type, CallbackInfo ci) {
        packProvider = switch (type) {
            case SERVER_DATA -> HTResourcePackProvider.SERVER;
            case CLIENT_RESOURCES -> HTResourcePackProvider.CLIENT;
        };
    }

    @Inject(method = "register(Ljava/util/function/Consumer;Lnet/minecraft/resource/ResourcePackProfile$Factory;)V", at = @At("RETURN"))
    private void ht_materials$register(Consumer<ResourcePackProfile> consumer, ResourcePackProfile.Factory factory, CallbackInfo ci) {
        if (packProvider != null) {
            packProvider.register(consumer, factory);
        }
    }

}