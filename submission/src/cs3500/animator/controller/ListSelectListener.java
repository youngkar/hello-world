package cs3500.animator.controller;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Represents a custom ListSelectionListener for listening to list selection events.
 */
public class ListSelectListener implements ListSelectionListener {
  Runnable listAction = () -> {
  };

  /**
   * Empty default constructor.
   */
  public ListSelectListener() {
    // default constructor for listselectlistener
  }

  /**
   * Sets the given Runnable as the action to run when a list select event occurs.
   *
   * @param r Runnable to set
   */
  public void setListActions(Runnable r) {
    listAction = r;
  }

  @Override
  public void valueChanged(ListSelectionEvent e) {
    listAction.run();
  }
}
