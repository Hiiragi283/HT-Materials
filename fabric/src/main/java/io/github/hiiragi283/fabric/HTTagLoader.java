package io.github.hiiragi283.fabric;

import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.Nullable;

public interface HTTagLoader<T> {
    @Nullable
    Registry<T> ht_materials$getRegistry();
    void ht_materials$setRegistry(Registry<T> registry);
}