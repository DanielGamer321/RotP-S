package com.danielgamer321.rotp_sp;

import com.danielgamer321.rotp_sp.power.SpCustomRegistries;
import com.danielgamer321.rotp_sp.init.*;

import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(RotpSpAddon.MOD_ID)
public class RotpSpAddon {
    // The value here should match an entry in the META-INF/mods.toml file
    public static final String MOD_ID = "rotp_sp";
    private static final Logger LOGGER = LogManager.getLogger();

    public RotpSpAddon() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, RotpSpConfig.commonSpec);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, RotpSpConfig.clientSpec);

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        InitEntities.ENTITIES.register(modEventBus);
        InitSounds.SOUNDS.register(modEventBus);
        InitItems.ITEMS.register(modEventBus);
        InitStructures.STRUCTURES.register(modEventBus);

        SpCustomRegistries.initCustomRegistries(modEventBus);
    }

    public static Logger getLogger() {
        return LOGGER;
    }
}
