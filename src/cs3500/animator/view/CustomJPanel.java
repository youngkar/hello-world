package cs3500.animator.view;

import java.awt.Graphics2D;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import cs3500.animator.model.AShape;

/**
 * This is a custom JPanel built to meet our specifications.
 */

public final class CustomJPanel extends JPanel {

  private Map<String, AShape> k = new HashMap<>();

  @Override
  public void paintComponent(Graphics g) {


    // I am not missing the super.paintComponent
    super.paintComponent(g);

    Graphics2D g2d = (Graphics2D) g;
    setSize(1000, 800);
    setLocation(0, 0);

    //k.put("square", new Square("square", 100,100,500, new OurColor(255,0,0)));
    for (AShape shape : k.values()) {
      switch (shape.shapeName()) {
        case "circle":
          g2d.setColor(shape.getC());
          g2d.fillOval(shape.getX(), shape.getY(), shape.getW(), shape.getH());
          break;
        case "rectangle":
          g2d.setColor(shape.getC());
          g2d.fillRect(shape.getX(), shape.getY(), shape.getW(), shape.getH());
          break;
        case "ellipse":
          g2d.setColor(shape.getC());
          g2d.fillOval(shape.getX(), shape.getY(), shape.getW(), shape.getH());
          break;
        case "square":
          g2d.setColor(shape.getC());
          g2d.fillRect(shape.getX(), shape.getY(), shape.getW(), shape.getH());
          break;

        default:
          throw new IllegalArgumentException("invalid shape name: " + shape.shapeName());
      }

    }
  }

  /**
   * It sets the map input to K so that the paint component can iterate over it and draw the
   * objects.
   *
   * @param sh the map that k is supposed to be set it too.
   */
  public void setTheMap(Map<String, AShape> sh) {
    this.k = new HashMap<>();
    for (String str : sh.keySet()) {
      this.k.put(str, sh.get(str));
    }

  }

}