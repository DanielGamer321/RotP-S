package com.danielgamer321.rotp_sp.entity.ai;

import java.util.function.Predicate;

import com.danielgamer321.rotp_sp.entity.mob.HenchmanEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;

public class HenchmanNearestAttackableTargetGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
    private final HenchmanEntity henchman;

    public HenchmanNearestAttackableTargetGoal(HenchmanEntity henchman, Class<T> targetClass, boolean mustSee) {
        this(henchman, targetClass, mustSee, false);
    }

    public HenchmanNearestAttackableTargetGoal(HenchmanEntity henchman, Class<T> targetClass, boolean mustSee,
            boolean mustReach) {
        this(henchman, targetClass, 10, mustSee, mustReach, null);
    }

    public HenchmanNearestAttackableTargetGoal(HenchmanEntity henchman, Class<T> targetClass, int randomInterval,
            boolean mustSee, boolean mustReach, Predicate<LivingEntity> targetCondition) {
        super(henchman, targetClass, randomInterval, mustSee, mustReach, targetCondition);
        this.henchman = henchman;
    }
    
    @Override
    public boolean canUse() {
        if (super.canUse()) {
            LivingEntity owner = henchman.getOwner();
            if (owner == null || henchman.farFromOwner(16)) {
                return true;
            }
        }
        return false;
    }

}
