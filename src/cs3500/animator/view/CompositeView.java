package cs3500.animator.view;

public interface CompositeView extends View {

  /**
   *
   */
  void go();

  void pauseAnimation();

  void resumeAnimation();

  void restartAnimation();

  void enableLoop();

  void disableLoop();

  void increaseSpeed();

  void decreaseSpeed();

  /**
   * Make the view visible. This is usually called after the view is constructed
   */
  void makeVisible();

  /**
   * Signal the view to draw itself.
   */
  void refresh();

  //TODO delete CompositeView interface
}
