package cheng.kamen_rider_mod_jei_linkage.recipe.kamen_rider_gotchard;

import cheng.kamen_rider_mod_jei_linkage.KamenRiderModJeiLinkage;
import cheng.kamen_rider_mod_jei_linkage.recipe.ModRecipeType;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.mcreator.kamenridergotchard.network.KamenRiderGotchardModVariables;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class AlchemistableJeiRecipe implements Recipe<SimpleContainer> {
    private final ResourceLocation id;
    private final int level;
    private final int experinence;
    private final ItemStack output;
    private final NonNullList<Ingredient> recipeItems;

    public AlchemistableJeiRecipe(ResourceLocation id, int level, int experinence, ItemStack output, NonNullList<Ingredient> recipeItems) {
        this.id = id;
        this.level = level;
        this.experinence = experinence;
        this.output = output;
        this.recipeItems = recipeItems;
    }

    @Override
    public boolean matches(@NotNull SimpleContainer pContainer, Level pLevel) {
        // ✅ 更好的实现，虽然JEI不用，但游戏内可能需要
        if (pLevel.isClientSide()) {
            return false;
        }
        // 简单的匹配逻辑
        for (int i = 0; i < Math.min(recipeItems.size(), pContainer.getContainerSize()); i++) {
            if (!recipeItems.get(i).test(pContainer.getItem(i))) {
                return false;
            }
        }
        return true;
    }

    public @NotNull NonNullList<Ingredient> getIngredients() {
        return this.recipeItems;
    }

    public @NotNull ItemStack assemble(@NotNull SimpleContainer pContainer) {
        return this.output;
    }

    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    public @NotNull ItemStack getResultItem() {
        return this.output.copy();
    }

    public @NotNull ResourceLocation getId() {
        return this.id;
    }

    public int getLevel() {
        return this.level;
    }

    public int getExperinence() {
        return this.experinence;
    }

    public @NotNull RecipeType<?> getType() {
        return ModRecipeType.Alchemistable.get();
    }

    public @NotNull RecipeSerializer<?> getSerializer() {
        return ModRecipeType.AlchemistableSerializer.get();
    }

    public static class Serializer implements RecipeSerializer<AlchemistableJeiRecipe> {
        private ResourceLocation registryName = ModRecipeType.AlchemistableSerializer.getId();

        @Override
        public @NotNull AlchemistableJeiRecipe fromJson(@NotNull ResourceLocation pRecipeId, @NotNull JsonObject pSerializedRecipe) {
            int level = GsonHelper.getAsInt(pSerializedRecipe, "level");
            int experinence = GsonHelper.getAsInt(pSerializedRecipe, "experinence");
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "output"));
            JsonArray ingredients = GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredients");

            // 修复：动态创建列表，检查数组大小
            NonNullList<Ingredient> inputs = NonNullList.create();
            for(int i = 0; i < ingredients.size(); ++i) {
                if (i < 11) { // 只取前11个材料
                    inputs.add(Ingredient.fromJson(ingredients.get(i)));
                }
            }

            // 确保有11个槽位
            while (inputs.size() < 11) {
                inputs.add(Ingredient.EMPTY);
            }

            return new AlchemistableJeiRecipe(pRecipeId, level, experinence, output, inputs);
        }

        @Nullable
        @Override
        public AlchemistableJeiRecipe fromNetwork(@NotNull ResourceLocation id, FriendlyByteBuf buf) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(buf.readInt(), Ingredient.EMPTY);

            for(int i = 0; i < inputs.size(); ++i) {
                inputs.set(i, Ingredient.fromNetwork(buf));
            }
            int level = buf.readInt();
            int experinence = buf.readInt();

            ItemStack output = buf.readItem();
            return new AlchemistableJeiRecipe(id, level, experinence, output, inputs);
        }

        // 需按照fromNetwork里的顺序来搞FriendlyByteBuf
        // 若不按照，则有可能导致无法联机
        @Override
        public void toNetwork(FriendlyByteBuf buf, AlchemistableJeiRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());

            for(Ingredient ing : recipe.getIngredients()) {
                ing.toNetwork(buf);
            }

            buf.writeInt(recipe.getLevel());
            buf.writeInt(recipe.getExperinence());

            buf.writeItemStack(recipe.getResultItem(), false);
        }

        // 保留并修复 setRegistryName 方法
        public RecipeSerializer<?> setRegistryName(ResourceLocation name) {
            this.registryName = name;
            return this;
        }

        // 保留并修复 getRegistryName 方法
        @Nullable
        public ResourceLocation getRegistryName() {
            return this.registryName;
        }

        // 保留并修复 getRegistryType 方法 - 这是关键修复
        public Class<RecipeSerializer<?>> getRegistryType() {
            // 使用原始类型转换，但添加了抑制警告
            return ModRecipeType.AlchemistableSerializer.get().getRegistryType();
        }
    }

    public void setLevel(Player player,int level){
        LazyplayerVariables(player).ifPresent(d-> {
            d.level = level;
            d.syncPlayerVariables(player);
        });
    }

    public double getExperinence(Player player){
        return playerVariables(player).experinence;
    }

    public double getLevel(Player player){
        return playerVariables(player).level;
    }
    private static KamenRiderGotchardModVariables.PlayerVariables playerVariables(Player player){
        return LazyplayerVariables(player).orElse(new KamenRiderGotchardModVariables.PlayerVariables());
    }
    private static LazyOptional<KamenRiderGotchardModVariables.PlayerVariables> LazyplayerVariables(Player player){
        return (player.getCapability(KamenRiderGotchardModVariables.PLAYER_VARIABLES_CAPABILITY,null));
    }
}