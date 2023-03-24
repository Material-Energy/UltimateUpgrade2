package material.ultimate.upgrade.registry;

import material.ultimate.upgrade.item.*;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.data.Main;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

import static material.ultimate.upgrade.UltimateUpgrade.MAIN_GROUP;
import static material.ultimate.upgrade.UltimateUpgrade.id;

public class UltimateItems {
    public static Item SURGE_ARROW;
    public static Item CORE_OF_THE_VOID, VOID_CRYSTAL, VOID_ASSEMBLER;
    public static Item SOUL;
    public static Item VOID_INFUSED_FABRIC;
    public static Item VOID_INFUSED_HELMET, VOID_INFUSED_CHESTPLATE, VOID_INFUSED_LEGGINGS, VOID_INFUSED_BOOTS;
    public static Item SPELL_TOME;
    public static Item ACCELERATOR;
    public static Item VOID_EDGE;

    static {
        CORE_OF_THE_VOID = registerItem("core_of_the_void", new Item(new FabricItemSettings().group(MAIN_GROUP).maxCount(1).rarity(Rarity.RARE)));
        VOID_CRYSTAL = registerItem("void_crystal", new Item(new FabricItemSettings().group(MAIN_GROUP).maxCount(16).rarity(Rarity.RARE)));
        VOID_ASSEMBLER = registerItem("void_assembler", new BlockItem(UltimateBlocks.VOID_ASSEMBLER, new FabricItemSettings().group(MAIN_GROUP).rarity(Rarity.RARE)));

        SOUL = registerItem("soul", new SoulShardItem(new FabricItemSettings().group(MAIN_GROUP).maxCount(1).rarity(Rarity.EPIC)));

        VOID_INFUSED_FABRIC = registerItem("void_infused_fabric", new Item(new FabricItemSettings().group(MAIN_GROUP).rarity(Rarity.RARE)));

        VOID_INFUSED_HELMET = registerItem("void_infused_helmet", new VoidArmorItem(UltimateArmorMaterials.VOID, EquipmentSlot.HEAD, new FabricItemSettings().group(MAIN_GROUP).maxCount(1).rarity(Rarity.RARE)));
        VOID_INFUSED_CHESTPLATE = registerItem("void_infused_chestplate", new VoidArmorItem(UltimateArmorMaterials.VOID, EquipmentSlot.CHEST, new FabricItemSettings().group(MAIN_GROUP).maxCount(1).rarity(Rarity.RARE)));
        VOID_INFUSED_LEGGINGS = registerItem("void_infused_leggings", new VoidArmorItem(UltimateArmorMaterials.VOID, EquipmentSlot.LEGS, new FabricItemSettings().group(MAIN_GROUP).maxCount(1).rarity(Rarity.RARE)));
        VOID_INFUSED_BOOTS = registerItem("void_infused_boots", new VoidArmorItem(UltimateArmorMaterials.VOID, EquipmentSlot.FEET, new FabricItemSettings().group(MAIN_GROUP).maxCount(1).rarity(Rarity.RARE)));

        SPELL_TOME = registerItem("spell_tome", new SpellTomeItem(new FabricItemSettings().group(MAIN_GROUP).rarity(Rarity.EPIC)));

        ACCELERATOR = registerItem("accelerator", new AcceleratorItem(new FabricItemSettings().maxDamage(1250).group(MAIN_GROUP).rarity(Rarity.EPIC)));
        SURGE_ARROW = registerItem("surge_arrow", new SurgeArrowItem(new FabricItemSettings().group(MAIN_GROUP).rarity(Rarity.RARE)));
        VOID_EDGE = registerItem("void_edge", new VoidEdgeItem(new FabricItemSettings().maxDamage(3400).group(MAIN_GROUP).rarity(Rarity.EPIC)));
    }

    public static void init(){
    }

    public static Item registerItem(String name, Item item){
        return Registry.register(Registry.ITEM, id(name), item);
    }
}
