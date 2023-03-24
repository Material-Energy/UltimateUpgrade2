package material.ultimate.upgrade.block.screen;

import material.ultimate.upgrade.recipe.VoidAssemblerRecipe;
import material.ultimate.upgrade.registry.UltimateRecipes;
import material.ultimate.upgrade.registry.UltimateScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.Slot;
import net.minecraft.world.World;

import java.util.Optional;

public class VoidAssemblerScreenHandler extends ScreenHandler {
    public final Inventory input = new SimpleInventory(4) {
        @Override
        public void markDirty() {
            super.markDirty();
            VoidAssemblerScreenHandler.this.onContentChanged(this);
        }
    };
    public final CraftingResultInventory output = new CraftingResultInventory(){
        @Override
        public void markDirty() {
            super.markDirty();
            VoidAssemblerScreenHandler.this.onContentChanged(this);
        }
    };

    public final ScreenHandlerContext context;
    public final PlayerEntity player;
    public VoidAssemblerScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, ScreenHandlerContext.EMPTY);
    }
    public VoidAssemblerScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(UltimateScreen.VOID_ASSEMBLER, syncId);
        checkSize(this.input, 4);
        this.context = context;
        this.player = playerInventory.player;

        this.addSlot(new Slot(this.output, 0, 124, 16));

        this.addSlot(new Slot(this.input, 0, 48, 16));

        this.addSlot(new Slot(this.input, 1, 26, 55));
        this.addSlot(new Slot(this.input, 2, 48, 55));
        this.addSlot(new Slot(this.input, 3, 70, 55));

        for (int m = 0; m < 3; ++m) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + m * 9 + 9, 8 + l * 18, 84 + m * 18));
            }
        }
        for (int m = 0; m < 9; ++m) {
            this.addSlot(new Slot(playerInventory, m, 8 + m * 18, 142));
        }
    }


    @Override
    public boolean canUse(PlayerEntity player) {
        return this.input.canPlayerUse(player);
    }

    @Override
    public void onContentChanged(Inventory inventory) {
        super.onContentChanged(inventory);

        if (inventory == this.input){
            this.context.run((world, blockPos) -> updateOutput(world));
        } else if (inventory == this.output){
            updateInput();
        }
    }

    private void updateInput() {
        if (this.output.getStack(0).isEmpty()){
            this.input.removeStack(0, 1);
            this.input.removeStack(1, 1);
            this.input.removeStack(2, 1);
            this.input.removeStack(3, 1);
        }
    }

    private void updateOutput(World world) {
        Optional<VoidAssemblerRecipe> match = world.getRecipeManager().getFirstMatch(VoidAssemblerRecipe.Type.INSTANCE, (SimpleInventory) this.input, world);
        match.ifPresentOrElse(voidAssemblerRecipe -> this.output.setStack(0, voidAssemblerRecipe.craft(new SimpleInventory(1))), () -> this.output.setStack(0, ItemStack.EMPTY));
    }

    @Override
    public void close(PlayerEntity player) {
        player.getInventory().offerOrDrop(input.getStack(0));
        player.getInventory().offerOrDrop(input.getStack(1));
        player.getInventory().offerOrDrop(input.getStack(2));
        player.getInventory().offerOrDrop(input.getStack(3));
        super.close(player);
    }

    public ItemStack transferSlot(PlayerEntity player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasStack()) {
            ItemStack itemStack2 = slot.getStack();
            itemStack = itemStack2.copy();
            if (index == 0) {
                this.context.run((world, pos) -> itemStack2.getItem().onCraft(itemStack2, world, player));
                if (!this.insertItem(itemStack2, 5, 41, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickTransfer(itemStack2, itemStack);
            } else if (index >= 5 && index < 41) {
                if (!this.insertItem(itemStack2, 1, 5, false)) {
                    if (index < 32) {
                        if (!this.insertItem(itemStack2, 32, 41, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (!this.insertItem(itemStack2, 5, 32, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else if (!this.insertItem(itemStack2, 5, 41, false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack2.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }

            if (itemStack2.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTakeItem(player, itemStack2);
            if (index == 0) {
                player.dropItem(itemStack2, false);
            }
        }

        return itemStack;
    }
}
