package material.ultimate.upgrade.registry;

import material.ultimate.upgrade.recipe.VoidAssemblerRecipe;
import net.minecraft.util.registry.Registry;

import static material.ultimate.upgrade.UltimateUpgrade.id;

public class UltimateRecipes {
    public static void init(){
        Registry.register(Registry.RECIPE_SERIALIZER, id(VoidAssemblerRecipe.Type.ID), VoidAssemblerRecipe.Serializer.INSTANCE);
        Registry.register(Registry.RECIPE_TYPE, id(VoidAssemblerRecipe.Serializer.ID), VoidAssemblerRecipe.Type.INSTANCE);
    }
}
