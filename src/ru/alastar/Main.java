package ru.alastar;


import com.sk89q.worldedit.Vector;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.logging.Logger;

/**
 * Created by Alastar on 28.07.2015.
 */
public class Main extends JavaPlugin implements Listener {

    protected static Logger log;
    static int heightOffset = 15;
    public static HashMap<Integer, Schema> schemas;
    public static Schema errorSchm;
    public static double UOmod = 4.5 / 20.0; //UO untis to minecraft blocks modifier
	public static double MultisUOmod = 3.45 / 20.0; // multis modifier  you may use old 4.0 / 20.0 for higher buildings
    public static int tilePerUpdate = 5000;
    public static long freq = 1;
    static LandWorker worker = new LandWorker();
    static MultisWorker mworker = new MultisWorker();
    public static HashMap<Integer, LandInfo> blocks;
    public static LandInfo stoneInfo = new LandInfo(Material.STONE, (byte) 0, Biome.PLAINS, false);
    public static LandInfo dirtInfo = new LandInfo(Material.DIRT, (byte) 0, Biome.PLAINS, true);

    @Override
    public void onEnable() {
        log = getLogger();
        //last - lava 500
        log.info("Init blocks db");
        //init blocks db
        blocks = new HashMap<>();

        blocks.put(16372, new LandInfo(Material.DIRT, (byte) 0, Biome.PLAINS, true));
        blocks.put(16376, new LandInfo(Material.DIRT, (byte) 0, Biome.PLAINS, true));
        blocks.put(16377, new LandInfo(Material.DIRT, (byte) 0, Biome.PLAINS, true));
        blocks.put(16378, new LandInfo(Material.DIRT, (byte) 0, Biome.PLAINS, true));
        blocks.put(16379, new LandInfo(Material.DIRT, (byte) 0, Biome.PLAINS, true));

        //WATER
        blocks.put(168, new LandInfo(Material.WATER, (byte) 0, Biome.DEEP_OCEAN, false));
        blocks.put(169, new LandInfo(Material.WATER, (byte) 0, Biome.DEEP_OCEAN, false));
        blocks.put(170, new LandInfo(Material.WATER, (byte) 0, Biome.DEEP_OCEAN, false));
        blocks.put(171, new LandInfo(Material.WATER, (byte) 0, Biome.DEEP_OCEAN, false));
        blocks.put(310, new LandInfo(Material.WATER, (byte) 0, Biome.DEEP_OCEAN, false));
        blocks.put(311, new LandInfo(Material.WATER, (byte) 0, Biome.DEEP_OCEAN, false));
        for (int i = 16368; i <= 16371; ++i) {
            blocks.put(i, new LandInfo(Material.WATER, (byte) 0, Biome.COLD_TAIGA_HILLS, true));    //water
        }
        //END WATER

        //GRASS
        blocks.put(3, new LandInfo(Material.GRASS, (byte) 0, Biome.PLAINS, true));
        blocks.put(4, new LandInfo(Material.GRASS, (byte) 0, Biome.PLAINS, true));
        blocks.put(5, new LandInfo(Material.GRASS, (byte) 0, Biome.PLAINS, true));
        blocks.put(6, new LandInfo(Material.GRASS, (byte) 0, Biome.PLAINS, true));
        blocks.put(16380, new LandInfo(Material.GRASS, (byte) 0, Biome.TAIGA, true));
        blocks.put(16381, new LandInfo(Material.GRASS, (byte) 0, Biome.TAIGA, true));
        blocks.put(16382, new LandInfo(Material.GRASS, (byte) 0, Biome.TAIGA, true));
        blocks.put(16383, new LandInfo(Material.GRASS, (byte) 0, Biome.TAIGA, true));

        for (int i = 172; i <= 191; ++i) {
            blocks.put(i, new LandInfo(Material.GRASS, (byte) 0, Biome.JUNGLE, true));    //grass
        }

        for (int i = 191; i <= 219; ++i) {
            blocks.put(i, new LandInfo(Material.GRASS, (byte) 0, Biome.PLAINS, true));    //grass
        }

        for (int i = 236; i <= 247; ++i) {
            blocks.put(i, new LandInfo(Material.STONE, (byte) 0, Biome.PLAINS, true));    //rock
        }

        for (int i = 248; i < 251; ++i) {
            blocks.put(i, new LandInfo(Material.GRASS, (byte) 0, Biome.PLAINS, true));    //grass
        }

        for (int i = 264; i <= 267; ++i) {
            blocks.put(i, new LandInfo(Material.GRASS, (byte) 0, Biome.JUNGLE, true));    //grass
        }

        for (int i = 349; i <= 372; ++i) {
            blocks.put(i, new LandInfo(Material.GRASS, (byte) 0, Biome.PLAINS, true));    //grass
        }

        for (int i = 420; i <= 423; ++i) {
            blocks.put(i, new LandInfo(Material.GRASS, (byte) 0, Biome.PLAINS, true));    //grass
        }

        for (int i = 496; i <= 499; ++i) {
            blocks.put(i, new LandInfo(Material.GRASS, (byte) 0, Biome.JUNGLE, true));    //grass
        }

        for (int i = 622; i <= 637; ++i) {
            blocks.put(i, new LandInfo(Material.GRASS, (byte) 0, Biome.JUNGLE, true));    //grass
        }

        for (int i = 658; i <= 661; ++i) {
            blocks.put(i, new LandInfo(Material.GRASS, (byte) 0, Biome.JUNGLE, true));    //grass
        }

        for (int i = 891; i <= 894; ++i) {
            blocks.put(i, new LandInfo(Material.GRASS, (byte) 0, Biome.PLAINS, true));    //grass
        }

        for (int i = 971; i <= 974; ++i) {
            blocks.put(i, new LandInfo(Material.GRASS, (byte) 0, Biome.PLAINS, true));    //grass
        }

        for (int i = 1351; i <= 1362; ++i) {
            blocks.put(i, new LandInfo(Material.GRASS, (byte) 0, Biome.FOREST, true));    //grass
        }

        for (int i = 1401; i <= 1446; ++i) {
            blocks.put(i, new LandInfo(Material.GRASS, (byte) 0, Biome.JUNGLE, true));    //grass
        }

        for (int i = 1521; i <= 1560; ++i) {
            blocks.put(i, new LandInfo(Material.GRASS, (byte) 0, Biome.FOREST, true));    //grass
        }

        for (int i = 1643; i <= 1646; ++i) {
            blocks.put(i, new LandInfo(Material.GRASS, (byte) 0, Biome.FOREST, true));    //grass
        }

        for (int i = 1697; i <= 1730; ++i) {
            blocks.put(i, new LandInfo(Material.GRASS, (byte) 0, Biome.PLAINS, true));    //grass
        }

        for (int i = 1758; i <= 1761; ++i) {
            blocks.put(i, new LandInfo(Material.GRASS, (byte) 0, Biome.PLAINS, true));    //grass
        }

        for (int i = 1855; i <= 1858; ++i) {
            blocks.put(i, new LandInfo(Material.GRASS, (byte) 0, Biome.JUNGLE, true));    //grass
        }

        for (int i = 2500; i <= 2539; ++i) {
            blocks.put(i, new LandInfo(Material.GRASS, (byte) 0, Biome.TAIGA, true));    //grass
        }
        //END GRASS

        //COBBLESTONE
        for (int i = 1001; i <= 1029; ++i) {
            blocks.put(i, new LandInfo(Material.COBBLESTONE, (byte) 0, Biome.PLAINS, true));    //cobblestone
        }

        for (int i = 1661; i <= 1696; ++i) {
            blocks.put(i, new LandInfo(Material.COBBLESTONE, (byte) 0, Biome.PLAINS, true));    //cobblestone
        }

        for (int i = 1946; i <= 1965; ++i) {
            blocks.put(i, new LandInfo(Material.COBBLESTONE, (byte) 0, Biome.PLAINS, true));    //cobblestone
        }

        for (int i = 11848; i <= 11859; ++i) {
            blocks.put(i, new LandInfo(Material.COBBLESTONE, (byte) 0, Biome.PLAINS, true));    //cobblestone
        }

        for (int i = 15882; i <= 15893; ++i) {
            blocks.put(i, new LandInfo(Material.COBBLESTONE, (byte) 0, Biome.PLAINS, true));    //cobblestone
        }

        for (int i = 16221; i <= 16232; ++i) {
            blocks.put(i, new LandInfo(Material.COBBLESTONE, (byte) 0, Biome.PLAINS, true));    //cobblestone
        }

        //END COBLESTONE

        //Obsidian
        for (int i = 16185; i <= 16220; ++i) {
            blocks.put(i, new LandInfo(Material.OBSIDIAN, (byte) 0, Biome.PLAINS, false));    //obsidian
        }
        for (int i = 16258; i <= 16319; ++i) {
            blocks.put(i, new LandInfo(Material.OBSIDIAN, (byte) 0, Biome.PLAINS, false));    //obsidian
        }
        for (int i = 16332; i <= 16363; ++i) {
            blocks.put(i, new LandInfo(Material.OBSIDIAN, (byte) 0, Biome.PLAINS, false));    //obsidian
        }
        //END

        //STONE BRICKS
        for (int i = 1259; i <= 1262; ++i) {
            blocks.put(i, new LandInfo(Material.SMOOTH_BRICK, (byte) 0, Biome.PLAINS, true));    //stonebrick
        }

        for (int i = 1367; i <= 1378; ++i) {
            blocks.put(i, new LandInfo(Material.SMOOTH_BRICK, (byte) 0, Biome.PLAINS, true));    //stonebrick
        }
        //END

        //SNOW
        for (int i = 268; i <= 271; ++i) {
            blocks.put(i, new LandInfo(Material.SNOW, (byte) 0, Biome.TAIGA, true));    //snow
        }
        for (int i = 276; i <= 285; ++i) {
            blocks.put(i, new LandInfo(Material.SNOW, (byte) 0, Biome.TAIGA, true));    //snow
        }

        for (int i = 302; i <= 305; ++i) {
            blocks.put(i, new LandInfo(Material.SNOW, (byte) 0, Biome.TAIGA, true));    //snow
        }

        for (int i = 377; i <= 394; ++i) {
            blocks.put(i, new LandInfo(Material.SNOW, (byte) 0, Biome.TAIGA, true));    //snow
        }

        for (int i = 937; i <= 940; ++i) {
            blocks.put(i, new LandInfo(Material.SNOW, (byte) 0, Biome.TAIGA, true));    //snow
        }

        for (int i = 1471; i <= 1510; ++i) {
            blocks.put(i, new LandInfo(Material.SNOW, (byte) 0, Biome.TAIGA, true));    //snow
        }

        for (int i = 1885; i <= 1907; ++i) {
            blocks.put(i, new LandInfo(Material.SNOW, (byte) 0, Biome.TAIGA, true));    //snow
        }
        //END SNOW

        //LAVA
        for (int i = 500; i <= 503; ++i) {
            blocks.put(i, new LandInfo(Material.LAVA, (byte) 0, Biome.DESERT, false));    //lava
        }
        //END LAVA

        //VOID
        for (int i = 506; i <= 511; ++i) {
            blocks.put(i, new LandInfo(Material.AIR, (byte) 0, Biome.PLAINS, false));    //void
        }
        for (int i = 12555; i <= 12574; ++i) {
            blocks.put(i, new LandInfo(Material.AIR, (byte) 0, Biome.PLAINS, false));    //void
        }
        //End VOID

        //Red stone floor
        blocks.put(512, new LandInfo(Material.NOTE_BLOCK, (byte) 0, Biome.PLAINS, true));    //note block
        for (int i = 517; i <= 524; ++i) {
            blocks.put(i, new LandInfo(Material.NOTE_BLOCK, (byte) 0, Biome.PLAINS, true));    //note block
        }

        for (int i = 1055; i <= 1063; ++i) {
            blocks.put(i, new LandInfo(Material.NOTE_BLOCK, (byte) 0, Biome.PLAINS, true));    //note block
        }

        for (int i = 1071; i <= 1077; ++i) {
            blocks.put(i, new LandInfo(Material.NOTE_BLOCK, (byte) 0, Biome.PLAINS, true));    //note block
        }
        //End Red stone floor

        //PLANKS
        for (int i = 662; i <= 699; ++i) {
            blocks.put(i, new LandInfo(Material.WOOD, (byte) 1, Biome.PLAINS, true));    //wood block
        }
        //END PLANKS

        //STONE FLOOR
        for (int i = 1078; i <= 1089; ++i) {
            blocks.put(i, new LandInfo(Material.DOUBLE_STEP, (byte) 0, Biome.PLAINS, true));    //stone slab
        }
        //END STONE FLOOR

        //FANCY CYAN STONE FLOOR
        for (int i = 1255; i <= 1256; ++i) {
            blocks.put(i, new LandInfo(Material.DIAMOND_BLOCK, (byte) 0, Biome.PLAINS, true));    //diamond block
        }
        //END

        //WOODEN FLOOR
        for (int i = 1030; i <= 1045; ++i) {
            blocks.put(i, new LandInfo(Material.WOOD, (byte) 0, Biome.PLAINS, true));    //wood block
        }
        for (int i = 1203; i <= 1249; ++i) {
            blocks.put(i, new LandInfo(Material.WOOD, (byte) 1, Biome.PLAINS, true));    //wood block
        }
        //END WOODEN FLOOR

        //Blue stone floor
        for (int i = 517; i <= 524; ++i) {
            blocks.put(i, new LandInfo(Material.LAPIS_BLOCK, (byte) 0, Biome.PLAINS, true));    //lapis block
        }

        for (int i = 1046; i <= 1054; ++i) {
            blocks.put(i, new LandInfo(Material.LAPIS_BLOCK, (byte) 0, Biome.PLAINS, true));    //lapis block
        }

        for (int i = 1064; i <= 1070; ++i) {
            blocks.put(i, new LandInfo(Material.LAPIS_BLOCK, (byte) 0, Biome.PLAINS, true));    //lapis block
        }
        //End Blue stone floor

        //Sandstone
        for (int i = 513; i <= 516; ++i) {
            blocks.put(i, new LandInfo(Material.SANDSTONE, (byte) 0, Biome.DESERT, true));    //sandstone
        }

        //yellow bricks road
        for (int i = 1090; i <= 1145; ++i) {
            blocks.put(i, new LandInfo(Material.SANDSTONE, (byte) 0, Biome.DESERT, true));    //sandstone
        }

        //yellow bricks road
        for (int i = 1281; i <= 1296; ++i) {
            blocks.put(i, new LandInfo(Material.SANDSTONE, (byte) 0, Biome.DESERT, true));    //sandstone
        }
        //END sandstone

        //RED BRICKS ROAD
        for (int i = 1146; i <= 1157; ++i) {
            blocks.put(i, new LandInfo(Material.NETHER_BRICK, (byte) 0, Biome.PLAINS, true));    //nether bricks
        }
        //END

        //FANCY BROWN FLOOR
        for (int i = 1167; i <= 1198; ++i) {
            blocks.put(i, new LandInfo(Material.WOOL, (byte) 12, Biome.PLAINS, true));    //wool
        }
        //END

        //BROWN BRICKS ROAD
        for (int i = 1167; i <= 1198; ++i) {
            blocks.put(i, new LandInfo(Material.BRICK, (byte) 0, Biome.PLAINS, true));    //bricks
        }
        for (int i = 1297; i <= 1308; ++i) {
            blocks.put(i, new LandInfo(Material.BRICK, (byte) 0, Biome.PLAINS, true));    //bricks
        }
        //END

        //WHITE BRICKS FLOOR
        for (int i = 1158; i <= 1161; ++i) {
            blocks.put(i, new LandInfo(Material.QUARTZ, (byte) 0, Biome.PLAINS, true));    //quartz
        }

        for (int i = 1257; i <= 1258; ++i) {
            blocks.put(i, new LandInfo(Material.QUARTZ, (byte) 0, Biome.PLAINS, true));    //quartz
        }

        for (int i = 1263; i <= 1266; ++i) {
            blocks.put(i, new LandInfo(Material.QUARTZ, (byte) 0, Biome.PLAINS, true));    //quartz
        }
        for (int i = 16364; i <= 16367; ++i) {
            blocks.put(i, new LandInfo(Material.QUARTZ, (byte) 0, Biome.PLAINS, false));    //quartz
        }
        //END

        //Farmland
        blocks.put(9, new LandInfo(Material.SOIL, (byte) 0, Biome.PLAINS, true));
        blocks.put(10, new LandInfo(Material.SOIL, (byte) 0, Biome.PLAINS, true));
        blocks.put(11, new LandInfo(Material.SOIL, (byte) 0, Biome.PLAINS, true));
        blocks.put(12, new LandInfo(Material.SOIL, (byte) 0, Biome.PLAINS, true));
        blocks.put(13, new LandInfo(Material.SOIL, (byte) 0, Biome.PLAINS, true));
        blocks.put(14, new LandInfo(Material.SOIL, (byte) 0, Biome.PLAINS, true));
        blocks.put(15, new LandInfo(Material.SOIL, (byte) 0, Biome.PLAINS, true));
        blocks.put(16, new LandInfo(Material.SOIL, (byte) 0, Biome.PLAINS, true));
        blocks.put(17, new LandInfo(Material.SOIL, (byte) 0, Biome.PLAINS, true));
        blocks.put(18, new LandInfo(Material.SOIL, (byte) 0, Biome.PLAINS, true));
        blocks.put(19, new LandInfo(Material.SOIL, (byte) 0, Biome.PLAINS, true));
        blocks.put(20, new LandInfo(Material.SOIL, (byte) 0, Biome.PLAINS, true));
        blocks.put(21, new LandInfo(Material.SOIL, (byte) 0, Biome.PLAINS, true));
        for (int i = 336; i <= 348; ++i) {
            blocks.put(i, new LandInfo(Material.SOIL, (byte) 0, Biome.PLAINS, true));
        }
        //END FARMLAND

        //SAND
        for (int i = 22; i <= 75; ++i) {
            blocks.put(i, new LandInfo(Material.SAND, (byte) 0, Biome.DESERT, false));    //sand
        }
        for (int i = 294; i <= 301; ++i) {
            blocks.put(i, new LandInfo(Material.SAND, (byte) 0, Biome.DESERT, false));    //sand
        }
        for (int i = 424; i <= 427; ++i) {
            blocks.put(i, new LandInfo(Material.SAND, (byte) 0, Biome.DESERT, false));    //sand
        }

        for (int i = 441; i <= 465; ++i) {
            blocks.put(i, new LandInfo(Material.SAND, (byte) 0, Biome.DESERT, false));    //sand
        }

        for (int i = 643; i <= 657; ++i) {
            blocks.put(i, new LandInfo(Material.SAND, (byte) 0, Biome.DESERT, false));    //sand
        }

        for (int i = 857; i <= 860; ++i) {
            blocks.put(i, new LandInfo(Material.SAND, (byte) 0, Biome.DESERT, false));    //sand
        }

        for (int i = 951; i <= 970; ++i) {
            blocks.put(i, new LandInfo(Material.SAND, (byte) 0, Biome.DESERT, false));    //sand
        }

        for (int i = 1447; i <= 1466; ++i) {
            blocks.put(i, new LandInfo(Material.SAND, (byte) 0, Biome.DESERT, false));    //sand
        }

        for (int i = 1611; i <= 1642; ++i) {
            blocks.put(i, new LandInfo(Material.SAND, (byte) 0, Biome.DESERT, false));    //sand
        }

        for (int i = 1647; i <= 1650; ++i) {
            blocks.put(i, new LandInfo(Material.SAND, (byte) 0, Biome.DESERT, false));    //sand
        }

        for (int i = 15918; i <= 15925; ++i) {
            blocks.put(i, new LandInfo(Material.SAND, (byte) 0, Biome.SWAMPLAND, true));    //sand
        }
        //END SAND

        //DIRT
        for (int i = 113; i < 168; ++i) {
            blocks.put(i, new LandInfo(Material.DIRT, (byte) 0, Biome.PLAINS, true));   //dirt
        }

        for (int i = 232; i < 236; ++i) {
            blocks.put(i, new LandInfo(Material.DIRT, (byte) 0, Biome.PLAINS, true));   //dirt
        }

        for (int i = 220; i < 227; ++i) {
            blocks.put(i, new LandInfo(Material.DIRT, (byte) 0, Biome.PLAINS, true));      //dirt
        }

        for (int i = 321; i <= 335; ++i) {
            blocks.put(i, new LandInfo(Material.DIRT, (byte) 0, Biome.PLAINS, true));      //dirt
        }

        for (int i = 361; i <= 372; ++i) {
            blocks.put(i, new LandInfo(Material.DIRT, (byte) 0, Biome.PLAINS, true));      //dirt
        }

        for (int i = 638; i <= 641; ++i) {
            blocks.put(i, new LandInfo(Material.DIRT, (byte) 0, Biome.PLAINS, true));      //dirt
        }

        for (int i = 720; i <= 856; ++i) {
            blocks.put(i, new LandInfo(Material.DIRT, (byte) 0, Biome.PLAINS, true));      //dirt
        }

        for (int i = 871; i <= 890; ++i) {
            blocks.put(i, new LandInfo(Material.DIRT, (byte) 0, Biome.PLAINS, true));      //dirt
        }

        for (int i = 901; i <= 936; ++i) {
            blocks.put(i, new LandInfo(Material.DIRT, (byte) 0, Biome.PLAINS, true));      //dirt
        }

        for (int i = 1309; i <= 1324; ++i) {
            blocks.put(i, new LandInfo(Material.DIRT, (byte) 0, Biome.PLAINS, true));      //dirt
        }

        for (int i = 1363; i <= 1366; ++i) {
            blocks.put(i, new LandInfo(Material.DIRT, (byte) 0, Biome.PLAINS, true));      //dirt
        }

        for (int i = 1571; i <= 1594; ++i) {
            blocks.put(i, new LandInfo(Material.DIRT, (byte) 0, Biome.PLAINS, true));      //dirt
        }

        for (int i = 1911; i <= 1937; ++i) {
            blocks.put(i, new LandInfo(Material.DIRT, (byte) 0, Biome.PLAINS, true));      //dirt
        }

        for (int i = 1966; i <= 1969; ++i) {
            blocks.put(i, new LandInfo(Material.DIRT, (byte) 0, Biome.PLAINS, true));      //dirt
        }

        for (int i = 2444; i <= 2495; ++i) {
            blocks.put(i, new LandInfo(Material.DIRT, (byte) 0, Biome.PLAINS, true));      //dirt
        }
        //END DIRT

        blocks.put(580, new LandInfo(Material.AIR, (byte) 0, Biome.PLAINS, false));

        //ROCK
        //sand rock
        for (int i = 290; i <= 293; ++i) {
            blocks.put(i, new LandInfo(Material.STONE, (byte) 0, Biome.EXTREME_HILLS, false));      //rock
        }
        for (int i = 228; i < 231; ++i) {
            blocks.put(i, new LandInfo(Material.STONE, (byte) 0, Biome.EXTREME_HILLS, false));      //rock
        }

        for (int i = 244; i < 248; ++i) {
            blocks.put(i, new LandInfo(Material.STONE, (byte) 0, Biome.EXTREME_HILLS, false));      //rock
        }

        for (int i = 260; i < 264; ++i) {
            blocks.put(i, new LandInfo(Material.STONE, (byte) 0, Biome.EXTREME_HILLS, false));      //rock
        }

        //snow rock
        for (int i = 272; i <= 275; ++i) {
            blocks.put(i, new LandInfo(Material.STONE, (byte) 0, Biome.EXTREME_HILLS, false));      //rock
        }

        for (int i = 467; i <= 474; ++i) {
            blocks.put(i, new LandInfo(Material.STONE, (byte) 0, Biome.EXTREME_HILLS, false));      //rock
        }


        for (int i = 543; i <= 621; ++i) {
            blocks.put(i, new LandInfo(Material.STONE, (byte) 0, Biome.EXTREME_HILLS, false, false));      //rock   //Use modifier set to false for high mountains
        }

        for (int i = 700; i <= 715; ++i) {
            blocks.put(i, new LandInfo(Material.STONE, (byte) 0, Biome.EXTREME_HILLS, false));      //rock
        }

        for (int i = 1325; i <= 1340; ++i) {
            blocks.put(i, new LandInfo(Material.STONE, (byte) 0, Biome.EXTREME_HILLS, false));      //rock
        }

        for (int i = 1595; i <= 1598; ++i) {
            blocks.put(i, new LandInfo(Material.STONE, (byte) 0, Biome.EXTREME_HILLS, false));      //rock
        }

        for (int i = 1741; i <= 1757; ++i) {
            blocks.put(i, new LandInfo(Material.STONE, (byte) 0, Biome.EXTREME_HILLS, false));      //rock
        }

        for (int i = 1771; i <= 1854; ++i) {
            blocks.put(i, new LandInfo(Material.STONE, (byte) 0, Biome.EXTREME_HILLS, false));      //rock
        }

        for (int i = 1861; i <= 1884; ++i) {
            blocks.put(i, new LandInfo(Material.STONE, (byte) 0, Biome.EXTREME_HILLS, false));      //rock
        }

        for (int i = 1981; i <= 2105; ++i) {
            blocks.put(i, new LandInfo(Material.STONE, (byte) 0, Biome.EXTREME_HILLS, false));      //rock
        }

        for (int i = 2540; i <= 2563; ++i) {
            blocks.put(i, new LandInfo(Material.STONE, (byte) 0, Biome.EXTREME_HILLS, false));      //rock
        }

        for (int i = 11860; i <= 1871; ++i) {
            blocks.put(i, new LandInfo(Material.STONE, (byte) 0, Biome.JUNGLE_EDGE, false));      //rock
        }

        for (int i = 15942; i <= 15973; ++i) {
            blocks.put(i, new LandInfo(Material.MOSSY_COBBLESTONE, (byte) 0, Biome.JUNGLE_EDGE, true));      //rock
        }
        //END ROCK

        //TREE
        for (int i = 11881; i <= 12213; ++i) {
            blocks.put(i, new LandInfo(Material.LOG, (byte) 0, Biome.ROOFED_FOREST, false));      //log
        }

        for (int i = 12519; i <= 12554; ++i) {
            blocks.put(i, new LandInfo(Material.LOG, (byte) 1, Biome.ROOFED_FOREST, false));      //log
        }

        for (int i = 14804; i <= 14823; ++i) {
            blocks.put(i, new LandInfo(Material.LOG, (byte) 2, Biome.ROOFED_FOREST, false));      //log
        }

        for (int i = 14879; i <= 14932; ++i) {
            blocks.put(i, new LandInfo(Material.LOG, (byte) 3, Biome.ROOFED_FOREST, false));      //log
        }
        //END

        //LEAVES
        for (int i = 15088; i <= 15096; ++i) {
            blocks.put(i, new LandInfo(Material.LEAVES, (byte) 0, Biome.ROOFED_FOREST, false));      //log
        }
        //END

        //DARK WOOD FLOOR
        blocks.put(15581, new LandInfo(Material.WOOD, (byte) 5, Biome.ROOFED_FOREST, false));      //log
        //END

        //SWAMP
        for (int i = 15717; i <= 15832; ++i) {
            blocks.put(i, new LandInfo(Material.GRASS, (byte) 0, Biome.SWAMPLAND, false));      //grass
        }

        for (int i = 15833; i <= 15869; ++i) {
            blocks.put(i, new LandInfo(Material.WATER, (byte) 0, Biome.SWAMPLAND, true));      //grass
        }

        for (int i = 15894; i <= 15905; ++i) {
            blocks.put(i, new LandInfo(Material.GRASS, (byte) 0, Biome.SWAMPLAND, true));      //grass
        }

        for (int i = 15906; i <= 15917; ++i) {
            blocks.put(i, new LandInfo(Material.WATER, (byte) 0, Biome.SWAMPLAND, true));      //grass
        }

        for (int i = 15926; i <= 15941; ++i) {
            blocks.put(i, new LandInfo(Material.WATER, (byte) 0, Biome.SWAMPLAND, true));      //grass
        }
        //END

        //ACID
        for (int i = 11778; i <= 11847; ++i) {
            blocks.put(i, new LandInfo(Material.WOOL, (byte) 5, Biome.EXTREME_HILLS, false));      //green wool?
        }
        //END

        //MULTIS
        schemas = new HashMap<>();
        errorSchm = new Schema();
        errorSchm.blocks.put(new Vector(0, 0, 0), new LandInfo(Material.WOOL, (byte) 15, Biome.DESERT, false));

        Schema waterSchm = new Schema();
        waterSchm.blocks.put(new Vector(0, 0, 0), new LandInfo(Material.WATER, (byte) 0, Biome.DESERT, false));
        schemas.put(5465, waterSchm);

        Schema fernSchm = new Schema();
        fernSchm.blocks.put(new Vector(0, 1, 0), new LandInfo(Material.LONG_GRASS, (byte) 2, Biome.DEEP_OCEAN, false));

        Schema grassSchm = new Schema();
        fernSchm.blocks.put(new Vector(0, 1, 0), new LandInfo(Material.LONG_GRASS, (byte) 1, Biome.DEEP_OCEAN, false));

        Schema deadBush = new Schema();
        deadBush.blocks.put(new Vector(0, 1, 0), new LandInfo(Material.DEAD_BUSH, (byte) 0, Biome.DEEP_OCEAN, false));

        Schema yellowFlowers = new Schema();
        yellowFlowers.blocks.put(new Vector(0, 1, 0), new LandInfo(Material.YELLOW_FLOWER, (byte) 0, Biome.DEEP_OCEAN, false));

        Schema boulder = new Schema();
        boulder.blocks.put(new Vector(0, 1, 0), new LandInfo(Material.STONE, (byte) 0, Biome.DEEP_OCEAN, false));

        Schema OaktreeSchm = new Schema();              //Better to place sapling to grow a tree after than build equal trees
        OaktreeSchm.blocks.put(new Vector(0, 1, 0), new LandInfo(Material.SAPLING, (byte) 0, Biome.DEEP_OCEAN, false));

        Schema pineTreeSchm = new Schema();              //Better to place sapling to grow a tree after than build equal trees
        pineTreeSchm.blocks.put(new Vector(0, 1, 0), new LandInfo(Material.SAPLING, (byte) 1, Biome.DEEP_OCEAN, false));

        Schema darkOakTreeSchm = new Schema();
        darkOakTreeSchm.blocks.put(new Vector(0, 1, 0), new LandInfo(Material.SAPLING, (byte) 5, Biome.DEEP_OCEAN, false));
        darkOakTreeSchm.blocks.put(new Vector(1, 1, 0), new LandInfo(Material.SAPLING, (byte) 5, Biome.DEEP_OCEAN, false));
        darkOakTreeSchm.blocks.put(new Vector(1, 1, 1), new LandInfo(Material.SAPLING, (byte) 5, Biome.DEEP_OCEAN, false));
        darkOakTreeSchm.blocks.put(new Vector(0, 1, 1), new LandInfo(Material.SAPLING, (byte) 5, Biome.DEEP_OCEAN, false));

        Schema whiteTreeSchm = new Schema();
        whiteTreeSchm.blocks.put(new Vector(0, 1, 0), new LandInfo(Material.SAPLING, (byte) 2, Biome.DEEP_OCEAN, false));

        Schema whiteMushroom = new Schema();
        whiteMushroom.blocks.put(new Vector(0, 1, 0), new LandInfo(Material.BROWN_MUSHROOM, (byte) 0, Biome.DEEP_OCEAN, false));

        Schema redMushroom = new Schema();
        redMushroom.blocks.put(new Vector(0, 1, 0), new LandInfo(Material.RED_MUSHROOM));

        Schema whiteFlower = new Schema();
        whiteFlower.blocks.put(new Vector(0, 1, 0), new LandInfo(Material.RED_ROSE, (byte) 5));

        Schema secretDoor = new WallSchema(Material.WOOL, (byte) 13, false);

        Schema stoneArch = new Schema();//null schema, it's too dangerous to handle arches

        Schema stoneConcrete = new ConcreteSchema(Material.SMOOTH_BRICK, (byte) 0);

        Schema stoneWall = new WallSchema(Material.SMOOTH_BRICK, (byte) 0, false);

        Schema logSchemaEast = new LogSchema((byte) 12, 0);
        Schema logSchemaSouth = new LogSchema((byte) 12, 1);

        Schema pinkFlower = new Schema();
        whiteFlower.blocks.put(new Vector(0, 1, 0), new LandInfo(Material.RED_ROSE, (byte) 7));

        Schema vines = new WallSchema(Material.VINE, (byte) 0, false);

        Schema pseudoPillow = new Schema();
        pseudoPillow.blocks.put(new Vector(0, 1, 0), new LandInfo(Material.IRON_PLATE, (byte) 0));

        Schema stonePillar = new PillarSchema(Material.SMOOTH_BRICK, 3, Material.SMOOTH_STAIRS);

        Schema cobbleStoneFloor = new FloorTileSchema(Material.COBBLESTONE, (byte) 0);

        Schema bedSchemaEastWest = new BedSchema(0);
        Schema bedSchemaSouthNorth = new BedSchema(0);

        Schema torchSouth = new Schema();
        torchSouth.blocks.put(new Vector(0, 0, 0), new LandInfo(Material.TORCH, (byte) 0));

        Schema torchEast = new Schema();
        torchEast.blocks.put(new Vector(0, 0, 0), new LandInfo(Material.TORCH, (byte) 1));

        Schema torchStraight = new Schema();
        torchStraight.blocks.put(new Vector(0, 0, 0), new LandInfo(Material.TORCH, (byte) 5));

        Schema logWall = new WallSchema(Material.LOG, (byte) 0, false);
        Schema logWallWindowed = new WallSchema(Material.LOG, (byte) 0, true);

        Schema woodTable = new Schema();
        woodTable.blocks.put(new Vector(0, 1, 0), new LandInfo(193, (byte) 0));

        Schema chimneyPiece1 = new Schema();
        chimneyPiece1.blocks.put(new Vector(0, 1, 0), new LandInfo(Material.COBBLESTONE));
        chimneyPiece1.blocks.put(new Vector(0, 2, 0), new LandInfo(Material.COBBLESTONE));

        Schema fireplace = new Schema();
        chimneyPiece1.blocks.put(new Vector(0, 0, 0), new LandInfo(Material.COBBLESTONE));
        chimneyPiece1.blocks.put(new Vector(1, 1, 0), new LandInfo(Material.COBBLESTONE));
        chimneyPiece1.blocks.put(new Vector(0, 1, 1), new LandInfo(Material.COBBLESTONE));
        chimneyPiece1.blocks.put(new Vector(1, 1, 1), new LandInfo(Material.COBBLESTONE));

        Schema stoneWallWindowed = new HighWindowedWall(Material.SMOOTH_BRICK);

        Schema woodRoof = new RoofTileSchema(Material.WOOD, (byte)4);
        Schema woodWall = new WallSchema(Material.WOOD, (byte)0, false);
        Schema woodWallWindowed = new WallSchema(Material.WOOD, (byte)0, true);
        Schema woodConcrete = new ConcreteSchema(Material.WOOD, (byte)0);

        Schema logConcrete = new ConcreteSchema(Material.LOG, (byte)0);


        Schema stoneFancyWall = new WallSchema(Material.SMOOTH_BRICK, (byte)3, false);
        Schema stoneFancyWallWindow = new WallSchema(Material.SMOOTH_BRICK, (byte)3, true);

        Schema  whiteWoodWall = new WallSchema(Material.WOOD, (byte)2, false);
        Schema  whiteWoodWallWindowed = new WallSchema(Material.WOOD, (byte)2, true);
        Schema  whiteWoodConcrete = new ConcreteSchema(Material.WOOD, (byte)2);

        Schema  CobbleWall = new WallSchema(Material.COBBLESTONE, (byte)0, false);
        Schema  CobbleWallWindowed = new WallSchema(Material.COBBLESTONE, (byte)0, true);
        Schema  CobbleConcrete = new ConcreteSchema(Material.COBBLESTONE, (byte)0);

        Schema leavesRoof = new RoofTileSchema(Material.LEAVES, (byte)0);
        Schema hayRoof = new RoofTileSchema(Material.HAY_BLOCK, (byte)0);
        Schema blueSlateRoof = new RoofTileSchema(Material.STAINED_CLAY, (byte)3);
        Schema logRoof = new RoofTileSchema(Material.LOG, (byte)0);
        Schema tentRoof = new RoofTileSchema(Material.WOOL, (byte)3);

        for(int i = 1630; i <= 1652; ++i)
        {
            schemas.put(i, tentRoof);
        }

        for(int i = 1587; i <= 1590; ++i)
        {
            schemas.put(i, tentRoof);
        }

        for(int i = 1608; i <= 1617; ++i)
        {
            schemas.put(i, tentRoof);
        }

        for(int i = 1535; i <= 1578; ++i)
        {
            schemas.put(i, tentRoof);
        }

        for(int i = 1520; i <= 1543; ++i)
        {
            schemas.put(i, logRoof);
        }

        for(int i = 1489; i <= 1516; ++i)
        {
            schemas.put(i, leavesRoof);
        }

        for(int i = 1429; i <= 1443; ++i)
        {
            schemas.put(i, blueSlateRoof);
        }

        for(int i = 1444; i <= 1458; ++i)
        {
            schemas.put(i, hayRoof);
        }

        for(int i = 1414; i <= 1428; ++i)
        {
            schemas.put(i, leavesRoof);
        }

        schemas.put(230, CobbleConcrete);
        schemas.put(231, CobbleConcrete);
        schemas.put(232, CobbleWall);
        schemas.put(234, CobbleWall);
        schemas.put(236, CobbleWall);
        schemas.put(238, CobbleWall);
        schemas.put(240, CobbleWall);
        schemas.put(242, CobbleWall);
        schemas.put(244, CobbleWall);
        schemas.put(246, CobbleWall);


        for(int i = 220; i <= 223; ++i)
        {
            schemas.put(i, CobbleConcrete);
        }

        for(int i = 224; i <= 229; ++i)
        {
            schemas.put(i, stoneArch);
        }

        for(int i = 205; i <= 218; ++i)
        {
            schemas.put(i, stoneArch);
        }

        schemas.put(202, CobbleWallWindowed);
        schemas.put(203, CobbleWallWindowed);
        schemas.put(204, CobbleWall);


        for(int i = 199; i <= 201; ++i)
        {
            schemas.put(i, CobbleWall);
        }

        schemas.put(197, CobbleWall);

        for(int i = 189; i <= 194; ++i)
        {
            schemas.put(i, whiteWoodConcrete);
        }

        for(int i = 185; i <= 188; ++i)
        {
            schemas.put(i, whiteWoodWallWindowed);
        }

        schemas.put(182, whiteWoodConcrete);
        schemas.put(183, whiteWoodConcrete);
        schemas.put(184, whiteWoodConcrete);


        for(int i = 166; i <= 181; ++i)
        {
            schemas.put(i, whiteWoodWall);
        }

        schemas.put(141, stoneFancyWallWindow);
        schemas.put(143, stoneFancyWallWindow);


        schemas.put(140, stoneFancyWall);
        schemas.put(142, stoneFancyWall);


        for(int i = 134; i <= 139; ++i)
        {
            schemas.put(i, stoneArch);
        }

        schemas.put(132, stoneWall);

        schemas.put(131, stoneWallWindowed);
        schemas.put(133, stoneWallWindowed);

        for(int i = 128; i <= 130; ++i)
        {
            schemas.put(i, stoneWall);
        }

        for(int i = 122; i <= 127; ++i)
        {
            schemas.put(i, stoneArch);
        }

        for (int i = 154; i <= 165; ++i) {
            schemas.put(i, logConcrete);
        }

        schemas.put(152, logWallWindowed);
        schemas.put(153, logWallWindowed);

        for (int i = 144; i <= 151; ++i) {
            schemas.put(i, logWall);
        }

        for (int i = 6; i <= 13; ++i) {
            schemas.put(i, woodWall);
        }

        for (int i = 16; i <= 25; ++i) {
            schemas.put(i, woodConcrete);
        }

        schemas.put(14, woodWallWindowed);
        schemas.put(15, woodWallWindowed);


        //TODO: make fireplace face dependent
        //schemas.put(2266, fireplace);
        //schemas.put(2255, fireplace);
        //schemas.put(2260, fireplace);
        //schemas.put(2270, fireplace);

        schemas.put(2265, stoneArch);
        schemas.put(2262, stoneArch);
        schemas.put(2268, stoneArch);
        schemas.put(2257, stoneArch);

        schemas.put(2258, chimneyPiece1);
        schemas.put(2269, chimneyPiece1);
        schemas.put(2264, chimneyPiece1);
        schemas.put(2261, chimneyPiece1);
        schemas.put(2263, chimneyPiece1);

        for (int i = 2923; i <= 2960; ++i) {
            schemas.put(i, woodTable);
        }

        for (int i = 1459; i <= 1488; ++i) {
            schemas.put(i, woodRoof);
        }

        for (int i = 4789; i <= 4793; ++i) {
            schemas.put(i, stoneArch);      //null
        }

        schemas.put(4794, darkOakTreeSchm);

        for (int i = 4795; i <= 4807; ++i) {
            schemas.put(i, stoneArch);      //null
        }

        for (int i = 2575; i <= 2580; ++i) {
            schemas.put(i, torchStraight);
        }

        for (int i = 2570; i <= 2574; ++i) {
            schemas.put(i, torchSouth);
        }

        for (int i = 2560; i <= 2564; ++i) {
            schemas.put(i, torchSouth);
        }

        for (int i = 2555; i <= 2559; ++i) {
            schemas.put(i, torchEast);
        }

        for (int i = 2565; i <= 2569; ++i) {
            schemas.put(i, torchEast);
        }

        schemas.put(2657, bedSchemaEastWest);
        schemas.put(2668, bedSchemaEastWest);
        schemas.put(2667, bedSchemaEastWest);

        schemas.put(2655, bedSchemaSouthNorth);
        schemas.put(2661, bedSchemaSouthNorth);
        schemas.put(2664, bedSchemaSouthNorth);
        schemas.put(2652, bedSchemaSouthNorth);
        schemas.put(2650, bedSchemaSouthNorth);

        schemas.put(2651, stoneArch);
        schemas.put(2654, stoneArch);
        schemas.put(2660, stoneArch);
        schemas.put(2652, stoneArch);
        schemas.put(2653, stoneArch);
        schemas.put(2665, stoneArch);
        schemas.put(2666, stoneArch);

        schemas.put(2656, stoneArch);
        schemas.put(2653, stoneArch);

        for (int i = 1301; i <= 1304; ++i) {
            schemas.put(i, cobbleStoneFloor);
        }

        schemas.put(200, stoneWall);
        schemas.put(203, stoneWallWindowed);

        schemas.put(204, stoneWall);

        for (int i = 3141; i <= 3150; ++i) {
            schemas.put(i, yellowFlowers);
        }

        schemas.put(3128, yellowFlowers);
        schemas.put(3127, yellowFlowers);

        schemas.put(216, stoneArch);

        schemas.put(3253, fernSchm);

        schemas.put(3213, whiteFlower);

        schemas.put(219, stonePillar);

        schemas.put(231, stoneConcrete);

        schemas.put(3205, yellowFlowers);

        schemas.put(3268, grassSchm);

        schemas.put(3272, deadBush);

        for (int i = 5028; i <= 5038; ++i) {
            schemas.put(i, pseudoPillow);

        }

        schemas.put(5015, pseudoPillow);

        for (int i = 3307; i <= 3314; ++i) {
            schemas.put(i, vines);
        }
        schemas.put(7086, stoneArch);

        schemas.put(3273, deadBush);

        for (int i = 3255; i <= 3261; ++i) {
            schemas.put(i, grassSchm);
        }

        schemas.put(3204, pinkFlower);
        schemas.put(3207, pinkFlower);
        schemas.put(3209, pinkFlower);
        schemas.put(3210, pinkFlower);

        schemas.put(3271, deadBush); //weed

        schemas.put(3319, stoneArch); //null
        schemas.put(3318, logSchemaEast);
        schemas.put(3317, stoneArch); //null

        schemas.put(3315, logSchemaSouth);
        schemas.put(3316, stoneArch); // null

        for (int i = 4943; i <= 4973; ++i) {
            schemas.put(i, boulder);
        }

        schemas.put(237, secretDoor);

        schemas.put(3211, whiteFlower);
        schemas.put(3212, whiteFlower);

        schemas.put(3391, deadBush);
        schemas.put(3392, deadBush);

        for (int i = 3351; i <= 3353; ++i) {
            schemas.put(i, whiteMushroom);
        }

        for (int i = 3349; i <= 3350; ++i) {
            schemas.put(i, redMushroom);
        }

        for (int i = 3340; i <= 3348; ++i) {
            schemas.put(i, whiteMushroom);
        }

        for (int i = 3277; i <= 3285; ++i) {
            schemas.put(i, whiteTreeSchm);
        }

        for (int i = 3290; i <= 3304; ++i) {
            schemas.put(i, OaktreeSchm);
        }

        for (int i = 3286; i <= 3289; ++i) {
            schemas.put(i, pineTreeSchm);
        }

        for (int i = 3274; i <= 3276; ++i) {
            schemas.put(i, OaktreeSchm);
        }

        schemas.put(3269, grassSchm);

        for (int i = 6001; i <= 6012; ++i) {
            schemas.put(i, boulder);
        }

        schemas.put(3369, yellowFlowers);
        schemas.put(3371, yellowFlowers);
        schemas.put(3373, yellowFlowers);
        schemas.put(3375, yellowFlowers);
        schemas.put(3382, yellowFlowers);

        schemas.put(3306, deadBush); // -----\/
        schemas.put(3305, deadBush);  // -> sapling id's, but they'll grow into trees :(

        for (int i = 3219; i <= 3252; ++i) {
            schemas.put(i, grassSchm);
        }
        schemas.put(3254, fernSchm);

        for (int i = 6038; i <= 6066; ++i) {
            schemas.put(i, waterSchm);
        }
        for (int i = 13422; i <= 13525; ++i) {
            schemas.put(i, waterSchm);
        }
        for (int i = 13549; i <= 13616; ++i) {
            schemas.put(i, waterSchm);
        }
        for (int i = 22000; i <= 22136; ++i) {
            schemas.put(i, waterSchm);
        }
    }

    @Override
    public void onDisable() {
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("convertuo")) {
            if (sender instanceof Player && args.length == 2) {
                if (args[0].equalsIgnoreCase("land")) {
                    World world = ((Player) sender).getWorld();
                    String name = args[1];
                    File f = new File(System.getProperty("user.dir") + "\\conv\\" + name + ".bin");
                    if(worker.str == null && !worker.processing) {
                        worker.set(world, f);
                        this.getServer().getScheduler().scheduleSyncRepeatingTask(this, worker, 0, freq);
                        sender.sendMessage("Land worker was started!");
                    }
                    else
                    {
                        sender.sendMessage("Land worker is busy!");
                    }
                    return true;
                } else if (args[0].equalsIgnoreCase("multis")) {
                    World world = ((Player) sender).getWorld();
                    String name = args[1];
                    File f = new File(System.getProperty("user.dir") + "\\conv\\" + name + ".bin");
                    if(mworker.str == null && !mworker.processing) {
                        mworker.set(world, f);
                        this.getServer().getScheduler().scheduleSyncRepeatingTask(this, mworker, 0, freq);
                        sender.sendMessage("Multis worker was started!");
                    }
                    else
                    {
                        sender.sendMessage("Multis worker is busy!");
                    }
                    return true;
                }
            }
        }
        return false;
    }

    private void convertMultis(final World world, final File f) {
        try {
            FileInputStream str = new FileInputStream(f);
            int x = 0;
            int y = 0;//y in minecraft coords
            int z = 0;//z in minecraft coords
            int id = 0;
            int height = 0;     //needed to get true multi's position - don't really needed, we already have true Z
            LandInfo bl;
            byte[] bytes = new byte[4];
            Block block;
            Schema schm;
            while (str.available() > 0) {

                str.read(bytes);
                x = ByteBuffer.wrap(bytes).getInt();

                str.read(bytes);
                z = ByteBuffer.wrap(bytes).getInt();

                str.read(bytes);
                y = ByteBuffer.wrap(bytes).getInt();

                str.read(bytes);
                id = ByteBuffer.wrap(bytes).getInt();

                str.read(bytes);
                height = ByteBuffer.wrap(bytes).getInt();

                //log.info("Multi Tile: (" + x + ", " + y + ", " + z + ") ID - " + id + " Height: " + height);

                schm = getSchemaById(id);

                for (Vector mod : schm.blocks.keySet()) {
                    bl = schm.blocks.get(mod);
                    block = world.getBlockAt(x + mod.getBlockX(), (int) (y * UOmod + heightOffset + mod.getBlockY()) /*- height*/, z + mod.getBlockZ());

                    if(!bl.isModded())
                        block.setType(bl.mat);
                    else
                        block.setTypeId(bl.getModId());
                    block.setData(bl.subId);
                }
            }
            str.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("Finished!");

    }

    public static Schema getSchemaById(int id) {
        if (schemas.containsKey(id)) {
            return schemas.get(id);
        }
        //If no blocks, set pink wool
        log.info("Unknown Multi ID: " + id);
        return errorSchm;
    }
    //id is ultima land tile id
    public static LandInfo getBlockEqualById(final int id) {
        if (blocks.containsKey(id)) {
            return blocks.get(id);
        }
        //If no blocks, set stone
        log.info("Unknown ID: " + id);
        return stoneInfo;
    }

}

