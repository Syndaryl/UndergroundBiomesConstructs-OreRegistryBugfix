package com.aang23.undergroundbiomes.blocks.stone;

import com.aang23.undergroundbiomes.UndergroundBiomes;
import com.aang23.undergroundbiomes.enums.MetamorphicVariant;
import com.aang23.undergroundbiomes.enums.UBStoneType;
import com.aang23.undergroundbiomes.world.StoneRegistry;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class MetamorphicStone extends UBStone {
    public MetamorphicVariant metamorphic_variant;

    public MetamorphicStone(MetamorphicVariant metamorphic_variant) {
        super();
        this.metamorphic_variant = metamorphic_variant;
        setRegistryName(UndergroundBiomes.modid + ":metamorphic_stone_" + metamorphic_variant.getName().toLowerCase());
    }

    @Override
    public float getBlockHardness(IBlockState blockState, IBlockReader worldIn, BlockPos pos) {
        return metamorphic_variant.getHardness();
    }

    @Override
    public float getExplosionResistance() {
        return metamorphic_variant.getResistance();
    }

    @Override
    public UBStoneType getStoneType() {
        return UBStoneType.METAMORPHIC;
    }

    @Override
    public void getDrops(IBlockState state, NonNullList<ItemStack> drops, World world, BlockPos pos, int fortune) {
        drops.add(new ItemStack(StoneRegistry.getCobbleForStone(this)));
    }

    @Override
    public IItemProvider getItemDropped(IBlockState state, World worldIn, BlockPos pos, int fortune) {
        return StoneRegistry.getCobbleForStone(this);
    }
}