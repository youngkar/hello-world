package cs3500.animator.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

import javax.swing.*;

import cs3500.animator.controller.ButtonListener;
import cs3500.animator.controller.ListSelectListener;
import cs3500.animator.model.AShape;
import cs3500.animator.model.Circle;
import cs3500.animator.model.Keyframe;
import cs3500.animator.model.KeyframeImpl;
import cs3500.animator.model.OurColor;
import cs3500.animator.model.Oval;
import cs3500.animator.model.ROAnimator;
import cs3500.animator.model.Rectangle;
import cs3500.animator.model.Square;

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

  private JPanel addS, addKF, editKF;

  //addShape text fields
  private JComboBox<String> chooseShape;
  private JTextField addSName, addSTime, addX, addY, addW, addH, R, G, B;

  //addKeyframe text fields
  private JTextField newT, newX, newY, newW, newH, newR, newG, newB;

  //editKeyframe text fields
  private JTextField editX, editY, editW, editH, editR, editG, editB;

  private JPanel selectionListPanel;
  private Timer t;

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
    
    this.speed = 3;
    this.roa = roa;

    this.canvasH = canvasH;
    this.canvasW = canvasW;

    this.paused = false;  //initially not paused
    this.looping = false; //default is not looping

    // Setting up the borders
    this.setLayout(new BorderLayout());

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
    selectionListPanel = new JPanel();
    selectionListPanel.setLayout(new BoxLayout(selectionListPanel, BoxLayout.X_AXIS));
    interactions.add(selectionListPanel);

    //list of shapes
    DefaultListModel<String> shapes = new DefaultListModel<>();
    for (AShape s : roa.getFrame(roa.getLastTick()).values()) {
      shapes.addElement(s.getName());
    }
    listOfShapes = new JList<>(shapes);
    listOfShapes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    selectionListPanel.add(new JScrollPane(listOfShapes));

    //list of keyframes
    listOfKeyframes = new JList<>();
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
    pane = new JScrollPane(panel, pane.VERTICAL_SCROLLBAR_AS_NEEDED,
            pane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    this.add(pane);
    this.time = 1;
    pack();


    t = new Timer(20, new ActionListener() {
      int lasttick = roa.getLastTick();

      /**
       * Invoked when an action occurs.
       */
      @Override
      public void actionPerformed(ActionEvent e) {
        panel.setTheMap(roa.getFrame(time));
        CompositeViewImpl.this.refresh();
        if (!paused) {
          time = time + speed;

          if (looping && time >= lasttick) {
            time = 0;
          }
        }
      }
    });
    setVisible(true);

  }

  @Override
    public void go() {
      //TODO figure out why play makes it go faster ... and make it stop.

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
    addKeyframe.addActionListener(actionListener);
    removeKeyframe.addActionListener(actionListener);
    editKeyframe.addActionListener(actionListener);
  }

  public void addItemListener(ItemListener i) {
    checkLooping.addItemListener(i);
  }

  public void addListSelectionListener(ListSelectListener lslistener) {
    listOfShapes.addListSelectionListener(lslistener);
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

    addS.add(new JLabel("Choose a shape:"));
    String[] options = {"Rectangle", "Circle", "Square", "Ellipse"};
    chooseShape = new JComboBox<String>();
    chooseShape.setActionCommand("Shape options");
    for (int i = 0; i < options.length; i++) {
      chooseShape.addItem(options[i]);
    }
    addS.add(chooseShape);

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
    String shapeType = (String) chooseShape.getSelectedItem();

    switch(shapeType) {
      case "Rectangle": return new Rectangle(name, x, y, w, h, col);
      case "Circle": return new Circle(name, x, y, w, col);
      case "Square": return new Square(name, x, y, w, col);
      case "Ellipse": return new Oval(name, x, y, w, h, col);
      default: return null;
    }
  }

  /**
   * returns a KF with all the given info in the dialogue box for a new KF
   * @return
   */
  public Keyframe getKFToAdd() {
    int t=Integer.parseInt(newT.getText());
    int x=Integer.parseInt(newX.getText());
    int y=Integer.parseInt(newY.getText());
    int w=Integer.parseInt(newW.getText());
    int h=Integer.parseInt(newH.getText());
    int r=Integer.parseInt(newR.getText());
    int g=Integer.parseInt(newG.getText());
    int b=Integer.parseInt(newB.getText());

    return new KeyframeImpl(t,x,y,w,h,r,g,b);
  }

  public Keyframe getEditedKF() {
    int time = this.getKFToEdit().getTime();
    int x=Integer.parseInt(editX.getText());
    int y=Integer.parseInt(editY.getText());
    int w=Integer.parseInt(editW.getText());
    int h=Integer.parseInt(editH.getText());
    int r=Integer.parseInt(editR.getText());
    int g=Integer.parseInt(editG.getText());
    int b=Integer.parseInt(editB.getText());

    return new KeyframeImpl(time,x,y,w,h,r,g,b);
  }

  /**
   * Finds the selected shape in the editor
   * @return
   */
  public AShape getShapeToEdit() {
    AShape shape = null;

    for (AShape s : roa.getFrame(roa.getLastTick()).values()) {
      if (s.getName().equals(this.listOfShapes.getSelectedValue())) {
        shape = s;
      }
    }
    return shape;
  }

  /**
   * Finds the selected KF in the editor
   * @return
   */
  public Keyframe getKFToEdit() {
    Keyframe kf = null;
    AShape shape = this.getShapeToEdit();

    for (Keyframe k : shape.getDirections()) {
      if (k.getTime() == (int) this.listOfKeyframes.getSelectedValue()) {
        kf = k;
      }
    }

    return kf;
  }

  /**
   * Updates the visual list of shapes in the editor
   */
  public void updateLists() {
    DefaultListModel<String> shapes = new DefaultListModel<>();
    for (AShape s : roa.getFrame(roa.getLastTick()).values()) {
      shapes.addElement(s.getName());
    }
    listOfShapes.setModel(shapes);

    selectionListPanel.updateUI();
  }

  public void findKeyframes(AShape s) {
    DefaultListModel<Integer> keyframes = new DefaultListModel<>();
    for (Keyframe kf : s.getDirections()) {
      keyframes.addElement(kf.getTime());
    }
    listOfKeyframes.setModel(keyframes);
    listOfKeyframes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    selectionListPanel.updateUI();
  }

  /**
   * creates/shows the dialog box to add a keyframe
   */
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

  public void editKeyframe() {
    editKF = new JPanel();
    editKF.setLayout(new FlowLayout());

    editX = new JTextField(3);
    editKF.add(new JLabel("X:"));
    editKF.add(editX);
    editY = new JTextField(3);
    editKF.add(new JLabel("Y:"));
    editKF.add(editY);
    editW = new JTextField(3);
    editKF.add(new JLabel("Width:"));
    editKF.add(editW);
    editH = new JTextField(3);
    editKF.add(new JLabel("Height:"));
    editKF.add(editH);

    editR = new JTextField(3);
    editKF.add(new JLabel("R:"));
    editKF.add(editR);
    editG = new JTextField(3);
    editKF.add(new JLabel("G:"));
    editKF.add(editG);
    editB = new JTextField(3);
    editKF.add(new JLabel("B:"));
    editKF.add(editB);

    JOptionPane.showConfirmDialog(null, editKF, "Enter Values",
            JOptionPane.OK_CANCEL_OPTION);
  }
}
