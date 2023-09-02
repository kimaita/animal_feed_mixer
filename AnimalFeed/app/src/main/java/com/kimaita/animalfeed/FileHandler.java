package com.kimaita.animalfeed;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.kimaita.animalfeed.models.Feed;
import com.kimaita.animalfeed.models.Nutrient;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    public static final String FILE_ERROR = "Data file not found";
    private static final String FILENAME = "feeds.json";
    private static final String OP_SUCCESS = "OK";
    private final Context c;

    public FileHandler(Context context) {
        c = context;
    }

    public String writeToFile(String s) {
        try (FileOutputStream outputStream =
                     c.openFileOutput(FILENAME, Context.MODE_PRIVATE);) {
            outputStream.write(s.getBytes());
            return OP_SUCCESS;
        } catch (IOException e) {
            return FILE_ERROR;
        }
    }

    public String readFile() {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                c.getAssets().open(FILENAME)));) {
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append(System.getProperty("line.separator"));
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            Log.e("FileHandler", e.toString());
            return FILE_ERROR;
        }
    }

    public List<Feed> loadAnimalFeeds() {
        String json = readFile();
        List<Feed> feedList = new ArrayList<>();
        if (!json.equals(FILE_ERROR)) {
            Type listType = new TypeToken<List<Feed>>() {
            }.getType();

            feedList = new Gson().fromJson(json, listType);
        }
        return feedList;
    }

    public String saveAnimalFeeds(List<Feed> animalFeedList) {
        String json = new Gson().toJson(animalFeedList);
        return writeToFile(json);
    }

}
