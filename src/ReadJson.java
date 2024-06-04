import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


// video to load jar
//https://www.youtube.com/watch?v=QAJ09o3Xl_0

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

// Program for print data in JSON format.
public class ReadJson {
    public static void main(String args[]) throws ParseException {
        // In java JSONObject is used to create JSON object
        // which is a subclass of java.util.HashMap.

        JSONObject file = new JSONObject();
        file.put("Full Name", "Ritu Sharma");
        file.put("Roll No.", new Integer(1704310046));
        file.put("Tution Fees", new Double(65400));


        System.out.println(file);
        // To print in JSON format.
        System.out.println(file.get("Tution Fees"));
        pull();

    }

    public static void pull() throws ParseException {
        String output = "abc";
        String totalJson="";
        try {
            URL url = new URL("https://last-airbender-api.fly.dev/api/v1/characters");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {

                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));


            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
                totalJson+=output;
            }

            conn.disconnect();



        } catch (MalformedURLException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONParser parser = new JSONParser();
        //System.out.println(str);
        JSONArray jsonArray = (JSONArray) parser.parse(totalJson);
        System.out.println(jsonArray);

        try {

            JSONObject character = (JSONObject) jsonArray.get(0);
            System.out.println(character.get("name"));
            String name = "";
            int n = jsonArray.size();
            for (int i = 0; i < n; i++) {
                JSONObject thecharacter = (JSONObject) jsonArray.get(i);
                System.out.println(thecharacter.get("name"));
            }

           /* String name = (String)jsonArray.get("name");
            System.out.println(name);
            System.out.println(jsonArray.get("hair_color"));



            JSONArray msg = (JSONArray) jsonArray.get("films");
            int n =   msg.size(); //(msg).length();
            String test = "";
            for (int i = 0; i < n; ++i) {
                String test =(String) msg.get(i);
                System.out.println(test);
                // System.out.println(person.getInt("key"));
            }
            String height= (String)jsonArray.get("height");
            System.out.println(name);*/
        }

        catch (Exception e) {
            e.printStackTrace();
        }




    }
}


