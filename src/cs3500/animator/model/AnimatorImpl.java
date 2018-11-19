package cs3500.animator.model;

import java.awt.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * AnimatorImpl represents an animation. It holds all the data for all the different shapes in it.
 */
public final class AnimatorImpl implements Animator {
  private Map<String, AShape> shapes = new LinkedHashMap<>();
  private StringBuilder str = new StringBuilder();

  public AnimatorImpl(Map<String, AShape> shapes) {
    this.shapes = shapes;
  }

  /**
   * Default constructor for an AnimatorModelImpl.
   */
  public AnimatorImpl() {
    /**
     * constructor for an AnimatorModelImpl.
     */
  }

  @Override
  public void createShape(AShape s, int time) {
    if (shapes.containsKey(s.name) || time < 0) {
      throw new IllegalArgumentException("AShape with that name already exists.");
    }
    s.setCT(time);
    shapes.put(s.name, s);
    shapes.get(s.name).addToDir(new KeyframeImpl(time, s.xPos, s.yPos, s.width, s.height,
            s.getC().getRed(), s.getC().getGreen(), s.getC().getBlue()));
  }

  @Override
  public void removeShape(String name, int time) throws IllegalArgumentException {
    shapes.remove(name);
  }


  @Override
  public int getLastTick() {
    int max = 0;
    for (AShape s : shapes.values()) {
      max = Math.max(max, s.getMaxTick());
    }
    return max;
  }

  @Override
  public String printSInfo(String name) {
    if (shapes.get(name) == null) {
      throw new IllegalArgumentException("AShape does not exist.");
    }
    AShape s = shapes.get(name);
    return "shape" + " " + s.name + " " + s.shapeName() + "\n" + s.getInfo();
  }

  @Override
  public String printDesc() {
    for (AShape s : shapes.values()) {
      if (str.length() < 1) {
        str.append(printSInfo(s.name));
      } else {
        str.append("\n" + printSInfo(s.name));

      }
    }
    return str.toString();
  }

  /**
   * Returns the state of given shape name at the given time.
   *
   * @param time The time at which the you want the state at.
   * @param name The name of the shape at which you want the state for.
   * @return the shape of with the name 'name'  at time 't'.
   */
  private AShape getState(int time, String name) {
    return shapes.get(name).getShapeState(time);
  }


  @Override
  public Map<String, AShape> getFrame(int time) {
    Map<String, AShape> data = new HashMap<>();
    for (AShape s : shapes.values()) {
      if (s.getCT() <= time) {
        data.put(s.name, this.getState(time, s.name));
      }
    }
    return data;
  }


  @Override
  public Map<String, AShape> getShapes() {
   Map<String, AShape> shapescopy = this.shapes;
   return shapescopy;
  }
}

