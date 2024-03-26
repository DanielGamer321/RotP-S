package com.danielgamer321.rotp_sp.action.non_stand;

import com.danielgamer321.rotp_sp.init.ModSpeedwagonActions;
import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.non_stand.NonStandAction;
import com.github.standobyte.jojo.entity.itemprojectile.BladeHatEntity;
import com.github.standobyte.jojo.init.ModSounds;
import com.github.standobyte.jojo.item.BladeHatItem;
import com.github.standobyte.jojo.power.impl.nonstand.INonStandPower;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class Attack1 extends SpeedwagonAction {
    public Attack1(NonStandAction.Builder builder) {
        super(builder);
    }

    @Override
    public ActionConditionResult checkSpecificConditions(LivingEntity user, INonStandPower power, ActionTarget target) {
        ItemStack headArmor = user.getItemBySlot(EquipmentSlotType.HEAD);
        ItemStack mainItem = user.getMainHandItem();
        ItemStack offItem = user.getOffhandItem();
        if ((headArmor == null || headArmor.isEmpty() || !(headArmor.getItem() instanceof BladeHatItem)) && (mainItem == null || mainItem.isEmpty() || !(mainItem.getItem() instanceof BladeHatItem)) && (offItem == null || offItem.isEmpty() || !(offItem.getItem() instanceof BladeHatItem))) {
            return conditionMessage("hat");
        }
        if (power.getHeldAction() == ModSpeedwagonActions.SKILL2.get()) {
            return ActionConditionResult.NEGATIVE;
        }
        return super.checkSpecificConditions(user, power, target);
    }

    @Override
    protected void perform(World world, LivingEntity user, INonStandPower power, ActionTarget target) {
        ItemStack item1 = user.getItemBySlot(EquipmentSlotType.HEAD);
        ItemStack item2 = user.getMainHandItem();
        ItemStack item3 = user.getOffhandItem();
        if (item1.getItem() instanceof BladeHatItem) {
            BladeHatEntity hat = new BladeHatEntity(world, user, item1);
            hat.shootFromRotation(user, 0.75F, 0.5F);
            world.addFreshEntity(hat);
            world.playSound(null, user, ModSounds.BLADE_HAT_THROW.get(), SoundCategory.AMBIENT,
                    Math.min(1.0F, 1.0F), 1.0F + (world.random.nextFloat() - 0.5F) * 0.15F);
            user.getItemBySlot(EquipmentSlotType.HEAD).shrink(1);
        } else {
            if (item2.getItem() instanceof BladeHatItem) {
                BladeHatEntity hat = new BladeHatEntity(world, user, item2);
                hat.shootFromRotation(user, 0.75F, 0.5F);
                world.addFreshEntity(hat);
                world.playSound(null, user, ModSounds.BLADE_HAT_THROW.get(), SoundCategory.AMBIENT,
                        Math.min(1.0F, 1.0F), 1.0F + (world.random.nextFloat() - 0.5F) * 0.15F);
                user.getMainHandItem().shrink(1);
            } else {
                if (item3.getItem() instanceof BladeHatItem) {
                    BladeHatEntity hat = new BladeHatEntity(world, user, item3);
                    hat.shootFromRotation(user, 0.75F, 0.5F);
                    world.addFreshEntity(hat);
                    world.playSound(null, user, ModSounds.BLADE_HAT_THROW.get(), SoundCategory.AMBIENT,
                            Math.min(1.0F, 1.0F), 1.0F + (world.random.nextFloat() - 0.5F) * 0.15F);
                    user.getOffhandItem().shrink(1);
                }
            }
        }
    }
}
