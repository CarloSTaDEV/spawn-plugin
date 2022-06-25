package spawn.spawn;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;


public class Command implements CommandExecutor {

    public Spawn spawn;

    public Command(Spawn spawn) {
        this.spawn = spawn;
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        FileConfiguration fileConfiguration = spawn.getConfig();
        if (sender instanceof Player) {
            if (args.length > 0) {
                if (fileConfiguration.getString("Enabled.set").equals("true")) {
                    if (args[0].equals("setspawn")) {


                        if (sender.hasPermission("spawn.setspawn") || sender.isOp()) {
                            Location location = ((Player) sender).getLocation();
                            double x = location.getX();
                            double y = location.getY();
                            double z = location.getZ();
                            String world = location.getWorld().getName();
                            float yaw = location.getYaw();
                            float pitch = location.getPitch();
                            fileConfiguration.set("Spawn.x", x);
                            fileConfiguration.set("Spawn.y", y);
                            fileConfiguration.set("Spawn.z", z);
                            fileConfiguration.set("Spawn.world", world);
                            fileConfiguration.set("Spawn.yaw", yaw);
                            fileConfiguration.set("Spawn.pitch", pitch);
                            spawn.saveConfig();
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&9&lThe spawn has been set successfully!"));
                        } else {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lYou don't have permission to execute this command &f&l%player%!").replaceAll("%player%", sender.getName()));
                            return false;
                        }
                    } else if (args[0].equals("spawn")) {
                        if (sender.hasPermission("spawn.spawn") || sender.isOp()) {
                            if (fileConfiguration.contains("Spawn.x")) {

                                double x = Double.valueOf(fileConfiguration.getString("Spawn.x"));
                                double y = Double.valueOf(fileConfiguration.getString("Spawn.y"));
                                double z = Double.valueOf(fileConfiguration.getString("Spawn.z"));
                                float yaw = Float.valueOf(fileConfiguration.getString("Spawn.yaw"));
                                float pitch = Float.valueOf(fileConfiguration.getString("Spawn.pitch"));
                                World world = spawn.getServer().getWorld(fileConfiguration.getString("Spawn.world"));
                                Location location = new Location(world, x, y, z, yaw, pitch);
                                ((Player) sender).teleport(location);

                                return true;
                            } else {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lThe spawn doesn't exists"));
                            }
                        } else {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lYou don't have permission to execute this command &f&l%player%!".replaceAll("%player%", sender.getName())));
                        }
                    } else if (args[0].equals("reload")) {
                        if (sender.hasPermission("spawn.reload") || sender.isOp()) {
                            spawn.reloadConfig();
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e&lSpawn: &9&lThe plugin configuration has been reloaded successfully"));
                            return true;
                        } else {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lYou don't have permission to execute this command &f&l%player%!".replaceAll("%player%", sender.getName())));
                            return false;
                        }
                    }
                } else if (fileConfiguration.getString("Enabled.set").equals("false")) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lThe plugin is disabled &f&l%player%").replaceAll("%player%", sender.getName()));
                    return false;
                } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lException occurred in the &f&lplugin config!"));
                    return false;
                }
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lThis command doesn't exists &f&l%player%".replaceAll("%player%", sender.getName())));
            }
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lThis command cannot be executed from the console!"));
            return false;
        }
        return true;
    }
}