package com.danielgamer321.rotp_sp.init;

import com.danielgamer321.rotp_sp.action.non_stand.*;
import com.danielgamer321.rotp_sp.power.impl.nonstand.type.SpeedwagonPowerType;
import com.github.standobyte.jojo.action.non_stand.NonStandAction;

import static com.github.standobyte.jojo.init.power.ModCommonRegisters.ACTIONS;
import static com.github.standobyte.jojo.init.power.ModCommonRegisters.NON_STAND_POWERS;

import net.minecraftforge.fml.RegistryObject;
import net.minecraft.world.spawner.WanderingTraderSpawner;
import net.minecraft.server.MinecraftServer;

public class ModSpeedwagonActions {
    
    public static void loadRegistryObjects() {}
    
    public static final RegistryObject<SpeedwagonAction> ATTACK1 = ACTIONS.register("attack1",
            () -> new Attack1(new NonStandAction.Builder().energyCost(250F)));

    public static final RegistryObject<SpeedwagonAction> ATTACK2 = ACTIONS.register("attack2",
            () -> new Attack2(new NonStandAction.Builder().energyCost(750F).cooldown(20)));

    public static final RegistryObject<SpeedwagonAction> SKILL1 = ACTIONS.register("skill1",
            () -> new Skill1(new NonStandAction.Builder().energyCost(50F).cooldown(200)));

    public static final RegistryObject<SpeedwagonAction> SKILL2 = ACTIONS.register("skill2",
            () -> new Skill2(new NonStandAction.Builder().holdType().holdEnergyCost(30F)));

    public static final RegistryObject<SpeedwagonAction> SKILL3 = ACTIONS.register("skill3",
            () -> new Skill3(new NonStandAction.Builder().holdToFire(60, false).energyCost(100F)));



    public static final RegistryObject<SpeedwagonPowerType> SPEEDWAGON = NON_STAND_POWERS.register("speedwagon",
            () -> new SpeedwagonPowerType(
                    new SpeedwagonAction[] {
                            ATTACK1.get(),
                            ATTACK2.get()},
                    new SpeedwagonAction[] {
                            SKILL1.get(),
                            SKILL2.get(),
                            SKILL3.get()}
                    ));

}
