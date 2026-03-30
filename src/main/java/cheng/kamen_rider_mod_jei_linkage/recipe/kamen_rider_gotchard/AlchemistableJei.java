//package cheng.kamen_rider_mod_jei_linkage.recipe.kamen_rider_gotchard;
//
//import cheng.kamen_rider_mod_jei_linkage.recipe.ModRecipeType;
//import com.google.gson.JsonArray;
//import com.google.gson.JsonObject;
//import net.minecraft.core.NonNullList;
//import net.minecraft.network.FriendlyByteBuf;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.util.GsonHelper;
//import net.minecraft.world.inventory.CraftingContainer;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.item.crafting.CraftingRecipe;
//import net.minecraft.world.item.crafting.Ingredient;
//import net.minecraft.world.item.crafting.RecipeSerializer;
//import net.minecraft.world.item.crafting.ShapedRecipe;
//import net.minecraft.world.level.Level;
//import org.jetbrains.annotations.Nullable;
//
//public class AlchemistableJei implements CraftingRecipe {
//    private final ResourceLocation id;
//    private final NonNullList<Ingredient> ingredientNonNullList;
//    private final int recipeAlchemyLevel;
//    private final int recipeAlchemyXp;
//    private final ItemStack resultItem;
//    private final int resultAlchemyLevel;
//
//    private AlchemistableJei(ResourceLocation id, NonNullList<Ingredient> ingredientNonNullList, int recipeAlchemyLevel, int recipeAlchemyXp, ItemStack resultItem, int resultAlchemyLevel) {
//        this.id = id;
//        this.ingredientNonNullList = ingredientNonNullList;
//        this.recipeAlchemyLevel = recipeAlchemyLevel;
//        this.recipeAlchemyXp = recipeAlchemyXp;
//        this.resultItem = resultItem;
//        this.resultAlchemyLevel = resultAlchemyLevel;
//    }
//
//    @Override
//    public boolean matches(CraftingContainer p_44002_, Level p_44003_) {
//        return false;
//    }
//
//    @Override
//    public ItemStack assemble(CraftingContainer p_44001_) {
//        return resultItem.copy();
//    }
//
//    @Override
//    public boolean canCraftInDimensions(int p_43999_, int p_44000_) {
//        return false;
//    }
//
//    @Override
//    public ItemStack getResultItem() {
//        return resultItem;
//    }
//
//    @Override
//    public ResourceLocation getId() {
//        return id;
//    }
//
//    @Override
//    public RecipeSerializer<?> getSerializer() {
//        return ModRecipeType.AlchemistableSerializer.get();
//    }
//
//    public int getRecipeAlchemyLevel() {
//        return recipeAlchemyLevel;
//    }
//
//    public int getRecipeAlchemyXp() {
//        return recipeAlchemyXp;
//    }
//
//    public int getResultAlchemyLevel() {
//        return resultAlchemyLevel;
//    }
//
//    public NonNullList<Ingredient> getIngredientNonNullList() {
//        return ingredientNonNullList;
//    }
//
//    public static class Serializer implements RecipeSerializer<AlchemistableJei>{
//        private ResourceLocation registryName = ModRecipeType.AlchemistableSerializer.getId();
//        public AlchemistableJei fromJson(ResourceLocation id, JsonObject pSerializedRecipe) {
//            JsonArray ingredients = GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredients");
//            // 修复：动态创建列表，检查数组大小
//            NonNullList<Ingredient> inputs = NonNullList.create();
//            for (int i = 0; i < ingredients.size(); ++i) {
//                if (i < 11) { // 只取前11个材料
//                    inputs.add(Ingredient.fromJson(ingredients.get(i)));
//                }
//            }
//            // 确保有11个槽位
//            while (inputs.size() < 11) {
//                inputs.add(Ingredient.EMPTY);
//            }
//
//            int recipe_level = GsonHelper.getAsInt(pSerializedRecipe, "level");
//            int recipe_experinence = GsonHelper.getAsInt(pSerializedRecipe, "experinence");
//
//            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "output"));
//            int ouput_level = GsonHelper.getAsInt(pSerializedRecipe, "ouput_level");
//            return new AlchemistableJei(id, inputs, recipe_level, recipe_experinence, output, ouput_level);
//        }
//        public @Nullable AlchemistableJei fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
//            NonNullList<Ingredient> inputs = NonNullList.withSize(buf.readInt(), Ingredient.EMPTY);
//
//            for(int i = 0; i < inputs.size(); ++i) {
//                inputs.set(i, Ingredient.fromNetwork(buf));
//            }
//            int recipe_level = buf.readInt();
//            int recipe_experinence = buf.readInt();
//
//            ItemStack output = buf.readItem();
//            int ouput_level = buf.readInt();
//
//            return new AlchemistableJei(id,inputs,recipe_level,recipe_experinence,output,ouput_level);
//        }
//        public void toNetwork(FriendlyByteBuf buf, AlchemistableJei recipe) {
//            buf.writeInt(recipe.getIngredients().size());
//
//            for(Ingredient ing : recipe.getIngredients()) {
//                ing.toNetwork(buf);
//            }
//
//            buf.writeInt(recipe.getRecipeAlchemyLevel());
//            buf.writeInt(recipe.getRecipeAlchemyXp());
//
//            buf.writeItemStack(recipe.getResultItem(), false);
//            buf.writeInt(recipe.getResultAlchemyLevel());
//        }
//
//        @Override
//        public RecipeSerializer<?> setRegistryName(ResourceLocation name) {
//            registryName = name;
//            return this;
//        }
//
//        @Override
//        public @Nullable ResourceLocation getRegistryName() {
//            return registryName;
//        }
//
//        @Override
//        public Class<RecipeSerializer<?>> getRegistryType() {
//            return ModRecipeType.AlchemistableSerializer.get().getRegistryType();
//        }
//    }
//}
