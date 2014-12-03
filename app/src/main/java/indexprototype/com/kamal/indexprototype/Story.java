package indexprototype.com.kamal.indexprototype;

import android.graphics.Bitmap;

import java.util.UUID;

/**
 * A class that represents a story in the website
 *
 * Created by Kamal on 9/23/2014.
 * @author Kamal Kamalaldin
 * @version 12/02/2014
 */


public class Story {

    //a public constatnt that signified the lack of cover image for the Story.
    public final static String NO_IMAGE = "NOIMAGE";

    //private instance variables
    private String title;
    private UUID ID;
    private String content;
    private String author;
    private String mImageURL;
    private Bitmap mImageBitmap;
    private String mSection;

    public Story(String Author, String Title, String Content, String imageURL, String section){
        if(Title!=null)
            title = Title.trim();
        else
            title = "No title";
        if(Author!=null)
            author = Author.trim();
        else
            author = "No author";
        ID = UUID.randomUUID();
        if(Content!=null)
            content = Content.trim();
        else
            content = "No content";
        if(imageURL!=null)
            mImageURL = imageURL;
        else
            mImageURL = "http://i0.wp.com/www.thekzooindex.com/wp-content/uploads/2014/08/Index-I2.png?resize=70%2C53";
        mSection = section;
    }

    /**
     * Returns the title of the story.
     * @return title    The title of the story.
     */
    public String getTitle(){
        return title;
    }

    /**
     * Returns the body of the story.
     * @return  content The body of the story.
     */
    public String getContent(){
        return content;
    }

    /**
     * Returns the unique ID of the story.
     * @return UUID The UUID of the story.
     */
    public UUID UUID(){
        return ID;
    }

    /**
     * Returns the image URL of the story in a String representation.
     * @return imageURL The URL of the story's image in a String format.
     */
    public String getImageURL(){
        return mImageURL;
    }

    /**
     * sets the image of the story.
     * @param bitmap    A bitmap object.
     */
    public void setImageBitmap(Bitmap bitmap){
        mImageBitmap = bitmap;
    }

    /**
     * Returns the image of the story.
     * @return image    The image of the story.
     */
    public Bitmap getImageBitmap(){
        return mImageBitmap;
    }

    /**
     * Checks if the story's body is empty.
     * @return boolean True if the story's body is not an empty string.
     */
    public boolean hasContent(){
        return (!content.trim().equals(""));
    }

    /**
     * Returns a string representation of the Story.
     * @return string A String representation of the Story.
     */
    public String toString(){
        String string = "";
        string += "title: " +title + "\n";
        string += "author: " + author + "\n";
        string += "Image: " + mImageURL + "\n";
        string += "content: " + content + "\n";

        return string;
    }
}
