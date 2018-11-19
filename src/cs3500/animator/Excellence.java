package cs3500.animator;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.*;

import cs3500.animator.model.Animator;
import cs3500.animator.model.ROAnimator;
import cs3500.animator.util.AnimationBuilder;
import cs3500.animator.util.AnimationBuilderImpl;
import cs3500.animator.util.AnimationReader;
import cs3500.animator.view.SVGViewImpl;
import cs3500.animator.view.TextViewImpl;
import cs3500.animator.view.View;
import cs3500.animator.view.VisualViewImpl;

public final class Excellence {

  public static void main(String[] args) {

    String fileName = "";
    String outFile = "";
    String view = "";
    double speed = 10;


    int size = args.length;


    for (int i = 0; i < size; i++) {

      switch (args[i]) {

        case "-in":
          fileName = args[i + 1];
          break;

        case "-out":
          outFile = args[i + 1];
          break;

        case "-view":
          view = args[i + 1];
          break;

        case "-speed":
          speed = Double.parseDouble(args[i + 1]);
          break;


      }

    }

    try {


// For thr input file. Checking that there is a input file being given.
      if (fileName.equals("")) {

        JOptionPane.showMessageDialog(null,
                "Needs an input file",
                "Input File  error",
                JOptionPane.ERROR_MESSAGE);


      }

      FileReader InputFile = new FileReader(fileName);
      AnimationBuilder<Animator> builder = new AnimationBuilderImpl();
      Animator model = AnimationReader.parseFile(InputFile, builder);
      ROAnimator goModel = model;


      if (view.equals("text")) {
        View txt = new TextViewImpl(goModel, System.out);
        txt.go();


      }

      Appendable outPut;
      if(!outFile.equals("")) {
        System.out.println(outFile);
        outPut = new FileWriter(outFile);
      } else {
        outPut = System.out;
      }



      if (view.equals("svg")) {
        View svg = new SVGViewImpl(speed, 1000, 1000, goModel, outPut);
        svg.go();

      }

      if (view.equals("visual")) {
        View visual = new VisualViewImpl(goModel, (int) speed, 1000, 1000);
        visual.go();
      }


    } catch (IOException e) {


    }


  }
}




