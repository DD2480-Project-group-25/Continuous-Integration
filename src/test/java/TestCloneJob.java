import org.junit.Test;
import se.kth.dd2480.grp25.ci.CloneJob;

public class TestCloneJob {

  /** Test that setUrl method throws exception when url is invalid */
  @Test(expected = IllegalArgumentException.class)
  public void testSetUrl() {
    String url = "DD2480-Project-group-25/Continuous-Integration";
    CloneJob job = new CloneJob();
    job.setUrl(url);
  }

  /** Test that setDirectory method throws exception when directory name is invalid */
  @Test(expected = IllegalArgumentException.class)
  public void testSetDirectory() {
    String d = "!directory";
    CloneJob job = new CloneJob();
    job.setDirectory(d);
  }
}
