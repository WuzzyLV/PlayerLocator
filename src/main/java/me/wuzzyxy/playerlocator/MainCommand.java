package me.wuzzyxy.playerlocator;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MainCommand implements CommandExecutor {

    private final PlayerLocator plugin;

    public MainCommand(PlayerLocator plugin){
        this.plugin = plugin;
        plugin.getCommand("playerlocator").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if(args.length == 0){
            sender.sendMessage("Usage: /playerlocator reload");
            return true;
        }

        if(args[0].equalsIgnoreCase("reload")){
            plugin.reloadConfig();
            sender.sendMessage("Config reloaded!");
            return true;
        }

        return false;
    }
}
