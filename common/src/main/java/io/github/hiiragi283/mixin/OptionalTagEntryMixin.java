package io.github.hiiragi283.mixin;

import io.github.hiiragi283.api.part.HTPart;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Tag.OptionalTagEntry.class)
public class OptionalTagEntryMixin {

    @ModifyVariable(method = "<init>", at = @At("HEAD"), argsOnly = true)
    private static Identifier injected(Identifier id) {
        HTPart part = HTPart.fromId(id);
        return part != null ? part.getPartId() : id;
    }

}