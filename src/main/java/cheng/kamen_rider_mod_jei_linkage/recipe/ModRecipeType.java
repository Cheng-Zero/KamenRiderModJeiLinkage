package cheng.kamen_rider_mod_jei_linkage.recipe;

import cheng.kamen_rider_mod_jei_linkage.KamenRiderModJeiLinkage;
import cheng.kamen_rider_mod_jei_linkage.recipe.build.FullbottlePurifierJeiRecipe;
import cheng.kamen_rider_mod_jei_linkage.recipe.kamen_rider_gotchard.AlchemistableJeiRecipe;
import cheng.kamen_rider_mod_jei_linkage.recipe.kamen_rider_gotchard.GotchardHenshinCardJeiRecipe;
import net.minecraft.core.Registry;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Optional;
import java.util.function.Supplier;

public class ModRecipeType {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES;
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS;
    // 歌查德
    public static final RegistryObject<RecipeType<AlchemistableJeiRecipe>> Alchemistable;
    public static final RegistryObject<RecipeType<GotchardHenshinCardJeiRecipe>> GotchardRecipe;

    public static final RegistryObject<RecipeSerializer<?>> AlchemistableSerializer;
    public static final RegistryObject<RecipeSerializer<?>> GotchardSerializer;

    // 创骑
    public static final RegistryObject<RecipeType<FullbottlePurifierJeiRecipe>> FullbottlePurifier;
    public static final RegistryObject<RecipeSerializer<?>> FullbottlePurifierSerializer;
    static {
        RECIPE_TYPES = DeferredRegister.create(Registry.RECIPE_TYPE.key(), KamenRiderModJeiLinkage.MODID);
        RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, KamenRiderModJeiLinkage.MODID);

        Alchemistable = registerRecipeType("alchemistable_jei");
        GotchardRecipe = registerRecipeType("gotchard_henshin_card");

        AlchemistableSerializer = registerRecipeSerializer("alchemistable_jei", AlchemistableJeiRecipe.Serializer::new);
        GotchardSerializer = registerRecipeSerializer("gotchard_henshin_card",GotchardHenshinCardJeiRecipe.Serializer::new);

        FullbottlePurifier = registerRecipeType("fullbottle_purifier");
        FullbottlePurifierSerializer = registerRecipeSerializer("fullbottle_purifier",FullbottlePurifierJeiRecipe.Serializer::new);
    }
    static <T extends Recipe<?>> RegistryObject<RecipeType<T>> registerRecipeType(final String name){
        return RECIPE_TYPES.register(name, () -> registerRecipe(name));
    }
    static <T extends RecipeSerializer<?>> RegistryObject<RecipeSerializer<?>> registerRecipeSerializer(final String name, Supplier<T> t){
        return RECIPE_SERIALIZERS.register(name,  t);
    }
    public static <T extends Recipe<?>> RecipeType<T> registerRecipe(final String identifier) {
        return new RecipeType<>() {
            public String toString() {
                return KamenRiderModJeiLinkage.MODID + ":" + identifier;
            }
        };
    }

}
