package cs3500.animator.controller;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Map;

public class CheckBoxListener implements ItemListener {
  Map<Integer,Runnable> checkboxActions;

  /**
   * Empty default constructor
   */
  public CheckBoxListener() {
  }

  /**
   * Set the map for key typed events. Key typed events in Java Swing are characters
   */

  public void setCheckboxActionsMap(Map<Integer,Runnable> map) {
    checkboxActions = map;
  }

  @Override
  public void itemStateChanged(ItemEvent e) {
    if (e.getStateChange() == ItemEvent.SELECTED) {
      checkboxActions.get(e.getStateChange()).run();
    } else {
      checkboxActions.get(e.getStateChange()).run();
    }
  }
}
