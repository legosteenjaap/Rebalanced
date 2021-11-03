package nl.tettelaar.rebalanced.mixin.village;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.trading.MerchantOffer;
import nl.tettelaar.rebalanced.village.TradeOfferRebalanced;

@Mixin(MerchantOffer.class)
public class TradeOfferMixin implements TradeOfferRebalanced {

	@Shadow private int uses;
	
	@Inject(method = "<init>", at = @At("RETURN"))
	public void InitNbt(CompoundTag nbt, CallbackInfo ci) {
		this.isTemporary = nbt.getBoolean("isTemporary");
	}

	@Inject(method = "createTag", at = @At("RETURN"), cancellable = true)
	public void createTag(CallbackInfoReturnable<CompoundTag> cir) {
		CompoundTag nbtCompound = cir.getReturnValue();
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
