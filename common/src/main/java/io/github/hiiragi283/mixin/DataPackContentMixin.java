package io.github.hiiragi283.mixin;

import com.google.common.collect.ImmutableSet;
import io.github.hiiragi283.api.HTMaterialsAPI;
import net.minecraft.server.DataPackContents;
import net.minecraft.tag.TagKey;
import net.minecraft.tag.TagManagerLoader;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mixin(DataPackContents.class)
public class DataPackContentMixin {

    /**
     * @author Hiiragi283
     * @reason To solve problem when merging tag builder
     */
    @Overwrite
    private static <T> void repopulateTags(DynamicRegistryManager dynamicRegistryManager, TagManagerLoader.RegistryTags<T> tags) {
        RegistryKey<? extends Registry<T>> registryKey = tags.key();
        Map<TagKey<T>, List<RegistryEntry<T>>> map = tags.tags().entrySet().stream().collect(
                Collectors.toUnmodifiableMap(
                        (entry) -> TagKey.of(registryKey, entry.getKey()),
                        (entry) -> entry.getValue().values(),
                        (entry1, entry2) -> {
                            var list = List.copyOf(ImmutableSet.<RegistryEntry<T>>builder()
                                    .addAll(entry1)
                                    .addAll(entry2)
                                    .build());
                            HTMaterialsAPI.log("Merged entries!");
                            list.forEach(entry -> HTMaterialsAPI.log("- " + entry));
                            return list;
                        }
                )
        );
        dynamicRegistryManager.get(registryKey).populateTags(map);
    }

}