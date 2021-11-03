package nl.tettelaar.rebalanced.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import nl.tettelaar.rebalanced.blocks.FungiBlock;

@Mixin(Blocks.class)
public class ReplaceBlockMixin {

	private static Block registerFungiBlock(String id, Block block) {
		return (Block) Registry.register(Registry.BLOCK, (String) id, block);
	}
	
	@Inject(method = "register", at = @At("HEAD"), cancellable = true)
	private static void register(String id, Block block, CallbackInfoReturnable<Block> cir) {
		switch (id) {
		case "nether_wart_block":
			cir.setReturnValue(registerFungiBlock(id, new FungiBlock(BlockBehaviour.Properties.of(Material.GRASS, MaterialColor.COLOR_RED).strength(1.0F).sound(SoundType.WART_BLOCK))));
			break;
		case "warped_wart_block":
			cir.setReturnValue(registerFungiBlock(id, new FungiBlock(BlockBehaviour.Properties.of(Material.GRASS, MaterialColor.WARPED_WART_BLOCK).strength(1.0F).sound(SoundType.WART_BLOCK))));
			break;
		case "shroomlight":
			cir.setReturnValue(registerFungiBlock(id, new FungiBlock(BlockBehaviour.Properties.of(Material.GRASS, MaterialColor.COLOR_RED).strength(1.0F).sound(SoundType.SHROOMLIGHT).lightLevel((blockStatex) -> {
		         return 15;
		      }))));
		}
	}

}
