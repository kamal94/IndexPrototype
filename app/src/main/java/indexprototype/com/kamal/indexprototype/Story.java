package indexprototype.com.kamal.indexprototype;

import android.graphics.Bitmap;

import java.util.UUID;

/**
 * Created by Kamal on 9/23/2014.
 */


public class Story {

    public final static String NO_IMAGE = "NOIMAGE";
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


    public String getTitle(){
        return title;
    }

    public String getContent(){
        return content;
    }

    public UUID getID(){
        return ID;
    }
    public String getImageURL(){
        return mImageURL;
    }
    public void setImageBitmap(Bitmap bitmap){
        mImageBitmap = bitmap;
    }
    public Bitmap getImageBitmap(){
        return mImageBitmap;
    }
    public boolean hasContent(){
        return (!content.trim().equals(""));
    }

    public String toString(){
        String string = "";
        string += "title: " +title + "\n";
        string += "author: " + author + "\n";
        string += "Image: " + mImageURL + "\n";
        string += "content: " + content + "\n";

        return string;
    }
}
