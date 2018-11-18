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
  }

  private void configureButtonListener() {
    Map<String, Runnable> buttonClickedMap = new HashMap<String, Runnable>();
    ButtonListener buttonListener = new ButtonListener();

    buttonClickedMap.put("Play Button", new PlayButtonAction());
    buttonClickedMap.put("Pause Button", new PauseButtonAction());
    buttonClickedMap.put("Resume Button", new ResumeButtonAction());
    buttonClickedMap.put("Restart Button", new RestartButtonAction());
    buttonClickedMap.put("go faster", new IncreaseSpeedButton());
    buttonClickedMap.put("go slower", new DecreaseSpeedButton());

    buttonClickedMap.put("Add shape", new AddShapeButton());
    buttonClickedMap.put("Remove shape",new RemoveShapeButton());

    buttonClickedMap.put("Add keyframe",new AddKeyframeButton());

    buttonListener.setButtonClickedActionMap(buttonClickedMap);
    this.view.addActionListener(buttonListener);
  }

  private void configureItemListener() {
    Map<Integer, Runnable> checkedBoxMap = new HashMap<Integer, Runnable>();
    CheckBoxListener checkBoxListener = new CheckBoxListener();

    checkedBoxMap.put(ItemEvent.SELECTED, new EnableLoopingAction());
    checkedBoxMap.put(ItemEvent.DESELECTED, new DisableLoopingAction());

    checkBoxListener.setCheckboxActionsMap(checkedBoxMap);
    this.view.addItemListener(checkBoxListener);
  }

  private void configureListSelectListener() {
    ListSelectListener listSelectListener = new ListSelectListener();
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

  class PlayButtonAction implements Runnable {
    public void run() {
      view.go();
    }
  }

  class PauseButtonAction implements Runnable {
    public void run() {
      view.pauseAnimation();
    }
  }

  class ResumeButtonAction implements Runnable {
    public void run() {
      view.resumeAnimation();
    }
  }

  class IncreaseSpeedButton implements Runnable {
    public void run() {
      view.increaseSpeed();
    }
  }

  class DecreaseSpeedButton implements Runnable {
    public void run() {
      view.decreaseSpeed();
    }
  }

  class RestartButtonAction implements Runnable {
    public void run() {
      view.restartAnimation();
    }
  }

  class EnableLoopingAction implements Runnable {
    public void run() {
      view.enableLoop();
    }
  }

  class DisableLoopingAction implements Runnable {
    public void run() {
      view.disableLoop();
    }
  }

  class AddShapeButton implements Runnable {
    public void run() {
      view.addShape();

      AShape newShape = view.getNewShapeInput();
      int time = view.getAddShapeTimeInput();

      //TODO don't make it just a rectangle...it can be anything
      model.createShape(newShape, time);
    }
  }

  class RemoveShapeButton implements Runnable {
    public void run() {
      AShape shape = view.getShapeToEdit();

      if (shape == null) {
        //TODO create dialog box error
      } else {
        model.removeShape(shape.getName(), shape.getCT()); //TODO what time do we remove a shape?
      }
    }
  }

  class AddKeyframeButton implements Runnable {
    public void run() {
      view.addKeyframe();

      //TODO: get values from text boxes and actually add a keyframe to some shape
    }
  }

  class ShowKeyframesAction implements Runnable {
    public void run() {
      AShape shape = view.getShapeToEdit();

      view.findKeyframes(shape);
    }
  }
}
