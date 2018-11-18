//package cs3500.animator.model;
//
///**
// * Represents an abstract Transformations class. It abstracts out all the common fields (old shape,
// * new shape, begin time and end time) and common methods that all Transformations have the same
// * of.
// */
//public final class Transformation implements Transformations {
//  protected AShape oldshape;
//  protected AShape newshape;
//  private int begin;
//  private int end;
//  private int x2;
//  private int y2;
//  private int w2;
//  private int h2;
//  private int r2;
//  private int g2;
//  private int b2;
//
//
//  /**
//   * Constructor for a Transformation.
//   *
//   * @param begin represents the start time for this Transformation
//   * @param end   represents the end time for this Transformation
//   * @throws IllegalArgumentException if the end time is before the start time
//   */
//  public Transformation(int begin, int end, int x2, int y2, int w2, int h2, int r2,
//                        int g2, int b2) {
//   this.begin = begin;
//   this.end = end;
//   this.x2 = x2;
//   this.y2 = y2;
//   this.w2 = w2;
//   this.h2 = h2;
//   this.r2 = r2;
//   this.g2 = g2;
//   this.b2 = b2;
//  }
//
//  @Override
//  public int getBegin() {
//    return this.begin;
//  }
//
//  @Override
//  public void setBegin(int begin) { this.begin = begin; }
//
//  @Override
//  public int getEnd() {
//    return this.end;
//  }
//
//  @Override
//  public void setEnd(int end) { this.end = end; }
//
//  @Override
//  public AShape getOldshape() {
//    return this.oldshape;
//  }
//
//  @Override
//  public AShape getNewshape() {
//    return this.newshape;
//  }
//
////TODO getSVG
//
//  @Override
//  public String getSVG(int speed, String shapeAtt) {
//    StringBuilder motion = new StringBuilder();
//    if (this.oldshape.getX() != this.x2) {
//      motion.append(this.getXMoveSVG(speed, shapeAtt));
//    }
//    if (this.oldshape.getY() != this.y2) {
//      motion.append(this.getYMoveSVG(speed, shapeAtt));
//    }
//    if (this.oldshape.getW() != this.w2) {
//      motion.append(this.getWResizeSVG(speed, shapeAtt));
//    }
//    if (this.oldshape.getH() != this.h2) {
//      motion.append(this.getHResizeSVG(speed, shapeAtt));
//    }
//    if(!this.oldshape.getC().equals(new OurColor(r2,g2,b2))){
//      motion.append(this.getRecolorSVG(speed, shapeAtt));
//    }
//    return motion.toString();
//  }
//
//  private String getRecolorSVG(int speed, String shapeAtt) {
//    return this.svgTemp(speed, "fill", this.oldshape.getC().formatSVG(),
//            this.newshape.getC().formatSVG());
//  }
//
//  private String getWResizeSVG(int speed, String shapeAtt) {
//    String att1 = "width";
//    if (shapeAtt.equalsIgnoreCase("ellipse")) {
//      att1 = "rx";
//    }
//    return this.svgTemp(speed, att1, Integer.toString(this.oldshape.getW()),
//            Integer.toString(this.newshape.getW()));
//  }
//
//  private String getHResizeSVG(int speed, String shapeAtt) {
//    String att2 = "height";
//    if (shapeAtt.equalsIgnoreCase("ellipse")) {
//      att2 = "ry";
//    }
//    return this.svgTemp(speed, att2, Integer.toString(this.oldshape.getH()),
//            Integer.toString(this.newshape.getH()));
//  }
//
//  private String getXMoveSVG(int speed, String shapeAtt) {
//    String att1 = "x";
//    if (shapeAtt.equalsIgnoreCase("ellipse")) {
//      att1 = "cx";
//    }
//    return this.svgTemp(speed, att1, Integer.toString(this.oldshape.getX()),
//            Integer.toString(this.newshape.getX()));
//  }
//
//  private String getYMoveSVG(int speed, String shapeAtt) {
//    String att2 = "y";
//    if (shapeAtt.equalsIgnoreCase("ellipse")) {
//      att2 = "cy";
//    }
//    return this.svgTemp(speed, att2, Integer.toString(this.oldshape.getY()),
//            Integer.toString(this.newshape.getY()));
//  }
//
//  /**
//   * Ensures there are is no stacked times between this Transformation and the given
//   * Transformation.
//   *
//   * @param s   the AShape whose Transformations we are checking for
//   * @param str the name of the Transformation
//   * @throws IllegalArgumentException if the times of this Transformation and the given
//   *                                  Transformation are stacked
//   */
//  public void checkStacked(AShape s, String str) throws IllegalArgumentException {
//    for (Transformations t : s.getDirections()) {
//        if (isStacked(t)) {
//          throw new IllegalArgumentException("Cannot make multiple " +
//                  str.toLowerCase() + "s at the" +
//                  " same time.");
//        }
//    }
//  }
//
//
//
//  @Override
//  public void apply(AShape s, int x2, int y2, int w2, int h2, int r2, int g2, int b2) {
//    this.oldshape = s.copy();
//    s.addNewKeyFrame(x2, y2, w2, h2, r2, g2, b2);
//    this.newshape = s.copy();
//    s.addToDir(this);
//  }
//
//
//  /**
//   * Checks if this transformation is overlapping in time with the given transformation.
//   *
//   * @param t the transformation to compare to
//   * @return true if the given transformation and this transformation overlap in time
//   */
//  protected boolean isStacked(Transformations t) {
//    return (this.getBegin() > t.getBegin() && this.getBegin() < t.getEnd())
//            || (this.getEnd() > t.getBegin() && this.getEnd() < t.getEnd())
//            || (t.getBegin() > this.getBegin() && t.getBegin() < this.getEnd())
//            || (t.getEnd() > this.getBegin() && t.getEnd() < this.getEnd())
//            || (t.getBegin() == this.getBegin() && this.getEnd() == t.getEnd());
//
//  }
//
//  @Override
//  public void transformationState(int time, AShape s) {
//    if (time >= this.getBegin() && time <= this.getEnd()) {
//      int xval = this.tween(time, this.getBegin(), this.getEnd(),
//              this.oldshape.xPos, this.newshape.xPos);
//      int yval = this.tween(time, this.getBegin(), this.getEnd(),
//              this.oldshape.yPos, this.newshape.yPos);
//      s.xPos = xval;
//      s.yPos = yval;
//
//      OurColor holder = s.col.tweenColor(oldshape.col, newshape.col, time,
//              this.getBegin(), this.getEnd());
//      s.col = holder;
//
//      int width = this.tween(time, this.getBegin(), this.getEnd(),
//              this.oldshape.width, this.newshape.width);
//      int height = this.tween(time, this.getBegin(), this.getEnd(),
//              this.oldshape.height, this.newshape.height);
//      s.width = width;
//      s.height = height;
//    }
//  }
//
//  /**
//   * Retuns the tweened value of the component at a time or a intermediate state.
//   * @param time we want the component value.
//   * @param start start time.
//   * @param end end time .
//   * @param initval intial value of the component.
//   * @param finalval final value of the component.
//   * @return the state of the component at a given time.
//   */
//  protected int tween(int time, int start, int end, int initval, int finalval) {
//    return (int) Math.round(initval * ((double) (end - time) / (end - start)) + finalval * ((double)
//            (time - start) / (end - start)));
//  }
//
//  /**
//   * Gives the svg format for the trasnsformations.
//   * @param speed the speed of the transformation.
//   * @param att the current string
//   * @param from the from string
//   * @param to the to string
//   * @return
//   */
//
//  protected String svgTemp(int speed, String att, String from, String to) {
//    return "\n\t<animate attributeType=\"xml\" begin=\"" + (100 * this.begin) + "ms\" dur=\"" +
//            (100 * speed * (this.end - this.begin)) + "ms\" attributeName=\"" + att + "\" from=\"" +
//            from + "\" to=\"" + to + "\" fill=\"freeze\" />";
//  }
//
//}