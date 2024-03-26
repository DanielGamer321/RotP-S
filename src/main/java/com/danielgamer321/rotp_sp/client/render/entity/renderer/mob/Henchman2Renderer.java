package com.danielgamer321.rotp_sp.client.render.entity.renderer.mob;

import com.danielgamer321.rotp_sp.RotpSpAddon;
import com.danielgamer321.rotp_sp.client.render.entity.model.mob.HenchmanModel;
import com.danielgamer321.rotp_sp.entity.mob.Henchman2Entity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class Henchman2Renderer extends MobRenderer<Henchman2Entity, HenchmanModel<Henchman2Entity>> {
    protected final ResourceLocation TEXTURE = new ResourceLocation(RotpSpAddon.MOD_ID, "textures/entity/biped/henchman2.png");

    public Henchman2Renderer(EntityRendererManager manager) {
        super(manager, new HenchmanModel<>(), 0.7F);
    }

    @Override
    public ResourceLocation getTextureLocation(Henchman2Entity entity) {
        return TEXTURE;
    }
}
