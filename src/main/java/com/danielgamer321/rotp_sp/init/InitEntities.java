package com.danielgamer321.rotp_sp.init;

import com.danielgamer321.rotp_sp.RotpSpAddon;
import com.danielgamer321.rotp_sp.entity.mob.*;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = RotpSpAddon.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class InitEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, RotpSpAddon.MOD_ID);

    public static final RegistryObject<EntityType<HenchmanEntity>> HENCHMAN = ENTITIES.register("henchman",
                    () -> EntityType.Builder.<HenchmanEntity>of(HenchmanEntity::new, EntityClassification.CREATURE).sized(0.6F, 1.95F)
                            .build(new ResourceLocation(RotpSpAddon.MOD_ID, "henchman").toString()));

    public static final RegistryObject<EntityType<Henchman2Entity>> HENCHMAN2 = ENTITIES.register("henchman2",
            () -> EntityType.Builder.<Henchman2Entity>of(Henchman2Entity::new, EntityClassification.CREATURE).sized(0.6F, 1.95F)
                    .build(new ResourceLocation(RotpSpAddon.MOD_ID, "henchman2").toString()));

    public static final RegistryObject<EntityType<InstructorEntity>> INSTRUCTOR = ENTITIES.register("instructor",
            () -> EntityType.Builder.<InstructorEntity>of(InstructorEntity::new, EntityClassification.MISC).sized(0.6F, 1.95F)
                    .build(new ResourceLocation(RotpSpAddon.MOD_ID, "instructor").toString()));



    @SubscribeEvent
    public static void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
        event.put(InitEntities.HENCHMAN.get(), HenchmanEntity.createAttributes().build());
        event.put(InitEntities.HENCHMAN2.get(), Henchman2Entity.createAttributes().build());
        event.put(InitEntities.INSTRUCTOR.get(), InstructorEntity.createAttributes().build());
    }

    public static void register(IEventBus eventBus){
        InitEntities.ENTITIES.register(eventBus);
    }
}
