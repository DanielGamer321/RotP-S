package com.danielgamer321.rotp_sp.entity.ai;

import java.util.EnumSet;

import com.danielgamer321.rotp_sp.entity.mob.HenchmanEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;

public class HenchmanFollowOwnerGoal extends Goal {
    private final HenchmanEntity henchman;
    private LivingEntity owner;
    private final double followSpeed;
    private final PathNavigator navigator;
    private int timeToRecalcPath;
    private final float maxDist;
    private final float minDist;
    private float oldWaterCost;

    public HenchmanFollowOwnerGoal(HenchmanEntity henchman, double followSpeed, float minDist, float maxDist, boolean canFly) {
        this.henchman = henchman;
        this.followSpeed = followSpeed;
        this.navigator = henchman.getNavigation();
        this.minDist = minDist;
        this.maxDist = maxDist;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        if (!(henchman.getNavigation() instanceof GroundPathNavigator) && !(henchman.getNavigation() instanceof FlyingPathNavigator)) {
            throw new IllegalArgumentException("Unsupported mob type for FollowOwnerGoal");
        }
    }

    @Override
    public boolean canUse() {
        LivingEntity owner = henchman.getOwner();
        if (owner == null) {
            return false;
        } else if (owner.isSpectator()) {
            return false;
        } else if (henchman.distanceToSqr(owner) < (double) (minDist * minDist)) {
            return false;
        } else {
            this.owner = owner;
            return true;
        }
    }

    @Override
    public boolean canContinueToUse() {
        if (navigator.isDone()) {
            return false;
        } else {
            return !(henchman.distanceToSqr(owner) <= (double) (maxDist * maxDist));
        }
    }

    @Override
    public void start() {
        timeToRecalcPath = 0;
        oldWaterCost = henchman.getPathfindingMalus(PathNodeType.WATER);
        henchman.setPathfindingMalus(PathNodeType.WATER, 0.0F);
    }

    @Override
    public void stop() {
        owner = null;
        navigator.stop();
        henchman.setPathfindingMalus(PathNodeType.WATER, this.oldWaterCost);
    }

    @Override
    public void tick() {
        henchman.getLookControl().setLookAt(owner, 10.0F, (float) henchman.getMaxHeadXRot());
        if (--timeToRecalcPath <= 0) {
            timeToRecalcPath = 10;
            if (!henchman.isLeashed() && !henchman.isPassenger() && !henchman.farFromOwner(12)) {
                navigator.moveTo(owner, followSpeed);
            }
        }
    }
}