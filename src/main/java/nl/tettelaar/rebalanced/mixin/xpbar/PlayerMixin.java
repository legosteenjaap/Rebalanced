package nl.tettelaar.rebalanced.mixin.xpbar;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.ClientRecipeBook;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import nl.tettelaar.rebalanced.api.ClientAPI;
import nl.tettelaar.rebalanced.xp.interfaces.XPBlockInterface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LocalPlayer.class)
public class PlayerMixin extends AbstractClientPlayer {

    HitResult block;

    public PlayerMixin(ClientLevel clientLevel, GameProfile gameProfile) {
        super(clientLevel, gameProfile);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo ci) {
        block = this.pick(20.0, 0.0f, false);
        if (this.block.getType() == HitResult.Type.BLOCK) {
            BlockPos blockPos = (BlockPos) ((BlockHitResult) this.block).getBlockPos();
            Block block = this.level.getBlockState(blockPos).getBlock();
            if (block instanceof XPBlockInterface) {
                XPBlockInterface xpBlockInterface = ((XPBlockInterface)block);
                ClientAPI.setXPCost(xpBlockInterface.getXPCost(this, blockPos, level));
                ClientAPI.setIsLevel(xpBlockInterface.isLevel());
                return;
            }
        }
        ClientAPI.setXPCost(0);
    }

    @Shadow public ClientRecipeBook getRecipeBook() {
        throw new AssertionError();
    }

}
