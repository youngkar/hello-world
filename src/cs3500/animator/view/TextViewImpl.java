package cs3500.animator.view;

import java.io.IOException;

import cs3500.animator.model.ROAnimator;

/**
 * Represents a Textual View.
 */
public class TextViewImpl implements View {
  private ROAnimator roa;



  /**
   * Constructor for TextualViewImpl.
   *
   * @param roa read only model to get data from
   */
  public TextViewImpl(ROAnimator roa, Appendable ap) {
    this.roa = roa;

  }

  @Override
  public void go() {
    roa.printDesc();
  }

}

