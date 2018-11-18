package cs3500.animator.controller;

import java.util.Map;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ListSelectListener implements ListSelectionListener {
  private JList listSelectedAction;

  /**
   * Empty default constructor
   */
  public ListSelectListener() {
  }

  @Override
  public void valueChanged(ListSelectionEvent e) {
    System.out.println(e.toString());
  }
}
