package com.festerhead;

import java.util.HashMap;
import java.util.Map;
import com.festerhead.listener.EntityDamage;
import com.festerhead.object.ConfigCause;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.plugin.java.JavaPlugin;

public class CancelDamage extends JavaPlugin {

  FileConfiguration config;
  ConfigCause defaultCause;
  private final Map<DamageCause, ConfigCause> causes = new HashMap<>();

  public Map<DamageCause, ConfigCause> getCauses() {
    return causes;
  }

  public ConfigCause getDefaultCause() {
    return defaultCause;
  }

  @Override
  public void onEnable() {
    reloadConfig();
    getServer().getPluginManager().registerEvents(new EntityDamage(this), this);
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
    if (!config.isConfigurationSection("damage-cause")) {
      getLogger().severe("Configuration error: damage-cause not found.");
      getLogger().severe("All damage causes will follow defaults.");
    } else {
      boolean defaultFound = false;
      for (String damageCause : config.getConfigurationSection("damage-cause").getKeys(false)) {
        boolean enabled = config.getBoolean("damage-cause." + damageCause + ".enabled", true);
        String percentChance = config.getString("damage-cause." + damageCause + ".percent-chance", "100");
        String percentDamage = config.getString("damage-cause." + damageCause + ".percent-damage", "100");
        double costPerDamage = config.getDouble("damage-cause." + damageCause + ".cost-per-damage", 0.0);
        if (damageCause.toUpperCase().equals("DEFAULT")) {
          setupDefaults(enabled, percentChance, percentDamage, costPerDamage);
          defaultFound = true;
        } else {
          try {
            DamageCause key = DamageCause.valueOf(damageCause.toUpperCase());
            causes.put(key, new ConfigCause(enabled, percentChance, percentDamage, costPerDamage));
            getLogger().info("Registered " + key.toString() + " damage cause.");
          } catch (IllegalArgumentException exception) {
            getLogger().severe(damageCause.toUpperCase() + " is not a valid DamageCause and will be skipped.");
          }
        }
      }
      if (!defaultFound) {
        setupDefaults(false, "100", "100", 0.0);
      }
    }
  }

  private void setupDefaults(boolean enabled, String percentChance, String percentDamage, double costPerDamage) {
    defaultCause = new ConfigCause(enabled, percentChance, percentDamage, costPerDamage);
    getLogger().info("Registered defaults:");
    getLogger().info("        enabled: " + enabled);
    getLogger().info(" percent-chance: " + percentChance);
    getLogger().info(" percent-damage: " + percentDamage);
    getLogger().info("cost-per-damage: " + costPerDamage);
  }
}
