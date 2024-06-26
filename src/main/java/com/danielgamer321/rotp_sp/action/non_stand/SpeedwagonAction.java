package com.danielgamer321.rotp_sp.action.non_stand;

import com.danielgamer321.rotp_sp.init.AddonPowers;
import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.non_stand.NonStandAction;
import com.github.standobyte.jojo.power.impl.nonstand.INonStandPower;
import net.minecraft.entity.LivingEntity;

public abstract class SpeedwagonAction extends NonStandAction {

    public SpeedwagonAction(Builder builder) {
        super(builder);
    }
    
    @Override
    public ActionConditionResult checkConditions(LivingEntity user, INonStandPower power, ActionTarget target) {
        ActionConditionResult spCheck = power.getTypeSpecificData(AddonPowers.SPEEDWAGON.get()).map(sp -> {
            return ActionConditionResult.POSITIVE;
        }).orElse(ActionConditionResult.NEGATIVE);
        if (!spCheck.isPositive()) {
            return spCheck;
        }

        return super.checkConditions(user, power, target);
    }
}
