package nl.tettelaar.rebalanced.mixin.recipe.book;

import com.google.common.collect.Lists;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundRecipePacket;
import net.minecraft.network.protocol.game.ServerGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.stats.RecipeBook;
import net.minecraft.stats.ServerRecipeBook;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import nl.tettelaar.rebalanced.api.RecipeAPI;
import nl.tettelaar.rebalanced.recipe.ClientboundRecipePacketInterface;
import nl.tettelaar.rebalanced.recipe.RecipeBookInterface;
import nl.tettelaar.rebalanced.recipe.ServerRecipeBookInterface;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.*;
import java.util.function.Consumer;

@Mixin(ServerRecipeBook.class)
public class ServerRecipeBookMixin extends RecipeBook implements ServerRecipeBookInterface {

    @Inject(method = "toNbt", at = @At("RETURN"), cancellable = true)
    public void toNbt(CallbackInfoReturnable<CompoundTag> cir) {
        CompoundTag compoundTag = cir.getReturnValue();
        ListTag listTag2 = new ListTag();
        for (ResourceLocation resourceLocation2 : ((RecipeBookInterface)(Object)(RecipeBook)this).getDiscoveredRecipes()) {
            listTag2.add(StringTag.valueOf(resourceLocation2.toString()));
        }
        compoundTag.put("discovered", listTag2);
    }

    @Inject(method = "fromNbt", at = @At("RETURN"), cancellable = true)
    public void fromNbt(CompoundTag compoundTag, RecipeManager recipeManager, CallbackInfo ci) {
        ListTag discovered = compoundTag.getList("discovered", 8);
        RecipeBookInterface recipeBookInterface = (RecipeBookInterface) (Object)(RecipeBook)this;
        this.loadRecipes(discovered, recipeBookInterface::discover, recipeManager);
    }

    @ModifyArg(method = "sendRecipes", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerGamePacketListenerImpl;send(Lnet/minecraft/network/protocol/Packet;)V"), index = 0)
    private Packet<ClientGamePacketListener> addDiscoveredRecipes (Packet<ClientGamePacketListener> recipePacket) {
        ((ClientboundRecipePacketInterface)(Object) recipePacket).setDiscovered(((RecipeBookInterface)(Object)(RecipeBook)this).getDiscoveredRecipes().stream().toList());
        return recipePacket;
    }

    @ModifyArg(method = "sendInitialRecipeBook", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerGamePacketListenerImpl;send(Lnet/minecraft/network/protocol/Packet;)V"), index = 0)
    private Packet<ClientGamePacketListener> addDiscoveredRecipesInit (Packet<ClientGamePacketListener> recipePacket) {
        ((ClientboundRecipePacketInterface)(Object) recipePacket).setDiscovered(((RecipeBookInterface)(Object)(RecipeBook)this).getDiscoveredRecipes().stream().toList());
        return recipePacket;
    }

    @Shadow
    private void loadRecipes(ListTag listTag, Consumer<Recipe<?>> consumer, RecipeManager recipeManager) {
    }

    @Shadow
    private void sendRecipes(ClientboundRecipePacket.State state, ServerPlayer serverPlayer, List<ResourceLocation> list) {
    }


    @Override
    public int discoverRecipes(Collection<Recipe<?>> recipeList, ServerPlayer player) {
        ArrayList<ResourceLocation> recipes = Lists.newArrayList();
        int discoveredRecipes = 0;
        RecipeBookInterface recipeBookInterface = (RecipeBookInterface)(Object)(RecipeBook)this;
        for (Recipe<?> recipe : recipeList) {
            ResourceLocation resourceLocation = recipe.getId();
            if (recipeBookInterface.getDiscoveredRecipes().contains(resourceLocation) || recipe.isSpecial() || !RecipeAPI.isDiscoverable(recipe.getResultItem().getItem())) continue;
            recipeBookInterface.discover(resourceLocation);
            recipes.add(resourceLocation);
            ++discoveredRecipes;
        }
        ClientboundRecipePacketInterface packet = (ClientboundRecipePacketInterface) (Object)new ClientboundRecipePacket(ClientboundRecipePacket.State.ADD, Collections.emptyList(), Collections.emptyList(), this.getBookSettings());
        packet.setDiscovered(recipes);
        player.connection.send((ClientboundRecipePacket)packet);
        return discoveredRecipes;
    }

    @Redirect(method = "removeRecipes", at = @At(value = "INVOKE", target = "Ljava/util/Set;contains(Ljava/lang/Object;)Z"))
    private boolean alsoRemoveDiscoveredRecipes(Set set, Object resourceLocation) {
        RecipeBookInterface recipeBookInterface = (RecipeBookInterface)(Object)(RecipeBook)this;
        return set.contains(resourceLocation) || recipeBookInterface.getDiscoveredRecipes().contains(resourceLocation);
    }
}