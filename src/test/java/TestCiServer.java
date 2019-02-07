import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
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

  @Test
  public void testPositiveHook() {
    String json = "{\"after\": \"9fc4d28b68c20a2e5c064d91b955d9c529b86a15\"}";
    int responseCode;
    byte[] postData = json.getBytes(StandardCharsets.UTF_8);
    try {
      CiServer server = new CiServer();
      URL url = new URL("http://localhost:8000/hooks/github");
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
