package indexprototype.com.kamal.indexprototype;

import java.util.UUID;

/**
 * Created by Kamal on 9/23/2014.
 */

//STUB...NEEDS TO CREATE AN IMPLEMENTAITON OF AN IMAGEVIEW FOR EACH STORY, TO HOLD IT'S SOTRY IMAGE
public class Story {

    private String title;
    private UUID ID;
    private String content;
    private String author;
    private int orderNumber;
    private String mImageURL;

    public Story(String Author, String Title, String Content, String imageURL){
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
            mImageURL = "No Image";
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
