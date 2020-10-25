package com.festerhead.object;

import java.util.concurrent.ThreadLocalRandom;

public class ConfigCause {
  private boolean enabled;
  private String percentChance;
  private String percentCancel;

  public ConfigCause(boolean enabled, String percentChance, String percentCancel) {
    this.enabled = enabled;
    this.percentChance = percentChance;
    this.percentCancel = percentCancel;
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

  public int getPercentCancel() {
    return convertInput(percentCancel);
  }

  public void setPercentCancel(String percentCancel) {
    this.percentCancel = percentCancel;
  }

  private int convertInput(String input) {
    int returnValue = 100;
    if (input.toUpperCase().startsWith("RND")) {
      String[] colonArray = input.split(":");
      int min = 0;
      int max = 100;
      if (colonArray.length == 3) {
        try {
          min = Integer.parseInt(colonArray[1]);
        } catch (NumberFormatException exception) {
          min = 0;
        }
        try {
          max = Integer.parseInt(colonArray[2]);
        } catch (NumberFormatException exception) {
          max = 100;
        }
      }
      returnValue = ThreadLocalRandom.current().nextInt(min, max + 1);
    } else {
      try {
        returnValue = Integer.parseInt(input);
      } catch (NumberFormatException exception) {
        returnValue = 100;
      }
    }
    return ((returnValue < 0) || (returnValue > 100) ? 100 : returnValue);
  }
}
