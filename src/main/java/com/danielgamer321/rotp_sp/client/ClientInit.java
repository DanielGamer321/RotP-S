package com.danielgamer321.rotp_sp.client;

import com.danielgamer321.rotp_sp.RotpSpAddon;
import com.danielgamer321.rotp_sp.client.render.entity.renderer.mob.*;
import com.danielgamer321.rotp_sp.init.InitEntities;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = RotpSpAddon.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientInit {

    @SubscribeEvent
    public static void onFMLClientSetup(FMLClientSetupEvent event) {

        RenderingRegistry.registerEntityRenderingHandler(InitEntities.HENCHMAN.get(), HenchmanRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(InitEntities.HENCHMAN2.get(), Henchman2Renderer::new);
        RenderingRegistry.registerEntityRenderingHandler(InitEntities.INSTRUCTOR.get(), InstructorRenderer::new);
    }
}
