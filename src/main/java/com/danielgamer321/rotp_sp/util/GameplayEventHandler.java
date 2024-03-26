package com.danielgamer321.rotp_sp.util;

import com.danielgamer321.rotp_sp.RotpSpAddon;
import com.danielgamer321.rotp_sp.init.AddonPowers;
import com.danielgamer321.rotp_sp.power.impl.nonstand.type.SpeedwagonUtil;
import com.github.standobyte.jojo.power.impl.nonstand.INonStandPower;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = RotpSpAddon.MOD_ID)
public class GameplayEventHandler {

    @SubscribeEvent(priority = EventPriority.LOW, receiveCanceled = true)
    public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        if (event.getCancellationResult() == ActionResultType.PASS && event.getHand() == Hand.MAIN_HAND && !event.getPlayer().isShiftKeyDown()) {
            Entity target = event.getTarget();
            if (target instanceof PlayerEntity) {
                PlayerEntity targetPlayer = (PlayerEntity) target;
                INonStandPower targetPower = INonStandPower.getNonStandPowerOptional(targetPlayer).orElse(null);
                INonStandPower playerPower = INonStandPower.getNonStandPowerOptional(event.getPlayer()).orElse(null);
                if (targetPower != null && playerPower != null &&
                        targetPower.getType() == AddonPowers.SPEEDWAGON.get()
                        && (!playerPower.hasPower() || playerPower.getType().isReplaceableWith(AddonPowers.SPEEDWAGON.get()))) {
                    SpeedwagonUtil.interactWithIntructor(target.level, event.getPlayer(), targetPlayer,
                            targetPower.getTypeSpecificData(AddonPowers.SPEEDWAGON.get()).get());
                    event.setCanceled(true);
                    event.setCancellationResult(ActionResultType.sidedSuccess(target.level.isClientSide));
                }
                else {
                    playerPower.getTypeSpecificData(AddonPowers.SPEEDWAGON.get()).ifPresent(sp -> {
                        sp.interactWithNewLearner(targetPlayer);
                        event.setCanceled(true);
                        event.setCancellationResult(ActionResultType.sidedSuccess(target.level.isClientSide));
                    });
                }
            }
        }
    }
}
