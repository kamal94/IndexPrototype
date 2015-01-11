package indexprototype.com.kamal.indexprototype.StorageManager;

import android.content.Context;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import indexprototype.com.kamal.indexprototype.R;
import indexprototype.com.kamal.indexprototype.StoriesBank;
import indexprototype.com.kamal.indexprototype.Story;

/**
 * A class that reads stories from a plain text file.
 * Created by Kamal on 10/19/2014.
 * @author Kamal Kamalaldin
 * @version 12/02/2014
 */
public class StoriesCreater {

    //private instance variables
    private Reader reader;      //a reader to read the file
    private StringBuilder fileContent;       //a string that represents a single fileContent. Will be used to quickly send a fileContent string to StoryReader to be converted
    private String nextLine = "";    //a placeholder for a line before it gets added to the fileContent
    private BufferedReader bufferedReader;
    private InputStreamReader inputStreamReader = null;
    private Context context;
    
    public StoriesCreater(Context activityContext){
        context = activityContext;
        resetBufferReader(context);
        fileContent = new StringBuilder();
    }

    public void readFileForStories() throws IOException {
        resetBufferReader(context);
        try {
            while( !( (nextLine =bufferedReader.readLine()) == null) && !nextLine.trim().equals("")){
                fileContent.append(nextLine + "\n" + "\t \t");
            }
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        } finally {
            bufferedReader.close();
            inputStreamReader.close();
        }

        readStories(fileContent.toString());
    }



    private void readStories(String storyString) {

        String author = storyString.substring(1, storyString.indexOf("AUTHOR"));
        String title = storyString.substring(storyString.indexOf("..") + 2, storyString.indexOf("TITLE"));
        String content = storyString.substring(storyString.indexOf("...") + 3, storyString.length() - 1);
        StoriesBank.addStory(new Story(null, author, title, content + content, null, "", StoriesBank.Section.NEWS));
        StoriesBank.addStory(new Story(null, author, title, content + content, null, "", StoriesBank.Section.NEWS));
        StoriesBank.addStory(new Story(null, author, title, content + content, null, "", StoriesBank.Section.NEWS));
        StoriesBank.addStory(new Story(null, author, title, content + content, null, "", StoriesBank.Section.NEWS));
        StoriesBank.addStory(new Story(null, author, title, content + content, null, "", StoriesBank.Section.NEWS));
        StoriesBank.addStory(new Story(null, author, title, content + content, null, "", StoriesBank.Section.NEWS));
        StoriesBank.addStory(new Story(null, author, title, content + content, null, "", StoriesBank.Section.NEWS));
        StoriesBank.addStory(new Story(null, author, title, content + content, null, "", StoriesBank.Section.NEWS));
    }

    /**
     * STUB method to refresh the stories by deleting the StoryBank and creating it again.
     * Better method would be to check for any version changes in each story and replace
     * outdated versions of a story with the latest one.
     */
    public void refreshStories(Context context){
        StoriesBank.clear();
        try {
            readFileForStories();
            Toast.makeText(context, "Stories refreshed", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void resetBufferReader(Context context){

        try {
            inputStreamReader = new InputStreamReader(context.getResources().openRawResource(R.raw.stories), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        bufferedReader = new BufferedReader(inputStreamReader);
    }
}
