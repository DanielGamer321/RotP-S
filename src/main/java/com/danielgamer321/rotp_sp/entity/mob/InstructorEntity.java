package com.danielgamer321.rotp_sp.entity.mob;

import com.danielgamer321.rotp_sp.init.AddonPowers;
import com.danielgamer321.rotp_sp.power.impl.nonstand.type.SpeedwagonData;
import com.danielgamer321.rotp_sp.power.impl.nonstand.type.SpeedwagonUtil;
import com.github.standobyte.jojo.entity.mob.IMobPowerUser;
import com.github.standobyte.jojo.power.impl.nonstand.INonStandPower;
import com.github.standobyte.jojo.power.impl.nonstand.NonStandPower;
import com.github.standobyte.jojo.util.mc.MCUtil;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class InstructorEntity extends MobEntity implements INPC, IMobPowerUser, IEntityAdditionalSpawnData {
    private final INonStandPower spPower = new NonStandPower(this);
    @Deprecated
    private boolean reAddBaseSp;
    public InstructorEntity(EntityType<? extends MobEntity> type, World world) {
        super(type, world);
    }

    @Override
    public boolean removeWhenFarAway(double distanceFromPlayer) {
        return false;
    }

    @Override
    public boolean requiresCustomPersistence() {
        return true;
    }

    @Override
    public ActionResultType mobInteract(PlayerEntity player, Hand hand) {
        if (hand == Hand.MAIN_HAND) {
            getPower().getTypeSpecificData(AddonPowers.SPEEDWAGON.get()).ifPresent(sp -> {
                SpeedwagonUtil.interactWithIntructor(level, player, this, sp);
            });
        }
        return super.mobInteract(player, hand);
    }

    @Override
    public INonStandPower getPower() {
        return spPower;
    }

    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld world, DifficultyInstance difficulty, SpawnReason reason,
                                           @Nullable ILivingEntityData additionalData, @Nullable CompoundNBT nbt) {
        addIntructor(spPower);
        setPersistenceRequired();

        return super.finalizeSpawn(world, difficulty, reason, additionalData, nbt);
    }

    @Deprecated
    private void restoreSp() {
        if (reAddBaseSp && !level.isClientSide()) {
            addIntructor(spPower);
            reAddBaseSp = false;
        }
    }

    public void addIntructor(INonStandPower power) {
        spPower.givePower(AddonPowers.SPEEDWAGON.get());
        SpeedwagonData sp = spPower.getTypeSpecificData(AddonPowers.SPEEDWAGON.get()).get();
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.put("SpPower", spPower.writeNBT());
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT nbt) {
        super.readAdditionalSaveData(nbt);
        if (nbt.contains("SpPower", MCUtil.getNbtId(CompoundNBT.class))) {
            spPower.readNBT(nbt.getCompound("SpPower"));
        }
        reAddBaseSp = true;
    }

    @Deprecated
    @Override
    public void writeSpawnData(PacketBuffer buffer) {
        restoreSp();
    }

    @Deprecated
    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Deprecated
    @Override
    public void readSpawnData(PacketBuffer additionalData) {}

    @Override
    protected void registerGoals() {}

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.ATTACK_DAMAGE, 3.0D)
                .add(Attributes.ATTACK_SPEED, 0.3F)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(ForgeMod.SWIM_SPEED.get(), 2.0D);
    }
}
