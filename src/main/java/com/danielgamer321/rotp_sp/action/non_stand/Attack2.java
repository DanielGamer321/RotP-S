package com.danielgamer321.rotp_sp.action.non_stand;

import com.danielgamer321.rotp_sp.init.ModSpeedwagonActions;
import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.non_stand.NonStandAction;
import com.github.standobyte.jojo.item.SledgehammerItem;
import com.github.standobyte.jojo.power.impl.nonstand.INonStandPower;
import com.github.standobyte.jojo.util.mc.damage.DamageUtil;
import com.github.standobyte.jojo.util.mc.reflection.CommonReflection;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class Attack2 extends SpeedwagonAction {
    public Attack2(NonStandAction.Builder builder) {
        super(builder);
    }

    @Override
    public TargetRequirement getTargetRequirement() {
        return TargetRequirement.ENTITY;
    }

    @Override
    public ActionConditionResult checkSpecificConditions(LivingEntity user, INonStandPower power, ActionTarget target) {
        ItemStack mainItem = user.getMainHandItem();
        ItemStack offItem = user.getOffhandItem();
        if ((mainItem == null || mainItem.isEmpty() || !(mainItem.getItem() instanceof SledgehammerItem)) && (offItem == null || offItem.isEmpty() || !(offItem.getItem() instanceof SledgehammerItem))) {
            return conditionMessage("hammer");
        }
        if (power.getHeldAction() == ModSpeedwagonActions.SKILL2.get()) {
            return ActionConditionResult.NEGATIVE;
        }
        return super.checkSpecificConditions(user, power, target);
    }

    @Override
    protected void perform(World world, LivingEntity user, INonStandPower power, ActionTarget target) {
        if (!world.isClientSide() && target.getType() == ActionTarget.TargetType.ENTITY) {
            Entity entity = target.getEntity();
            if (entity instanceof LivingEntity) {
                LivingEntity targetEntity = (LivingEntity) entity;
                if (dealDamage(targetEntity, power)) {
                }
                int attackStrengthTicker = CommonReflection.getAttackStrengthTicker(user);
                CommonReflection.setAttackStrengthTicker(user, attackStrengthTicker);
            }
        }
    }

    @Override
    public void onPerform(World world, LivingEntity user, INonStandPower power, ActionTarget target) {
        perform(world, user, power, target);
    }

    protected float getDamage() {
        return 12.0F;
    }

    protected boolean dealDamage(LivingEntity targetEntity, INonStandPower power) {
        PlayerEntity player = (PlayerEntity) power.getUser();
        return DamageUtil.hurtThroughInvulTicks(targetEntity, DamageSource.playerAttack(player), getDamage());
    }
}
