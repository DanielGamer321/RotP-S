package com.danielgamer321.rotp_sp.util;

import com.danielgamer321.rotp_sp.RotpSpAddon;
import com.danielgamer321.rotp_sp.network.PacketManager;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@EventBusSubscriber(modid = RotpSpAddon.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class CommonSetup {
    
    @SubscribeEvent
    public static void onFMLCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            PacketManager.init();
        });
    }

}
