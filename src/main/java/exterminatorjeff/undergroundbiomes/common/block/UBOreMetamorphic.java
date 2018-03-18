package exterminatorjeff.undergroundbiomes.common.block;

import static exterminatorjeff.undergroundbiomes.api.enums.MetamorphicVariant.*;

import exterminatorjeff.undergroundbiomes.api.API;
import net.minecraft.block.Block;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

/**
 *
 * @author CurtisA, LouisDB
 *
 */
public class UBOreMetamorphic extends UBOre {

	public UBOreMetamorphic(Block baseOre, int baseOreMeta) {
		super(baseOre, baseOreMeta);
	}

	@Override
	public UBStone baseStone() {
		return (UBStone) API.METAMORPHIC_STONE.getBlock();
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, METAMORPHIC_VARIANT_PROPERTY);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(METAMORPHIC_VARIANT_PROPERTY, values()[meta]);
	}

	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return getDefaultState().withProperty(METAMORPHIC_VARIANT_PROPERTY, values()[placer.getHeldItemMainhand().getMetadata()]);
	}

}
