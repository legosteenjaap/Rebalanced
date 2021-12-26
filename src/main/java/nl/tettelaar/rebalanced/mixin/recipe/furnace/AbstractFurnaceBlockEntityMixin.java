package nl.tettelaar.rebalanced.mixin.recipe.furnace;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import nl.tettelaar.rebalanced.recipe.interfaces.FurnaceBlockEntityInterface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

@Mixin(AbstractFurnaceBlockEntity.class)
public abstract class AbstractFurnaceBlockEntityMixin extends BaseContainerBlockEntity implements FurnaceBlockEntityInterface {

    UUID player;

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

        if (player != null && player.getRecipeBook().contains(recipe)) return ((AbstractFurnaceBlockEntityInvoker)abstractFurnaceBlockEntity).getLitTime() > 0;
        return true;
    }

    @Redirect(method = "serverTick()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/entity/AbstractFurnaceBlockEntity;isLit()Z", ordinal = 5))
    private static boolean stopUnownedRecipeBurning2(AbstractFurnaceBlockEntity abstractFurnaceBlockEntity) {
        ServerPlayer player = ((ServerPlayer)((FurnaceBlockEntityInterface)abstractFurnaceBlockEntity).getOwner());

        Recipe recipe = (Recipe) abstractFurnaceBlockEntity.getLevel().getRecipeManager().getRecipeFor((((AbstractFurnaceBlockEntityInvoker)abstractFurnaceBlockEntity).getRecipeType()), abstractFurnaceBlockEntity, abstractFurnaceBlockEntity.getLevel()).orElse(null);

        if (player != null && player.getRecipeBook().contains(recipe)) return ((AbstractFurnaceBlockEntityInvoker)abstractFurnaceBlockEntity).getLitTime() > 0;
        return false;
    }

    @Override
    public void setOwner(Player player) {
        this.player = player.getUUID();
    }

    @Override
    public Player getOwner() {
        return this.getLevel().getPlayerByUUID(this.player);
    }

}
