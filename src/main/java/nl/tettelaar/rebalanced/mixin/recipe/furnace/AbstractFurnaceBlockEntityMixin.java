package nl.tettelaar.rebalanced.mixin.recipe.furnace;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import nl.tettelaar.rebalanced.api.RecipeAPI;
import nl.tettelaar.rebalanced.network.NetworkingServer;
import nl.tettelaar.rebalanced.recipe.interfaces.FurnaceBlockEntityInterface;
import nl.tettelaar.rebalanced.util.RecipeUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mixin(AbstractFurnaceBlockEntity.class)
public abstract class AbstractFurnaceBlockEntityMixin extends BaseContainerBlockEntity implements FurnaceBlockEntityInterface {

    UUID player;

    @Shadow int cookingProgress;

    public ArrayList<ServerPlayer> inspectingPlayers = new ArrayList<>();

    protected AbstractFurnaceBlockEntityMixin(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }

    @Inject(method = "saveAdditional", at = @At("HEAD"), cancellable = true)
    protected void saveAdditional(CompoundTag compoundTag, CallbackInfo ci) {
        if (player != null) compoundTag.putUUID("Owner", player);
    }

    //DONT KNOW IF WORK WHEN PLAYER OFFLINE

    @Inject(method = "load", at = @At("HEAD"), cancellable = true)
    public void load(CompoundTag compoundTag, CallbackInfo ci) {
        if (compoundTag != null && compoundTag.hasUUID("Owner")) player = compoundTag.getUUID("Owner");
    }

    //KINDA STOOPID HACK

    @Redirect(method = "serverTick()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/entity/AbstractFurnaceBlockEntity;isLit()Z", ordinal = 3))
    private static boolean stopUnownedRecipeBurning1(AbstractFurnaceBlockEntity abstractFurnaceBlockEntity) {
        ServerPlayer player = ((ServerPlayer)((FurnaceBlockEntityInterface)abstractFurnaceBlockEntity).getOwner());

        Recipe recipe = (Recipe) abstractFurnaceBlockEntity.getLevel().getRecipeManager().getRecipeFor((((AbstractFurnaceBlockEntityInvoker)abstractFurnaceBlockEntity).getRecipeType()), abstractFurnaceBlockEntity, abstractFurnaceBlockEntity.getLevel()).orElse(null);
        Optional<Integer> XPCost;
        if (recipe != null )XPCost = RecipeAPI.getItemXPCost(recipe.getResultItem().getItem());
        else {
            XPCost = Optional.empty();
        }
        if (player != null && (player.getRecipeBook().contains(recipe) || (XPCost.isPresent() && RecipeUtil.isUnlockable(player, XPCost.get(), recipe)))) return ((AbstractFurnaceBlockEntityInvoker)abstractFurnaceBlockEntity).getLitTime() > 0;
        return true;
    }

    @Redirect(method = "serverTick()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/entity/AbstractFurnaceBlockEntity;isLit()Z", ordinal = 5))
    private static boolean stopUnownedRecipeBurning2(AbstractFurnaceBlockEntity abstractFurnaceBlockEntity) {
        ServerPlayer player = ((ServerPlayer)((FurnaceBlockEntityInterface)abstractFurnaceBlockEntity).getOwner());

        Recipe recipe = (Recipe) abstractFurnaceBlockEntity.getLevel().getRecipeManager().getRecipeFor((((AbstractFurnaceBlockEntityInvoker)abstractFurnaceBlockEntity).getRecipeType()), abstractFurnaceBlockEntity, abstractFurnaceBlockEntity.getLevel()).orElse(null);

        Optional<Integer> XPCost;
        if (recipe != null )XPCost = RecipeAPI.getItemXPCost(recipe.getResultItem().getItem());
        else {
            XPCost = Optional.empty();
        }
        if (player != null && (player.getRecipeBook().contains(recipe) || (XPCost.isPresent() && RecipeUtil.isUnlockable(player, XPCost.get(), recipe)))) return ((AbstractFurnaceBlockEntityInvoker)abstractFurnaceBlockEntity).getLitTime() > 0;
        return false;
    }

    @Inject(method = "serverTick", at = @At("HEAD"))
    private static void serverTick(Level level, BlockPos blockPos, BlockState blockState, AbstractFurnaceBlockEntity abstractFurnaceBlockEntity, CallbackInfo ci) {
        Recipe recipe = (Recipe) level.getRecipeManager().getRecipeFor(((AbstractFurnaceBlockEntityInvoker)abstractFurnaceBlockEntity).getRecipeType(), abstractFurnaceBlockEntity, level).orElse(null);
        FriendlyByteBuf buf = PacketByteBufs.create();
        buf.writeBoolean(recipe != null);
        if (recipe != null) {
            buf.writeResourceLocation(recipe.getId());
        }
        for (ServerPlayer serverPlayer : ((FurnaceBlockEntityInterface)(Object)abstractFurnaceBlockEntity).getInspectingPlayers()) {
            if (((FurnaceBlockEntityInterface)abstractFurnaceBlockEntity).getOwner() != null && ((FurnaceBlockEntityInterface)abstractFurnaceBlockEntity).getOwner().getUUID().equals(serverPlayer.getUUID())) {
                ServerPlayNetworking.send(serverPlayer, NetworkingServer.FURNACE_RECIPE, buf);
            }
            else {
                FriendlyByteBuf emptyBuf = PacketByteBufs.create();
                emptyBuf.writeBoolean(false);
                ServerPlayNetworking.send(serverPlayer, NetworkingServer.FURNACE_RECIPE, emptyBuf);
            }
        }
    }

    @Redirect(method = "serverTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/entity/AbstractFurnaceBlockEntity;setRecipeUsed(Lnet/minecraft/world/item/crafting/Recipe;)V"))
    private static void removeXPAndUnlockRecipe(AbstractFurnaceBlockEntity furnaceBlockEntity, Recipe<?> recipe) {
        ServerPlayer player = (ServerPlayer)((FurnaceBlockEntityInterface)furnaceBlockEntity).getOwner();
        Optional<Integer> XPCost = RecipeAPI.getItemXPCost(recipe.getResultItem().getItem());
        if (player != null && XPCost.isPresent()) {
            player.giveExperienceLevels(-XPCost.get());
            player.getRecipeBook().addRecipes(RecipeAPI.getRecipesWithDiscoverableItem(recipe.getResultItem(), player.getLevel().getRecipeManager()), player);
        }
    }

    @Override
    public void setOwner(Player player) {
        this.player = player.getUUID();
    }

    @Override
    public Player getOwner() {
        if (this.player != null) return this.getLevel().getPlayerByUUID(this.player);
        return null;
    }

    @Override
    public boolean isCookingRecipe() {
        return cookingProgress > 0;
    }
    @Override
    public void addInspectingPlayer(ServerPlayer player) {
        inspectingPlayers.add(player);
    }

    @Override
    public void removeInspectingPlayer(ServerPlayer player) {
        inspectingPlayers.remove(player);
    }

    @Override
    public List<ServerPlayer> getInspectingPlayers() {
        return inspectingPlayers;
    }

}
