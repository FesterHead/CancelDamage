package com.festerhead.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

public class Completer implements TabCompleter {

  @Override
  public List<String> onTabComplete(CommandSender sender, Command command, String commandLable, String[] args) {

    List<String> completions = new ArrayList<>();
    if (args.length == 1) {
      completions.add("reload");
      return StringUtil.copyPartialMatches(args[0], completions, new ArrayList<>());
    }
    return null;
  }
}