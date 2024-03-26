package com.danielgamer321.rotp_sp.power.impl.nonstand.type;

import com.danielgamer321.rotp_sp.RotpSpConfig;
import com.github.standobyte.jojo.action.non_stand.NonStandAction;
import com.github.standobyte.jojo.init.power.non_stand.ModPowers;
import com.github.standobyte.jojo.power.impl.nonstand.INonStandPower;
import com.github.standobyte.jojo.power.impl.nonstand.type.NonStandPowerType;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;

import net.minecraft.entity.LivingEntity;

public class SpeedwagonPowerType extends NonStandPowerType<SpeedwagonData> {
    public static final int COLOR = 0x2A2833;

    public SpeedwagonPowerType(NonStandAction[] startingAttacks, NonStandAction[] startingAbilities) {
        super(startingAttacks, startingAbilities, startingAbilities[0], SpeedwagonData::new);
    }
    
    @Override
    public boolean keepOnDeath(INonStandPower power) {
        return RotpSpConfig.getCommonConfigInstance(false).keepSpOnDeath.get();
    }

    @Override
    public float getTargetResolveMultiplier(INonStandPower power, IStandPower attackingStand) {
        return 2;
    }
    
    @Override
    public float getMaxEnergy(INonStandPower power) {
        return power.getTypeSpecificData(this).get().getMaxEnergy();
    }

    @Override
    public float tickEnergy(INonStandPower power) {
        return power.getTypeSpecificData(this).get().tickEnergy();
    }

    @Override
    public float getStaminaRegenFactor(INonStandPower power, IStandPower standPower) {
        return 100F;
    }

    @Override
    public void tickUser(LivingEntity user, INonStandPower power) {
    }

    @Override
    public boolean isReplaceableWith(NonStandPowerType<?> newType) {
        return newType == ModPowers.VAMPIRISM.get() || newType == ModPowers.HAMON.get();
    }
}
