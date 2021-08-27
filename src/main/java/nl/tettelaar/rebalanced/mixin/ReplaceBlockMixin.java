package nl.tettelaar.rebalanced.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.registry.Registry;
import nl.tettelaar.rebalanced.blocks.FungiBlock;

@Mixin(Blocks.class)
public class ReplaceBlockMixin {

	private static Block registerFungiBlock(String id, Block block) {
		return (Block) Registry.register(Registry.BLOCK, (String) id, block);
	}
	
	@Inject(method = "register(Ljava/lang/String;Lnet/minecraft/block/Block;)Lnet/minecraft/block/Block;", at = @At("HEAD"), cancellable = true)
	private static void register(String id, Block block, CallbackInfoReturnable<Block> cir) {
		switch (id) {
		case "nether_wart_block":
			cir.setReturnValue(registerFungiBlock(id, new FungiBlock(AbstractBlock.Settings.of(Material.SOLID_ORGANIC, MapColor.RED).strength(1.0F).sounds(BlockSoundGroup.WART_BLOCK))));
			break;
		case "warped_wart_block":
			cir.setReturnValue(registerFungiBlock(id, new FungiBlock(AbstractBlock.Settings.of(Material.SOLID_ORGANIC, MapColor.BRIGHT_TEAL).strength(1.0F).sounds(BlockSoundGroup.WART_BLOCK))));
			break;
		case "shroomlight":
			cir.setReturnValue(registerFungiBlock(id, new FungiBlock(AbstractBlock.Settings.of(Material.SOLID_ORGANIC, MapColor.RED).strength(1.0F).sounds(BlockSoundGroup.SHROOMLIGHT).luminance((blockStatex) -> {
		         return 15;
		      }))));
		}
	}

}
