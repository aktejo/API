import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.lang.System;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class GPTExample {

    public static void main(String[] args) {
        GPTExample ex = new GPTExample();
    }

    public GPTExample() {

        try {
            URL url = new URL("https://api.openai.com/v1/chat/completions");
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestMethod("POST");

            httpConn.setRequestProperty("Content-Type", "application/json");
            httpConn.setRequestProperty("Authorization", "Bearer " + System.getenv("OPENAI_API_KEY"));

            httpConn.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream());
            writer.write("{\n    \"model\": \"gpt-3.5-turbo\",\n    \"messages\": [\n      {\n        \"role\": \"system\",\n        \"content\": \"You are a poetic assistant, skilled in explaining complex programming concepts with creative flair.\"\n      },\n      {\n        \"role\": \"user\",\n        \"content\": \"Compose a poem that explains the concept of recursion in programming.\"\n      }\n    ]\n  }");
            writer.flush();
            writer.close();
            httpConn.getOutputStream().close();

            InputStream responseStream = httpConn.getResponseCode() / 100 == 2
                    ? httpConn.getInputStream()
                    : httpConn.getErrorStream();
            Scanner s = new Scanner(responseStream).useDelimiter("\\A");
            String response = s.hasNext() ? s.next() : "";
            System.out.println(response);
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

}
