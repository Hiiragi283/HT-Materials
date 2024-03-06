package io.github.hiiragi283.material.mixin;

import io.github.hiiragi283.api.tag.GlobalTagEvent;
import net.minecraft.registry.tag.TagGroupLoader;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Mixin(TagGroupLoader.class)
public abstract class TagGroupLoaderMixin<T> {

    @Final
    @Shadow
    private String dataType;

    @Inject(method = "buildGroup", at = @At("HEAD"))
    private void ht_materials$buildGroup(Map<Identifier, List<TagGroupLoader.TrackedEntry>> tags, CallbackInfoReturnable<Map<Identifier, Collection<T>>> cir) {
        // Invoke each event
        // HTMaterialsAPI.log("current tag type; " + dataType);
        switch (dataType) {
            case "tags/blocks": {
                GlobalTagEvent.getBLOCK().invoker().register(new GlobalTagEvent.Handler(tags));
                break;
            }
            case "tags/items": {
                GlobalTagEvent.getITEM().invoker().register(new GlobalTagEvent.Handler(tags));
                break;
            }
            case "tags/fluids": {
                GlobalTagEvent.getFLUID().invoker().register(new GlobalTagEvent.Handler(tags));
                break;
            }
            case "tags/entity_types": {
                GlobalTagEvent.getENTITY_TYPE().invoker().register(new GlobalTagEvent.Handler(tags));
                break;
            }
            default:
                break;
        }
        // Remove empty Builder
        new HashMap<>(tags).forEach((Identifier id, List<TagGroupLoader.TrackedEntry> list) -> {
            if (list.isEmpty()) tags.remove(id);
        });
        // HTMaterialsAPI.log("Removed empty tag builders!");
    }
}