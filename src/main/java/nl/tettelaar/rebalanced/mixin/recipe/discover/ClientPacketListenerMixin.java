package nl.tettelaar.rebalanced.mixin.recipe.discover;

import net.minecraft.client.ClientRecipeBook;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.toasts.RecipeToast;
import net.minecraft.client.gui.screens.recipebook.RecipeUpdateListener;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundRecipePacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.RecipeBook;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import nl.tettelaar.rebalanced.recipe.ClientboundRecipePacketInterface;
import nl.tettelaar.rebalanced.recipe.RecipeBookInterface;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public class ClientPacketListenerMixin {

    @Shadow @Final
    private Minecraft minecraft;
    @Shadow @Final
    private RecipeManager recipeManager = new RecipeManager();


    @Inject(method = "handleAddOrRemoveRecipes", at = @At("RETURN"))
    public void handleAddOrRemoveRecipes(ClientboundRecipePacket clientboundRecipePacket, CallbackInfo ci) {
        ClientboundRecipePacketInterface packetInterface = (ClientboundRecipePacketInterface)(Object)clientboundRecipePacket;
        ClientRecipeBook clientRecipeBook = this.minecraft.player.getRecipeBook();
        RecipeBookInterface recipeBookInterface = (RecipeBookInterface) (Object) (RecipeBook)clientRecipeBook;
        clientRecipeBook.setBookSettings(clientboundRecipePacket.getBookSettings());
        ClientboundRecipePacket.State state = clientboundRecipePacket.getState();

        switch (state) {
            case INIT:
            case ADD: {
                for (ResourceLocation resourceLocation : packetInterface.getDiscovered()) {
                    this.recipeManager.byKey(resourceLocation).ifPresent(recipeBookInterface::discover);
                }
                break;
            }
        }
        clientRecipeBook.getCollections().forEach(recipeCollection -> recipeCollection.updateKnownRecipes(clientRecipeBook));
        if (this.minecraft.screen instanceof RecipeUpdateListener) {
            ((RecipeUpdateListener)((Object)this.minecraft.screen)).recipesUpdated();
        }
    }

}
