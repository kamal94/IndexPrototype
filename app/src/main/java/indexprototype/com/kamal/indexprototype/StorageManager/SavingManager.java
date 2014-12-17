package indexprototype.com.kamal.indexprototype.StorageManager;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

import indexprototype.com.kamal.indexprototype.Story;

/**
 * A class that saves stories and their information into internal memory.
 * A folder is created for each story. Each folder contains a JSONObjecr
 * representation of the story.
 * @author Kamal Kamalaldin
 * @version 12/15/2014
 */
public class SavingManager {

    private JSONArray storiesJsonArray;
    private Context mContext;

    /**
     * A public constructor
     * @param context The application's context.
     */
    public SavingManager(Context context){
        mContext = context;
    }



    /**
     * Saves List of stories into memory. Each story is saved as a JSONObjct
     * into its own folder
     * @param storiesList A <Code>List</Code> of stories.
     * @return boolean  True if all the stories were saved successfully to memory.
     * False if any story was not saved successfully.
     */
    public boolean saveStoriesToMemory(List<Story> storiesList){
        boolean success = true;
        for(Story story: storiesList){
            if(!saveStory(story))
                success= false;
        }

        return success;
    }


    /**
     * Saves a story in JSON format in folder named after the story's title,
     * without spaces or "/" characters.
     * @param story The story to be saved to memeory.
     * @return boolean  true if the JSONObject was saved successfully, false
     * if any errors were encountered.
     */
    private boolean saveStory(Story story){
        String filename = "";
        File storiesFile = mContext.getDir("Stories", Context.MODE_PRIVATE);
        JSONObject storyJson;
        try {
            storyJson = story.toJSON();
        } catch (JSONException e) {
            Log.e("SavingManager", "Story could not be converted to JSONObject");
            e.printStackTrace();
            return false;
        }

        Writer writer = null;
        try {
            filename = story.getTitle().replaceAll("\\s+", "").replaceAll("/",".").trim();
            File storyFile = new File(storiesFile, filename);
            storyFile.mkdir();

            File jsonStoryFile = new File(storyFile, "story");

            OutputStream storyOutputStream = new FileOutputStream(jsonStoryFile);
            writer = new OutputStreamWriter(storyOutputStream);
            writer.write(storyJson.toString());

            if(story.getImageBitmap()!=null) {
                File storyImageFile = new File(storyFile,"image");
                OutputStream imageOutputStream = new FileOutputStream(storyImageFile);
                writer = new OutputStreamWriter(imageOutputStream);
                writer.write(story.getImageBitmap().toString());
            }
        } catch (FileNotFoundException e) {
            Log.e("SavingManager", "The file was unable to be created");
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if(writer!=null)
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
        }

        Log.d("SavingManager", "Story saved to file " + mContext.getFilesDir() + "/" + STORIES_FILE_PATH + "/" + filename + " successfully.");
        return true;
    }


    public final static String STORIES_FILE_PATH = "Stories";

}
