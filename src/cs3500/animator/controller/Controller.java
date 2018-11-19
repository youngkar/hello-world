package cs3500.animator.controller;

import java.awt.event.ItemEvent;
import java.util.HashMap;
import java.util.Map;

import cs3500.animator.model.AShape;
import cs3500.animator.model.Animator;
import cs3500.animator.model.Keyframe;
import cs3500.animator.view.CompositeViewImpl;

public class Controller implements IController {
  private Animator model;
  private CompositeViewImpl view;

  public Controller(Animator m, CompositeViewImpl v) {
    this.model = m;
    this.view = v;
    configureButtonListener();
    configureItemListener();
    configureListSelectListener();
  }

  private void configureButtonListener() {
    Map<String, Runnable> buttonClickedMap = new HashMap<String, Runnable>();
    ButtonListener buttonListener = new ButtonListener();

    buttonClickedMap.put("Play Button", () -> view.go());
    buttonClickedMap.put("Pause Button", () -> view.pauseAnimation());
    buttonClickedMap.put("Resume Button", () -> view.resumeAnimation());
    buttonClickedMap.put("Restart Button", () -> view.restartAnimation());
    buttonClickedMap.put("go faster", () -> view.increaseSpeed());
    buttonClickedMap.put("go slower", () -> view.decreaseSpeed());

    buttonClickedMap.put("Add shape", new AddShapeButton());
    buttonClickedMap.put("Remove shape",new RemoveShapeButton());

    buttonClickedMap.put("Add keyframe",new AddKeyframeButton());
    buttonClickedMap.put("Remove keyframe",new RemoveKeyframeButton());
    buttonClickedMap.put("Edit keyframe",new EditKeyframeButton());

    buttonListener.setButtonClickedActionMap(buttonClickedMap);
    this.view.addActionListener(buttonListener);
  }

  private void configureItemListener() {
    Map<Integer, Runnable> checkedBoxMap = new HashMap<Integer, Runnable>();
    CheckBoxListener checkBoxListener = new CheckBoxListener();

    checkedBoxMap.put(ItemEvent.SELECTED, () -> view.enableLoop());
    checkedBoxMap.put(ItemEvent.DESELECTED, () -> view.disableLoop());

    checkBoxListener.setCheckboxActionsMap(checkedBoxMap);
    this.view.addItemListener(checkBoxListener);
  }

  private void configureListSelectListener() {
    ListSelectListener listSelectListener = new ListSelectListener();
    listSelectListener.setListActions(new ShowKeyframesAction());

    this.view.addListSelectionListener(listSelectListener);
  }

  @Override
  public void addKeyframe(Keyframe s, String name) {
    model.getShapes().get(name).addToDir(s);
  }

  @Override
  public void removeKeyframe(String name, int time) {
    boolean found = false;
    AShape s = model.getShapes().get(name);
    for (Keyframe kf : s.getDirections()) {
      if (kf.getTime() == time) {
        s.getDirections().remove(kf);
        found = true;
      }
    }
    if (!found) {
      throw new IllegalArgumentException("no keyframe at the given time "
              + time + " for this shape");
    }
  }

  class AddShapeButton implements Runnable {
    public void run() {
      view.addShape();

      AShape newShape = view.getNewShapeInput();
      int time = view.getAddShapeTimeInput();

      model.createShape(newShape, time);
      view.updateLists();
    }
  }

  class RemoveShapeButton implements Runnable {
    public void run() {
      AShape shape = view.getShapeToEdit();

      if (shape == null) {
        //TODO create dialog box error
      } else {
        model.removeShape(shape.getName(), shape.getCT()); //TODO what time do we remove a shape?
        view.updateLists();
      }
    }
  }

  class AddKeyframeButton implements Runnable {
    public void run() {
      AShape shape = view.getShapeToEdit();
      System.out.println(shape.getDirections());

      if (shape != null) {
        view.addKeyframe();
      }

      Keyframe newKF = view.getKFToAdd();

      shape.addToDir(newKF);
      view.updateLists();
    }
  }

  class RemoveKeyframeButton implements Runnable {
    public void run() {
      AShape shape = view.getShapeToEdit();
      Keyframe kf = view.getKFToEdit();
      System.out.println(kf);

      if (shape != null && kf != null) {
        for (AShape s : model.getShapes().values()) {
          s.removeKeyframe(kf);
          view.updateLists();
        }
      }
    }
  }

  class ShowKeyframesAction implements Runnable {
    public void run() {
      AShape shape = view.getShapeToEdit();
      if (shape != null) {
        view.findKeyframes(shape);
      }
    }
  }

  class EditKeyframeButton implements Runnable {
    public void run() {

      view.editKeyframe();

      Keyframe kf = view.getEditedKF();
      AShape shape = view.getShapeToEdit();

      if (kf != null && shape != null) {
        shape.addToDir(kf);
      }
    }
  }
}
