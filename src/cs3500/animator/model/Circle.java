package cs3500.animator.model;

/**
 * This class represents the shape circle.
 */
public final class Circle extends AShape {

  /**
   * Constructor for a Circle.
   *
   * @param name   the unique name of a circle
   * @param xPos   the x position of a circle
   * @param yPos   the y position of a circle
   * @param radius the radius of a circle
   * @param col    the color of the circle
   */
  public Circle(String name, int xPos, int yPos, int radius, OurColor col) {
    super(name, xPos, yPos, radius * 2, radius * 2, col);
  }

  @Override
  public String shapeName() {
    return "circle";
  }

  @Override
  public AShape copy() {
    return new Circle(this.name, this.xPos, this.yPos, this.width / 2, this.col);
  }
}
