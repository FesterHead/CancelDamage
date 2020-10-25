package com.festerhead;

import java.util.HashMap;
import java.util.Map;

import com.festerhead.command.CommandManager;
import com.festerhead.command.Completer;
import com.festerhead.listener.EntityDamage;
import com.festerhead.object.ConfigCause;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.plugin.java.JavaPlugin;

public class CancelDamage extends JavaPlugin {

  FileConfiguration config;
  ConfigCause defaultCause;
  int logLevel;
  private final Map<DamageCause, ConfigCause> causes = new HashMap<>();

  public Map<DamageCause, ConfigCause> getCauses() {
    return causes;
  }

  public ConfigCause getDefaultCause() {
    return defaultCause;
  }

  public void logMessage(int level, String message) {
    if (logLevel >= level) {
      getLogger().info(message);
    }
  }

  @Override
  public void onEnable() {
    reloadConfig();
    getServer().getPluginManager().registerEvents(new EntityDamage(this), this);
    getCommand("canceldamage").setExecutor(new CommandManager(this));
    getCommand("canceldamage").setTabCompleter(new Completer());
  }

  @Override
  public void onDisable() {
  }

  @Override
  public void reloadConfig() {
    super.reloadConfig();
    saveDefaultConfig();
    config = getConfig();
    config.options().copyDefaults(true);
    saveConfig();

    getLogger().info("Loading configuration...");
    logLevel = config.getInt("log-level", 0);
    if (logLevel < 0 || logLevel > 2) {
      logLevel = 0;
    }
    getLogger().info("log-level: " + logLevel + (logLevel == 2 ? " - expect plenty console spam!"
        : (logLevel == 1 ? " - expect moderate console spam!" : "")));
    causes.clear();
    boolean defaultFound = false;
    for (String damageCause : config.getConfigurationSection("damage-cause").getKeys(false)) {
      boolean enabled = config.getBoolean("damage-cause." + damageCause + ".enabled", false);
      String percentChance = config.getString("damage-cause." + damageCause + ".percent-chance", "100");
      String percentCancel = config.getString("damage-cause." + damageCause + ".percent-cancel", "100");
      if (damageCause.equalsIgnoreCase("DEFAULT")) {
        setupDefaults(enabled, percentChance, percentCancel);
        defaultFound = true;
      } else {
        try {
          DamageCause key = DamageCause.valueOf(damageCause.toUpperCase());
          causes.put(key, new ConfigCause(enabled, percentChance, percentCancel));
          getLogger().info(key.toString() + " damage is " + (enabled ? "" : "not ") + "enabled and will be "
              + (enabled ? "processed." : "skipped."));
          if (enabled) {
            getLogger().info(" percent-chance: " + percentChance);
            getLogger().info(" percent-cancel: " + percentCancel);
          }
        } catch (IllegalArgumentException exception) {
          getLogger().severe(
              damageCause.toUpperCase() + " is not a valid entity.EntityDamageEvent.DamageCause and will be skipped.");
        }
      }
    }
    if (!defaultFound) {
      setupDefaults(false, "100", "100");
    }
    getLogger().info("Configuration loaded.");
  }

  private void setupDefaults(boolean enabled, String percentChance, String percentCancel) {
    defaultCause = new ConfigCause(enabled, percentChance, percentCancel);
    getLogger().info("Default damage is " + (enabled ? "" : "not ")
        + "enabled and all specific damage causes not listed will be " + (enabled ? "processed" : "skipped") + ".");
    if (enabled) {
      getLogger().info(" percent-chance: " + percentChance);
      getLogger().info(" percent-cancel: " + percentCancel);
    }
  }

}
