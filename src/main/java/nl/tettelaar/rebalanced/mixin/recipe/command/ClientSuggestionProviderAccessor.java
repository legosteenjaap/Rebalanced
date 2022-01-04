package nl.tettelaar.rebalanced.mixin.recipe.command;

import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.ClientSuggestionProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.sql.Connection;

@Mixin(ClientSuggestionProvider.class)
public interface ClientSuggestionProviderAccessor {

    @Accessor
    ClientPacketListener getConnection();

}
