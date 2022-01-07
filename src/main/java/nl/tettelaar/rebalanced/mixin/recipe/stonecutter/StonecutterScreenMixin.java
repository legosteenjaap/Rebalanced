package nl.tettelaar.rebalanced.mixin.recipe.stonecutter;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.StonecutterScreen;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.StonecutterMenu;
import net.minecraft.world.level.GameRules;
import nl.tettelaar.rebalanced.network.NetworkingClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(StonecutterScreen.class)
public abstract class StonecutterScreenMixin extends AbstractContainerScreen<StonecutterMenu> {

    public StonecutterScreenMixin(StonecutterMenu abstractContainerMenu, Inventory inventory, Component component) {
        super(abstractContainerMenu, inventory, component);
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    public void init(StonecutterMenu abstractContainerMenu, Inventory inventory, Component component, CallbackInfo ci) {
        FriendlyByteBuf buf = PacketByteBufs.create();
        buf.writeBoolean(inventory.player.getLevel().getGameRules().getBoolean(GameRules.RULE_LIMITED_CRAFTING));
        NetworkingClient.doLimitedCrafting = true;
        ClientPlayNetworking.send(NetworkingClient.DO_LIMITEDCRAFTING_ID, buf);
    }

}
