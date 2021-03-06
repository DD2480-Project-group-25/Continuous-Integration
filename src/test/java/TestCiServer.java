import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import org.junit.Assert;
import org.junit.Test;
import se.kth.dd2480.grp25.ci.CiServer;
import se.kth.dd2480.grp25.ci.EventQueue;

public class TestCiServer {

  /**
   * Test that the server responds when connecting to localhost on a random port between 10000-60000
   */
  @Test(expected = Test.None.class)
  public void testResponseOK() {

    // Generate random port between 10000-60000
    Random rand = new Random();
    int randPort = rand.nextInt(50000) + 10000;
    try {
      EventQueue queue = new EventQueue();
      CiServer server = new CiServer(queue, randPort);
      URL url = new URL("http://localhost:" + randPort);
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

  /** Test the response for the POST request */
  @Test
  public void testPositiveHook() {
    Random rand = new Random();
    int randPort = rand.nextInt(50000) + 10000;

    String json = "{\"after\": \"9fc4d28b68c20a2e5c064d91b955d9c529b86a1\"}";
    int responseCode;
    byte[] postData = json.getBytes(StandardCharsets.UTF_8);
    try {
      EventQueue queue = new EventQueue();
      CiServer server = new CiServer(queue, randPort);
      URL url = new URL("http://localhost:" + randPort + "/hooks/github");
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();

      connection.setDoOutput(true);
      try (OutputStream os = connection.getOutputStream()) {
        os.write(postData);
      }

      responseCode = connection.getResponseCode();
      Assert.assertEquals(HttpURLConnection.HTTP_OK, responseCode);

      server.stop();
    } catch (IOException e) {
      Assert.fail(e.toString());
    }
  }
}
