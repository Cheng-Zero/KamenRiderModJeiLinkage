package cheng.kamen_rider_mod_jei_linkage.recipe.build;

import cheng.kamen_rider_mod_jei_linkage.KamenRiderModJeiLinkage;
import cheng.kamen_rider_mod_jei_linkage.recipe.ModRecipeType;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class FullbottlePurifierJeiRecipe implements Recipe<SimpleContainer> {
    private final ResourceLocation id;
    private final ItemStack output;
    private final NonNullList<Ingredient> recipeItems;

    public FullbottlePurifierJeiRecipe(ResourceLocation id, ItemStack output, NonNullList<Ingredient> recipeItems) {
        this.id = id;
        this.output = output;
        this.recipeItems = recipeItems;
    }

    @Override
    public boolean matches(@NotNull SimpleContainer pContainer, Level pLevel) {
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

    public @NotNull RecipeType<?> getType() {
        return ModRecipeType.FullbottlePurifier.get();
    }

    public @NotNull RecipeSerializer<?> getSerializer() {
        return ModRecipeType.FullbottlePurifierSerializer.get();
    }

    public static class Serializer implements RecipeSerializer<FullbottlePurifierJeiRecipe> {
        private ResourceLocation registryName = ModRecipeType.FullbottlePurifierSerializer.getId();

        @Override
        public @NotNull FullbottlePurifierJeiRecipe fromJson(@NotNull ResourceLocation pRecipeId, @NotNull JsonObject pSerializedRecipe) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "output"));
            JsonArray ingredients = GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredients");

            NonNullList<Ingredient> inputs = NonNullList.create();
            for (int i = 0; i < ingredients.size() && i < 3; i++) { // 只取前3个材料
                inputs.add(Ingredient.fromJson(ingredients.get(i)));
            }

            // 确保有3个槽位
            while (inputs.size() < 3) {
                inputs.add(Ingredient.EMPTY);
            }

            return new FullbottlePurifierJeiRecipe(pRecipeId, output, inputs);
        }

        @Nullable
        @Override
        public FullbottlePurifierJeiRecipe fromNetwork(@NotNull ResourceLocation id, @NotNull FriendlyByteBuf buf) {
            // 先读取材料数量
            int size = buf.readInt();

            // 创建适当大小的列表
            NonNullList<Ingredient> inputs = NonNullList.withSize(size, Ingredient.EMPTY);

            // 读取所有材料
            for (int i = 0; i < size; i++) {
                inputs.set(i, Ingredient.fromNetwork(buf));
            }

            // 读取输出物品
            ItemStack output = buf.readItem();

            return new FullbottlePurifierJeiRecipe(id, output, inputs);
        }

        // 需按照fromNetwork里的顺序来搞FriendlyByteBuf
        // 若不按照，则有可能导致无法联机
        @Override
        public void toNetwork(@NotNull FriendlyByteBuf buf, @NotNull FullbottlePurifierJeiRecipe recipe) {
            // 先写入材料数量
            buf.writeInt(recipe.getIngredients().size());

            // 写入所有材料
            for (Ingredient ing : recipe.getIngredients()) {
                ing.toNetwork(buf);
            }

            // 修复：使用正确的方法名
            buf.writeItem(recipe.getResultItem());
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

        public Class<RecipeSerializer<?>> getRegistryType() {
            // 使用原始类型转换，但添加了抑制警告
            return ModRecipeType.FullbottlePurifierSerializer.get().getRegistryType();
        }
    }
}