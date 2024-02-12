package io.github.hiiragi283.fabric.mixin;

import io.github.hiiragi283.api.part.HTPart;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(TagKey.class)
public class TagKeyMixin {

    @ModifyVariable(method = "<init>", at = @At("HEAD"), ordinal = 1)
    private static Identifier ht_materials$modifyId(Identifier id) {
        HTPart part = HTPart.fromId(id);
        return part != null ? part.getPartId() : id;
    }

}