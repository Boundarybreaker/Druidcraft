package com.vulp.druidcraft.blocks;


import net.minecraft.block.Block;

public class PressurePlateBlock extends net.minecraft.block.PressurePlateBlock {

    public PressurePlateBlock(ActivationRule sensitivity, Block.Settings properties) {
        super(sensitivity, properties);
    }
}