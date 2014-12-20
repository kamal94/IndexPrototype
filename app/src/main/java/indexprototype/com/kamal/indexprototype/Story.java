package indexprototype.com.kamal.indexprototype;

import android.graphics.Bitmap;

import org.json.JSONException;
import org.json.JSONObject;

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
//    public final static String NO_IMAGE = "NOIMAGE";
    //the image link to the default story image. If this link is found to be the story's image,
    //the link is discarded as it points to a low-res image, and a more high-res
    //default image is set for the story instead.
    public final static String DEFAULT_IMAGE_URL = "http://www.thekzooindex.com/wp-content/uploads/2014/12/Index-Social-Media-Filler-Thumbnail-174x131.png";

    //private instance variables
    private String mTitle;
    private String mStoryURL;
    private UUID ID;
    private String mContent;
    private String author;
    private String mImageURL;
    private Bitmap mImageBitmap;
    private String mSection;
    private String mByline;

    public Story(String storyURL, String Author, String Title, String Content, String byline, String imageURL, String section){
        if(storyURL!=null)
            mStoryURL = storyURL;
        else
            mStoryURL = "No URL";
        if(Title!=null)
            mTitle = Title.trim();
        else
            mTitle = "No mTitle";
        if(Author!=null)
            author = Author.trim();
        else
            author = "No author";
        ID = UUID.randomUUID();
        if(Content!=null)
            mContent = Content.trim();
        else
            mContent = "No mContent";
        if(byline!=null){
            mByline=byline;
        } else
            mByline = "No byline";
        if(imageURL!=null && !imageURL.equals(DEFAULT_IMAGE_URL))
            mImageURL = imageURL;
        else
            mImageURL = DEFAULT_IMAGE_URL;
        mSection = section;
    }

    /**
     * Returns whether the story has an image, or if it's imageURL
     * points to the default image.
     * @return boolean True if the story has an image, false if it does not.
     */
    public boolean hasImage(){
        return (!mImageURL.equals(DEFAULT_IMAGE_URL));
    }
    /**
     * Returns the mTitle of the story.
     * @return mTitle    The mTitle of the story.
     */
    public String getTitle(){
        return mTitle;
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
     * Gets the byline of the story.
     * @return byline The byline of the story.
     */
    public String getByline() {
        return mByline;
    }

    /**
     * Sets the byline of the story.
     * @param byline The desired byline of the story.
     */
    public void setByline(String byline) {
        mByline = byline;
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
     * @return image    The image of the story. If the story does not
     * have an image URL, null is returned.
     */
    public Bitmap getImageBitmap(){
        if(this.mImageURL.equals(DEFAULT_IMAGE_URL))
           return null;
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
        string += "mTitle: " + mTitle + "\n";
        string += "author: " + author + "\n";
        string += "Image: " + mImageURL + "\n";
        string += "mContent: " + mContent + "\n";

        return string;
    }

    /**
     * Removes spaces and path separating characters from the story's title.
     * @return title The story's title without spaces or path separators.
     */
    public String getCondensedTitle(){
        String title = mTitle.replaceAll("\\s+", "")
                .replaceAll("/",".")
                .trim();
        return title;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(JSON_BYLINE, getByline());
        jsonObject.put(JSON_STORY_URL, getStoryURL());
        jsonObject.put(JSON_AUTHOR, getAuthor());
        jsonObject.put(JSON_CONTENT, getContent());
        jsonObject.put(JSON_TITLE, getTitle());
        jsonObject.put(JSON_ID, getID());
        jsonObject.put(JSON_IMAGE_URL, getImageURL());
        jsonObject.put(JSON_SECTION, getSection());

        return jsonObject;
    }

    public static final String JSON_STORY_URL = "JSONSTORYURL";
    public static final String JSON_TITLE = "JSONTITLE";
    public static final String JSON_AUTHOR = "JSONAUTHOR";
    public static final String JSON_ID = "JSONID";
    public static final String JSON_CONTENT = "JSONOCONTENT";
    public static final String JSON_BYLINE = "JSONBYLINE";
    public static final String JSON_IMAGE_URL = "JSONIMAGEURL";
    public static final String JSON_IMAGE_BITMAP = "JSONBITMAP";
    public static final String JSON_SECTION = "JSONSECTION";
//    public static final String JSON_ = "JSON"
//    public static final String JSON_ = "JSON"
}
