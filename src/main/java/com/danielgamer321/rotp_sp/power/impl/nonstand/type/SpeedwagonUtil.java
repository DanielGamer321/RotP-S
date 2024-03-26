package com.danielgamer321.rotp_sp.power.impl.nonstand.type;

import com.danielgamer321.rotp_sp.init.AddonPowers;
import com.github.standobyte.jojo.capability.entity.PlayerUtilCap;
import com.github.standobyte.jojo.capability.entity.PlayerUtilCapProvider;
import com.github.standobyte.jojo.power.impl.nonstand.INonStandPower;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.*;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import java.util.*;

public class SpeedwagonUtil {

    
    public static void interactWithIntructor(World world, PlayerEntity player, LivingEntity Instructor, SpeedwagonData InstructorSp) {
        INonStandPower.getNonStandPowerOptional(player).ifPresent(power -> {
            Optional<SpeedwagonData> spOptional = power.getTypeSpecificData(AddonPowers.SPEEDWAGON.get());
            if (!spOptional.isPresent() && !world.isClientSide()) {
                if (Instructor instanceof PlayerEntity) {
                    InstructorSp.addNewPlayerLearner(player);
                }
                else {
                    startLearningSp(world, player, power, Instructor, InstructorSp);
                }
            }
        });
    }
    
    public static void startLearningSp(World world, PlayerEntity player, INonStandPower playerPower, LivingEntity Instructor, SpeedwagonData InstructorSp) {
        if (playerPower.givePower(AddonPowers.SPEEDWAGON.get())) {
            playerPower.getTypeSpecificData(AddonPowers.SPEEDWAGON.get()).ifPresent(sp -> {
                player.sendMessage(new TranslationTextComponent("rotp_sp.chat.message.learnt_sp"), Util.NIL_UUID);
                PlayerUtilCap utilCap = player.getCapability(PlayerUtilCapProvider.CAPABILITY).orElseThrow(() -> new IllegalStateException());
            });
        }
        else {
            player.displayClientMessage(new TranslationTextComponent("rotp_sp.chat.message.cant_learn_sp"), true);
        }
        return;
    }
}
