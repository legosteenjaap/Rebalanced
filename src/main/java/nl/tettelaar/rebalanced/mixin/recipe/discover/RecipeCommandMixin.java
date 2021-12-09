package nl.tettelaar.rebalanced.mixin.recipe.discover;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.commands.synchronization.SuggestionProviders;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.commands.RecipeCommand;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.crafting.Recipe;
import nl.tettelaar.rebalanced.recipe.PlayerRecipeInterface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.Collection;
import java.util.Collections;

@Mixin(RecipeCommand.class)
public class RecipeCommandMixin {

    private static final SimpleCommandExceptionType ERROR_DISCOVER_FAILED = new SimpleCommandExceptionType(new TranslatableComponent("commands.recipe.discover.failed"));

    @ModifyArg(method = "register", at = @At(value = "INVOKE", target = "Lcom/mojang/brigadier/CommandDispatcher;register(Lcom/mojang/brigadier/builder/LiteralArgumentBuilder;)Lcom/mojang/brigadier/tree/LiteralCommandNode;"), index = 0)
    private static LiteralArgumentBuilder registerDiscoverRecipes (LiteralArgumentBuilder argumentBuilder) {
        return (LiteralArgumentBuilder) argumentBuilder.then(Commands.literal("discover").then((ArgumentBuilder<CommandSourceStack, ?>)((RequiredArgumentBuilder)Commands.argument("targets", EntityArgument.players()).then((ArgumentBuilder<CommandSourceStack, ?>)Commands.argument("recipe", ResourceLocationArgument.id()).suggests(SuggestionProviders.ALL_RECIPES).executes(commandContext -> RecipeCommandMixin.discoverRecipes((CommandSourceStack)commandContext.getSource(), EntityArgument.getPlayers(commandContext, "targets"), Collections.singleton(ResourceLocationArgument.getRecipe(commandContext, "recipe")))))).then(Commands.literal("*").executes(commandContext -> RecipeCommandMixin.discoverRecipes((CommandSourceStack)commandContext.getSource(), EntityArgument.getPlayers(commandContext, "targets"), ((CommandSourceStack)commandContext.getSource()).getServer().getRecipeManager().getRecipes())))));

    }

    private static int discoverRecipes (CommandSourceStack commandSourceStack, Collection<ServerPlayer> collection, Collection<Recipe<?>> collection2) throws CommandSyntaxException{
        int i = 0;
        for (ServerPlayer serverPlayer : collection) {
            i += ((PlayerRecipeInterface) (Object) serverPlayer).discoverRecipes(collection2);
        }

        if (i == 0) {
            throw ERROR_DISCOVER_FAILED.create();
        }

        if (collection.size() == 1) {
            commandSourceStack.sendSuccess(new TranslatableComponent("commands.recipe.give.discover.single", collection2.size(), collection.iterator().next().getDisplayName()), true);
        } else {
            commandSourceStack.sendSuccess(new TranslatableComponent("commands.recipe.give.discover.multiple", collection2.size(), collection.size()), true);
        }
        return i;
    }

}
