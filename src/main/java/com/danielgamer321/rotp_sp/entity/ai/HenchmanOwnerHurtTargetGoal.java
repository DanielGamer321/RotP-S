package com.danielgamer321.rotp_sp.entity.ai;

import java.util.EnumSet;

import com.danielgamer321.rotp_sp.entity.mob.HenchmanEntity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.util.DamageSource;

public class HenchmanOwnerHurtTargetGoal extends TargetGoal {
    private final HenchmanEntity henchman;
    private LivingEntity attacked;
    private int timestamp;

    public HenchmanOwnerHurtTargetGoal(HenchmanEntity henchman) {
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
            this.attacked = owner.getLastHurtMob();
            int i = owner.getLastHurtMobTimestamp();
            DamageSource attackedBy = attacked != null ? attacked.getLastDamageSource() : null;
            return i != timestamp && !(attackedBy != null && !attackedBy.getMsgId().startsWith("bloodDrain")) &&
                    canAttack(attacked, EntityPredicate.DEFAULT) && henchman.wantsToAttack(attacked, owner);
        }
    }

    @Override
    public void start() {
        mob.setTarget(attacked);
        LivingEntity owner = henchman.getOwner();
        if (owner != null) {
            timestamp = owner.getLastHurtMobTimestamp();
        }
        super.start();
    }
}