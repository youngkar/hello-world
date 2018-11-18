package cs3500.animator;


import java.io.FileNotFoundException;
import java.io.FileReader;

import cs3500.animator.controller.Controller;
import cs3500.animator.model.Animator;
import cs3500.animator.model.ROAnimator;
import cs3500.animator.util.AnimationBuilder;
import cs3500.animator.util.AnimationBuilderImpl;
import cs3500.animator.util.AnimationReader;
import cs3500.animator.view.CompositeViewImpl;
import cs3500.animator.view.TextViewImpl;
import cs3500.animator.view.View;
import cs3500.animator.view.VisualViewImpl;


public final class MAIN {


  public static void main(String[] args) throws FileNotFoundException {
    AnimationBuilder<Animator> builder = new AnimationBuilderImpl();

    String filename = "hanoi.txt";
    FileReader read = new FileReader(filename);
    Animator model = AnimationReader.parseFile(read, builder);
    ROAnimator ro = model;
    Appendable app = new StringBuilder();

//    View textview = new TextViewImpl(ro, app);
//    textview.go();
//    System.out.println(((TextViewImpl) textview).ap);

  //  View vv = new VisualViewImpl(ro, 15, 800, 1000);
  //  vv.go();

    //View v = new SVGViewImpl(1, 1000, 1000, ro, System.out);
    //v.go();



    View cv = new CompositeViewImpl(ro, 15, 800);
    Controller controller = new Controller(model,(CompositeViewImpl) cv);


  }

}