package com.danielgamer321.rotp_sp.action.non_stand;

import com.danielgamer321.rotp_sp.entity.mob.*;
import com.danielgamer321.rotp_sp.init.ModSpeedwagonActions;
import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.non_stand.NonStandAction;
import com.github.standobyte.jojo.power.impl.nonstand.INonStandPower;

import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;

import java.util.List;

public class Skill1 extends SpeedwagonAction {
    public Skill1(NonStandAction.Builder builder) {
        super(builder);
    }

    @Override
    public ActionConditionResult checkSpecificConditions(LivingEntity user, INonStandPower power, ActionTarget target) {
        if (!Skill1.getHenchmanStatus(user) || !Skill1.getHenchman2Status(user)) {
            return conditionMessage("no_more_henchmen");
        }
        if (power.getHeldAction() == ModSpeedwagonActions.SKILL2.get()) {
            return ActionConditionResult.NEGATIVE;
        }
        return super.checkSpecificConditions(user, power, target);
    }

    @Override
    protected void perform(World world, LivingEntity user, INonStandPower power, ActionTarget target) {
        if (!world.isClientSide()) {
            HenchmanEntity henchman = new HenchmanEntity(world);
            henchman.setSummonedFromAbility();
            henchman.copyPosition(user);
            henchman.setOwner(user);
            world.addFreshEntity(henchman);
            Henchman2Entity henchman2 = new Henchman2Entity(world);
            henchman2.setSummonedFromAbility();
            henchman2.copyPosition(user);
            henchman2.setOwner(user);
            world.addFreshEntity(henchman2);
        }
    }

    public static boolean getHenchmanStatus(LivingEntity user) {
        List<HenchmanEntity> henchmanPresent = user.level.getEntitiesOfClass(HenchmanEntity.class,
                user.getBoundingBox().inflate(16), string -> user.is(string.getOwner()));
        return !henchmanPresent.isEmpty() ? false : true;
    }

    public static boolean getHenchman2Status(LivingEntity user) {
        List<Henchman2Entity> henchmanPresent = user.level.getEntitiesOfClass(Henchman2Entity.class,
                user.getBoundingBox().inflate(16), string -> user.is(string.getOwner()));
        return !henchmanPresent.isEmpty() ? false : true;
    }
}
