package cs3500.animator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.Timer;
import javax.swing.JLabel;
import javax.swing.DefaultListModel;
import javax.swing.ListSelectionModel;

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

/**
 * Represents an Visual view with an editor. It ie the view where the animation can be edited on the
 * flow.You  add , delete shapes as well as keyframes.
 */
public final class CompositeViewImpl extends JFrame implements CompositeView {
  private CustomJPanel panel;
  private ROAnimator roa;
  private int speed;
  private int time;
  private boolean paused;
  private boolean looping;

  private JButton playButton;
  private JButton pauseButton;
  private JButton resumeButton;
  private JButton restartButton;
  private JButton increasespeed;
  private JButton decreasespeed;
  private JButton addShape;
  private JButton removeShape;
  private JButton addKeyframe;
  private JButton removeKeyframe;
  private JButton editKeyframe;
  private JButton saveSVG;
  private JButton saveText;
  private JCheckBox checkLooping;
  private JScrollPane pane;

  //lists of shapes and keyframes to choose from
  private JList listOfShapes;
  private JList listOfKeyframes;

  //addShape text fields
  private JComboBox<String> chooseShape;
  private JTextField addSName;
  private JTextField addSTime;
  private JTextField addX;
  private JTextField addY;
  private JTextField addW;
  private JTextField addH;
  private JTextField r;
  private JTextField g;
  private JTextField b;

  //addKeyframe text fields
  private JTextField newT;
  private JTextField newX;
  private JTextField newY;
  private JTextField newW;
  private JTextField newH;
  private JTextField newR;
  private JTextField newG;
  private JTextField newB;

  //editKeyframe text fields
  private JTextField editX;
  private JTextField editY;
  private JTextField editW;
  private JTextField editH;
  private JTextField editR;
  private JTextField editG;
  private JTextField editB;

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
    increasespeed.setActionCommand("beginAnimation faster");
    controls.add(increasespeed);
    pack();

    //decrease speed button
    decreasespeed = new JButton("slower");
    decreasespeed.setActionCommand("beginAnimation slower");
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
    editor.setBorder(javax.swing.BorderFactory.createTitledBorder("Edit Animation"));
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

    //save SVG button
    saveSVG = new JButton("Save svg");
    saveSVG.setActionCommand("Save svg");
    editor.add(saveSVG);
    pack();

    //save text button
    saveText = new JButton("Save text");
    saveText.setActionCommand("Save text");
    editor.add(saveText);
    pack();

    // Creating the new Panel
    this.panel = new CustomJPanel();
    panel.setPreferredSize(new Dimension(canvasW, canvasH));
    pane = new JScrollPane(panel, pane.VERTICAL_SCROLLBAR_AS_NEEDED,
            pane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    this.add(pane);
    this.time = 1;
    pack();


    t = new Timer(40, new ActionListener() {
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
  public void beginAnimation() {
    this.makeVisible();
    t.start();
  }

  @Override
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

    saveSVG.addActionListener(actionListener);
    saveText.addActionListener(actionListener);
  }

  @Override
  public void addItemListener(ItemListener i) {
    checkLooping.addItemListener(i);
  }

  @Override
  public void addListSelectionListener(ListSelectListener lslistener) {
    listOfShapes.addListSelectionListener(lslistener);
  }

  private void refresh() {
    this.repaint();
  }

  private void makeVisible() {
    this.setVisible(true);
  }

  @Override
  public void pauseAnimation() {
    this.paused = true;
  }

  @Override
  public void resumeAnimation() {
    this.paused = false;
  }

  @Override
  public void restartAnimation() {
    this.time = 0;
    this.paused = false;
    panel.setTheMap(roa.getFrame(time));
    CompositeViewImpl.this.refresh();
    this.setVisible(true);
  }

  @Override
  public void enableLoop() {
    this.looping = true;
  }

  @Override
  public void disableLoop() {
    this.looping = false;
  }

  @Override
  public void increaseSpeed() {
    speed++;
  }

  @Override
  public void decreaseSpeed() {
    if (speed > 1) {
      speed--;
    }
  }

  @Override
  public void addShapeDialog() {
    JPanel addS = new JPanel();
    addS.setLayout(new FlowLayout());

    addS.add(new JLabel("Choose a shape:"));
    String[] options = {"Rectangle", "Circle", "Square", "Ellipse"};
    chooseShape = new JComboBox<>();
    chooseShape.setActionCommand("Shape options");
    for (String option : options) {
      chooseShape.addItem(option);
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

    r = new JTextField(3);
    addS.add(new JLabel("r:"));
    addS.add(r);
    g = new JTextField(3);
    addS.add(new JLabel("g:"));
    addS.add(g);
    b = new JTextField(3);
    addS.add(new JLabel("b:"));
    addS.add(b);

    JOptionPane.showConfirmDialog(null, addS, "Enter Values",
            JOptionPane.OK_CANCEL_OPTION);
  }

  @Override
  public int getAddShapeTimeInput() {
    return Integer.parseInt(this.addSTime.getText());
  }

  @Override
  public AShape getNewShapeInput() {
    String name = addSName.getText();
    int x = Integer.parseInt(addX.getText());
    int y = Integer.parseInt(addY.getText());
    int w = Integer.parseInt(addW.getText());
    int h = Integer.parseInt(addH.getText());
    OurColor col = new OurColor(Integer.parseInt(r.getText()), Integer.parseInt(g.getText()),
            Integer.parseInt(b.getText()));
    String shapeType = (String) chooseShape.getSelectedItem();

    assert shapeType != null;
    switch (shapeType) {
      case "Rectangle":
        return new Rectangle(name, x, y, w, h, col);
      case "Circle":
        return new Circle(name, x, y, w, col);
      case "Square":
        return new Square(name, x, y, w, col);
      case "Ellipse":
        return new Oval(name, x, y, w, h, col);
      default:
        return null;
    }
  }

  @Override
  public Keyframe getKFToAdd() {
    int t = Integer.parseInt(newT.getText());
    int x = Integer.parseInt(newX.getText());
    int y = Integer.parseInt(newY.getText());
    int w = Integer.parseInt(newW.getText());
    int h = Integer.parseInt(newH.getText());
    int r = Integer.parseInt(newR.getText());
    int g = Integer.parseInt(newG.getText());
    int b = Integer.parseInt(newB.getText());

    return new KeyframeImpl(t, x, y, w, h, r, g, b);
  }

  @Override
  public Keyframe getEditedKF() {
    int time = this.getKFToEdit().getTime();
    int x = Integer.parseInt(editX.getText());
    int y = Integer.parseInt(editY.getText());
    int w = Integer.parseInt(editW.getText());
    int h = Integer.parseInt(editH.getText());
    int r = Integer.parseInt(editR.getText());
    int g = Integer.parseInt(editG.getText());
    int b = Integer.parseInt(editB.getText());

    return new KeyframeImpl(time, x, y, w, h, r, g, b);
  }

  @Override
  public AShape getShapeToEdit() {
    AShape shape = null;

    for (AShape s : roa.getFrame(roa.getLastTick()).values()) {
      if (s.getName().equals(this.listOfShapes.getSelectedValue())) {
        shape = s;
      }
    }
    return shape;
  }

  @Override
  public Keyframe getKFToEdit() {
    Keyframe kf = null;
    AShape shape = this.getShapeToEdit();

    for (Keyframe k : shape.getDirections()) {
      if (this.listOfKeyframes.getSelectedValue() != null) {
        if (k.getTime() == (int) this.listOfKeyframes.getSelectedValue()) {
          kf = k;
        }
      }
    }

    return kf;
  }

  @Override
  public void updateLists() {
    DefaultListModel<String> shapes = new DefaultListModel<>();
    for (AShape s : roa.getFrame(roa.getLastTick()).values()) {
      shapes.addElement(s.getName());
    }
    listOfShapes.setModel(shapes);

    selectionListPanel.updateUI();
  }

  @Override
  public void findKeyframes(AShape s) {
    DefaultListModel<Integer> keyframes = new DefaultListModel<>();
    for (Keyframe kf : s.getDirections()) {
      keyframes.addElement(kf.getTime());
    }
    listOfKeyframes.setModel(keyframes);
    listOfKeyframes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    selectionListPanel.updateUI();
  }

  @Override
  public void addKeyframeDialog() {
    JPanel addKF = new JPanel();
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
    addKF.add(new JLabel("r:"));
    addKF.add(newR);
    newG = new JTextField(3);
    addKF.add(new JLabel("g:"));
    addKF.add(newG);
    newB = new JTextField(3);
    addKF.add(new JLabel("b:"));
    addKF.add(newB);

    JOptionPane.showConfirmDialog(null, addKF, "Enter Values",
            JOptionPane.OK_CANCEL_OPTION);
  }

  @Override
  public void editKeyframeDialog() {
    JPanel editKF = new JPanel();
    editKF.setLayout(new FlowLayout());

    editX = new JTextField(Integer.toString(this.getKFToEdit().getX()), 3);
    editKF.add(new JLabel("X:"));
    editKF.add(editX);
    editY = new JTextField(Integer.toString(this.getKFToEdit().getY()), 3);
    editKF.add(new JLabel("Y:"));
    editKF.add(editY);
    editW = new JTextField(Integer.toString(this.getKFToEdit().getW()), 3);
    editKF.add(new JLabel("Width:"));
    editKF.add(editW);
    editH = new JTextField(Integer.toString(this.getKFToEdit().getH()), 3);
    editKF.add(new JLabel("Height:"));
    editKF.add(editH);

    editR = new JTextField(Integer.toString(this.getKFToEdit().getR()), 3);
    editKF.add(new JLabel("r:"));
    editKF.add(editR);
    editG = new JTextField(Integer.toString(this.getKFToEdit().getG()), 3);
    editKF.add(new JLabel("g:"));
    editKF.add(editG);
    editB = new JTextField(Integer.toString(this.getKFToEdit().getB()), 3);
    editKF.add(new JLabel("b:"));
    editKF.add(editB);

    JOptionPane.showConfirmDialog(null, editKF, "Enter Values",
            JOptionPane.OK_CANCEL_OPTION);
  }
}
