package indexprototype.com.kamal.indexprototype.OnlineStoriesReader;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.UUID;

import indexprototype.com.kamal.indexprototype.StoriesBank;
import indexprototype.com.kamal.indexprototype.Story;


/**
 * A class that creates a story by accessing its link.
 *
 * @author Kamal Kamalaldin
 * @version 11/14/2014
 */
public class StoryBuilder {


    //private instance fields
    private String mURL;  //The url of the Story website
    private String mStoryContent = ""; //The contents of the story
    private String mAuthorName = ""; //The name of the author
    private String mTitle = "";  //The title of the story
    private Document doc = null;    //The representation of the story website for Jsoup
    private String imageURL = null; //The URL string of the story's image
    private String mByline = null;  //The byline of the story as fetched from the gits.

    /**
     * Creates a story gist from the element passed to it.
     * @param element   An Element that contains the link to the story, the title of
     *                  the story in text, and a link to the image of the story.
     * @param section   The section that the story belongs to.
     * @return  boolean True if the story was added successfully to the StoriesBank,
     *                  false if an error was encountered while adding the story. If
     *                  the passed Element is <code>null</code>, false is returned.
     */
    public Story getStoryGist(Element element, StoriesBank.Section section){

        if(element==null)
            return null;

        mURL = URLTokenizer.getURL(element.toString());
        Elements title = element.getElementsByAttributeValue("class", "loop-title");
        for(Element ele: title){
            mTitle = ele.text();
        }
        Elements byline = element.getElementsByAttributeValue("class", "mh-excerpt");
        for(Element ele: title){
            mByline = mByline + ele.text();
        }
        mByline = byline.text();
        if(ImageContainerDeterminer.containsImage(element.toString())){
            imageURL = ImageContainerDeterminer.getImageURL(element.toString());
        }
//
//        Log.d("StoryBuilder", "Story Gist: \n" + "link: " + mURL + "\ntitle: " + mTitle + "\nimageURL : " + imageURL);
        Story story = new Story(mURL, null, mTitle, null, mByline, imageURL, section);
        StoriesBank.addStory(story);

        return story;
    }
    /**
     * Reads a story from the website by fetching the story's URL
     * from the database. It then
     * @return boolean True if the story was read successfully. False if any errors
     * were encountered.
     */
    public boolean readStory(UUID uuid){
        //reads the url as a Jsoup Document
        mURL = StoriesBank.findById(uuid).getStoryURL();
        try {
            doc =   Jsoup.connect(mURL).get();
        } catch (SocketTimeoutException e){
            Log.e("StoryBuilder", "Connection timed out while accessing " + mURL + " \n Trying again...!");
            try{
                doc = Jsoup.connect(mURL).get();
            } catch (SocketTimeoutException e1){
                Log.e("StoryBuilder", "Connection timed out again while accessing " + mURL + " \n Trying For the third time...!");
                try {
                    doc = Jsoup.connect(mURL).get();
                } catch(SocketTimeoutException e2){
                    Log.e("StoryBuilder", "Connection timed out for the third time while accessing " + mURL + " \n Aborting!");
                    return false;
                } catch(IOException e2) {
                    e2.printStackTrace();
                }
            } catch (IOException e1){
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //gets elements according to their classes' attributes
        Elements storyContentElements = doc.getElementsByAttributeValue("class", "entry clearfix");
        Elements author = doc.getElementsByAttributeValue("style", "color: #000000;");
        ArrayList<Elements> innerElementsArray = new ArrayList<Elements>(); //creats an arraylist that will be filled with inner elements of mStoryContent paragraphs

        //adds inner elements that have the story content in the arraylist
        for(Element element: storyContentElements){
            Elements innerElements = element.children();
            innerElementsArray.add(innerElements);
        }

        //searches for the author and sets mAuthorName to it. If no author name is found, mAuthorName is set to null
        for(Element element: author){
            String authorTag = element.toString();
            int start = authorTag.indexOf("By");
            int end = authorTag.indexOf("<", start);
            if(start!=-1){
                mAuthorName = authorTag.substring(start + 3, end);
            }
        }

        //adds the paragraphs to mStoryContent, and searches for an image URL in the story.
        for(Elements elementsCollection: innerElementsArray){
            for(Element element: elementsCollection){
                if(element.hasText())
                    mStoryContent += element.text() + "\n" + "\t";
            }
            Log.d("StoryBuilder", "Content read: " + mStoryContent);
        }

        StoriesBank.findById(uuid).setContent(mStoryContent);
        StoriesBank.findById(uuid).setAuthor(mAuthorName);
        return true;
    }
}
