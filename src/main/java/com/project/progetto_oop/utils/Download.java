package com.project.progetto_oop.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Download {

    public void downloadCsv(String name) throws IOException, JSONException {

        URLConnection openConnection = new URL("http://data.europa.eu/euodp/data/api/3/action/package_show?id=esener-2").openConnection();
        InputStream in = openConnection.getInputStream();

        String data = "";
        String line = "";
        try {
            InputStreamReader inR = new InputStreamReader(in);
            BufferedReader buf = new BufferedReader(inR);
            while ((line = buf.readLine()) != null) {
                data += line;
            }
        } finally {
            in.close();
        }
        JSONObject obj = new JSONObject(data);
        JSONObject objI = (obj.getJSONObject("result"));
        JSONArray objA = (objI.getJSONArray("resources"));

        for (int i = 0; i < objA.length(); i++) {
            JSONObject o1 = objA.getJSONObject(i);
            String format = (String) o1.get("format");
            String urlD = (String) o1.get("url");
            if (format.contains("CSV")) {
                download(urlD, name);
            }
        }
    }
    private void download(String url, String name) throws IOException {
        try (InputStream in = URI.create(url).toURL().openStream()) {
            Files.copy(in, Paths.get(name));
        }
    }
}
