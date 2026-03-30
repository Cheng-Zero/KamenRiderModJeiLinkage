package cheng.kamen_rider_mod_jei_linkage.hhh;

import cheng.kamen_rider_mod_jei_linkage.recipe.ModRecipeType;
import cheng.kamen_rider_mod_jei_linkage.recipe.kamen_rider_gotchard.AlchemistableJeiRecipe;
import net.mcreator.kamenridergotchard.init.KamenRiderGotchardModItems;
import net.mcreator.kamenridergotchard.network.KamenRiderGotchardModVariables;
import net.mcreator.kamenridergotchard.procedures.AlchemyRingCraftingProcedure;
import net.mcreator.kamenridergotchard.procedures.UltimatecraftProcedure;
import net.mcreator.kamenridergotchard.world.inventory.RidersAlchemyyableMenu;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.LevelAccessor;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

public class AlchemisTableNewPHelp {

    private static final int playerLevel = 0;
    private static final int RecipeLevel = 0;

    public static void execute(LevelAccessor world, final Entity entity) {
        if (entity != null) {
            if (entity instanceof ServerPlayer serverPlayer) {
                if (serverPlayer.containerMenu instanceof RidersAlchemyyableMenu menu) {
                    if (((Supplier<?>) menu).get() instanceof Map<?,?> _slots){
                        ItemStack itemStack = getResult(world, serverPlayer).copy();
                        ((Slot) _slots.get(0)).remove(1);
                        ((Slot) _slots.get(1)).remove(1);
                        ((Slot) _slots.get(2)).remove(1);
                        ((Slot) _slots.get(3)).remove(1);
                        ((Slot) _slots.get(4)).remove(1);
                        ((Slot) _slots.get(5)).remove(1);
                        ((Slot) _slots.get(6)).remove(1);
                        ((Slot) _slots.get(7)).remove(1);
                        ((Slot) _slots.get(8)).remove(1);
                        ((Slot) _slots.get(9)).remove(1);
                        ((Slot) _slots.get(10)).remove(1);
                        ((Slot) _slots.get(10)).set(itemStack);

                    }
                    serverPlayer.containerMenu.broadcastChanges();
                }
            }
            AlchemyRingCraftingProcedure.execute(world, entity);
            UltimatecraftProcedure.execute(world, entity);
        }
    }

    private static List<AlchemistableJeiRecipe> recipeList(LevelAccessor world){
        return Objects.requireNonNull(world.getServer()).getRecipeManager().getAllRecipesFor(ModRecipeType.Alchemistable.get());
    }

    private static ItemStack getResult(LevelAccessor world, Entity entity) {
        for (AlchemistableJeiRecipe recipe : recipeList(world)) {
            NonNullList<Ingredient> ingredients = recipe.getIngredients();
            int level = recipe.getLevel();
            int xp = recipe.getExperinence();
            if (entity instanceof Player player && player.containerMenu instanceof Supplier _splr) {
                // 如果 玩家炼金术等级不足
                if (level != 0 && level > getPlayerAlchemisLevel(player)){
                    if (level >= 4)
                        player.displayClientMessage(new TextComponent("炼金术等级不足，请升至S"),false);
                    else if (level == 3)
                        player.displayClientMessage(new TextComponent("炼金术等级不足，请升至A"),false);
                    else if (level == 2)
                        player.displayClientMessage(new TextComponent("炼金术等级不足，请升至B"),false);
                    else if (level == 1)
                        player.displayClientMessage(new TextComponent("炼金术等级不足，请升至C"),false);
                    continue;
                }
                if (xp != 0 && xp >= getPlayerAlchemisXp(player)){
                    if (Objects.equals(getResult(world, entity), new ItemStack(KamenRiderGotchardModItems.ALCHEMYRINGB.get())))
                        player.displayClientMessage(new TextComponent("炼金术经验不足,请升至1000"),false);
                    if (Objects.equals(getResult(world, entity), new ItemStack(KamenRiderGotchardModItems.ALCHEMYRINGA.get())))
                        player.displayClientMessage(new TextComponent("炼金术经验不足，请升至2000"),false);
                    if (Objects.equals(getResult(world, entity), new ItemStack(KamenRiderGotchardModItems.ALCHEMYRINGS.get())))
                        player.displayClientMessage(new TextComponent("炼金术经验不足，请升至7000"),false);
                    continue;
                }
                if (!getIngredients(ingredients,_splr,0))
                    continue;
                if (!getIngredients(ingredients,_splr,1))
                    continue;
                if (!getIngredients(ingredients,_splr,2))
                    continue;
                if (!getIngredients(ingredients,_splr,3))
                    continue;
                if (!getIngredients(ingredients,_splr,4))
                    continue;
                if (!getIngredients(ingredients,_splr,5))
                    continue;
                if (!getIngredients(ingredients,_splr,6))
                    continue;
                if (!getIngredients(ingredients,_splr,7))
                    continue;
                if (!getIngredients(ingredients,_splr,8))
                    continue;
                if (!getIngredients(ingredients,_splr,9))
                    continue;
                if (!getIngredients(ingredients,_splr,10))
                    continue;

                if (Objects.equals(getResult(world, entity), new ItemStack(KamenRiderGotchardModItems.ALCHEMYRINGC.get())) && getPlayerAlchemisLevel(player) < 1)
                    setPlayerAlchemisLevel(player,1);
                if (Objects.equals(getResult(world, entity), new ItemStack(KamenRiderGotchardModItems.ALCHEMYRINGB.get())) && getPlayerAlchemisLevel(player) == 1)
                    setPlayerAlchemisLevel(player,2);
                if (Objects.equals(getResult(world, entity), new ItemStack(KamenRiderGotchardModItems.ALCHEMYRINGA.get())) && getPlayerAlchemisLevel(player) == 2)
                    setPlayerAlchemisLevel(player,3);
                if (Objects.equals(getResult(world, entity), new ItemStack(KamenRiderGotchardModItems.ALCHEMYRINGS.get())) && getPlayerAlchemisLevel(player) == 3)
                    setPlayerAlchemisLevel(player,4);
            }
            return recipe.getResultItem();
        }

        return ItemStack.EMPTY;
    }
    private static boolean getIngredients(NonNullList<Ingredient> ingredients,Supplier _splr,int slot){
        return ingredients.get(slot).test((_splr.get() instanceof Map _slt ? ((Slot) _slt.get(slot)).getItem() : ItemStack.EMPTY));
    }

    private static int getPlayerAlchemisLevel(Player player){
        return (int) playerVariables(player).level;
    }
    private static void setPlayerAlchemisLevel(Player player,int level){
        playerVariables(player).level = level;
        playerVariables(player).syncPlayerVariables(player);
    }

    private static int getPlayerAlchemisXp(Player player){
        return (int) playerVariables(player).experinence;
    }

    private static KamenRiderGotchardModVariables.PlayerVariables playerVariables(Player player){
        return (player.getCapability(KamenRiderGotchardModVariables.PLAYER_VARIABLES_CAPABILITY,null).orElse(new KamenRiderGotchardModVariables.PlayerVariables()));
    }
}
