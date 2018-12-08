package cs3500.animator.model;

/**
 * Represents an Oval.
 */
public final class Oval extends AShape {

  /**
   * Constructor for an Oval.
   *
   * @param name   the unique name of this oval
   * @param xPos   the x position of this oval
   * @param yPos   the y position of this oval
   * @param width  the width of this oval
   * @param height the height of this oval
   * @param col    the color of this oval
   */
  public Oval(String name, int xPos, int yPos, int width, int height, OurColor col) {
    super(name, xPos, yPos, width, height, col);
  }

  @Override
  public String shapeName() {
    return "ellipse";
  }

  @Override
  public AShape copy() {
    return new Oval(this.name, this.xPos, this.yPos, this.width, this.height, this.col);
  }
}

