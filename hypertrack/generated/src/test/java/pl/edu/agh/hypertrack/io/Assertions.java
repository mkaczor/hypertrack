package pl.edu.agh.hypertrack.io;

/**
 * Entry point for assertions of different data types. Each method in this class is a static factory for the
 * type-specific assertion objects.
 */
public class Assertions {

  /**
   * Creates a new instance of <code>{@link pl.edu.agh.hypertrack.io.JsonWorkflowAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  public static pl.edu.agh.hypertrack.io.JsonWorkflowAssert assertThat(pl.edu.agh.hypertrack.io.JsonWorkflow actual) {
    return new pl.edu.agh.hypertrack.io.JsonWorkflowAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link pl.edu.agh.hypertrack.model.HyperflowInputSignalAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  public static pl.edu.agh.hypertrack.model.HyperflowInputSignalAssert assertThat(pl.edu.agh.hypertrack.model.HyperflowInputSignal actual) {
    return new pl.edu.agh.hypertrack.model.HyperflowInputSignalAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link pl.edu.agh.hypertrack.model.HyperflowOutputSignalAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  public static pl.edu.agh.hypertrack.model.HyperflowOutputSignalAssert assertThat(pl.edu.agh.hypertrack.model.HyperflowOutputSignal actual) {
    return new pl.edu.agh.hypertrack.model.HyperflowOutputSignalAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link pl.edu.agh.hypertrack.model.HyperflowProcessAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  public static pl.edu.agh.hypertrack.model.HyperflowProcessAssert assertThat(pl.edu.agh.hypertrack.model.HyperflowProcess actual) {
    return new pl.edu.agh.hypertrack.model.HyperflowProcessAssert(actual);
  }

  /**
   * Creates a new <code>{@link Assertions}</code>.
   */
  protected Assertions() {
    // empty
  }
}
