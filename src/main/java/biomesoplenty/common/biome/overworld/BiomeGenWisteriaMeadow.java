package biomesoplenty.common.biome.overworld;

import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import biomesoplenty.api.biome.BOPBiome;
import biomesoplenty.api.biome.generation.GeneratorStage;
import biomesoplenty.api.biome.generation.GeneratorWeighted;
import biomesoplenty.api.block.BOPBlocks;
import biomesoplenty.common.block.BlockBOPCoral;
import biomesoplenty.common.block.BlockBOPDoublePlant;
import biomesoplenty.common.block.BlockBOPLilypad;
import biomesoplenty.common.block.BlockBOPPlant;
import biomesoplenty.common.entities.EntityButterfly;
import biomesoplenty.common.entities.EntitySnail;
import biomesoplenty.common.enums.BOPClimates;
import biomesoplenty.common.enums.BOPFlowers;
import biomesoplenty.common.enums.BOPGems;
import biomesoplenty.common.enums.BOPPlants;
import biomesoplenty.common.enums.BOPTrees;
import biomesoplenty.common.enums.BOPWoods;
import biomesoplenty.common.util.biome.GeneratorUtils.ScatterYMethod;
import biomesoplenty.common.world.BOPWorldSettings;
import biomesoplenty.common.world.feature.GeneratorDoubleFlora;
import biomesoplenty.common.world.feature.GeneratorFlora;
import biomesoplenty.common.world.feature.GeneratorGrass;
import biomesoplenty.common.world.feature.GeneratorOreSingle;
import biomesoplenty.common.world.feature.GeneratorWaterside;
import biomesoplenty.common.world.feature.tree.GeneratorBasicTree;
import biomesoplenty.common.world.feature.tree.GeneratorBush;
import biomesoplenty.common.world.feature.tree.GeneratorTaigaTree;

public class BiomeGenWisteriaMeadow extends BOPBiome
{    
    public BiomeGenWisteriaMeadow()
    {
        // terrain
        this.terrainSettings.avgHeight(64).heightVariation(5, 5).octaves(0, 1, 2, 2, 1, 0).sidewaysNoise(0.1D);

        this.setColor(11569864);
        this.setTemperatureRainfall(0.5F, 0.7F);

        this.addWeight(BOPClimates.COOL_TEMPERATE, 2);
        
        this.canGenerateVillages = false;
        
        this.spawnableCreatureList.add(new SpawnListEntry(EntityButterfly.class, 6, 2, 4));
        
        //trees
        this.addGenerator("trees", GeneratorStage.TREE, (new GeneratorBasicTree.Builder()).amountPerChunk(12.0F).minHeight(9).maxHeight(14).hanging(BlockBOPPlant.paging.getVariantState(BOPPlants.WISTERIA)).log(BOPWoods.JACARANDA).leaves(BOPTrees.JACARANDA).maxLeavesRadius(2).hangingChance(0.75F).create());
        
        // grasses
        GeneratorWeighted grassGenerator = new GeneratorWeighted(8.0F);
        this.addGenerator("grass", GeneratorStage.GRASS, grassGenerator);
        grassGenerator.add("shortgrass", 1, (new GeneratorGrass.Builder()).with(BOPPlants.SHORTGRASS).create());
        grassGenerator.add("mediumgrass", 1, (new GeneratorGrass.Builder()).with(BOPPlants.MEDIUMGRASS).create());
        grassGenerator.add("wheatgrass", 1, (new GeneratorGrass.Builder()).with(BOPPlants.WHEATGRASS).create());
        grassGenerator.add("dampgrass", 1, (new GeneratorGrass.Builder()).with(BOPPlants.DAMPGRASS).create());
        grassGenerator.add("tallgrass", 2, (new GeneratorGrass.Builder()).with(BlockTallGrass.EnumType.GRASS).create());
        
        // flowers
        GeneratorWeighted flowerGenerator = new GeneratorWeighted(30.0F);
        this.addGenerator("flowers", GeneratorStage.GRASS, flowerGenerator);
        flowerGenerator.add("clover", 5, (new GeneratorFlora.Builder().with(BOPFlowers.CLOVER).create()));
        flowerGenerator.add("white_anemones", 5, (new GeneratorFlora.Builder().with(BOPFlowers.WHITE_ANEMONE).create()));
        flowerGenerator.add("houstonia", 6, (new GeneratorFlora.Builder().with(BlockFlower.EnumFlowerType.HOUSTONIA).create()));
        flowerGenerator.add("oxeye_daisy", 6, (new GeneratorFlora.Builder().with(BlockFlower.EnumFlowerType.OXEYE_DAISY).create()));
        flowerGenerator.add("syringa", 1, (new GeneratorDoubleFlora.Builder().with(BlockDoublePlant.EnumPlantType.SYRINGA).create()));
        flowerGenerator.add("allium", 1, (new GeneratorFlora.Builder().with(BlockFlower.EnumFlowerType.ALLIUM).create()));
        flowerGenerator.add("paeonias", 1, (new GeneratorDoubleFlora.Builder()).with(BlockDoublePlant.EnumPlantType.PAEONIA).create());
        
        // gem
        this.addGenerator("peridot", GeneratorStage.SAND, (new GeneratorOreSingle.Builder()).amountPerChunk(12).with(BOPGems.PERIDOT).create());
 
    }
    
    @Override
    public void applySettings(BOPWorldSettings settings)
    {
        if (!settings.generateBopGems) {this.removeGenerator("peridot");}
        
        if (!settings.generateBopFoliage) {this.removeGenerator("caveweed"); this.removeGenerator("bushes"); this.removeGenerator("koru"); this.removeGenerator("shrubs"); this.removeGenerator("leaf_piles"); this.removeGenerator("dead_leaf_piles"); this.removeGenerator("clover_patches"); this.removeGenerator("sprouts");}
        
        if (!settings.generateBopPlants) {this.removeGenerator("cattail"); this.removeGenerator("double_cattail"); this.removeGenerator("river_cane"); this.removeGenerator("tiny_cacti"); this.removeGenerator("roots"); this.removeGenerator("rafflesia"); this.removeGenerator("desert_sprouts");}
        
        GeneratorWeighted flowerGen = (GeneratorWeighted)this.getGenerator("flowers");
        if (!settings.generateBopFlowers) {flowerGen.removeGenerator("bluebells"); flowerGen.removeGenerator("clover"); flowerGen.removeGenerator("swampflower"); flowerGen.removeGenerator("deathbloom"); flowerGen.removeGenerator("glowflower"); flowerGen.removeGenerator("blue_hydrangeas"); flowerGen.removeGenerator("pink_daffodil"); flowerGen.removeGenerator("white_anemones"); flowerGen.removeGenerator("orange_cosmos"); flowerGen.removeGenerator("wildflowers"); flowerGen.removeGenerator("violet"); flowerGen.removeGenerator("hibiscus"); flowerGen.removeGenerator("goldenrods"); flowerGen.removeGenerator("icy_irises"); flowerGen.removeGenerator("wilted_lily"); flowerGen.removeGenerator("lily_of_the_valley"); flowerGen.removeGenerator("bromeliad"); this.removeGenerator("bromeliad");}
        
        GeneratorWeighted grassGen = (GeneratorWeighted)this.getGenerator("grass");
        if (!settings.generateBopGrasses) {grassGen.removeGenerator("shortgrass"); grassGen.removeGenerator("mediumgrass"); grassGen.removeGenerator("wheatgrass"); grassGen.removeGenerator("dampgrass");}
    }
}