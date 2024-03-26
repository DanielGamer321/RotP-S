package com.danielgamer321.rotp_sp.client.render.entity.renderer.mob;

import com.danielgamer321.rotp_sp.RotpSpAddon;
import com.danielgamer321.rotp_sp.client.render.entity.model.mob.HenchmanModel;
import com.danielgamer321.rotp_sp.entity.mob.HenchmanEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class HenchmanRenderer extends MobRenderer<HenchmanEntity, HenchmanModel<HenchmanEntity>> {
    protected final ResourceLocation TEXTURE = new ResourceLocation(RotpSpAddon.MOD_ID, "textures/entity/biped/henchman.png");

    public HenchmanRenderer(EntityRendererManager manager) {
        super(manager, new HenchmanModel<>(), 0.7F);
    }

    @Override
    public ResourceLocation getTextureLocation(HenchmanEntity entity) {
        return TEXTURE;
    }
}
