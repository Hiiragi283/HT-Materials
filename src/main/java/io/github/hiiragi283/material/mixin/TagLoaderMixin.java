package io.github.hiiragi283.material.mixin;

import io.github.hiiragi283.material.HTTagLoaderMixin;
import io.github.hiiragi283.material.util.HTTagLoader;
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

/**
 * Reference: <a href="https://github.com/GregTechCEu/GregTech-Modern/blob/1.20.1/common/src/main/java/com/gregtechceu/gtceu/core/mixins/TagLoaderMixin.java">...</a>
 *
 * @param <T>
 */

@Mixin(TagGroupLoader.class)
public class TagLoaderMixin<T> implements HTTagLoader<T> {

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