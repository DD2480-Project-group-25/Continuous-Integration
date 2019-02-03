import java.io.*;
import java.net.*;
import org.junit.Assert;
import org.junit.Test;
import se.kth.dd2480.grp25.ci.CiServer;

public class TestCiServer {

  /** Test that the server responds when connecting to localhost on port 8000 */
  @Test(expected = Test.None.class)
  public void testResponseOK() {
    try {
      CiServer server = new CiServer();
      URL url = new URL("http://localhost:8000");
      URLConnection connection = url.openConnection();
      BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      String responseResult = in.readLine();

      Assert.assertEquals("Hello World!", responseResult);
      in.close();
      server.stop();
    } catch (IOException e) {
      Assert.fail(e.toString());
    }
  }
}
