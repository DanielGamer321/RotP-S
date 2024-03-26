package com.danielgamer321.rotp_sp.power;

import com.danielgamer321.rotp_sp.init.ModSpeedwagonActions;
import com.github.standobyte.jojo.init.power.JojoCustomRegistries;
import net.minecraftforge.eventbus.api.IEventBus;

public class SpCustomRegistries extends JojoCustomRegistries {


    public static void initCustomRegistries(IEventBus modEventBus) {
        ModSpeedwagonActions.loadRegistryObjects();
    }
}
