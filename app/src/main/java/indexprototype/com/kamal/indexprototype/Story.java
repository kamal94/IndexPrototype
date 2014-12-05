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
    private String mStoryURL;
    private UUID ID;
    private String mContent;
    private String author;
    private String mImageURL;
    private Bitmap mImageBitmap;
    private String mSection;

    public Story(String storyURL, String Author, String Title, String Content, String imageURL, String section){
        if(storyURL!=null)
            mStoryURL = storyURL;
        else
            mStoryURL = "No URL";
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
            mContent = Content.trim();
        else
            mContent = "No mContent";
        if(imageURL!=null)
            mImageURL = imageURL;
        else
            mImageURL = "http://i0.wp.com/www.thekzooindex.com/wp-mContent/uploads/2014/08/Index-I2.png?resize=70%2C53";
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
     * @return  mContent The body of the story.
     */
    public String getContent(){
        return mContent;
    }

    /**
     * Rerturns the section the story belongs to.
     * @return  section The section that the story belongs to.
     */
    public String getSection() {
        return mSection;
    }

    /**
     * Returns String representation of the author of the story
     * @return author The the author of the story.
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Sets the author of the story
     * @param author The name of the author of the story
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Sets the content of the story.
     * @param content   The content of the story in a String format.
     */
    public void setContent(String content){
        mContent = content;
    }


    /**
     * Returns the image URL of the story in a String representation.
     * @return imageURL The URL of the story's image in a String format.
     */
    public String getImageURL(){
        return mImageURL;
    }

    /**
     * Returns the URL that points to the story on the website.
     * @return  URL A string representation of the URL that points to the story
     * on the website.
     */
    public String getStoryURL() {
        return mStoryURL;
    }

    /**
     * Returns the unique ID of the story in a UUID format.
     * @return UUID The UUID object of the story that represents the unique ID
     * of the story within the <class>StoriesBank</class>.
     */
    public UUID getID() {
        return ID;
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
        return (!mContent.trim().equals(""));
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
        string += "mContent: " + mContent + "\n";

        return string;
    }
}
