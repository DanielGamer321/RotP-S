package com.danielgamer321.rotp_sp.world.gen.structures;

import com.danielgamer321.rotp_sp.RotpSpAddon;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.structure.TemplateStructurePiece;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;

import java.util.List;
import java.util.Random;

public class InstructorsHousePieces {
    private static IStructurePieceType INSTRUCTORS_HOUSE_PIECES;
    
    private static final ResourceLocation PIECE_BUILDING = new ResourceLocation(RotpSpAddon.MOD_ID, "instructors_house/instructors_house");
    
    public static void start(TemplateManager templateManager, BlockPos blockPos, List<StructurePiece> pieces, Random random) {
        Rotation rotation = Rotation.values()[random.nextInt(Rotation.values().length)];
        pieces.add(new Piece(templateManager, PIECE_BUILDING, blockPos, new BlockPos(0, 1, 0), rotation));
    }
    
    public static void initPieceType() {
        INSTRUCTORS_HOUSE_PIECES = IStructurePieceType.setPieceId(Piece::new, RotpSpAddon.MOD_ID + ":instructors_house");
    }

    private static class Piece extends TemplateStructurePiece {
        private final ResourceLocation piece;
        private final Rotation rotation;

        public Piece(TemplateManager templateManager, ResourceLocation piece, BlockPos blockPos, BlockPos pieceOffset, Rotation rotation) {
            super(INSTRUCTORS_HOUSE_PIECES, 0);
            this.piece = piece;
            this.templatePosition = blockPos.offset(pieceOffset.rotate(rotation));
            this.rotation = rotation;
            this.setupPiece(templateManager);
        }

        public Piece(TemplateManager templateManager, CompoundNBT cnbt) {
           super(INSTRUCTORS_HOUSE_PIECES, cnbt);
           this.piece = new ResourceLocation(cnbt.getString("Template"));
           this.rotation = Rotation.valueOf(cnbt.getString("Rotation"));
           this.setupPiece(templateManager);
        }

        private void setupPiece(TemplateManager templateManager) {
            Template template = templateManager.getOrCreate(piece);
            PlacementSettings placementsettings = new PlacementSettings().setRotation(rotation).setMirror(Mirror.NONE);
            setup(template, templatePosition, placementsettings);
        }

        @Override
        protected void addAdditionalSaveData(CompoundNBT cnbt) {
            super.addAdditionalSaveData(cnbt);
            cnbt.putString("Template", piece.toString());
            cnbt.putString("Rotation", rotation.name());
        }

        @Override
        protected void handleDataMarker(String function, BlockPos pos, IServerWorld world, Random rand, MutableBoundingBox sbb) {}
    }

}
