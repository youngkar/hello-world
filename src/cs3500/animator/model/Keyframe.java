package cs3500.animator.model;

public interface Keyframe {

  AShape getState();

  int getTime();

  String getKeyInfo();

  String getSVG(double speed, String shapeAtt);

  void editKF(int time, int x, int y, int w, int h, int r, int g, int b);

}
