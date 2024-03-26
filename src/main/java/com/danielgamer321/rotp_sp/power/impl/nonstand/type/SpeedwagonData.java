package com.danielgamer321.rotp_sp.power.impl.nonstand.type;

import com.github.standobyte.jojo.JojoMod;
import com.github.standobyte.jojo.action.Action;
import com.github.standobyte.jojo.client.ClientUtil;
import com.github.standobyte.jojo.client.ui.actionshud.BarsRenderer;
import com.github.standobyte.jojo.client.ui.actionshud.BarsRenderer.BarType;
import com.github.standobyte.jojo.power.impl.nonstand.INonStandPower;
import com.github.standobyte.jojo.power.impl.nonstand.TypeSpecificData;

import com.danielgamer321.rotp_sp.init.ModSpeedwagonActions;
import com.github.standobyte.jojo.power.impl.nonstand.type.hamon.HamonUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@EventBusSubscriber(modid = JojoMod.MOD_ID)
public class SpeedwagonData extends TypeSpecificData {

    private Set<PlayerEntity> newLearners = new HashSet<>();

    private int noEnergyDecayTicks = 0;
    public SpeedwagonData() {
    }

    public void tick() {
        LivingEntity user = power.getUser();
        if (user.isAlive()) {
            if (!user.level.isClientSide()) {
                tickNewPlayerLearners(user);
            }
        }
    }
    
    public float tickEnergy() {
        LivingEntity user = power.getUser();
        if (user.getAirSupply() >= user.getMaxAirSupply()) {
            if (user.level.isClientSide() && power.getEnergy() > 0) {
                if (user == ClientUtil.getClientPlayer()) {
                    BarsRenderer.getBarEffects(BarType.ENERGY_VAMPIRE).resetRedHighlight();
                }
            }
            return power.getEnergy()+10;
        }
        else {
            if (noEnergyDecayTicks > 0) {
                noEnergyDecayTicks--;
                return power.getEnergy();
            }
            else {
                return power.getEnergy();
            }
        }
    }

    public float getMaxEnergy() {
        return 1000;
    }
    
    @Override
    public boolean isActionUnlocked(Action<INonStandPower> action, INonStandPower powerData) {
        return action == ModSpeedwagonActions.ATTACK1.get()
                || action == ModSpeedwagonActions.ATTACK2.get()
                || action == ModSpeedwagonActions.SKILL1.get()
                || action == ModSpeedwagonActions.SKILL2.get()
                || action == ModSpeedwagonActions.SKILL3.get();
    }



    public void addNewPlayerLearner(PlayerEntity player) {
        newLearners.add(player);
        LivingEntity user = power.getUser();
        if (user instanceof PlayerEntity) {
            ((PlayerEntity) user).displayClientMessage(new TranslationTextComponent("rotp_sp.chat.message.new_sp_learner", player.getDisplayName()), true);
        }
    }

    private void tickNewPlayerLearners(LivingEntity user) {
        for (Iterator<PlayerEntity> it = newLearners.iterator(); it.hasNext(); ) {
            PlayerEntity player = it.next();
            if (user.distanceToSqr(player) > 64) {
                it.remove();
            }
        }
    }

    public void interactWithNewLearner(PlayerEntity player) {
        if (newLearners.contains(player)) {
            SpeedwagonUtil.startLearningSp(player.level, player, INonStandPower.getPlayerNonStandPower(player), power.getUser(), this);
            newLearners.remove(player);
        }
    }

    @Override
    public CompoundNBT writeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        return nbt;
    }

    @Override
    public void readNBT(CompoundNBT nbt) {

    }

    @Override
    public void syncWithUserOnly(ServerPlayerEntity user) {

    }

    @Override
    public void syncWithTrackingOrUser(LivingEntity user, ServerPlayerEntity entity) {

    }
}
