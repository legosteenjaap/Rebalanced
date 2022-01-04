package nl.tettelaar.rebalanced.mixin.recipe.book.network;

import com.google.common.collect.ImmutableList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundRecipePacket;
import net.minecraft.resources.ResourceLocation;
import nl.tettelaar.rebalanced.recipe.interfaces.ClientboundRecipePacketInterface;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ClientboundRecipePacket.class)
public class ClientboundRecipePacketMixin implements ClientboundRecipePacketInterface {

    boolean isDiscover = false;

    //List<ResourceLocation> discovered = ImmutableList.of();

    @Inject(method = "<init>(Lnet/minecraft/network/FriendlyByteBuf;)V", at = @At("RETURN"))
    public void ClientboundRecipePacketMixin(FriendlyByteBuf friendlyByteBuf, CallbackInfo ci) {
        this.isDiscover = friendlyByteBuf.readBoolean();
        //this.discovered = friendlyByteBuf.readList(FriendlyByteBuf::readResourceLocation);
    }

    @Inject(method = "write", at = @At("RETURN"), cancellable = true)
    public void write(@NotNull FriendlyByteBuf friendlyByteBuf, CallbackInfo ci) {
        friendlyByteBuf.writeBoolean(this.isDiscover);
        //friendlyByteBuf.writeCollection(this.discovered, FriendlyByteBuf::writeResourceLocation);
    }


    @Override
    public void setIsDiscover() {
        isDiscover = true;
    }

    @Override
    public boolean IsDiscover() {
        return isDiscover;
    }

    /*@Override
    public List<ResourceLocation> getDiscovered() {
        return this.discovered;
    }

    @Override
    public void setDiscovered(List<ResourceLocation> discovered) {
        this.discovered = discovered;
    }*/

}
