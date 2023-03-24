package material.ultimate.upgrade.mixin;

import material.ultimate.upgrade.registry.UltimateBlocks;
import material.ultimate.upgrade.registry.UltimateItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CraftingTableBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CraftingTableBlock.class)
public class CraftingTableBlockMixin {

    @Inject(
            method = "onUse",
            at = @At("HEAD"),
            cancellable = true
    )
    public void onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir){
        if (!world.isClient){
            ItemStack stack = player.getStackInHand(hand);
            if (stack.getItem().equals(UltimateItems.CORE_OF_THE_VOID)){
                world.setBlockState(pos, UltimateBlocks.VOID_ASSEMBLER.getDefaultState());
                cir.setReturnValue(ActionResult.SUCCESS);
            }
        }
    }
}
