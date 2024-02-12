package io.github.hiiragi283.mixin;

import io.github.hiiragi283.api.HTTagLoader;
import io.github.hiiragi283.api.HTTagLoaderMixin;
import net.minecraft.resource.ResourceManager;
import net.minecraft.tag.Tag;
import net.minecraft.tag.TagGroupLoader;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(TagGroupLoader.class)
public abstract class TagGroupLoaderMixin<T> implements HTTagLoader<T> {

    @Inject(method = "loadTags", at = @At(value = "RETURN"))
    private void ht_materials$loadTags(ResourceManager manager, CallbackInfoReturnable<Map<Identifier, Tag.Builder>> cir) {
        HTTagLoaderMixin.loadTags(cir.getReturnValue(), ht_materials$getRegistry());
    }

    //    HTTagLoader    //

    @Nullable
    @Unique
    private Registry<T> registry;

    @Nullable
    @Override
    public Registry<T> ht_materials$getRegistry() {
        return registry;
    }

    @Override
    public void ht_materials$setRegistry(Registry<T> registry) {
        this.registry = registry;
    }

}