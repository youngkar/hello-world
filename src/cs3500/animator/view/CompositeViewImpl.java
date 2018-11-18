package cs3500.animator.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

import javax.swing.*;

import cs3500.animator.controller.ButtonListener;
import cs3500.animator.controller.ListSelectListener;
import cs3500.animator.model.AShape;
import cs3500.animator.model.Keyframe;
import cs3500.animator.model.OurColor;
import cs3500.animator.model.ROAnimator;
import cs3500.animator.model.Rectangle;
import javafx.scene.control.RadioButton;



public final class CompositeViewImpl extends JFrame implements CompositeView {
  private CustomJPanel panel;
  private ROAnimator roa;
  private int speed;
  private int time;
  private int canvasW;
  private int canvasH;
  private boolean paused;
  private boolean looping;

  private JButton playButton, pauseButton, resumeButton, restartButton,
          increasespeed, decreasespeed;
  private JButton addShape, removeShape, addKeyframe, removeKeyframe,
          editKeyframe;
  private JCheckBox checkLooping;
  private JScrollPane pane;

  //
  private JList listOfShapes, listOfKeyframes;

  private JPanel addS, addKF;

  //addShape text fields
  private JTextField addSName, addSTime, addX, addY, addW, addH, R, G, B;

  //addKeyframe text fields
  private JTextField newT, newX, newY, newW, newH, newR, newG, newB;

  /**
   * Constructor for Composite View.
   *
   * @param roa read only model
   */
  public CompositeViewImpl(ROAnimator roa, int canvasW, int canvasH) {

    // Setting up the Canvas
    super();
    this.setTitle("Animator Editor");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    // Speed
    
    this.speed = 10;
    this.roa = roa;

    this.canvasH = canvasH;
    this.canvasW = canvasW;

    this.paused = false;  //initially not paused
    this.looping = false; //default is not looping

    // Setting up the borders
    this.setLayout(new BorderLayout());
    pane = new JScrollPane(panel, pane.VERTICAL_SCROLLBAR_AS_NEEDED,
            pane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    this.add(pane);

    // setting the size of the canvas
    this.setSize(canvasW, canvasH);

    //interactions
    JPanel interactions = new JPanel();
    interactions.setLayout(new BoxLayout(interactions, BoxLayout.LINE_AXIS));
    this.add(interactions, BorderLayout.PAGE_END);

    //contains play, pause, resume, restart, speed up, slow down and enable looping
    JPanel controls = new JPanel();
   // controls.setBorder(BorderFactory.createTitledBorder("Controls"));
    controls.setLayout(new BoxLayout(controls, BoxLayout.PAGE_AXIS));
    interactions.add(controls);

    //play button
    playButton = new JButton("Play");
    playButton.setActionCommand("Play Button");
    controls.add(playButton);
    pack();

    //pause button
    pauseButton = new JButton("Pause");
    pauseButton.setActionCommand("Pause Button");
    controls.add(pauseButton);
    pack();

    //resume button
    resumeButton = new JButton("Resume");
    resumeButton.setActionCommand("Resume Button");
    controls.add(resumeButton);
    pack();

    //restart button
    restartButton = new JButton("Restart");
    restartButton.setActionCommand("Restart Button");
    controls.add(restartButton);
    pack();

    //increase speed button
    increasespeed = new JButton("faster");
    increasespeed.setActionCommand("go faster");
    controls.add(increasespeed);
    pack();

    //decrease speed button
    decreasespeed = new JButton("slower");
    decreasespeed.setActionCommand("go slower");
    controls.add(decreasespeed);
    pack();

    //looping checkbox
    checkLooping = new JCheckBox("Enable Looping");
    checkLooping.setSelected(false);
    checkLooping.setActionCommand("Looping");
    controls.add(checkLooping);
    pack();


    //shape and keyframe selection panel
    JPanel selectionListPanel = new JPanel();
    selectionListPanel.setLayout(new BoxLayout(selectionListPanel, BoxLayout.X_AXIS));
    interactions.add(selectionListPanel);

    //list of shapes
    DefaultListModel<String> shapes = new DefaultListModel<>();
    for (AShape s : roa.getFrame(roa.getLastTick()).values()) {
      shapes.addElement(s.getName());
    }
    listOfShapes = new JList<>(shapes);
    listOfShapes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    listOfShapes.addListSelectionListener(new ListSelectListener());
    selectionListPanel.add(new JScrollPane(listOfShapes));

    //list of keyframes
    selectionListPanel.add(new JScrollPane(listOfKeyframes));

    //edit actions panel
    JPanel editor = new JPanel();
    editor.setBorder(BorderFactory.createTitledBorder("Edit Animation"));
    editor.setLayout(new BoxLayout(editor, BoxLayout.PAGE_AXIS));
    interactions.add(editor);

    //addShape button
    addShape = new JButton("Add shape");
    addShape.setActionCommand("Add shape");
    editor.add(addShape);
    pack();

    //removeShape button
    removeShape = new JButton("Remove shape");
    removeShape.setActionCommand("Remove shape");
    editor.add(removeShape);
    pack();

    //addKeyframe button
    addKeyframe = new JButton("Add keyframe");
    addKeyframe.setActionCommand("Add keyframe");
    editor.add(addKeyframe);
    pack();

    //removeKeyframe button
    removeKeyframe = new JButton("Remove keyframe");
    removeKeyframe.setActionCommand("Remove keyframe");
    editor.add(removeKeyframe);
    pack();

    //editKeyframe button
    editKeyframe = new JButton("Edit keyframe");
    editKeyframe.setActionCommand("Edit keyframe");
    editor.add(editKeyframe);
    pack();

    // Creating the new Panel
    this.panel = new CustomJPanel();
    panel.setPreferredSize(new Dimension(canvasW, canvasH));
    this.add(panel);
    this.time = 1;
    pack();

    setVisible(true);

  }

  @Override
    public void go() {
      //TODO figure out why play makes it go faster ... and make it stop.
      int lasttick = roa.getLastTick();
      Timer t = new Timer(speed, new ActionListener() {
        /**
         * Invoked when an action occurs.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
          panel.setTheMap(roa.getFrame(time));
          CompositeViewImpl.this.refresh();
          if (!paused) {
            time= time+speed;

            if (looping && time >= lasttick) {
              time = 0;
            }
          }
        }
      });
      this.makeVisible();
      t.start();
  }

  public void addActionListener(ActionListener actionListener) {
    playButton.addActionListener(actionListener);
    pauseButton.addActionListener(actionListener);
    resumeButton.addActionListener(actionListener);
    restartButton.addActionListener(actionListener);

    increasespeed.addActionListener(actionListener);
    decreasespeed.addActionListener(actionListener);

    addShape.addActionListener(actionListener);
    removeShape.addActionListener(actionListener);
  }

  public void addItemListener(ItemListener i) {
    checkLooping.addItemListener(i);
  }

  public void refresh() {
    this.repaint();
  }

  public void makeVisible() {
    this.setVisible(true);
  }

  public void pauseAnimation() {
    this.paused = true;
  }

  //Start and resume may be the same thing??
  public void resumeAnimation() {
    this.paused = false;
  }

  public void restartAnimation() {
    this.time = 0;
    this.paused = false;
    panel.setTheMap(roa.getFrame(time));
    CompositeViewImpl.this.refresh();
    this.setVisible(true);
  }

  public void enableLoop() {
    this.looping = true;
  }

  public void disableLoop() {
    this.looping = false;
  }

  public void increaseSpeed() {
    speed++;
    System.out.println(speed);
  }

  public void decreaseSpeed() {
    if(speed>1) {
      speed--;
      System.out.println(speed);
    }
  }

  public void addShape() {
    addS = new JPanel();
    addS.setLayout(new FlowLayout());
    addSTime = new JTextField(3);
    addS.add(new JLabel("TIME:"));
    addS.add(addSTime);

    addSName = new JTextField(5);
    addS.add(new JLabel("NAME:"));
    addS.add(addSName);
    addX = new JTextField(3);
    addS.add(new JLabel("X:"));
    addS.add(addX);
    addY = new JTextField(3);
    addS.add(new JLabel("Y:"));
    addS.add(addY);
    addW = new JTextField(3);
    addS.add(new JLabel("Width:"));
    addS.add(addW);
    addH = new JTextField(3);
    addS.add(new JLabel("Height:"));
    addS.add(addH);

    R = new JTextField(3);
    addS.add(new JLabel("R:"));
    addS.add(R);
    G = new JTextField(3);
    addS.add(new JLabel("G:"));
    addS.add(G);
    B = new JTextField(3);
    addS.add(new JLabel("B:"));
    addS.add(B);

    JOptionPane.showConfirmDialog(null, addS, "Enter Values",
            JOptionPane.OK_CANCEL_OPTION);
  }

  public int getAddShapeTimeInput() {
    return Integer.parseInt(this.addSTime.getText());
  }

  public AShape getNewShapeInput() {
    String name=addSName.getText();
    int x=Integer.parseInt(addX.getText());
    int y=Integer.parseInt(addY.getText());
    int w=Integer.parseInt(addW.getText());
    int h=Integer.parseInt(addH.getText());
    OurColor col=new OurColor(Integer.parseInt(R.getText()), Integer.parseInt(G.getText()),
            Integer.parseInt(B.getText()));

     return new Rectangle(name,x,y,w,h,col);
  }

  public AShape getShapeToEdit() {
    AShape shape = null;

    for (AShape s : roa.getFrame(roa.getLastTick()).values()) {
      if (s.getName().equals(this.listOfShapes.getSelectedValue())) {
        shape = s;
      }
    }
    return shape;
  }

  public void findKeyframes(AShape s) {
    DefaultListModel<Integer> keyframes = new DefaultListModel<>();
    for (Keyframe kf : s.getDirections()) {
      keyframes.addElement(kf.getTime());
    }
    listOfKeyframes = new JList<>(keyframes);
    listOfKeyframes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
  }

  public void addKeyframe() {
    addKF = new JPanel();
    addKF.setLayout(new FlowLayout());
    newT = new JTextField(3);
    addKF.add(new JLabel("TIME:"));
    addKF.add(newT);

    newX = new JTextField(3);
    addKF.add(new JLabel("X:"));
    addKF.add(newX);
    newY = new JTextField(3);
    addKF.add(new JLabel("Y:"));
    addKF.add(newY);
    newW = new JTextField(3);
    addKF.add(new JLabel("Width:"));
    addKF.add(newW);
    newH = new JTextField(3);
    addKF.add(new JLabel("Height:"));
    addKF.add(newH);

    newR = new JTextField(3);
    addKF.add(new JLabel("R:"));
    addKF.add(newR);
    newG = new JTextField(3);
    addKF.add(new JLabel("G:"));
    addKF.add(newG);
    newB = new JTextField(3);
    addKF.add(new JLabel("B:"));
    addKF.add(newB);

    JOptionPane.showConfirmDialog(null, addKF, "Enter Values",
            JOptionPane.OK_CANCEL_OPTION);
  }
}

/*

Rectangle("name", Integer.parseInt(addX.getText()),
     Integer.parseInt(addY.getText()),
     Integer.parseInt(addW.getText()),
     Integer.parseInt(addH.getText()),
     new OurColor(Integer.parseInt(R.getText()),
     Integer.parseInt(G.getText()),
     Integer.parseInt(B.getText())));

 */