package com.danielgamer321.rotp_sp.init;

import com.danielgamer321.rotp_sp.RotpSpAddon;
import com.danielgamer321.rotp_sp.RotpSpConfig;
import com.danielgamer321.rotp_sp.world.gen.structures.*;
import com.danielgamer321.rotp_sp.util.ForgeBusEventSubscriber;
import com.github.standobyte.jojo.util.mc.reflection.CommonReflection;
import com.github.standobyte.jojo.world.gen.ConfiguredStructureSupplier;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

@EventBusSubscriber(modid = RotpSpAddon.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class InitStructures {
    public static final DeferredRegister<Structure<?>> STRUCTURES = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, RotpSpAddon.MOD_ID);
    

    public static final RegistryObject<Structure<NoFeatureConfig>> INSTRUCTORS_HOUSE = STRUCTURES.register("instructors_house",
            () -> (new InstructorsHouseStructure(NoFeatureConfig.CODEC)));
    public static final ConfiguredStructureSupplier<?, ?> CONFIGURED_INSTRUCTORS_HOUSE = new ConfiguredStructureSupplier<>(INSTRUCTORS_HOUSE, IFeatureConfig.NONE);

    public static final Predicate<BiomeLoadingEvent> INSTRUCTORS_HOUSE_BIOMES = biome -> biome.getCategory() == Biome.Category.PLAINS
            || biome.getCategory() == Biome.Category.FOREST
            || biome.getCategory() == Biome.Category.TAIGA
            || biome.getClimate().precipitation == Biome.RainType.SNOW && biome.getCategory() != Biome.Category.OCEAN;
    
    
    @SubscribeEvent(priority = EventPriority.LOW)
    public static final void afterStructuresRegister(RegistryEvent.Register<Structure<?>> event) {
        Registry<StructureFeature<?, ?>> registry = WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE;

        setupMapSpacingAndLand(INSTRUCTORS_HOUSE.get(), new StructureSeparationSettings(20, 8, 114284120), true);

        InstructorsHousePieces.initPieceType();

        registerConfiguredStructure(registry, CONFIGURED_INSTRUCTORS_HOUSE.get(),
                new ResourceLocation(RotpSpAddon.MOD_ID, "configured_instructors_house"), INSTRUCTORS_HOUSE.get(),
                INSTRUCTORS_HOUSE_BIOMES.and(b -> RotpSpConfig.getCommonConfigInstance(false).instructorsHouseSpawn.get()));
    }
    
    private static <F extends Structure<?>> void setupMapSpacingAndLand(
            F structure,
            StructureSeparationSettings structureSeparationSettings,
            boolean transformSurroundingLand) {
        Structure.STRUCTURES_REGISTRY.put(structure.getRegistryName().toString(), structure);

        if (transformSurroundingLand) {
            Structure.NOISE_AFFECTING_FEATURES = ImmutableList.<Structure<?>>builder()
                    .addAll(Structure.NOISE_AFFECTING_FEATURES)
                    .add(structure)
                    .build();
        }

        DimensionStructuresSettings.DEFAULTS = ImmutableMap.<Structure<?>, StructureSeparationSettings>builder()
                .putAll(DimensionStructuresSettings.DEFAULTS)
                .put(structure, structureSeparationSettings)
                .build();

        WorldGenRegistries.NOISE_GENERATOR_SETTINGS.entrySet().forEach(settings -> {
            Map<Structure<?>, StructureSeparationSettings> structureMap = settings.getValue().structureSettings().structureConfig();
            
            if (structureMap instanceof ImmutableMap) {
                Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(structureMap);
                tempMap.put(structure, structureSeparationSettings);
                settings.getValue().structureSettings().structureConfig = tempMap;
            }
            else{
                structureMap.put(structure, structureSeparationSettings);
            }
        });
    }
    
    private static void registerConfiguredStructure(Registry<StructureFeature<?, ?>> registry, StructureFeature<?, ?> configured, 
            ResourceLocation resLoc, Structure<?> structure, @Nullable Predicate<BiomeLoadingEvent> structureBiome) {
        Registry.register(registry, resLoc, configured);
        CommonReflection.flatGenSettingsStructures().put(structure, configured);
        if (structureBiome != null) {
            ForgeBusEventSubscriber.structureBiomes.put(() -> configured, structureBiome);
        }
    }
}
