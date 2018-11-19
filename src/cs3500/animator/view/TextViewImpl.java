package cs3500.animator.view;

import java.io.IOException;

import cs3500.animator.model.ROAnimator;

/**
 * Represents a Textual View.
 */
public class TextViewImpl implements View {
  private ROAnimator roa;
  private Appendable ap;


  /**
   * Constructor for TextualViewImpl.
   *
   * @param roa read only model to get data from
   */
  public TextViewImpl(ROAnimator roa, Appendable ap) {
    this.roa = roa;
    this.ap = ap;

  }

  @Override
  public void go() {
    try {
      ap.append(roa.printDesc());
    } catch (IOException e) {
      throw new IllegalStateException("Error trying to append to appendable.");
    }
  }

}

