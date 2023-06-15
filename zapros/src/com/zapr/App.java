package com.zapr;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class App extends JFrame {

    public static final int DEFAULT_WIDTH = 500;
    public static final int DEFAULT_HEIGHT = 500;
    private JPanel lala;
    public String ff = "https://yandex.by/";
    public String hh = "0";

    public App() {

        setTitle("Запросы");
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        JTextArea textArea = new JTextArea(0, 4);
        textArea.setFont(new Font("Serif", Font.ITALIC, 16));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(textArea);

        add(scrollPane, BorderLayout.CENTER);

        JPanel southPanel = new JPanel();

        JButton insertButton = new JButton("POST");
        JButton insertButton1 = new JButton("GET");
        JButton insertButton2 = new JButton("DELETE");
        southPanel.add(insertButton);
        southPanel.add(insertButton1);
        southPanel.add(insertButton2);
        insertButton.addActionListener(event -> {
            String po = "POST";
            String f = Zapr.POSTandDEL(ff, hh, po);
            textArea.append(f);

        });
        insertButton1.addActionListener(event -> {

            try {
                String fffb = Zapr.GET(ff);
                textArea.append("\n" + "\n" + "GET-запрос" + "\n" + fffb);
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
        insertButton2.addActionListener(event -> {
            String del = "DELETE";
            String fffsd = Zapr.POSTandDEL(ff, hh, del);
            textArea.append("\n" + "\n" + "DELETE-запрос" + "\n" + fffsd);

        });
        add(southPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        App f = new App();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
}

class Zapr {
    public static String POSTandDEL(String targetURL, String urlParameters, String ff) {
        HttpURLConnection connection = null;

        try {
            //Create connection
            URL url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(ff);
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");

            connection.setRequestProperty("Content-Length",
                    Integer.toString(urlParameters.getBytes().length));
            connection.setRequestProperty("Content-Language", "en-US");

            connection.setUseCaches(false);
            connection.setDoOutput(true);

            //Send request
            DataOutputStream wr = new DataOutputStream(
                    connection.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.close();


            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);


            }

            rd.close();
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }


    public static String GET(String urlToRead) throws Exception {
        StringBuilder result = new StringBuilder();
        URL url = new URL(urlToRead);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(conn.getInputStream()))) {
            for (String line; (line = reader.readLine()) != null; ) {
                result.append(line);
            }
        }
        return result.toString();
    }
}


