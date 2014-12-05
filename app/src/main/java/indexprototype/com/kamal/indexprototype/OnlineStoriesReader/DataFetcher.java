package indexprototype.com.kamal.indexprototype.OnlineStoriesReader;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

import indexprototype.com.kamal.indexprototype.StoriesBank;

/**
 * A class that fetches stories and their images from different categories in the the index website.
 * This class depends on the final URLs that are specified by the <class>StoriesBank</class>. and is dependent
 * on that class and the <class>StoryBuilder</class>.
 *
 * DISCLAIMER: This class uses functionality from the  JSOUP library (http://jsoup.org/)
 *
 * @author Kamal Kamalaldin
 * @version 12/02/2014
 */
public class DataFetcher {

    //private instance fields that are later modified by the run() method
    private static String section;
    private static String url;
    private static String tag = "loop-wrap clearfix";   //instance field created for easily
                                                //changing the tag to look for stories
                                                //if the website HTML changes.

    /**
     * Reads different categories in the index website, as specified by constant
     * Strings in <class>StoriesDatabase</class>. Then searches for links with the tag
     * specified, and opens those links and creates stories from them using <class>StoryBuilder</class>
     * @return
     */
	public static boolean run() {
        int startingNumOfStories = StoriesBank.howMany();   //records initial number of stories in bank
        //Goes through every section...
        for(int i=0; i<6; i++){
            switch (i){
                case 0: section = StoriesBank.NEWS;
                    url = StoriesBank.NEWS_URL;
                    Log.d("DataFetcher", "News reached on Case operator");
                    break;
                case 1: section = StoriesBank.FEATURES;
                    url = StoriesBank.FEATURES_URL;
                    Log.d("DataFetcher", "Features reached on Case operator");
                    break;
                case 2: section = StoriesBank.ARTS;
                    url = StoriesBank.ARTS_URL;
                    Log.d("DataFetcher", "Arts reached on Case operator");
                    break;
                case 3: section = StoriesBank.OPINIONS;
                    url = StoriesBank.OPINIONS_URL;
                    Log.d("DataFetcher", "Opinions reached on Case operator");
                    break;
                case 4: section = StoriesBank.SPORTS;
                    url = StoriesBank.SPORTS_URL;
                    Log.d("DataFetcher", "Sports reached on Case operator");
                    break;
                case 5: section = StoriesBank.BUZZKILL;
                    url = StoriesBank.BUZZKILL_URL;
                    Log.d("DataFetcher", "Buzkill reached on Case operator");
                    break;
                default:
                    Log.d("DataFetcher", "Defaulted on Case statement operation!!!");
            }
            Document doc = null;
            try {
                doc =   Jsoup.connect(url).get();   //..and opens links to them
            } catch(SocketTimeoutException e){
                Log.e("DataFetcher", "Connection timed out while accessing " + url + "\nTrying Again...");

                try {
                    doc =   Jsoup.connect(url).get();   //..and opens links to them
                } catch (SocketTimeoutException e1) {
                    Log.e("DataFetcher", "Connection timed out while accessing " + url + "\nTrying Third time...");

                    try {
                        doc =   Jsoup.connect(url).get();   //..and opens links to them
                    } catch (SocketTimeoutException e2) {
                        Log.e("DataGetcher", "Socket timed out for the third time. Aborting!");
                        break;
                    } catch (IOException e2){
                        e2.printStackTrace();
                    }
                } catch (IOException e1){
                    e1.printStackTrace();
                }
            } catch(IOException e) {
                e.printStackTrace();
            }

            //fetches stories and creates stories from them.
            Elements elements = doc.getElementsByAttributeValue("class", tag);
            ArrayList<Elements> elementsArray = new ArrayList<Elements>();
            for(Element element: elements){
                Elements innerElements = element.children();
//                Log.d("OBSERVER", innerElements.toString());
                StoryBuilder storyBuilder = new StoryBuilder();
                storyBuilder.getStoryGist(element, section);
                elementsArray.add(innerElements);
            }
            for(Elements elementsCollection: elementsArray){
                for(Element element: elementsCollection){
//                    Log.d("DataFetcher", element.toString());
//                    if (storyBuilder.readStory(URLTokenizer.getURL(element.toString())))
//                        storyBuilder.addStory(section);
                }
            }
        }

        //compares final story numbers in bank to inital.
        if(StoriesBank.howMany()>startingNumOfStories)
            return true;
        else
            return false;
	}

}
