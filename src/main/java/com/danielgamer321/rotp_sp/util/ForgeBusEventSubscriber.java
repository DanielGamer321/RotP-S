package com.danielgamer321.rotp_sp.util;

import com.danielgamer321.rotp_sp.RotpSpAddon;
import com.danielgamer321.rotp_sp.init.InitStructures;
import com.github.standobyte.jojo.util.mc.reflection.CommonReflection;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.gen.FlatChunkGenerator;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.Supplier;

@EventBusSubscriber(modid = RotpSpAddon.MOD_ID)
public class ForgeBusEventSubscriber {
    @SubscribeEvent
    public static void onWorldLoad(WorldEvent.Load event) {
        if (event.getWorld() instanceof ServerWorld) {
            ServerWorld serverWorld = (ServerWorld) event.getWorld();
            addDimensionalSpacing(serverWorld);
        }
    }
    
    private static void addDimensionalSpacing(ServerWorld serverWorld) {
        ResourceLocation cgRL = Registry.CHUNK_GENERATOR.getKey(CommonReflection.getCodec(serverWorld.getChunkSource().getGenerator()));
        if (cgRL != null && cgRL.getNamespace().equals("terraforged")) {
            return;
        }
        
        if (serverWorld.getChunkSource().getGenerator() instanceof FlatChunkGenerator && serverWorld.dimension().equals(World.OVERWORLD)) {
            return;
        }

        Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(
                serverWorld.getChunkSource().getGenerator().getSettings().structureConfig());
        for (RegistryObject<Structure<?>> structure : InitStructures.STRUCTURES.getEntries()) {
            tempMap.putIfAbsent(structure.get(), DimensionStructuresSettings.DEFAULTS.get(structure.get()));
        }
        serverWorld.getChunkSource().getGenerator().getSettings().structureConfig = tempMap;
    }

    public static final Map<Supplier<StructureFeature<?, ?>>, Predicate<BiomeLoadingEvent>> structureBiomes = new HashMap<>();
    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onBiomeLoading(BiomeLoadingEvent event) {
        List<Supplier<StructureFeature<?, ?>>> structureStarts = event.getGeneration().getStructures();
        
        for (Map.Entry<Supplier<StructureFeature<?, ?>>, Predicate<BiomeLoadingEvent>> entry : structureBiomes.entrySet()) {
            if (entry.getValue() != null && entry.getValue().test(event)) {
                structureStarts.add(entry.getKey());
            }
        }
    }
}
