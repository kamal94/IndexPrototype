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

    public Story(String Author, String Title, String Content, int orderNo){
        title = Title;
        author = Author;
        ID = UUID.randomUUID();
        content = Content;
        orderNumber = orderNo;
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

}
