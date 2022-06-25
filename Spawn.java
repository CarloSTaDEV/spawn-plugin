package spawn.spawn;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;

public final class Spawn extends JavaPlugin  implements Listener {

    PluginDescriptionFile descriptionFile = getDescription();
    @Override
    public void onEnable() {
        // Plugin startup logic
        registerConfig();
        registerCommands();
        this.getServer().getPluginManager().registerEvents(this, this);
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&lThe plugin &e&lSpawn &a&l is enabled! &f&l(version: " + ChatColor.BLUE + descriptionFile.getVersion() + "&f&l)"));
    }

    @EventHandler
    public void onPlayerJoin (PlayerJoinEvent joinEvent){
        Player player = joinEvent.getPlayer();
        FileConfiguration fileConfiguration = this.getConfig();
        double x = Double.valueOf(fileConfiguration.getString("Spawn.x"));
        double y = Double.valueOf(fileConfiguration.getString("Spawn.y"));
        double z = Double.valueOf(fileConfiguration.getString("Spawn.z"));
        float yaw = Float.valueOf(fileConfiguration.getString("Spawn.yaw"));
        float pitch = Float.valueOf(fileConfiguration.getString("Spawn.pitch"));
        World world = this.getServer().getWorld(fileConfiguration.getString("Spawn.world"));
        Location location = new Location(world, x, y, z, yaw, pitch);
        player.teleport(location);
    }

    public void registerConfig() {
        File file = new File(this.getDataFolder(), "config.yml");
        if (!file.exists()) {
            this.getConfig().options().copyDefaults(true);
            saveConfig();
        }
    }

    public void registerCommands() {
        this.getCommand("sp").setExecutor(new Command(this));
    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lThe plugin &e&lSpawn &c&l is disabled! &f&l(version: " + ChatColor.BLUE + descriptionFile.getVersion() + "&f&l)"));
    }
}