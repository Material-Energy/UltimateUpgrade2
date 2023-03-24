package material.ultimate.upgrade;

import material.ultimate.upgrade.registry.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemGroup;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.util.Identifier;

public class UltimateUpgrade implements ModInitializer {

    public static ItemGroup MAIN_GROUP = FabricItemGroupBuilder.create(id("ultimate_name")).build();

    @Override
    public void onInitialize() {
        UltimateScreen.init();
        UltimateBlocks.init();
        UltimateItems.init();
        UltimateEntityAttributes.init();
        UltimateEvents.init();
        UltimateParticles.init();
        UltimateRecipes.init();
        UltimateEntityTypes.init();


        LootTableLoadingCallback.EVENT.register((resourceManager, manager, id, supplier, setter) -> {
            if (id.equals(EntityType.ENDER_DRAGON.getLootTableId())){
                LootPool.Builder poolBuilder = LootPool.builder()
                        .with(ItemEntry.builder(UltimateItems.CORE_OF_THE_VOID));

                supplier.pool(poolBuilder);
            }
        });
    }

    public static Identifier id(String name){
        return new Identifier("ultimateupgrade", name);
    }
}
