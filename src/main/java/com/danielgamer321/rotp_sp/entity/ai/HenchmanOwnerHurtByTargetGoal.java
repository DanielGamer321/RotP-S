package com.danielgamer321.rotp_sp.entity.ai;

import java.util.EnumSet;

import com.danielgamer321.rotp_sp.entity.mob.HenchmanEntity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.TargetGoal;

public class HenchmanOwnerHurtByTargetGoal extends TargetGoal {
    private final HenchmanEntity henchman;
    private LivingEntity attacker;
    private int timestamp;

    public HenchmanOwnerHurtByTargetGoal(HenchmanEntity henchman) {
        super(henchman, false);
        this.henchman = henchman;
        this.setFlags(EnumSet.of(Goal.Flag.TARGET));
    }

    @Override
    public boolean canUse() {
        LivingEntity owner = henchman.getOwner();
        if (owner == null || henchman.farFromOwner(12)) {
            return false;
        } else {
            attacker = owner.getLastHurtByMob();
            int i = owner.getLastHurtByMobTimestamp();
            return i != timestamp && canAttack(attacker, EntityPredicate.DEFAULT) && henchman.wantsToAttack(attacker, owner);
        }
    }


    @Override
    public void start() {
        mob.setTarget(attacker);
        LivingEntity owner = henchman.getOwner();
        if (owner != null) {
            timestamp = owner.getLastHurtByMobTimestamp();
        }
        super.start();
    }
}