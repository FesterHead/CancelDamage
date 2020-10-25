package com.festerhead.command;

import com.festerhead.CancelDamage;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandManager implements CommandExecutor {

  private CancelDamage plugin;

  public CommandManager(CancelDamage plugin) {
    this.plugin = plugin;
  }

  public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
    if (cmd.getName().equalsIgnoreCase("canceldamage") && (sender.hasPermission("canceldamage.admin"))
        && args.length > 0) {
      switch (args[0].toUpperCase()) {
        case "RELOAD":
          plugin.reloadConfig();
          sender.sendMessage("CancelDamage configuration reloaded.");
          break;
        default:
          sender.sendMessage("Invalid command.");
          break;
      }
    }
    return true;
  }
}