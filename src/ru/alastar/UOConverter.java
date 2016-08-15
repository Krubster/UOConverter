package ru.alastar;


import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by Alastar on 28.07.2015.
 */
public class UOConverter extends JavaPlugin implements Listener {

    public static HashMap<Integer, Schema> schemas;
    public static Schema errorSchm;
    public static double UOmod = 4.5 / 20.0; //UO untis to minecraft blocks modifier
    public static double MultisUOmod = 3.7 / 20.0; // multis modifier  you may use old 4.0 / 20.0 for higher buildings
    public static double UOHillsMod = 1; // hills modifier
    public static double UODungeonMod = 5 / 20.0; // dungeon modifier
    public static boolean clearAbove = false;


    public static int tilePerUpdate = 1000;
    public static long freq = 40; // in ticks(remember that 1 second = 20 ticks)
    public static HashMap<Integer, LandInfo> blocks;
    public static LandInfo stoneInfo = new LandInfo(Material.STONE.getId(), (byte) 0, Biome.PLAINS.ordinal(), false);
    public static LandInfo dirtInfo = new LandInfo(Material.DIRT.getId(), (byte) 0, Biome.PLAINS.ordinal(), true);
    public static UOConverter instance;
    public static BinaryManager b_manager = new BinaryManager();
    public static HashMap<UOVector, Integer> _unknown_clilocs;
    protected static Logger log;
    static int heightOffset = 25;
    static LandWorker worker = new LandWorker();
    static MultisWorker mworker = new MultisWorker();
    static ClilocWorker clilocworker = new ClilocWorker();
    static ArrayList<Player> _tools = new ArrayList<>();

    public static Schema getSchemaById(int id) {
        if (schemas.containsKey(id)) {
            return schemas.get(id);
        }
        //If no blocks, set pink wool
        // log.info("Unknown Multi ID: " + id);
        return errorSchm;
    }

    //id is ultima land tile id
    public static LandInfo getBlockEqualById(final int id) {
        if (blocks.containsKey(id)) {
            return blocks.get(id);
        }
        //If no blocks, set stone
        //  log.info("Unknown ID: " + id);
        return stoneInfo;
    }

    public static void saveUnknown() {
        try {

            File tempF = new File(System.getProperty("user.dir") + "\\unknown_clilocs.bin");
            if (!tempF.exists())
                tempF.createNewFile();

            FileOutputStream fos = new FileOutputStream(tempF);
            BufferedOutputStream out = new BufferedOutputStream(fos);
            out.write(ByteBuffer.allocate(4).putInt(_unknown_clilocs.entrySet().size()).array());

            for (Map.Entry<UOVector, Integer> entr : _unknown_clilocs.entrySet()) {
                out.write(ByteBuffer.allocate(4).putInt(entr.getKey().getBlockX()).array());
                out.write(ByteBuffer.allocate(4).putInt(entr.getKey().getBlockY()).array());
                out.write(ByteBuffer.allocate(4).putInt(entr.getKey().getBlockZ()).array());
                out.write(ByteBuffer.allocate(4).putInt(entr.getValue()).array());
            }
            out.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onEnable() {
        instance = this;
        log = getLogger();
        log.info("Init blocks db");

        blocks = b_manager.loadBlocksDB("blocks");
        log.info("Blocks entries loaded! Count: " + blocks.size());

        schemas = b_manager.loadSchemasDB("schemas");
        log.info("Schemas entries loaded! Count: " + schemas.size());

        _unknown_clilocs = b_manager.loadUnknown("unknown_clilocs.bin");
        log.info("Unknown clilocs loaded! Count: " + _unknown_clilocs.size());

        heightOffset = this.getConfig().getInt("heightOffset");
        clearAbove = this.getConfig().getBoolean("clearAbove");
        freq = this.getConfig().getInt("freq");
        tilePerUpdate = this.getConfig().getInt("tilesPerUpdate");
        UOmod = this.getConfig().getDouble("UOmod");
        MultisUOmod = this.getConfig().getDouble("UOMultisMod");
        UOHillsMod = this.getConfig().getDouble("UOHillsMod");
        UODungeonMod = this.getConfig().getDouble("UODungeonMod");

        errorSchm = new Schema();
        errorSchm.blocks.put(new UOVector(0, 0, 0), new LandInfo(Material.WOOL, (byte) 15, Biome.DESERT, false));
        getServer().getPluginManager().registerEvents(this, this);

    }

    @Override
    public void onDisable() {

    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("convertuo")) {
            if (sender instanceof Player && args.length >= 3) {
                if (args[0].equalsIgnoreCase("land")) {
                    if (args[1].equalsIgnoreCase("force")) {

                        World world = ((Player) sender).getWorld();
                        String name = args[2];
                        File f = new File(System.getProperty("user.dir") + "\\conv\\" + name + ".bin");
                        if (worker.str == null && !worker.processing && f.exists()) {
                            launchLWorker(sender, world, f, 0);
                        } else {
                            sender.sendMessage("Land worker is busy!(Or there's no file!)");
                        }
                        return true;
                    } else if (args.length == 4) {
                        World world = ((Player) sender).getWorld();
                        String name = args[2];
                        String idstr = args[3];

                        File f = new File(System.getProperty("user.dir") + "\\conv\\" + name + idstr + ".bin");
                        if (worker.str == null && !worker.processing && f.exists()) {
                            worker.setNextInc(name, Integer.parseInt(idstr));
                            launchLWorker(sender, world, f, 0);
                        } else {
                            sender.sendMessage("Land worker is busy!(Or there's no file!)");
                        }
                        return true;
                    }
                } else if (args[0].equalsIgnoreCase("multis")) {
                    if (args[1].equalsIgnoreCase("force")) {
                        World world = ((Player) sender).getWorld();
                        String name = args[2];
                        File f = new File(System.getProperty("user.dir") + "\\conv\\" + name + ".bin");
                        if (mworker.str == null && !mworker.processing && f.exists()) {
                            launchMWorker(sender, world, f, 0);
                        } else {
                            sender.sendMessage("Multis worker is busy!(Or there's no file!)");
                        }
                        return true;
                    } else if (args.length == 4) {
                        World world = ((Player) sender).getWorld();
                        String name = args[2];
                        String idstr = args[3];

                        File f = new File(System.getProperty("user.dir") + "\\conv\\" + name + idstr + ".bin");
                        if (mworker.str == null && !mworker.processing && f.exists()) {
                            mworker.setNextInc(name, Integer.parseInt(idstr));
                            launchMWorker(sender, world, f, 0);
                        } else {
                            sender.sendMessage("Multis worker is busy!(Or there's no file!)");
                        }
                        return true;
                    }
                }
            }
        }
        if (cmd.getName().equalsIgnoreCase("uotool")) {
            if (sender instanceof Player) {
                setTool((Player) sender);
            }
            return true;
        }
        if (cmd.getName().equalsIgnoreCase("uofind")) {
            if (sender instanceof Player) {
                World world = ((Player) sender).getWorld();
                String mode = args[0];
                String name = args[1];
                String idstr = args[2];
                // File f = new File(System.getProperty("user.dir") + "\\conv\\" + name + idstr + ".bin");
                if (mode.equalsIgnoreCase("multis"))
                    launchClilocWorker(sender, world, name, 0, 0, Integer.parseInt(idstr));
                else
                    launchClilocWorker(sender, world, name, 0, 1, Integer.parseInt(idstr));

                if (sender != null)
                    sender.sendMessage("Cliloc worker was started!");
            }
            return true;
        }
        return false;
    }


    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (_tools.contains(event.getPlayer()) && event.getItem() == null) {
                Block b = event.getClickedBlock();
                UOVector vec = new UOVector(b.getX(), b.getY(), b.getZ());
                if (_unknown_clilocs.containsKey(vec)) {
                    event.getPlayer().sendRawMessage("Unknown cliloc id: " + _unknown_clilocs.get(vec));
                } else {
                    event.getPlayer().sendRawMessage("Tis is not unknown cliloc!");
                }
            }
        }
    }

    private void setTool(Player sender) {
        if (_tools.contains(sender)) {
            _tools.remove(sender);
            sender.sendRawMessage("You dont have tool anymore!");
        } else {
            _tools.add(sender);
            sender.sendRawMessage("Your arm was bound as tool!");
        }
    }


    public void launchClilocWorker(CommandSender sender, World world, String f, int delay, int mode, int id) {

        clilocworker.set(world, f, mode, id);
        this.getServer().getScheduler().scheduleSyncRepeatingTask(this, clilocworker, delay, freq);
    }

    public void launchLWorker(CommandSender sender, World world, File f, int delay) {
        worker.set(world, f);
        this.getServer().getScheduler().scheduleSyncRepeatingTask(this, worker, delay, freq);
        if (sender != null)
            sender.sendMessage("Land worker was started!");
    }

    public void launchMWorker(CommandSender sender, World world, File f, int delay) {
        mworker.set(world, f);
        this.getServer().getScheduler().scheduleSyncRepeatingTask(this, mworker, delay, freq);
        if (sender != null)
            sender.sendMessage("Multis worker was started!");
    }

}

