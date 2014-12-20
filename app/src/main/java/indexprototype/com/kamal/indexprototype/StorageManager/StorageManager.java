package indexprototype.com.kamal.indexprototype.StorageManager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

import indexprototype.com.kamal.indexprototype.StoriesBank;
import indexprototype.com.kamal.indexprototype.Story;

/**
 * A class that saves stories and their information into internal memory.
 * A folder is created for each story. Each folder contains a JSON object
 * of the story object, and the respective story's image (if the story downloaded
 * an image). <Class>StorageManager</Class> uses the application's context to access
 * the internal memory of the device.
 * @author Kamal Kamalaldin
 * @version 12/19/2014
 */
public class StorageManager {

    private Context mContext;

    /**
     * A public constructor that accepts the application's context.
     * @param context The application's context.
     */
    public StorageManager(Context context){
        mContext = context;
    }



    /**
     * Saves List of stories into memory. For each story a folder is created, and
     * a JSONObject representation of the story and a JPEG image of the story
     * are saved in that folder.
     * @param storiesList A <Code>List</Code> of stories.
     * @return boolean  True if all the stories were saved successfully to memory.
     * False if any story was not saved successfully.
     */
    public boolean saveStoriesToMemory(List<Story> storiesList){
//        Log.d("StorageManager", "Stories in database: " );
//        StoriesBank.printStories();
        boolean allSuccess = true;
        for(Story story: storiesList){
            boolean oneSucess = saveStory(story);
            if (!oneSucess)
                allSuccess = false;
//            Log.d("StorageManager",  story.getTitle() + " was saved: " + oneSucess);
        }

        return allSuccess;
    }


    /**
     * Saves a story in JSON format in a folder named after the story's condensed title.
     * If the story has an image other than the default image, and that image has been downloaded,
     * the said image will be stored in the folder.
     * @param story The story to be saved to memory.
     * @return boolean  true if the JSONObject was saved successfully, false
     * if any errors were encountered.
     */
    private boolean saveStory(Story story){

        Writer writer = null;

        try {
            JSONObject JSONstory = story.toJSON();
            File storiesFile = mContext.getDir(STORIES_FILE_PATH, Context.MODE_APPEND);
            File storyFile = new File(storiesFile, story.getCondensedTitle());
            boolean createdDirectory = storyFile.mkdir();
//            Log.d("StorageManager", "Directory " + storyFile + " was created with success: " + createdDirectory);

            File storyJSONFile = new File(storyFile, STORY_FILE_NAME);

            OutputStream outputStream = new FileOutputStream(storyJSONFile);
            writer = new OutputStreamWriter(outputStream);
            writer.write(JSONstory.toString());

            if(story.hasImage() && story.getImageBitmap()!=null){
                story.getImageBitmap().compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(new File(storyFile, STORY_IMAGE_FILE_NAME)));
            }


        } catch (JSONException e) {
            Log.e("StorageManager", "The file was unable to be created");
            e.printStackTrace();
            return false;
        } catch (FileNotFoundException e) {
            Log.e("StorageManager", "The file was unable to be created");
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            Log.e("StorageManager", "The file was unable to be created");
            e.printStackTrace();
            return false;
        } finally {
            if(writer!=null)
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return true;
    }

    /**
     * Reads the stories in JSON format from the file in memory and
     * adds the stories to the StoriesBank.
     * @return boolean  True if all the stories were retrieved successfully
     * from the JSON files, false if any problem was encountered.
     */
    public boolean loadStories(){
        boolean success = true;
        File pathFile= mContext.getDir(STORIES_FILE_PATH, Context.MODE_APPEND);
        File[] storiesFilePath = pathFile.listFiles();
        Log.d("StorageManager", "Stories file path: " + pathFile.toString());
        String paths = "";
        for(File filesss: storiesFilePath){
            paths +=filesss.toString() + "----";
        }
//        Log.d("StorageManager", "File array: " + paths);


        for(File file: storiesFilePath){
            if(!loadStory(file))
                success = false;
        }


        return success;
    }

    /**
     * Requirements: In order to load a story, there must be a JSONObject representation of a
     * <Class>Story</Class> inside the directory of the passed file.
     * Loads a story in JSON format from the passed File. named after the story's condensed title.
     * If the story has a custom image other than the default image, and that image has been downloaded,
     * the custom image will be stored in the folder, and called after
     * @param file  A file containing a JSONObject representation of a Story.
     * @return  boolean True if the story was loaded successfully. False if any problems were
     * encountered. If the there was a custom image for the story and it was not loaded properly,
     * true will still be returned.
     */
    private boolean loadStory(File file){
        boolean success = true;
//        Log.d("StorageManager", "File accessing: " + file.toString());
        File[] storyFiles = file.listFiles();
        if(storyFiles.length>0) {
            BufferedReader reader = null;
            try {
                File storyObjectFile = new File(file, STORY_FILE_NAME);
                InputStream inputStream = new FileInputStream(storyObjectFile);
                reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine())!=null){
                    stringBuilder.append(line);
                }
                org.json.JSONObject storyJSON = (org.json.JSONObject) new JSONTokener(stringBuilder.toString()).nextValue();
//                Log.d("StorageManager", "Json Object: " + storyJSON.toString());
                Story story = Story.getStory(storyJSON);
                //Loads the story's image from the file.
                if(!story.getImageURL().equals(Story.DEFAULT_IMAGE_URL)){
                    File storyImageFile = new File(file, STORY_IMAGE_FILE_NAME);
                    Bitmap storyImage = BitmapFactory.decodeFile(storyImageFile.getPath());
                    story.setImageBitmap(storyImage);}

                StoriesBank.addStory(story);

//                Log.d("StorageManager", "Story loaded successfully: " + story.toString());
            } catch (IOException | JSONException e) {
                Log.e("StorageManager", "Could not read file " + file.toString());
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
        } else {
            Log.e("StorageManager", "The path " + file + " does not contain a story.");
        }
        return success;
    }


    public final static String STORIES_FILE_PATH = "Stories";
    public final static String STORY_FILE_NAME = "story";
    public final static String STORY_IMAGE_FILE_NAME = "image";

}
