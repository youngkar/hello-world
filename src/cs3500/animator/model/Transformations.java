//package cs3500.animator.model;
//
///**
// * Represents a Transformation, and all the operations that can be performed on a Transformation. A
// * user can make the transformation apply to a shape, can get the begin time, end time, old shape,
// * and name of the Transformation.
// */
//public interface Transformations {
//
//  /**
//   * Applies this transformation to the given shape.
//   *
//   * @param s the shape for the Transformation to be applied to
//   */
//  void apply(AShape s, int x2, int y2, int w2, int h2, int r2, int g2, int b2);
//
//  /**
//   * Gets the begin time.
//   *
//   * @return the begin time
//   */
//  int getBegin();
//
//  /**
//   *
//   */
//  void setBegin(int begin);
//
//  /**
//   * Gets the end time.
//   *
//   * @return the end time
//   */
//  int getEnd();
//
//  /**
//   *
//   */
//  void setEnd(int end);
//
//  /**
//   * Gets the old shape.
//   *
//   * @return the old shape (before the Transformation mutated it)
//   */
//  AShape getOldshape();
//
//  /**
//   * Gets the new shape.
//   *
//   * @return a copy of the new shape.
//   */
//  AShape getNewshape();
//
//  /**
//   * @param time The time at which a user wants to get the state.
//   * @param s    The shape the user wants the state from.
//   */
//  void transformationState(int time, AShape s);
//
//  /**
//   * @param s   The given shape.
//   * @param str The name of the transformation a user wants to check.
//   */
//  void checkStacked(AShape s, String str);
//
//  /**
//   * Gets the SVG display format.
//   *
//   * @param speed    the speed of the animation.
//   * @param shapeAtt the shape at time.
//   * @return SVG string.
//   */
//
//  String getSVG(int speed, String shapeAtt);
//
//}
