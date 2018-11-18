package cs3500.animator.view;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import cs3500.animator.model.ROAnimator;

/**
 * Represents a Visual View.
 */
public final class VisualViewImpl extends JFrame implements View {
  private final double speed;

  // would have to change a lot of code to get rid of these.
  private final int wCanvas;
  private final int hCanvas;
  private int time;
  private CustomJPanel panel;
  private ROAnimator a;
  private JScrollPane pane;

  /**
   * Constructor for VisualViewImpl.
   *
   * @param a read only animator to get data from.
   * @param speed speed of animation.
   * @param hCanvas width of canvas.
   * @param wCanvas height of canvas.
   */
  public VisualViewImpl(ROAnimator a, int speed, int hCanvas, int wCanvas) {

    // Setting up the Canvas
    super();
    this.setTitle("Easy Animator");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    // Speed
    this.speed = speed;// * 1000;
    this.a = a;

    // Setting up the borders
    this.setLayout(new BorderLayout());
    pane = new JScrollPane(panel, pane.VERTICAL_SCROLLBAR_AS_NEEDED,
            pane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    this.add(pane);

    this.hCanvas = hCanvas;
    this.wCanvas = wCanvas;

    // setting the size of the canvas
    this.setSize(wCanvas, hCanvas);


    // Creating the new Panel
    this.panel = new CustomJPanel();
    panel.setPreferredSize(new Dimension(this.wCanvas, this.hCanvas));
    this.add(panel);
    this.time = 1;
    this.pack();

  }


  @Override
  public void go() {
    Timer t = new Timer((int)speed, new ActionListener() {
      /**
       * Invoked when an action occurs.
       */
      @Override
      public void actionPerformed(ActionEvent e) {
        panel.setTheMap(a.getFrame(time));
        VisualViewImpl.this.refresh();
        time++;
      }
    });
    this.makeVisible();
    t.start();
  }

  private void makeVisible() {
    this.setVisible(true);
  }

  private void refresh() {
    this.repaint();
  }

}

