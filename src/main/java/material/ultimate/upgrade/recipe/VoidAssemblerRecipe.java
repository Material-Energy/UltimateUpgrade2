package material.ultimate.upgrade.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Map;

public class VoidAssemblerRecipe implements Recipe<SimpleInventory> {
    public Identifier id;
    public Ingredient input;
    public DefaultedList<Ingredient> modifiers;
    public ItemStack output;
    public boolean keepNbt;

    public VoidAssemblerRecipe(Identifier id, Ingredient input, DefaultedList<Ingredient> modifiers, ItemStack output, boolean keepNbt){
        this.id = id;
        this.input = input;
        this.modifiers = modifiers;
        this.output = output;
        this.keepNbt = keepNbt;
    }


    @Override
    public boolean matches(SimpleInventory inventory, World world) {
        if (input.test(inventory.getStack(0))){
            ArrayList<ItemStack> modifiers = new ArrayList<>();
            modifiers.add(inventory.getStack(1));
            modifiers.add(inventory.getStack(2));
            modifiers.add(inventory.getStack(3));
            modifiers.removeIf(ItemStack::isEmpty);
            for (Ingredient ingredient: this.modifiers){
                boolean matched = false;
                for (ItemStack itemStack: modifiers){
                    if (ingredient.test(itemStack)) {
                        matched = true;
                        modifiers.remove(itemStack);
                        break;
                    }
                }
                if (!matched) return false;
            }
            return modifiers.size() == 0;
        }
        return false;
    }

    @Override
    public ItemStack craft(SimpleInventory inventory) {
        ItemStack itemStack = getOutput().copy();
        if (keepNbt){
            NbtCompound nbtCompound = inventory.getStack(0).getOrCreateNbt();
            itemStack.setNbt(nbtCompound);
        }
        return itemStack;
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getOutput() {
        return this.output;
    }

    @Override
    public Identifier getId() {
        return this.id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<VoidAssemblerRecipe> {
        private Type() {}
        public static final Type INSTANCE = new Type();
        public static final String ID = "void_assembler";
    }

    public static class Serializer implements RecipeSerializer<VoidAssemblerRecipe>{

        public static final Serializer INSTANCE = new Serializer();
        public static final String ID = "void_assembler";

        @Override
        public VoidAssemblerRecipe read(Identifier id, JsonObject json) {
            Ingredient input = Ingredient.fromJson(JsonHelper.getObject(json, "input"));
            DefaultedList<Ingredient> modifiers = DefaultedList.ofSize(3);
            for (JsonElement entry : JsonHelper.getArray(json, "modifiers")) {
                modifiers.add(Ingredient.fromJson(entry));
            }
            ItemStack output = ShapedRecipe.outputFromJson(JsonHelper.getObject(json, "output"));
            boolean keepNbt = JsonHelper.getBoolean(json, "keepNbt");
            return new VoidAssemblerRecipe(id, input, modifiers, output, keepNbt);
        }

        @Override
        public VoidAssemblerRecipe read(Identifier id, PacketByteBuf buf) {
            Ingredient input = Ingredient.fromPacket(buf);
            int i = buf.readInt();
            DefaultedList<Ingredient> modifiers = DefaultedList.of();
            for (int x = 0; x == i; x++){
                modifiers.add(Ingredient.fromPacket(buf));
            }
            ItemStack itemStack = buf.readItemStack();
            boolean keepNbt = buf.readBoolean();
            return new VoidAssemblerRecipe(id, input, modifiers, itemStack, keepNbt);
        }

        @Override
        public void write(PacketByteBuf buf, VoidAssemblerRecipe recipe) {
            recipe.input.write(buf);
            buf.writeInt(recipe.modifiers.size());
            recipe.modifiers.forEach(ingredient -> ingredient.write(buf));
            buf.writeItemStack(recipe.output);
            buf.writeBoolean(recipe.keepNbt);
        }
    }
}
