package material.ultimate.upgrade.item;

import material.ultimate.upgrade.entity.CosmicFireballEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class SpellTomeItem extends Item {

    public SpellTomeItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        user.setCurrentHand(hand);
        return TypedActionResult.consume(user.getStackInHand(hand));
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        super.usageTick(world, user, stack, remainingUseTicks);

    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        super.onStoppedUsing(stack, world, user, remainingUseTicks);
        int cooldown = 0;
        float useTime = getUseSeconds(stack, remainingUseTicks);
        switch(getSpellNbt(stack)){
            case "cosmic_fireball" -> {
                CosmicFireballEntity fireball = new CosmicFireballEntity(world, user, 0, 0, 0, (int) (MathHelper.clamp(useTime / 4.0, 0.0, 1.0) * 8.0F));
                fireball.setVelocity(user, user.getPitch(), user.getYaw(), 0, 0.5f, 0.0f);
                fireball.setPos(user.getX(), user.getEyeY(), user.getZ());
                fireball.setOwner(user);
                fireball.setLifetime(MathHelper.clamp(useTime / 4.0, 0.0, 1.0) * 500);
                world.spawnEntity(fireball);
                cooldown = 2;
            }
            case "vitality" -> {
                user.heal(8.0F);
                user.setAbsorptionAmount(4.0F + user.getAbsorptionAmount());
                cooldown = 5;
            }
        }
        if (user instanceof PlayerEntity player){
            player.getItemCooldownManager().set(this, cooldown);
        }
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 72000;
    }

    float getUseSeconds(ItemStack itemStack, int remainingUseTicks){
        return (getMaxUseTime(itemStack) - remainingUseTicks) / 20.0F;
    }

    String getSpellNbt(ItemStack itemStack){
        return itemStack.getOrCreateNbt().getString("selectedSpell");
    }
}
