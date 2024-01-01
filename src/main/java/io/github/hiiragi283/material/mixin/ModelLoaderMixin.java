package io.github.hiiragi283.material.mixin;

import io.github.hiiragi283.material.HTModelLoaderMixin;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ModelLoader.class)
public abstract class ModelLoaderMixin {

    /**
     * @see <a href="https://github.com/Flytre/ResourceCore/blob/master/src/main/java/net/flytre/resource_core/mixin/ModelLoaderMixin.java">GitHub - ResourceCore</a>
     */

    @Final
    @Shadow
    private ResourceManager resourceManager;

    @Inject(
            method = "loadModelFromJson",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/resource/ResourceManager;getResource(Lnet/minecraft/util/Identifier;)Lnet/minecraft/resource/Resource;"
            ),
            cancellable = true
    )
    private void ht_materials$loadModelFromJson(Identifier id, CallbackInfoReturnable<JsonUnbakedModel> cir) {
        HTModelLoaderMixin.loadModelFromJson(id, resourceManager, cir);
    }

}