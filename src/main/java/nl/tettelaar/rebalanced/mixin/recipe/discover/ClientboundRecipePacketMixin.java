package nl.tettelaar.rebalanced.mixin.recipe.discover;

import com.google.common.collect.ImmutableList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundRecipePacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.RecipeBookSettings;
import nl.tettelaar.rebalanced.recipe.ClientboundRecipePacketInterface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;
import java.util.List;

@Mixin(ClientboundRecipePacket.class)
public class ClientboundRecipePacketMixin implements ClientboundRecipePacketInterface {

    List<ResourceLocation> discovered = ImmutableList.of();

    @Inject(method = "<init>(Lnet/minecraft/network/FriendlyByteBuf;)V", at = @At("RETURN"))
    public void ClientboundRecipePacketMixin(FriendlyByteBuf friendlyByteBuf, CallbackInfo ci) {
        this.discovered = friendlyByteBuf.readList(FriendlyByteBuf::readResourceLocation);
    }


    @Inject(method = "write", at = @At("RETURN"), cancellable = true)
    public void write(FriendlyByteBuf friendlyByteBuf, CallbackInfo ci) {
        friendlyByteBuf.writeCollection(this.discovered, FriendlyByteBuf::writeResourceLocation);
    }

    @Override
    public List<ResourceLocation> getDiscovered() {
        return this.discovered;
    }

    @Override
    public void setDiscovered(List<ResourceLocation> discovered) {
        this.discovered = discovered;
    }
}
