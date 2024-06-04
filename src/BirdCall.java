import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.awt.Desktop;
import java.io.*;
import java.net.URI;
import java.io.File;
import java.io.IOException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.sound.sampled.*;
import java.nio.file.*;
import java.io.InputStream;


// video to load jar
//https://www.youtube.com/watch?v=QAJ09o3Xl_0

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;

// Program for print data in JSON format.
public class BirdCall extends JFrame implements ActionListener{
    public static void main(String args[]) throws ParseException {
        BirdCall bc = new BirdCall();
    }

    public JPanel panel;
    public JButton playButton;
    public static JFrame frame;
    public JLabel statusLabel;
    public JTextField countryInput;
    public JTextArea birdInfo;
    String filePath = "";
    String country;
    //String country;

    public BirdCall() throws ParseException {
        //Scanner scanner = new Scanner(System.in);
        frame = new JFrame();
        frame.setSize(300, 100);
        countryInput = new JTextField("Enter Country Name Here");
        birdInfo = new JTextArea("Bird Info Will Appear Here");

        birdInfo.setEditable(false);
        frame.setLayout(new BorderLayout(10, 10));

        JPanel panel = new JPanel();
        frame.add(panel);
        panel.setLayout(new BorderLayout(10, 10));
        frame.setLayout(new FlowLayout());

        JButton playButton = new JButton("Play");
        //playButton.setSize(20, 10);


        JLabel statusLabel = new JLabel("Click Play to start playing audio.");

        frame.add(panel);

        frame.add(panel, BorderLayout.WEST);
        frame.add(birdInfo, BorderLayout.EAST);

        panel.add(countryInput, BorderLayout.NORTH);
        panel.add(playButton, BorderLayout.CENTER);
        panel.add(statusLabel, BorderLayout.SOUTH);



//        playButton.addActionListener(this);
        playButton.addActionListener(e -> {
            System.out.println("button was clicked");
            country = countryInput.getText();
            try {
                pull();
            } catch (ParseException ex) {
                throw new RuntimeException(ex);
            }
            //websiteAddress = website.getText();
            System.out.println("buttonclicked");
            //birdInfo.setText("");

            //populateResults();
                });

        frame.pack();
        frame.setSize(1600, 800);
        birdInfo.setSize(50, 100);
        //website.setSize(100, 100);
        countryInput.setSize(100, 100);
        frame.setVisible(true);

        //System.out.println("Random Bird\nEnter country: "); // Prompt the user for input
        //String country = scanner.nextLine();
        //String country = "brazil";
        //pull();
    }

    public void pull() throws ParseException {
        String output = "abc";
        String totalJson="";
        System.out.println(country);

        try {
            URL url = new URL("https://xeno-canto.org/api/2/recordings?query=cnt:" + country);
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
                //System.out.println(output);
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
        JSONObject jsonObject = (JSONObject) parser.parse(totalJson);
        //System.out.println(jsonObject);

        try {
            //JSONArray recordingsArray = (jsonArray) jsonObject.get("recordings");
            JSONArray recordings = (JSONArray) jsonObject.get("recordings");
            int arraySize = recordings.size();
            int birdIndex = (int) (Math.random()*arraySize);
            JSONObject bird = (JSONObject) recordings.get(birdIndex);
            System.out.println(bird.get("en"));
            birdInfo.setText(birdInfo.getText() + bird.get("en"));
            String songLink = (String)bird.get("url");
            System.out.println("Link to song: " + songLink.substring(2) + "/download");
            birdInfo.setText(birdInfo.getText() + "\n" + bird.get("en"));
            Desktop desk = Desktop.getDesktop();

            // now we enter our URL that we want to open in our
            // default browser
            String urlString = "https://" + songLink.substring(2) + "/download";
//            URL url = new URL(urlString);
            System.out.println(urlString);
            birdInfo.setText(birdInfo.getText() + "\n" + urlString);
//            desk.browse(new URI(urlString));

            // Save the downloaded audio file to a directory
//            Path audioDirectory = Paths.get(System.getProperty("user.home"), "audio");
//            Files.createDirectories(audioDirectory);
//            String fileName = songLink.substring(songLink.lastIndexOf('/') + 1);
//            Path audioFilePath = audioDirectory.resolve(fileName);
//            try (InputStream in = new URL(urlString).openStream()) {
//                Files.copy(in, audioFilePath, StandardCopyOption.REPLACE_EXISTING);
//            }

//            /Users/abhiramtejomurtula/Downloads/XC665055 - Chinese Bamboo Partridge - Bambusicola thoracicus (1).mp3

            String projectDir = System.getProperty("user.dir");
            System.out.println("project directory??: " + projectDir);
            Path audioDirectory = Paths.get(projectDir, "audio");
            Files.createDirectories(audioDirectory);
            String fileName = "bc.mp3";
            Path audioFilePath = audioDirectory.resolve(fileName);
            try (InputStream in = new URL(urlString).openStream()) {
                Files.copy(in, audioFilePath, StandardCopyOption.REPLACE_EXISTING);
            }

            filePath = audioFilePath.toString();
            openFile(filePath);

            //String name = "";
            /*int n = jsonObject.size();
            for (int i = 0; i < n; i++) {
                JSONObject thecharacter = (JSONObject) jsonObject.get(i);
                System.out.println(thecharacter.get("name"));
            }*/

           /* String name = (String)jsonObject.get("name");
            System.out.println(name);
            System.out.println(jsonObject.get("hair_color"));



            JSONArray msg = (JSONArray) jsonObject.get("films");
            int n =   msg.size(); //(msg).length();
            String test = "";
            for (int i = 0; i < n; ++i) {
                String test =(String) msg.get(i);
                System.out.println(test);
                // System.out.println(person.getInt("key"));
            }
            String height= (String)jsonObject.get("height");
            System.out.println(name);*/
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void openFile(String pathForFile) {
        try {
            File file = new File(pathForFile);
            if (file.exists()) {
                Desktop desktop = Desktop.getDesktop();
                desktop.open(file);
            } else {
                System.out.println("File does not exist: " + pathForFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void playAudio() {
        try {
            File audioFile = new File(filePath); // Update with the path to your downloaded audio file
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            AudioFormat format = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            Clip audioClip = (Clip) AudioSystem.getLine(info);
            audioClip.open(audioStream);
            audioClip.start();
            statusLabel.setText("Playing audio...");
        } catch (Exception e) {
            e.printStackTrace();
            statusLabel.setText("Error playing audio.");
        }
    }

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == playButton) {
            System.out.println("button was clicked");
            country = countryInput.getText();
            try {
                pull();
            } catch (ParseException ex) {
                throw new RuntimeException(ex);
            }
            //websiteAddress = website.getText();
            System.out.println("buttonclicked");
            //birdInfo.setText("");

            //populateResults();
        }
        String command = e.getActionCommand();
        if (source == playButton)
        playAudio();
    }
}


