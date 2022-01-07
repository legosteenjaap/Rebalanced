package nl.tettelaar.rebalanced.mixin.recipe.campfire;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;
import nl.tettelaar.rebalanced.api.RecipeAPI;
import nl.tettelaar.rebalanced.xp.interfaces.XPBlockInterface;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Optional;

@Mixin(CampfireBlock.class)
public class CampfireBlockClientMixin implements XPBlockInterface {

    @Override
    public int getXPCost(Player player, BlockPos blockPos, Level level) {
        Optional<CampfireCookingRecipe> optionalRecipeMain = ((CampfireBlockEntity)level.getBlockEntity(blockPos)).getCookableRecipe(player.getItemInHand(InteractionHand.MAIN_HAND));
        Optional<CampfireCookingRecipe> optionalRecipeOff = ((CampfireBlockEntity)level.getBlockEntity(blockPos)).getCookableRecipe(player.getItemInHand(InteractionHand.OFF_HAND));
        CampfireCookingRecipe recipe = null;
        if(optionalRecipeMain.isPresent()) {
            recipe = optionalRecipeMain.get();
        } else if (optionalRecipeOff.isPresent()) {
            recipe = optionalRecipeOff.get();
        }
        if (player.isShiftKeyDown()) recipe = null;
        if (recipe != null) {
            Optional<Integer> XPCost = RecipeAPI.getItemXPCost(recipe.getResultItem().getItem());
            if (XPCost.isPresent() && (!(player instanceof LocalPlayer) || !((LocalPlayer)player).getRecipeBook().contains(recipe))) return XPCost.get();
        }
        return 0;
    }

    @Override
    public boolean isLevel() {
        return true;
    }

}
