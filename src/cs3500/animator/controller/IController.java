package cs3500.animator.controller;

import cs3500.animator.model.Keyframe;

public interface IController {

  /**
   * Adds a keyframe to given shape.
   * @param kf The keyframe to be added to the shape.
   * @param name The name of the shape.
   */
  void addKeyframe(Keyframe kf, String name);

  /**
   * Removes the key frame at the given time.
   * @param name The name of the shape where the keyframe is.
   * @param time The time at which the keyframe exists.
   * @throws IllegalArgumentException if no keyframe exists at the given time.
   */
  void removeKeyframe(String name, int time) throws IllegalArgumentException;

}
