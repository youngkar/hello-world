package cs3500.animator.model;

public interface Keyframe {

  AShape getState();

  int getTime();

  String getKeyInfo();

  String getSVG(double speed, String shapeAtt);

}
