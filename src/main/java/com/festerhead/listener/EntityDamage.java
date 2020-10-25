package com.festerhead.listener;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

import com.festerhead.CancelDamage;
import com.festerhead.object.ConfigCause;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamage implements Listener {
  private CancelDamage plugin;

  public EntityDamage(CancelDamage plugin) {
    this.plugin = plugin;
  }

  @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
  public void onEntityDamage(EntityDamageEvent event) {
    Entity entity = event.getEntity();

    if (Objects.isNull(entity) || (entity instanceof Player)) {
      return;
    }

    ConfigCause cause = null;

    // Check the damage cause is configured or use the default
    if (plugin.getCauses().containsKey(event.getCause())) {
      cause = plugin.getCauses().get(event.getCause());
    } else {
      cause = plugin.getDefaultCause();
    }

    plugin.logMessage(1, "Is " + event.getEntityType() + " " + event.getCause() + " enabled? " + cause.isEnabled());

    // Check if the damage cause is enabled for cancelling
    if (Objects.nonNull(cause) && cause.isEnabled()) {
      // Is this a complete cancel?
      int percentChance = cause.getPercentChance();
      int rnd = ThreadLocalRandom.current().nextInt(0, 101);
      plugin.logMessage(2,
          "   " + "Cancel calculation = %-chance:" + percentChance + " >= rnd:" + rnd + " ? " + (percentChance >= rnd));
      if (percentChance >= rnd) {
        plugin.logMessage(2, "   " + event.getEntityType() + " " + event.getCause() + " damage is cancelled!");
        event.setCancelled(true);
      } else {
        // Compute and apply damage reduction
        int percentCancel = cause.getPercentCancel();
        double newDamage = ((double) ((100 - percentCancel) / 100.0)) * event.getDamage();
        plugin.logMessage(2, "   " + "Incoming damage: " + event.getDamage() + " %-cancel:" + percentCancel
            + " new damage:" + newDamage);
        event.setDamage(newDamage);
      }
    }
  }
}
