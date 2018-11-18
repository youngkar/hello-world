package cs3500.animator.model;

public class KeyframeImpl implements Keyframe {
  private int time;
  private int x;
  private int y;
  private int w;
  private int h;
  private int r;
  private int g;
  private int b;

  public KeyframeImpl(int time, int x, int y, int w, int h, int r, int g, int b) {
    this.time = time;
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
    this.r = r;
    this.g = g;
    this.b = b;
  }


  @Override
  public AShape getState() {
    AShape s = new Rectangle("holder", x,y,w,h,new OurColor(r,g,b));
    return s;
  }

  @Override
  public int getTime() {
    int holder = this.time;
    return holder;
  }

  @Override
  public String getKeyInfo() {
    return null;
  }

  @Override
  public String getSVG(double speed, String shapeAtt) {
    return null;
  }
}
