package com.danielgamer321.rotp_sp.action.non_stand;

import com.danielgamer321.rotp_sp.init.ModSpeedwagonActions;
import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.non_stand.NonStandAction;
import com.github.standobyte.jojo.init.ModStatusEffects;
import com.github.standobyte.jojo.power.impl.nonstand.INonStandPower;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.world.World;

public class Skill3 extends SpeedwagonAction {
    public Skill3(NonStandAction.Builder builder) {
        super(builder);
    }

    @Override
    public TargetRequirement getTargetRequirement() {
        return TargetRequirement.ENTITY;
    }

    @Override
    public ActionConditionResult checkSpecificConditions(LivingEntity user, INonStandPower power, ActionTarget target) {
        if (power.getHeldAction() == ModSpeedwagonActions.SKILL2.get()) {
            return ActionConditionResult.NEGATIVE;
        }
        return super.checkSpecificConditions(user, power, target);
    }

    @Override
    protected void perform(World world, LivingEntity user, INonStandPower power, ActionTarget target) {
        if (!world.isClientSide()) {
            if (target.getType() == ActionTarget.TargetType.ENTITY) {
                Entity entityTarget = target.getEntity();
                if (entityTarget instanceof LivingEntity) {
                    LivingEntity targetLiving = (LivingEntity) entityTarget;
                    targetLiving.removeEffect(ModStatusEffects.FREEZE.get());
                }
            }
        }
    }
}
