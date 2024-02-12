package io.github.hiiragi283.fabric.mixin;

import io.github.hiiragi283.fabric.HTTagLoader;
import net.minecraft.resource.ResourceManager;
import net.minecraft.tag.TagGroupLoader;
import net.minecraft.tag.TagManagerLoader;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@SuppressWarnings("unchecked")
@Mixin(TagManagerLoader.class)
public abstract class TagManagerLoaderMixin {

    @Inject(method = "buildRequiredGroup", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/tag/TagGroupLoader;<init>(Ljava/util/function/Function;Ljava/lang/String;)V",
            shift = At.Shift.BY,
            by = 2
    ), locals = LocalCapture.CAPTURE_FAILHARD)
    private <T> void ht_materials$getRegistryToTagLoader(
            ResourceManager resourceManager,
            Executor prepareExecutor,
            DynamicRegistryManager.Entry<T> requirement,
            CallbackInfoReturnable<CompletableFuture<TagManagerLoader.RegistryTags<T>>> cir,
            RegistryKey<T> registryKey,
            Registry<T> registry,
            TagGroupLoader<T> tagGroupLoader
    ) {
        ((HTTagLoader<T>) tagGroupLoader).ht_materials$setRegistry(registry);
    }

}