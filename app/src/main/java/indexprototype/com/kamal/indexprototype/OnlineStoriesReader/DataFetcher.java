package indexprototype.com.kamal.indexprototype.OnlineStoriesReader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
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
    private static String tag = "loop-title";   //instance field created for easily
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
                    break;
                case 1: section = StoriesBank.FEATURES;
                    url = StoriesBank.FEATURES_URL;
                    break;
                case 2: section = StoriesBank.ARTS;
                    url = StoriesBank.ARTS_URL;
                    break;
                case 3: section = StoriesBank.OPINIONS;
                    url = StoriesBank.OPINIONS_URL;
                    break;
                case 4: section = StoriesBank.SPORTS;
                    url = StoriesBank.SPORTS_URL;
                    break;
                case 5: section = StoriesBank.BUZZKILL;
                    url = StoriesBank.BUZZKILL_URL;
                    break;
            }
            Document doc = null;
            try {
                doc =   Jsoup.connect(url).get();   //..and opens links to them
            } catch (IOException e) {
                e.printStackTrace();
            }

            //fetches stories and creates stories from them.
            Elements elements = doc.getElementsByAttributeValue("class", tag);
            ArrayList<Elements> elementsArray = new ArrayList<Elements>();
            for(Element element: elements){
                Elements innerElements = element.children();
                elementsArray.add(innerElements);
            }
            for(Elements elementsCollection: elementsArray){
                for(Element element: elementsCollection){
                    StoryBuilder storyBuilder = new StoryBuilder();
                    storyBuilder.readStory(URLTokenizer.getURL(element.toString()));
                    storyBuilder.addStory(section);
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
