package cs3500.animator.controller;

import java.util.HashMap;
import java.util.Map;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import cs3500.animator.model.AShape;

public class ListSelectListener implements ListSelectionListener {
  Runnable listAction = () -> {};

  /**
   * Empty default constructor
   */
  public ListSelectListener() {
  }

  public void setListActions(Runnable r) {
    listAction = r;
  }

  @Override
  public void valueChanged(ListSelectionEvent e) {
      listAction.run();
  }
}
