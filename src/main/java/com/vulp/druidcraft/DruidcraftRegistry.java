package com.vulp.druidcraft;

import com.vulp.druidcraft.blocks.DoorBlock;
import com.vulp.druidcraft.blocks.OreBlock;
import com.vulp.druidcraft.blocks.PressurePlateBlock;
import com.vulp.druidcraft.blocks.SaplingBlock;
import com.vulp.druidcraft.blocks.StairsBlock;
import com.vulp.druidcraft.blocks.WoodButtonBlock;
import com.vulp.druidcraft.blocks.*;
import com.vulp.druidcraft.blocks.tileentities.CrateTileEntity;
import com.vulp.druidcraft.blocks.tileentities.LunarMothJarTileEntity;
import com.vulp.druidcraft.blocks.trees.DarkwoodTree;
import com.vulp.druidcraft.blocks.trees.ElderTree;
import com.vulp.druidcraft.entities.LunarMothColors;
import com.vulp.druidcraft.items.*;
import com.vulp.druidcraft.registry.*;
import com.vulp.druidcraft.world.biomes.DarkwoodForest;
import net.fabricmc.fabric.api.biomes.v1.OverworldBiomes;
import net.fabricmc.fabric.api.biomes.v1.OverworldClimate;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.container.ContainerFactory;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.fabricmc.fabric.api.tools.FabricToolTags;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.container.Container;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.feature.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DruidcraftRegistry {

    public static final String MODID = "druidcraft";
    public static final Logger LOGGER = LogManager.getLogger(MODID);
    public static final ItemGroup DRUIDCRAFT = FabricItemGroupBuilder.build(new Identifier(MODID, "druidcraft"), () -> new ItemStack(ItemRegistry.hemp));

    // ITEM REGISTRATION
    public static void onItemsRegistry()
    {
        // True items:
        ItemRegistry.hemp = register(new Item(new Item.Settings().group(DRUIDCRAFT)), "hemp");
        ItemRegistry.hemp_seeds = register(new AliasedBlockItem(BlockRegistry.hemp_crop, new Item.Settings().group(DRUIDCRAFT)), "hemp_seeds");
        ItemRegistry.amber = register(new Item(new Item.Settings().group(DRUIDCRAFT)), "amber");
        ItemRegistry.moonstone = register(new Item(new Item.Settings().group(DRUIDCRAFT)), "moonstone");
        ItemRegistry.fiery_glass = register(new SmeltableItem(new Item.Settings().group(DRUIDCRAFT), 2400), "fiery_glass");
        ItemRegistry.rockroot = register(new Item(new Item.Settings().group(DRUIDCRAFT)), "rockroot");
        ItemRegistry.chitin = register(new Item(new Item.Settings().group(DRUIDCRAFT)), "chitin");
        ItemRegistry.knife = register(new KnifeItem(new Item.Settings().group(DRUIDCRAFT).maxCount(1)), "knife");

        //Tools & Armour:
        ItemRegistry.bone_sword = register(new SwordItem(ToolMaterialRegistry.bone, 3, -2.4f, new Item.Settings().group(DRUIDCRAFT)), "bone_sword");
        ItemRegistry.bone_shovel = register(new ShovelItem(ToolMaterialRegistry.bone, 1.5f, -3.0f, new Item.Settings().group(DRUIDCRAFT)), "bone_shovel");
        ItemRegistry.bone_pickaxe = register(new DruidPickaxeItem(ToolMaterialRegistry.bone, 1, -2.8f, new Item.Settings().group(DRUIDCRAFT)), "bone_pickaxe");
        ItemRegistry.bone_axe = register(new DruidAxeItem(ToolMaterialRegistry.bone, 7.0f, -3.2f, new Item.Settings().group(DRUIDCRAFT)), "bone_axe");
        ItemRegistry.bone_hoe = register(new HoeItem(ToolMaterialRegistry.bone, -2.0f, new Item.Settings().group(DRUIDCRAFT)), "bone_hoe");
        ItemRegistry.bone_sickle = register(new SickleItem(new ItemProperties().attackDamage(-1).attackSpeed(-1.5f).tier(ToolMaterialRegistry.bone).radius(2).setGroup(DRUIDCRAFT)), "bone_sickle");
        ItemRegistry.bone_helmet = register(new ArmorItem(ArmorMaterialRegistry.bone, EquipmentSlot.HEAD, new Item.Settings().group(DRUIDCRAFT)), "bone_helmet");
        ItemRegistry.bone_chestplate = register(new ArmorItem(ArmorMaterialRegistry.bone, EquipmentSlot.CHEST, new Item.Settings().group(DRUIDCRAFT)), "bone_chestplate");
        ItemRegistry.bone_leggings = register(new ArmorItem(ArmorMaterialRegistry.bone, EquipmentSlot.LEGS, new Item.Settings().group(DRUIDCRAFT)), "bone_leggings");
        ItemRegistry.bone_boots = register(new ArmorItem(ArmorMaterialRegistry.bone, EquipmentSlot.FEET, new Item.Settings().group(DRUIDCRAFT)), "bone_boots");

        ItemRegistry.chitin_helmet = register(new ArmorItem(ArmorMaterialRegistry.chitin, EquipmentSlot.HEAD, new Item.Settings().group(DRUIDCRAFT)), "chitin_helmet");
        ItemRegistry.chitin_chestplate = register(new ArmorItem(ArmorMaterialRegistry.chitin, EquipmentSlot.CHEST, new Item.Settings().group(DRUIDCRAFT)), "chitin_chestplate");
        ItemRegistry.chitin_leggings = register(new ArmorItem(ArmorMaterialRegistry.chitin, EquipmentSlot.LEGS, new Item.Settings().group(DRUIDCRAFT)), "chitin_leggings");
        ItemRegistry.chitin_boots = register(new ArmorItem(ArmorMaterialRegistry.chitin, EquipmentSlot.FEET, new Item.Settings().group(DRUIDCRAFT)), "chitin_boots");

        ItemRegistry.moonstone_sword = register(new SwordItem(ToolMaterialRegistry.moonstone, 3, -2.4f, new Item.Settings().group(DRUIDCRAFT)), "moonstone_sword");
        ItemRegistry.moonstone_shovel = register(new ShovelItem(ToolMaterialRegistry.moonstone, 1.5f, -3.0f, new Item.Settings().group(DRUIDCRAFT)), "moonstone_shovel");
        ItemRegistry.moonstone_pickaxe = register(new DruidPickaxeItem(ToolMaterialRegistry.moonstone, 1, -2.8f, new Item.Settings().group(DRUIDCRAFT)), "moonstone_pickaxe");
        ItemRegistry.moonstone_axe = register(new DruidAxeItem(ToolMaterialRegistry.moonstone, 7.0f, -3.2f, new Item.Settings().group(DRUIDCRAFT)), "moonstone_axe");
        ItemRegistry.moonstone_hoe = register(new HoeItem(ToolMaterialRegistry.moonstone, -2.0f, new Item.Settings().group(DRUIDCRAFT)), "moonstone_hoe");
        ItemRegistry.moonstone_sickle = register(new SickleItem(new ItemProperties().attackDamage(0).attackSpeed(-1.5f).tier(ToolMaterialRegistry.moonstone).radius(4).setGroup(DRUIDCRAFT)), "moonstone_sickle");
        ItemRegistry.moonstone_helmet = register(new ArmorItem(ArmorMaterialRegistry.moonstone, EquipmentSlot.HEAD, new Item.Settings().group(DRUIDCRAFT)), "moonstone_helmet");
        ItemRegistry.moonstone_chestplate = register(new ArmorItem(ArmorMaterialRegistry.moonstone, EquipmentSlot.CHEST, new Item.Settings().group(DRUIDCRAFT)), "moonstone_chestplate");
        ItemRegistry.moonstone_leggings = register(new ArmorItem(ArmorMaterialRegistry.moonstone, EquipmentSlot.LEGS, new Item.Settings().group(DRUIDCRAFT)), "moonstone_leggings");
        ItemRegistry.moonstone_boots = register(new ArmorItem(ArmorMaterialRegistry.moonstone, EquipmentSlot.FEET, new Item.Settings().group(DRUIDCRAFT)), "moonstone_boots");

        ItemRegistry.wooden_sickle = register(new SickleItem(new ItemProperties().attackDamage(0).attackSpeed(-1.5f).tier(ToolMaterials.WOOD).radius(1).setGroup(ItemGroup.TOOLS)), "wooden_sickle");
        ItemRegistry.stone_sickle = register(new SickleItem(new ItemProperties().attackDamage(0).attackSpeed(-1.5f).tier(ToolMaterials.STONE).radius(2).setGroup(ItemGroup.TOOLS)), "stone_sickle");
        ItemRegistry.iron_sickle = register(new SickleItem(new ItemProperties().attackDamage(0).attackSpeed(-1.5f).tier(ToolMaterials.IRON).radius(3).setGroup(ItemGroup.TOOLS)), "iron_sickle");
        ItemRegistry.gold_sickle = register(new SickleItem(new ItemProperties().attackDamage(0).attackSpeed(-1.5f).tier(ToolMaterials.GOLD).radius(1).setGroup(ItemGroup.TOOLS)), "gold_sickle");
        ItemRegistry.diamond_sickle = register(new SickleItem(new ItemProperties().attackDamage(0).attackSpeed(-1.5f).tier(ToolMaterials.DIAMOND).radius(4).setGroup(ItemGroup.TOOLS)), "diamond_sickle");

        ItemRegistry.lunar_moth_jar_turquoise = register(new LunarMothJarItem(BlockRegistry.turquoise_lunar_moth_jar, LunarMothColors.TURQUOISE, new Item.Settings().group(DRUIDCRAFT)), "lunar_moth_jar");
//                        ItemRegistry.lunar_moth_jar_white = register(new LunarMothJarItem(BlockRegistry.white_lunar_moth_jar, LunarMothColors.WHITE, new LunarMothJarItem.Settings().group(DRUIDCRAFT)), "white_lunar_moth_jar");
//                        ItemRegistry.lunar_moth_jar_lime = register(new LunarMothJarItem(BlockRegistry.lime_lunar_moth_jar, LunarMothColors.LIME, new LunarMothJarItem.Settings().group(DRUIDCRAFT)), "lime_lunar_moth_jar");
//                        ItemRegistry.lunar_moth_jar_yellow = register(new LunarMothJarItem(BlockRegistry.yellow_lunar_moth_jar, LunarMothColors.YELLOW, new LunarMothJarItem.Settings().group(DRUIDCRAFT)), "yellow_lunar_moth_jar");
//                        ItemRegistry.lunar_moth_jar_orange = register(new LunarMothJarItem(BlockRegistry.orange_lunar_moth_jar, LunarMothColors.ORANGE, new LunarMothJarItem.Settings().group(DRUIDCRAFT)), "orange_lunar_moth_jar");
//                        ItemRegistry.lunar_moth_jar_pink = register(new LunarMothJarItem(BlockRegistry.pink_lunar_moth_jar, LunarMothColors.PINK, new LunarMothJarItem.Settings().group(DRUIDCRAFT)), "pink_lunar_moth_jar");


        // Item-blocks:
        ItemRegistry.amber_ore = register(new BlockItem(BlockRegistry.amber_ore, new Item.Settings().group(DRUIDCRAFT)), Registry.BLOCK.getId(BlockRegistry.amber_ore));
        ItemRegistry.moonstone_ore = register(new BlockItem(BlockRegistry.moonstone_ore, new Item.Settings().group(DRUIDCRAFT)), Registry.BLOCK.getId(BlockRegistry.moonstone_ore));
        ItemRegistry.fiery_glass_ore = register(new BlockItem(BlockRegistry.fiery_glass_ore, new Item.Settings().group(DRUIDCRAFT)), Registry.BLOCK.getId(BlockRegistry.fiery_glass_ore));
        ItemRegistry.rockroot_ore = register(new BlockItem(BlockRegistry.rockroot_ore, new Item.Settings().group(DRUIDCRAFT)), Registry.BLOCK.getId(BlockRegistry.rockroot_ore));
        ItemRegistry.amber_block = register(new BlockItem(BlockRegistry.amber_block, new Item.Settings().group(DRUIDCRAFT)), Registry.BLOCK.getId(BlockRegistry.amber_block));
        ItemRegistry.moonstone_block = register(new BlockItem(BlockRegistry.moonstone_block, new Item.Settings().group(DRUIDCRAFT)), Registry.BLOCK.getId(BlockRegistry.moonstone_block));
        ItemRegistry.fiery_glass_block = register(new BlockItem(BlockRegistry.fiery_glass_block, new Item.Settings().group(DRUIDCRAFT)), Registry.BLOCK.getId(BlockRegistry.fiery_glass_block));
        ItemRegistry.rockroot_block = register(new BlockItem(BlockRegistry.rockroot_block, new Item.Settings().group(DRUIDCRAFT)), Registry.BLOCK.getId(BlockRegistry.rockroot_block));
        ItemRegistry.darkwood_log = register(new BlockItem(BlockRegistry.darkwood_log, new Item.Settings().group(DRUIDCRAFT)), Registry.BLOCK.getId(BlockRegistry.darkwood_log));
        ItemRegistry.stripped_darkwood_log = register(new BlockItem(BlockRegistry.stripped_darkwood_log, new Item.Settings().group(DRUIDCRAFT)), Registry.BLOCK.getId(BlockRegistry.stripped_darkwood_log));
        ItemRegistry.darkwood_leaves = register(new BlockItem(BlockRegistry.darkwood_leaves, new Item.Settings().group(DRUIDCRAFT)), Registry.BLOCK.getId(BlockRegistry.darkwood_leaves));
        ItemRegistry.darkwood_sapling = register(new BlockItem(BlockRegistry.darkwood_sapling, new Item.Settings().group(DRUIDCRAFT)), Registry.BLOCK.getId(BlockRegistry.darkwood_sapling));
        ItemRegistry.darkwood_planks = register(new BlockItem(BlockRegistry.darkwood_planks, new Item.Settings().group(DRUIDCRAFT)), Registry.BLOCK.getId(BlockRegistry.darkwood_planks));
        ItemRegistry.stripped_darkwood_wood = register(new BlockItem(BlockRegistry.stripped_darkwood_wood, new Item.Settings().group(DRUIDCRAFT)), Registry.BLOCK.getId(BlockRegistry.stripped_darkwood_wood));
        ItemRegistry.darkwood_wood = register(new BlockItem(BlockRegistry.darkwood_wood, new Item.Settings().group(DRUIDCRAFT)), Registry.BLOCK.getId(BlockRegistry.darkwood_wood));
        ItemRegistry.darkwood_slab = register(new BlockItem(BlockRegistry.darkwood_slab, new Item.Settings().group(DRUIDCRAFT)), Registry.BLOCK.getId(BlockRegistry.darkwood_slab));
        ItemRegistry.darkwood_stairs = register(new BlockItem(BlockRegistry.darkwood_stairs, new Item.Settings().group(DRUIDCRAFT)), Registry.BLOCK.getId(BlockRegistry.darkwood_stairs));
        ItemRegistry.darkwood_fence = register(new BlockItem(BlockRegistry.darkwood_fence, new Item.Settings().group(DRUIDCRAFT)), Registry.BLOCK.getId(BlockRegistry.darkwood_fence));
        ItemRegistry.darkwood_fence_gate = register(new BlockItem(BlockRegistry.darkwood_fence_gate, new Item.Settings().group(DRUIDCRAFT)), Registry.BLOCK.getId(BlockRegistry.darkwood_fence_gate));
        ItemRegistry.darkwood_pressure_plate = register(new BlockItem(BlockRegistry.darkwood_pressure_plate, new Item.Settings().group(DRUIDCRAFT)), Registry.BLOCK.getId(BlockRegistry.darkwood_pressure_plate));
        ItemRegistry.darkwood_button = register(new BlockItem(BlockRegistry.darkwood_button, new Item.Settings().group(DRUIDCRAFT)), Registry.BLOCK.getId(BlockRegistry.darkwood_button));
        ItemRegistry.darkwood_trapdoor = register(new BlockItem(BlockRegistry.darkwood_trapdoor, new Item.Settings().group(DRUIDCRAFT)), Registry.BLOCK.getId(BlockRegistry.darkwood_trapdoor));
        ItemRegistry.darkwood_door = register(new BlockItem(BlockRegistry.darkwood_door, new Item.Settings().group(DRUIDCRAFT)), Registry.BLOCK.getId(BlockRegistry.darkwood_door));

        ItemRegistry.elder_log = register(new BlockItem(BlockRegistry.elder_log, new Item.Settings().group(DRUIDCRAFT)), Registry.BLOCK.getId(BlockRegistry.elder_log));
        ItemRegistry.elder_leaves = register(new BlockItem(BlockRegistry.elder_leaves, new Item.Settings().group(DRUIDCRAFT)), Registry.BLOCK.getId(BlockRegistry.elder_leaves));
        ItemRegistry.elder_sapling = register(new BlockItem(BlockRegistry.elder_sapling, new Item.Settings().group(DRUIDCRAFT)), Registry.BLOCK.getId(BlockRegistry.elder_sapling));
        ItemRegistry.elder_wood = register(new BlockItem(BlockRegistry.elder_wood, new Item.Settings().group(DRUIDCRAFT)), Registry.BLOCK.getId(BlockRegistry.elder_wood));

        ItemRegistry.oak_beam = register(new BlockItem(BlockRegistry.oak_beam, new Item.Settings().group(DRUIDCRAFT)), Registry.BLOCK.getId(BlockRegistry.oak_beam));
        ItemRegistry.spruce_beam = register(new BlockItem(BlockRegistry.spruce_beam, new Item.Settings().group(DRUIDCRAFT)), Registry.BLOCK.getId(BlockRegistry.spruce_beam));
        ItemRegistry.birch_beam = register(new BlockItem(BlockRegistry.birch_beam, new Item.Settings().group(DRUIDCRAFT)), Registry.BLOCK.getId(BlockRegistry.birch_beam));
        ItemRegistry.jungle_beam = register(new BlockItem(BlockRegistry.jungle_beam, new Item.Settings().group(DRUIDCRAFT)), Registry.BLOCK.getId(BlockRegistry.jungle_beam));
        ItemRegistry.acacia_beam = register(new BlockItem(BlockRegistry.acacia_beam, new Item.Settings().group(DRUIDCRAFT)), Registry.BLOCK.getId(BlockRegistry.acacia_beam));
        ItemRegistry.dark_oak_beam = register(new BlockItem(BlockRegistry.dark_oak_beam, new Item.Settings().group(DRUIDCRAFT)), Registry.BLOCK.getId(BlockRegistry.dark_oak_beam));
        ItemRegistry.darkwood_beam = register(new BlockItem(BlockRegistry.darkwood_beam, new Item.Settings().group(DRUIDCRAFT)), Registry.BLOCK.getId(BlockRegistry.darkwood_beam));
        ItemRegistry.oak_small_beam = register(new BlockItem(BlockRegistry.oak_small_beam, new Item.Settings().group(DRUIDCRAFT)), Registry.BLOCK.getId(BlockRegistry.oak_small_beam));
        ItemRegistry.spruce_small_beam = register(new BlockItem(BlockRegistry.spruce_small_beam, new Item.Settings().group(DRUIDCRAFT)), Registry.BLOCK.getId(BlockRegistry.spruce_small_beam));
        ItemRegistry.birch_small_beam = register(new BlockItem(BlockRegistry.birch_small_beam, new Item.Settings().group(DRUIDCRAFT)), Registry.BLOCK.getId(BlockRegistry.birch_small_beam));
        ItemRegistry.jungle_small_beam = register(new BlockItem(BlockRegistry.jungle_small_beam, new Item.Settings().group(DRUIDCRAFT)), Registry.BLOCK.getId(BlockRegistry.jungle_small_beam));
        ItemRegistry.acacia_small_beam = register(new BlockItem(BlockRegistry.acacia_small_beam, new Item.Settings().group(DRUIDCRAFT)), Registry.BLOCK.getId(BlockRegistry.acacia_small_beam));
        ItemRegistry.dark_oak_small_beam = register(new BlockItem(BlockRegistry.dark_oak_small_beam, new Item.Settings().group(DRUIDCRAFT)), Registry.BLOCK.getId(BlockRegistry.dark_oak_small_beam));
        ItemRegistry.darkwood_small_beam = register(new BlockItem(BlockRegistry.darkwood_small_beam, new Item.Settings().group(DRUIDCRAFT)), Registry.BLOCK.getId(BlockRegistry.darkwood_small_beam));
        ItemRegistry.oak_panels = register(new BlockItem(BlockRegistry.oak_panels, new Item.Settings().group(DRUIDCRAFT)), Registry.BLOCK.getId(BlockRegistry.oak_panels));
        ItemRegistry.spruce_panels = register(new BlockItem(BlockRegistry.spruce_panels, new Item.Settings().group(DRUIDCRAFT)), Registry.BLOCK.getId(BlockRegistry.spruce_panels));
        ItemRegistry.birch_panels = register(new BlockItem(BlockRegistry.birch_panels, new Item.Settings().group(DRUIDCRAFT)), Registry.BLOCK.getId(BlockRegistry.birch_panels));
        ItemRegistry.jungle_panels = register(new BlockItem(BlockRegistry.jungle_panels, new Item.Settings().group(DRUIDCRAFT)), Registry.BLOCK.getId(BlockRegistry.jungle_panels));
        ItemRegistry.acacia_panels = register(new BlockItem(BlockRegistry.acacia_panels, new Item.Settings().group(DRUIDCRAFT)), Registry.BLOCK.getId(BlockRegistry.acacia_panels));
        ItemRegistry.dark_oak_panels = register(new BlockItem(BlockRegistry.dark_oak_panels, new Item.Settings().group(DRUIDCRAFT)), Registry.BLOCK.getId(BlockRegistry.dark_oak_panels));
        ItemRegistry.darkwood_panels = register(new BlockItem(BlockRegistry.darkwood_panels, new Item.Settings().group(DRUIDCRAFT)), Registry.BLOCK.getId(BlockRegistry.darkwood_panels));
        ItemRegistry.wet_mud_bricks = register(new BlockItem(BlockRegistry.wet_mud_bricks, new Item.Settings().group(DRUIDCRAFT)), Registry.BLOCK.getId(BlockRegistry.wet_mud_bricks));
        ItemRegistry.dry_mud_bricks = register(new BlockItem(BlockRegistry.dry_mud_bricks, new Item.Settings().group(DRUIDCRAFT)), Registry.BLOCK.getId(BlockRegistry.dry_mud_bricks));
        ItemRegistry.fiery_torch = register(new WallStandingBlockItem(BlockRegistry.fiery_torch, BlockRegistry.wall_fiery_torch, new Item.Settings().group(DRUIDCRAFT)), Registry.BLOCK.getId(BlockRegistry.fiery_torch));
        ItemRegistry.rope = register(new BlockItem(BlockRegistry.rope, new Item.Settings().group(DRUIDCRAFT)), Registry.BLOCK.getId(BlockRegistry.rope));
        ItemRegistry.blueberries = register(new AliasedBlockItem(BlockRegistry.blueberry_bush, new Item.Settings().group(DRUIDCRAFT).food(FoodRegistry.blueberries)), "blueberries");
        ItemRegistry.elderberries = register(new Item(new Item.Settings().group(DRUIDCRAFT).food(FoodRegistry.elderberries)), "elderberries");
        ItemRegistry.elderflower = register(new Item(new Item.Settings().group(DRUIDCRAFT)), "elderflower");
        ItemRegistry.blueberry_muffin = register(new Item(new Item.Settings().group(DRUIDCRAFT).food(FoodRegistry.blueberry_muffin)), "blueberry_muffin");
        ItemRegistry.apple_elderberry_crumble = register(new Item(new Item.Settings().group(DRUIDCRAFT).food(FoodRegistry.apple_elderberry_crumble)), "apple_elderberry_crumble");
        ItemRegistry.elderflower_cordial = register(new DrinkableItem(new Item.Settings().group(DRUIDCRAFT).food(FoodRegistry.elderflower_cordial)), "elderflower_cordial");
        ItemRegistry.crate = register(new BlockItem(BlockRegistry.crate_temp, new Item.Settings().group(DRUIDCRAFT)), Registry.BLOCK.getId(BlockRegistry.crate_temp));
        ItemRegistry.ceramic_lantern = register(new BlockItem(BlockRegistry.ceramic_lantern, new Item.Settings().group(DRUIDCRAFT)), Registry.BLOCK.getId(BlockRegistry.ceramic_lantern));




        ItemRegistry.black_soulfire = register(new BlockItem(BlockRegistry.black_soulfire, new Item.Settings().group(DRUIDCRAFT)), Registry.BLOCK.getId(BlockRegistry.black_soulfire));
        ItemRegistry.red_soulfire = register(new BlockItem(BlockRegistry.red_soulfire, new Item.Settings().group(DRUIDCRAFT)), Registry.BLOCK.getId(BlockRegistry.red_soulfire));
        ItemRegistry.green_soulfire = register(new BlockItem(BlockRegistry.green_soulfire, new Item.Settings().group(DRUIDCRAFT)), Registry.BLOCK.getId(BlockRegistry.green_soulfire));
        ItemRegistry.brown_soulfire = register(new BlockItem(BlockRegistry.brown_soulfire, new Item.Settings().group(DRUIDCRAFT)), Registry.BLOCK.getId(BlockRegistry.brown_soulfire));
        ItemRegistry.blue_soulfire = register(new BlockItem(BlockRegistry.blue_soulfire, new Item.Settings().group(DRUIDCRAFT)), Registry.BLOCK.getId(BlockRegistry.blue_soulfire));
        ItemRegistry.purple_soulfire = register(new BlockItem(BlockRegistry.purple_soulfire, new Item.Settings().group(DRUIDCRAFT)), Registry.BLOCK.getId(BlockRegistry.purple_soulfire));
        ItemRegistry.cyan_soulfire = register(new BlockItem(BlockRegistry.cyan_soulfire, new Item.Settings().group(DRUIDCRAFT)), Registry.BLOCK.getId(BlockRegistry.cyan_soulfire));
        ItemRegistry.light_gray_soulfire = register(new BlockItem(BlockRegistry.light_gray_soulfire, new Item.Settings().group(DRUIDCRAFT)), Registry.BLOCK.getId(BlockRegistry.light_gray_soulfire));
        ItemRegistry.gray_soulfire = register(new BlockItem(BlockRegistry.gray_soulfire, new Item.Settings().group(DRUIDCRAFT)), Registry.BLOCK.getId(BlockRegistry.gray_soulfire));
        ItemRegistry.pink_soulfire = register(new BlockItem(BlockRegistry.pink_soulfire, new Item.Settings().group(DRUIDCRAFT)), Registry.BLOCK.getId(BlockRegistry.pink_soulfire));
        ItemRegistry.lime_soulfire = register(new BlockItem(BlockRegistry.lime_soulfire, new Item.Settings().group(DRUIDCRAFT)), Registry.BLOCK.getId(BlockRegistry.lime_soulfire));
        ItemRegistry.yellow_soulfire = register(new BlockItem(BlockRegistry.yellow_soulfire, new Item.Settings().group(DRUIDCRAFT)), Registry.BLOCK.getId(BlockRegistry.yellow_soulfire));
        ItemRegistry.light_blue_soulfire = register(new BlockItem(BlockRegistry.light_blue_soulfire, new Item.Settings().group(DRUIDCRAFT)), Registry.BLOCK.getId(BlockRegistry.light_blue_soulfire));
        ItemRegistry.magenta_soulfire = register(new BlockItem(BlockRegistry.magenta_soulfire, new Item.Settings().group(DRUIDCRAFT)), Registry.BLOCK.getId(BlockRegistry.magenta_soulfire));
        ItemRegistry.orange_soulfire = register(new BlockItem(BlockRegistry.orange_soulfire, new Item.Settings().group(DRUIDCRAFT)), Registry.BLOCK.getId(BlockRegistry.orange_soulfire));
        ItemRegistry.white_soulfire = register(new BlockItem(BlockRegistry.white_soulfire, new Item.Settings().group(DRUIDCRAFT)), Registry.BLOCK.getId(BlockRegistry.white_soulfire));



        EntityRegistry.registerEntitySpawnEggs();
        LOGGER.info("Items registered.");
    }

    // BLOCK REGISTRATION
    public static void onBlocksRegistry()
    {
        BlockRegistry.hemp_crop = register(new HempBlock(FabricBlockSettings.of(Material.PLANT).sounds(BlockSoundGroup.CROP).strength(0.0f, 0.0f).noCollision().ticksRandomly().build()), "hemp_crop");
        BlockRegistry.amber_ore = register(new OreBlock(FabricBlockSettings.of(Material.STONE).strength(3.0f, 3.0f).breakByTool(FabricToolTags.PICKAXES,1).build(), 6, 12), "amber_ore");
        BlockRegistry.moonstone_ore = register(new OreBlock(FabricBlockSettings.of(Material.STONE).strength(3.0f, 3.0f).breakByTool(FabricToolTags.PICKAXES, 3).build(), 6, 14), "moonstone_ore");
        BlockRegistry.fiery_glass_ore = register(new OreBlock(FabricBlockSettings.of(Material.STONE).strength(3.0f, 3.0f).breakByTool(FabricToolTags.PICKAXES, 2).build(), 4, 10), "fiery_glass_ore");
        BlockRegistry.rockroot_ore = register(new OreBlock(FabricBlockSettings.of(Material.STONE).strength(3.0f, 3.0f).breakByTool(FabricToolTags.PICKAXES, 0).build(), 2, 10), "rockroot_ore");
        BlockRegistry.amber_block = register(new Block(FabricBlockSettings.of(Material.STONE).strength(3.0f, 3.0f).breakByTool(FabricToolTags.PICKAXES, 1).sounds(BlockSoundGroup.STONE).build()), "amber_block");
        BlockRegistry.moonstone_block = register(new Block(FabricBlockSettings.of(Material.STONE).strength(6.0f, 6.0f).breakByTool(FabricToolTags.PICKAXES, 2).sounds(BlockSoundGroup.STONE).build()), "moonstone_block");
        BlockRegistry.fiery_glass_block = register(new Block(FabricBlockSettings.of(Material.STONE).strength(4.0f, 4.0f).breakByTool(FabricToolTags.PICKAXES, 2).sounds(BlockSoundGroup.STONE).build()), "fiery_glass_block");
        BlockRegistry.rockroot_block = register(new Block(FabricBlockSettings.of(Material.STONE).strength(3.0f, 3.0f).breakByTool(FabricToolTags.PICKAXES, 0).sounds(BlockSoundGroup.STONE).build()), "rockroot_block");
        BlockRegistry.darkwood_log = register(new LogBlock(MaterialColor.WOOD, FabricBlockSettings.of(Material.WOOD).strength(2.0f, 2.0f).breakByTool(FabricToolTags.AXES).sounds(BlockSoundGroup.WOOD).build()), "darkwood_log");
        BlockRegistry.stripped_darkwood_log = register(new LogBlock(MaterialColor.WOOD, FabricBlockSettings.copy(BlockRegistry.darkwood_log).build()), "stripped_darkwood_log");
        BlockRegistry.darkwood_leaves = register(new LeavesBlock(FabricBlockSettings.of(Material.LEAVES).strength(0.2f, 0.2f).ticksRandomly().sounds(BlockSoundGroup.GRASS).build()), "darkwood_leaves");
        BlockRegistry.darkwood_sapling = register(new SaplingBlock(new DarkwoodTree(), FabricBlockSettings.of(Material.PLANT).strength(0.0f, 0.0f).noCollision().sounds(BlockSoundGroup.GRASS).build()), "darkwood_sapling");
        BlockRegistry.potted_darkwood_sapling = register(new FlowerPotBlock(BlockRegistry.darkwood_sapling, FabricBlockSettings.of(Material.PART).strength(0.0f, 0.0f).sounds(BlockSoundGroup.STONE).build()), "potted_darkwood_sapling");
        BlockRegistry.darkwood_planks = register(new Block(FabricBlockSettings.of(Material.WOOD).strength(2.0F, 3.0f).breakByTool(FabricToolTags.AXES).sounds(BlockSoundGroup.WOOD).build()), "darkwood_planks");
        BlockRegistry.stripped_darkwood_wood = register(new PillarBlock(FabricBlockSettings.copy(BlockRegistry.darkwood_log).build()), "stripped_darkwood_wood");
        BlockRegistry.darkwood_wood = register(new PillarBlock(FabricBlockSettings.copy(BlockRegistry.darkwood_log).build()), "darkwood_wood");
        BlockRegistry.darkwood_slab = register(new SlabBlock(FabricBlockSettings.copy(BlockRegistry.darkwood_planks).build()), "darkwood_slab");
        BlockRegistry.darkwood_stairs = register(new StairsBlock(BlockRegistry.darkwood_planks.getDefaultState(), FabricBlockSettings.copy(BlockRegistry.darkwood_planks).build()), "darkwood_stairs");
        BlockRegistry.darkwood_fence = register(new FenceBlock(FabricBlockSettings.of(Material.WOOD).strength(2.0f, 3.0f).breakByTool(FabricToolTags.AXES).sounds(BlockSoundGroup.WOOD).build()), "darkwood_fence");
        BlockRegistry.darkwood_fence_gate = register(new FenceGateBlock(FabricBlockSettings.copy(BlockRegistry.darkwood_fence).build()), "darkwood_fence_gate");
        BlockRegistry.darkwood_pressure_plate = register(new PressurePlateBlock(net.minecraft.block.PressurePlateBlock.ActivationRule.EVERYTHING, FabricBlockSettings.of(Material.WOOD).strength(0.5f, 0.5f).breakByTool(FabricToolTags.AXES).sounds(BlockSoundGroup.WOOD).build()), "darkwood_pressure_plate");
        BlockRegistry.darkwood_button = register(new WoodButtonBlock(FabricBlockSettings.of(Material.WOOD).strength(0.5f, 0.5f).breakByTool(FabricToolTags.AXES).sounds(BlockSoundGroup.WOOD).build()), "darkwood_button");
        BlockRegistry.darkwood_trapdoor = register(new TrapDoorBlock(FabricBlockSettings.of(Material.WOOD).strength(3.0f, 3.0f).breakByTool(FabricToolTags.AXES).sounds(BlockSoundGroup.WOOD).build()), "darkwood_trapdoor");
        BlockRegistry.darkwood_door = register(new DoorBlock(FabricBlockSettings.of(Material.WOOD).strength(3.0f, 5.0f).breakByTool(FabricToolTags.AXES).sounds(BlockSoundGroup.WOOD).build()), "darkwood_door");

        BlockRegistry.elder_sapling = register(new SaplingBlock(new ElderTree(), FabricBlockSettings.of(Material.PLANT).strength(0.0f, 0.0f).noCollision().sounds(BlockSoundGroup.GRASS).build()), "elder_sapling");
        BlockRegistry.elder_fruit = register(new ElderFruitBlock(FabricBlockSettings.of(Material.PLANT).strength(0.0f, 0.0f).noCollision().sounds(BlockSoundGroup.GRASS).ticksRandomly().build()), "elder_fruit");
        BlockRegistry.elder_log = register(new LogBlock(MaterialColor.WOOD, FabricBlockSettings.of(Material.WOOD).strength(2.0f, 2.0f).breakByTool(FabricToolTags.AXES).sounds(BlockSoundGroup.WOOD).build()), "elder_log");
        BlockRegistry.elder_wood = register(new PillarBlock(FabricBlockSettings.copy(BlockRegistry.elder_log).build()), "elder_wood");
        BlockRegistry.elder_leaves = register(new ElderLeavesBlock(FabricBlockSettings.of(Material.LEAVES).strength(0.2f, 0.2f).ticksRandomly().sounds(BlockSoundGroup.GRASS).build()), "elder_leaves");

        BlockRegistry.oak_beam = register(new PillarBlock(FabricBlockSettings.of(Material.WOOD).sounds(BlockSoundGroup.WOOD).strength(2.0f, 2.0f).breakByTool(FabricToolTags.AXES).build()), "oak_beam");
        BlockRegistry.spruce_beam = register(new PillarBlock(FabricBlockSettings.of(Material.WOOD).sounds(BlockSoundGroup.WOOD).strength(2.0f, 2.0f).breakByTool(FabricToolTags.AXES).build()), "spruce_beam");
        BlockRegistry.birch_beam = register(new PillarBlock(FabricBlockSettings.of(Material.WOOD).sounds(BlockSoundGroup.WOOD).strength(2.0f, 2.0f).breakByTool(FabricToolTags.AXES).build()), "birch_beam");
        BlockRegistry.jungle_beam = register(new PillarBlock(FabricBlockSettings.of(Material.WOOD).sounds(BlockSoundGroup.WOOD).strength(2.0f, 2.0f).breakByTool(FabricToolTags.AXES).build()), "jungle_beam");
        BlockRegistry.acacia_beam = register(new PillarBlock(FabricBlockSettings.of(Material.WOOD).sounds(BlockSoundGroup.WOOD).strength(2.0f, 2.0f).breakByTool(FabricToolTags.AXES).build()), "acacia_beam");
        BlockRegistry.dark_oak_beam = register(new PillarBlock(FabricBlockSettings.of(Material.WOOD).sounds(BlockSoundGroup.WOOD).strength(2.0f, 2.0f).breakByTool(FabricToolTags.AXES).build()), "dark_oak_beam");
        BlockRegistry.darkwood_beam = register(new PillarBlock(FabricBlockSettings.of(Material.WOOD).sounds(BlockSoundGroup.WOOD).strength(2.0f, 2.0f).breakByTool(FabricToolTags.AXES).build()), "darkwood_beam");
        BlockRegistry.oak_small_beam = register(new SmallBeamBlock(FabricBlockSettings.of(Material.WOOD).sounds(BlockSoundGroup.WOOD).strength(2.0f, 2.0f).breakByTool(FabricToolTags.AXES).build()), "oak_small_beam");
        BlockRegistry.spruce_small_beam = register(new SmallBeamBlock(FabricBlockSettings.of(Material.WOOD).sounds(BlockSoundGroup.WOOD).strength(2.0f, 2.0f).breakByTool(FabricToolTags.AXES).build()), "spruce_small_beam");
        BlockRegistry.birch_small_beam = register(new SmallBeamBlock(FabricBlockSettings.of(Material.WOOD).sounds(BlockSoundGroup.WOOD).strength(2.0f, 2.0f).breakByTool(FabricToolTags.AXES).build()), "birch_small_beam");
        BlockRegistry.jungle_small_beam = register(new SmallBeamBlock(FabricBlockSettings.of(Material.WOOD).sounds(BlockSoundGroup.WOOD).strength(2.0f, 2.0f).breakByTool(FabricToolTags.AXES).build()), "jungle_small_beam");
        BlockRegistry.acacia_small_beam = register(new SmallBeamBlock(FabricBlockSettings.of(Material.WOOD).sounds(BlockSoundGroup.WOOD).strength(2.0f, 2.0f).breakByTool(FabricToolTags.AXES).build()), "acacia_small_beam");
        BlockRegistry.dark_oak_small_beam = register(new SmallBeamBlock(FabricBlockSettings.of(Material.WOOD).sounds(BlockSoundGroup.WOOD).strength(2.0f, 2.0f).breakByTool(FabricToolTags.AXES).build()), "dark_oak_small_beam");
        BlockRegistry.darkwood_small_beam = register(new SmallBeamBlock(FabricBlockSettings.of(Material.WOOD).sounds(BlockSoundGroup.WOOD).strength(2.0f, 2.0f).breakByTool(FabricToolTags.AXES).build()), "darkwood_small_beam");
        BlockRegistry.oak_panels = register(new Block(FabricBlockSettings.of(Material.WOOD).sounds(BlockSoundGroup.WOOD).strength(2.0f, 3.0f).breakByTool(FabricToolTags.AXES).build()), "oak_panels");
        BlockRegistry.spruce_panels = register(new Block(FabricBlockSettings.of(Material.WOOD).sounds(BlockSoundGroup.WOOD).strength(2.0f, 3.0f).breakByTool(FabricToolTags.AXES).build()), "spruce_panels");
        BlockRegistry.birch_panels = register(new Block(FabricBlockSettings.of(Material.WOOD).sounds(BlockSoundGroup.WOOD).strength(2.0f, 3.0f).breakByTool(FabricToolTags.AXES).build()), "birch_panels");
        BlockRegistry.jungle_panels = register(new Block(FabricBlockSettings.of(Material.WOOD).sounds(BlockSoundGroup.WOOD).strength(2.0f, 3.0f).breakByTool(FabricToolTags.AXES).build()), "jungle_panels");
        BlockRegistry.acacia_panels = register(new Block(FabricBlockSettings.of(Material.WOOD).sounds(BlockSoundGroup.WOOD).strength(2.0f, 3.0f).breakByTool(FabricToolTags.AXES).build()), "acacia_panels");
        BlockRegistry.dark_oak_panels = register(new Block(FabricBlockSettings.of(Material.WOOD).sounds(BlockSoundGroup.WOOD).strength(2.0f, 3.0f).breakByTool(FabricToolTags.AXES).build()), "dark_oak_panels");
        BlockRegistry.darkwood_panels = register(new Block(FabricBlockSettings.of(Material.WOOD).sounds(BlockSoundGroup.WOOD).strength(2.0f, 3.0f).breakByTool(FabricToolTags.AXES).build()), "darkwood_panels");
        BlockRegistry.dry_mud_bricks = register(new Block(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE).strength(2.0F, 4.0F).breakByTool(FabricToolTags.PICKAXES, 0).build()), "dry_mud_bricks");
        BlockRegistry.wet_mud_bricks = register(new WetMudBlock(BlockRegistry.dry_mud_bricks, FabricBlockSettings.of(Material.EARTH).sounds(BlockSoundGroup.SLIME).strength(0.8f, 0.8f).breakByTool(FabricToolTags.SHOVELS).ticksRandomly().build()), "wet_mud_bricks");
        BlockRegistry.fiery_torch = register(new FieryTorchBlock(FabricBlockSettings.of(Material.PART).noCollision().strength(0.0f, 0.0f).lightLevel(15).sounds(BlockSoundGroup.BAMBOO).build()), "fiery_torch");
        BlockRegistry.wall_fiery_torch = register(new WallFieryTorchBlock(FabricBlockSettings.of(Material.PART).noCollision().strength(0.0f, 0.0f).lightLevel(15).sounds(BlockSoundGroup.BAMBOO).dropsLike(BlockRegistry.fiery_torch).build()), "wall_fiery_torch");
        BlockRegistry.rope = register(new RopeBlock(FabricBlockSettings.of(Material.WOOL).sounds(BlockSoundGroup.WOOL).strength(0.0f, 0.0f).build()), "rope");
        BlockRegistry.rope_lantern = register(new RopeLanternBlock(FabricBlockSettings.of(Material.METAL).strength(3.5F, 3.5F).sounds(BlockSoundGroup.LANTERN).lightLevel(15).dropsLike(Blocks.LANTERN).build()), "rope_lantern");
        BlockRegistry.blueberry_bush = register(new BerryBushBlock(() -> ItemRegistry.blueberries, false, FabricBlockSettings.of(Material.PLANT).ticksRandomly().noCollision().sounds(BlockSoundGroup.SWEET_BERRY_BUSH).build()), "blueberry_bush");
        BlockRegistry.crate_temp = register(new CrateTempBlock(FabricBlockSettings.of(Material.WOOD).sounds(BlockSoundGroup.WOOD).strength(2.0f, 2.0f).breakByTool(FabricToolTags.AXES).build()), "crate");
        BlockRegistry.ceramic_lantern = register(new RopeableLanternBlock(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE).strength(2.0f, 2.0f).lightLevel(13).breakByTool(FabricToolTags.PICKAXES).build()), "ceramic_lantern");
        BlockRegistry.turquoise_lunar_moth_jar = register(new LunarMothJarBlock(FabricBlockSettings.of(Material.GLASS).sounds(BlockSoundGroup.GLASS).strength(1.0f, 1.0f).lightLevel(10).ticksRandomly().build(), 1), "turquoise_lunar_moth_lantern");
//        BlockRegistry.white_lunar_moth_jar = register(new LunarMothJarBlock(FabricBlockSettings.of(Material.GLASS).sounds(BlockSoundGroup.GLASS).strength(1.0f, 1.0f).lightLevel(10).ticksRandomly(), 1), "white_lunar_moth_lantern");
//        BlockRegistry.lime_lunar_moth_jar = register(new LunarMothJarBlock(FabricBlockSettings.of(Material.GLASS).sounds(BlockSoundGroup.GLASS).strength(1.0f, 1.0f).lightLevel(10).ticksRandomly(), 1), "lime_lunar_moth_lantern");
//        BlockRegistry.yellow_lunar_moth_jar = register(new LunarMothJarBlock(FabricBlockSettings.of(Material.GLASS).sounds(BlockSoundGroup.GLASS).strength(1.0f, 1.0f).lightLevel(10).ticksRandomly(), 1), "yellow_lunar_moth_lantern");
//        BlockRegistry.orange_lunar_moth_jar = register(new LunarMothJarBlock(FabricBlockSettings.of(Material.GLASS).sounds(BlockSoundGroup.GLASS).strength(1.0f, 1.0f).lightLevel(10).ticksRandomly(), 1), "orange_lunar_moth_lantern");
//        BlockRegistry.pink_lunar_moth_jar = register(new LunarMothJarBlock(FabricBlockSettings.of(Material.GLASS).sounds(BlockSoundGroup.GLASS).strength(1.0f, 1.0f).lightLevel(10).ticksRandomly(), 1), "pink_lunar_moth_lantern");


        BlockRegistry.black_soulfire = register(new SoulfireBlock(DyeColor.BLACK, FabricBlockSettings.of(Material.FIRE).sounds(BlockSoundGroup.SNOW).strength(0.0f, 0.0f).noCollision().lightLevel(13).build()), "black_soulfire");
        BlockRegistry.red_soulfire = register(new SoulfireBlock(DyeColor.RED, FabricBlockSettings.of(Material.FIRE).sounds(BlockSoundGroup.SNOW).strength(0.0f, 0.0f).noCollision().lightLevel(13).build()), "red_soulfire");
        BlockRegistry.green_soulfire = register(new SoulfireBlock(DyeColor.GREEN, FabricBlockSettings.of(Material.FIRE).sounds(BlockSoundGroup.SNOW).strength(0.0f, 0.0f).noCollision().lightLevel(13).build()), "green_soulfire");
        BlockRegistry.brown_soulfire = register(new SoulfireBlock(DyeColor.BROWN, FabricBlockSettings.of(Material.FIRE).sounds(BlockSoundGroup.SNOW).strength(0.0f, 0.0f).noCollision().lightLevel(13).build()), "brown_soulfire");
        BlockRegistry.blue_soulfire = register(new SoulfireBlock(DyeColor.BLUE, FabricBlockSettings.of(Material.FIRE).sounds(BlockSoundGroup.SNOW).strength(0.0f, 0.0f).noCollision().lightLevel(13).build()), "blue_soulfire");
        BlockRegistry.purple_soulfire = register(new SoulfireBlock(DyeColor.PURPLE, FabricBlockSettings.of(Material.FIRE).sounds(BlockSoundGroup.SNOW).strength(0.0f, 0.0f).noCollision().lightLevel(13).build()), "purple_soulfire");
        BlockRegistry.cyan_soulfire = register(new SoulfireBlock(DyeColor.CYAN, FabricBlockSettings.of(Material.FIRE).sounds(BlockSoundGroup.SNOW).strength(0.0f, 0.0f).noCollision().lightLevel(13).build()), "cyan_soulfire");
        BlockRegistry.light_gray_soulfire = register(new SoulfireBlock(DyeColor.LIGHT_GRAY, FabricBlockSettings.of(Material.FIRE).sounds(BlockSoundGroup.SNOW).strength(0.0f, 0.0f).noCollision().lightLevel(13).build()), "light_gray_soulfire");
        BlockRegistry.gray_soulfire = register(new SoulfireBlock(DyeColor.GRAY, FabricBlockSettings.of(Material.FIRE).sounds(BlockSoundGroup.SNOW).strength(0.0f, 0.0f).noCollision().lightLevel(13).build()), "gray_soulfire");
        BlockRegistry.pink_soulfire = register(new SoulfireBlock(DyeColor.PINK, FabricBlockSettings.of(Material.FIRE).sounds(BlockSoundGroup.SNOW).strength(0.0f, 0.0f).noCollision().lightLevel(13).build()), "pink_soulfire");
        BlockRegistry.lime_soulfire = register(new SoulfireBlock(DyeColor.LIME, FabricBlockSettings.of(Material.FIRE).sounds(BlockSoundGroup.SNOW).strength(0.0f, 0.0f).noCollision().lightLevel(13).build()), "lime_soulfire");
        BlockRegistry.yellow_soulfire = register(new SoulfireBlock(DyeColor.YELLOW, FabricBlockSettings.of(Material.FIRE).sounds(BlockSoundGroup.SNOW).strength(0.0f, 0.0f).noCollision().lightLevel(13).build()), "yellow_soulfire");
        BlockRegistry.light_blue_soulfire = register(new SoulfireBlock(DyeColor.LIGHT_BLUE, FabricBlockSettings.of(Material.FIRE).sounds(BlockSoundGroup.SNOW).strength(0.0f, 0.0f).noCollision().lightLevel(13).build()), "light_blue_soulfire");
        BlockRegistry.magenta_soulfire = register(new SoulfireBlock(DyeColor.MAGENTA, FabricBlockSettings.of(Material.FIRE).sounds(BlockSoundGroup.SNOW).strength(0.0f, 0.0f).noCollision().lightLevel(13).build()), "magenta_soulfire");
        BlockRegistry.orange_soulfire = register(new SoulfireBlock(DyeColor.ORANGE, FabricBlockSettings.of(Material.FIRE).sounds(BlockSoundGroup.SNOW).strength(0.0f, 0.0f).noCollision().lightLevel(13).build()), "orange_soulfire");
        BlockRegistry.white_soulfire = register(new SoulfireBlock(DyeColor.WHITE, FabricBlockSettings.of(Material.FIRE).sounds(BlockSoundGroup.SNOW).strength(0.0f, 0.0f).noCollision().lightLevel(13).build()), "white_soulfire");


        LOGGER.info("Blocks registered.");
    }

    // SOUND REGISTRATION
    public static void onSoundRegistry()
    {
        register(SoundEventRegistry.fill_bottle, "fill_bottle");
        register(SoundEventRegistry.open_crate, "open_crate");
        register(SoundEventRegistry.close_crate, "close_crate");

        LOGGER.info("Sound events registered.");
    }

    // ENTITY REGISTRATION
    public static void onEntityRegistry()
    {
        register(EntityRegistry.dreadfish_entity, "dreadfish");
        register(EntityRegistry.beetle_entity, "beetle");
        register(EntityRegistry.lunar_moth_entity, "lunar_moth");

        EntityRegistry.registerEntityWorldSpawns();
        LOGGER.info("Entities registered.");
    }

    // PARTICLE REGISTRATION
    public static void onParticleRegistry()
    {
        ParticleRegistry.registerFactories();

        LOGGER.info("Particles registered.");
    }

    public static void onRecipeRegistry()
    {

        RecipeRegistry.register();

        LOGGER.info("Recipes registered.");
    }

    // GUI REGISTRATION
    public static void onContainerRegistry()
    {
        //TODO: container reg
        register(GUIRegistry.beetle_inv, "beetle_inv");

        LOGGER.info("GUI registered.");
    }

    // TILE ENTITY REGISTRATION
    public static void onTileEntityRegistry()
    {
        TileEntityRegistry.crate = TileEntityRegistry.register("crate", BlockEntityType.Builder.create(CrateTileEntity::new, BlockRegistry.crate_temp));
        TileEntityRegistry.lunar_moth_jar = TileEntityRegistry.register("lunar_moth_jar", BlockEntityType.Builder.create(LunarMothJarTileEntity::new, BlockRegistry.turquoise_lunar_moth_jar /*, BlockRegistry.yellow_lunar_moth_jar, BlockRegistry.white_lunar_moth_jar, BlockRegistry.pink_lunar_moth_jar, BlockRegistry.orange_lunar_moth_jar, BlockRegistry.lime_lunar_moth_jar*/));

        LOGGER.info("Tile Entities registered.");
    }

    // FEATURE REGISTRATION
    public static void onFeatureRegistry()
    {

        FeatureRegistry.elder_tree = register(new OakTreeFeature(BranchedTreeFeatureConfig::deserialize2), "elder_tree");
        FeatureRegistry.blueberry_bush = register(new RandomPatchFeature(RandomPatchFeatureConfig::deserialize), "blueberry_bush");

        FeatureRegistry.spawnFeatures();
        LOGGER.info("Features registered.");
    }

    // BIOME REGISTRATION
    public static void onBiomeRegistry()
    {
        BiomeRegistry.darkwood_forest = register(new DarkwoodForest(), "darkwood_forest");

        OverworldBiomes.addContinentalBiome(BiomeRegistry.darkwood_forest, OverworldClimate.COOL, 1);
        OverworldBiomes.addBiomeVariant(Biomes.GIANT_SPRUCE_TAIGA, BiomeRegistry.darkwood_forest, 0.06, OverworldClimate.COOL);

        LOGGER.info("Biomes registered.");
    }

    public static Item register(Item item, String name) {
        return register(item, new Identifier(Druidcraft.MODID, name));
    }

    public static Item register(Item item, Identifier id) {
        return Registry.register(Registry.ITEM, id, item);
    }

    public static Block register(Block block, String name) {
        return Registry.register(Registry.BLOCK, new Identifier(Druidcraft.MODID, name), block);
    }

    public static SoundEvent register(SoundEvent event, String name) {
        return Registry.register(Registry.SOUND_EVENT, new Identifier(Druidcraft.MODID, name), event);
    }

    public static <T extends Entity> EntityType<T> register(EntityType<T> entity, String name) {
        return Registry.register(Registry.ENTITY_TYPE, new Identifier(Druidcraft.MODID, name), entity);
    }

    public static Biome register(Biome biome, String name) {
        return Registry.register(Registry.BIOME, new Identifier(Druidcraft.MODID, name), biome);
    }

    public static void register(ContainerFactory<Container> factory, String name) {
        ContainerProviderRegistry.INSTANCE.registerFactory(new Identifier(Druidcraft.MODID, name), factory);
    }

    public static <T extends FeatureConfig> Feature<T> register(Feature<T> feature, String name) {
        return Registry.register(Registry.FEATURE, new Identifier(Druidcraft.MODID, name), feature);
    }

}
