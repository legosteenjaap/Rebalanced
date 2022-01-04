package nl.tettelaar.rebalanced.mixin.recipe.book.network;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.ClientRecipeBook;
import net.minecraft.client.ClientTelemetryManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.recipebook.RecipeUpdateListener;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.Connection;
import net.minecraft.network.PacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketUtils;
import net.minecraft.network.protocol.game.ClientboundRecipePacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.RecipeBook;
import net.minecraft.util.thread.BlockableEventLoop;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import nl.tettelaar.rebalanced.recipe.interfaces.ClientboundRecipePacketInterface;
import nl.tettelaar.rebalanced.recipe.DiscoverRecipeToast;
import nl.tettelaar.rebalanced.recipe.interfaces.RecipeBookInterface;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public class ClientPacketListenerMixin {

    @Shadow @Final
    private Minecraft minecraft;
    @Shadow @Final
    private final RecipeManager recipeManager = new RecipeManager();

    @Inject(method = "handleAddOrRemoveRecipes", at = @At("HEAD"), cancellable = true)
    public void handleAddOrRemoveRecipes(ClientboundRecipePacket clientboundRecipePacket, CallbackInfo ci) {
        PacketUtils.ensureRunningOnSameThread(clientboundRecipePacket, (ClientPacketListener)(Object)this, this.minecraft);
        ClientboundRecipePacketInterface packetInterface = (ClientboundRecipePacketInterface) (Object) clientboundRecipePacket;
        ClientRecipeBook clientRecipeBook = this.minecraft.player.getRecipeBook();
        RecipeBookInterface recipeBookInterface = (RecipeBookInterface) (Object) (RecipeBook) clientRecipeBook;
        clientRecipeBook.setBookSettings(clientboundRecipePacket.getBookSettings());
        ClientboundRecipePacket.State state = clientboundRecipePacket.getState();

        if (packetInterface.IsDiscover()) {
            switch (state) {
                case INIT:
                    for (ResourceLocation resourceLocation : clientboundRecipePacket.getRecipes()) {
                        this.recipeManager.byKey(resourceLocation).ifPresent(recipeBookInterface::discover);
                    }
                    break;
                case ADD:
                    for (ResourceLocation resourceLocation : clientboundRecipePacket.getRecipes()) {
                        this.recipeManager.byKey(resourceLocation).ifPresent(recipe -> {
                            recipeBookInterface.discover(recipe);
                            clientRecipeBook.addHighlight((Recipe<?>) recipe);
                            DiscoverRecipeToast.discoverOrUpdate(this.minecraft.getToasts(), recipe);
                        });
                    }
                    break;
            }
            clientRecipeBook.getCollections().forEach(recipeCollection -> recipeCollection.updateKnownRecipes(clientRecipeBook));
            if (this.minecraft.screen instanceof RecipeUpdateListener) {
                ((RecipeUpdateListener)((Object)this.minecraft.screen)).recipesUpdated();
            }
            ci.cancel();
        }
    }

    @Redirect(method = "handleAddOrRemoveRecipes", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/protocol/PacketUtils;ensureRunningOnSameThread(Lnet/minecraft/network/protocol/Packet;Lnet/minecraft/network/PacketListener;Lnet/minecraft/util/thread/BlockableEventLoop;)V"))
    private  void removeEnsureRunningOnSameThread(Packet packet, PacketListener packetListener, BlockableEventLoop blockableEventLoop) {
    }
}
