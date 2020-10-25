package com.festerhead.object;

import java.util.Random;

public class ConfigCause {
  private boolean enabled;
  private String percentChance;
  private String percentDamage;
  private double costPerDamage;

  public ConfigCause(boolean enabled, String percentChance, String percentDamage, double costPerDamage) {
    this.enabled = enabled;
    this.percentChance = percentChance;
    this.percentDamage = percentDamage;
    this.costPerDamage = costPerDamage;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public int getPercentChance() {
    return convertInput(percentChance);
  }

  public void setPercentChance(String percentChance) {
    this.percentChance = percentChance;
  }

  public int getPercentDamage() {
    return convertInput(percentDamage);
  }

  public void setPercentDamage(String percentDamage) {
    this.percentDamage = percentDamage;
  }

  public double getCostPerDamage() {
    return costPerDamage;
  }

  public void setCostPerDamage(double costPerDamage) {
    this.costPerDamage = costPerDamage;
  }

  private int convertInput(String input) {
    int returnValue = 100;
    if (input.toUpperCase().equals("RND")) {
      returnValue = new Random().nextInt(100);
    }
    try {
      returnValue = Integer.parseInt(input);
    } catch (NumberFormatException exception) {
      returnValue = 100;
    }
    return ((returnValue < 0) || (returnValue > 100) ? 100 : returnValue);
  }
}
