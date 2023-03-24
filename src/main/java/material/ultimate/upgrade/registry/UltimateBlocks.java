package material.ultimate.upgrade.registry;

import material.ultimate.upgrade.block.VoidAssemblerBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.util.registry.Registry;

import static material.ultimate.upgrade.UltimateUpgrade.id;

public class UltimateBlocks {
    public static Block VOID_ASSEMBLER;

    public static void init(){
        VOID_ASSEMBLER = Registry.register(Registry.BLOCK, id("void_assembler"), new VoidAssemblerBlock(FabricBlockSettings.of(Material.METAL, MapColor.DIAMOND_BLUE).hardness(5.0F).luminance(10).strength(5.0F)));
    }
}
