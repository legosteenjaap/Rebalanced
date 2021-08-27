package nl.tettelaar.rebalanced.mixin.village;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.village.TradeOffer;
import nl.tettelaar.rebalanced.village.TradeOfferRebalanced;

@Mixin(TradeOffer.class)
public class TradeOfferMixin implements TradeOfferRebalanced {

	@Shadow private int uses;
	
	@Inject(method = "<init>(Lnet/minecraft/nbt/NbtCompound;)V", at = @At("RETURN"))
	public void InitNbt(NbtCompound nbt, CallbackInfo ci) {
		this.isTemporary = nbt.getBoolean("isTemporary");
	}

	@Inject(method = "toNbt", at = @At("RETURN"), cancellable = true)
	public void toNbt(CallbackInfoReturnable<NbtCompound> cir) {
		NbtCompound nbtCompound = cir.getReturnValue();
		nbtCompound.putBoolean("isTemporary", this.isTemporary);
		cir.setReturnValue(nbtCompound);
	}

	@Inject(method = "resetUses", at = @At("HEAD"), cancellable = true)
	public void resetUses(CallbackInfo ci) {
		if (this.isTemporary) {
			ci.cancel();
		}
	}
	
	private boolean isTemporary = false;

	@Override
	public boolean isTemporary() {
		return this.isTemporary;
	}

	@Override
	public void setTemporary() {
		this.isTemporary = true;
	}

}
