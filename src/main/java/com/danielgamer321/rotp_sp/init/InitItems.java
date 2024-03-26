package com.danielgamer321.rotp_sp.init;

import com.danielgamer321.rotp_sp.RotpSpAddon;
import com.github.standobyte.jojo.JojoMod;

import net.minecraft.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class InitItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, RotpSpAddon.MOD_ID);



    public static final RegistryObject<SpawnEggItem>HENCHMAN_SPAWN_EGG = ITEMS.register("henchman_spawn_egg",
            () -> new ForgeSpawnEggItem(InitEntities.HENCHMAN, 0xC4A160, 0xBF4147, new Item.Properties().tab(JojoMod.MAIN_TAB)));

    public static final RegistryObject<SpawnEggItem>HENCHMAN2_SPAWN_EGG = ITEMS.register("henchman2_spawn_egg",
            () -> new ForgeSpawnEggItem(InitEntities.HENCHMAN2, 0x6D8A7D, 0x54343B, new Item.Properties().tab(JojoMod.MAIN_TAB)));

    public static final RegistryObject<SpawnEggItem>INSTRUCTOR_SPAWN_EGG = ITEMS.register("instructor_spawn_egg",
            () -> new ForgeSpawnEggItem(InitEntities.INSTRUCTOR, 0x2A2833, 0x4F4C70, new Item.Properties().tab(JojoMod.MAIN_TAB)));
}
