package indexprototype.com.kamal.indexprototype.StorageManager;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import indexprototype.com.kamal.indexprototype.StoriesBank;
import indexprototype.com.kamal.indexprototype.Story;

/**
 * A class responsible for loading Stories into the StoriesDatabase.
 * The class
 * Created by Kamal on 12/15/2014.
 */
public class LoadingManager {

    private Context  mContext;

    /**
     * A public constructor
     * @param context The application's <Code>Context</Code>.
     */
    public LoadingManager(Context context){
        mContext = context;
    }

    /**
     * Reads the stories in JSON format from the file in memory and
     * adds the stories to the StoriesBank.
     * @return boolean  True if all the stories were retrieved successfully
     * from the JSON files, false if any problem was encountered.
     */
    public boolean loadStories(){
        boolean success = true;
        File pathFile= mContext.getDir(SavingManager.STORIES_FILE_PATH, Context.MODE_APPEND);
        File[] storiesFilePath = pathFile.listFiles();
        Log.d("LoadingManager", "Stories file path: " + pathFile.toString());
        String paths = "";
        for(File filesss: storiesFilePath){
            paths +=filesss.toString() + "----";
        }
        Log.d("LoadingManager", "File array: " + paths);


        for(File file: storiesFilePath){
            Log.d("LoadingManager", "File accessing: " + file.toString());
            File[] storyFiles = file.listFiles();
            if(storyFiles.length>0) {
                BufferedReader reader = null;
                try {
                    InputStream inputStream = new FileInputStream(file.getPath()+"/story");
                    reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = reader.readLine())!=null){
                        stringBuilder.append(line);
                    }
                    Log.d("LoadingManager", "JSON story: " + stringBuilder.toString());
                    org.json.JSONObject storyJSON = (org.json.JSONObject) new JSONTokener(stringBuilder.toString()).nextValue();
                    Log.d("LoadingManager", "Json Object: " + storyJSON.toString());
                    Story story = new Story((String) storyJSON.get(Story.JSON_STORY_URL),
                            (String) storyJSON.get(Story.JSON_AUTHOR),
                            (String) storyJSON.get(Story.JSON_TITLE),
                            (String) storyJSON.get(Story.JSON_CONTENT),
                            (String) storyJSON.get(Story.JSON_BYLINE),
                            (String) storyJSON.get(Story.JSON_IMAGE_URL),
                            (String) storyJSON.get(Story.JSON_SECTION)
                    );
//                    if(!story.getImageURL().equals(Story.DEFAULT_IMAGE_URL)){
//
////                        inputStream = new FileInputStream(file.getPath()+"/image");
////                        reader = new BufferedReader(new InputStreamReader(inputStream));
//                        Bitmap storyImage = BitmapFactory.decodeFile(file.getPath()+"/image");
//                        story.setImageBitmap(storyImage);
//                    }
                    StoriesBank.addStory(story);
                    Log.d("LoadingManager", "Story loaded successfully: " + story.toString());
                } catch (FileNotFoundException e) {
                    Log.e("LoadingManager", "Could not read file " + file.toString());
                    e.printStackTrace();
                    success = false;
                } catch (IOException e) {
                    Log.e("LoadingManager", "Could not read file " + file.toString());
                    e.printStackTrace();
                    success = false;
                } catch (JSONException e) {
                    Log.e("LoadingManager", "Could not read file " + file.toString());
                    e.printStackTrace();
                    success = false;
                } finally {
                    if (reader != null)
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                }
            }
        }


        return success;
    }
}
