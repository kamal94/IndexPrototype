package indexprototype.com.kamal.indexprototype.OnlineStoriesReader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import indexprototype.com.kamal.indexprototype.StoriesBank;


public class DataFetcher {

    private static String section;
    private static String url;

	public static boolean run() {
        for(int i=0; i<6; i++){
            switch (i){
                case 0: section = StoriesBank.NEWS;
                    url = StoriesBank.NEWS_URL;
                case 1: section = StoriesBank.FEATURES;
                    url = StoriesBank.FEATURES_URL;
                case 2: section = StoriesBank.ARTS;
                    url = StoriesBank.ARTS_URL;
                case 3: section = StoriesBank.OPINIONS;
                    url = StoriesBank.OPINIONS_URL;
                case 4: section = StoriesBank.SPORTS;
                    url = StoriesBank.SPORTS_URL;
                case 5: section = StoriesBank.BUZZKILL;
                    url = StoriesBank.BUZZKILL_URL;
            }
            Document doc = null;
            try {
                doc =   Jsoup.connect(url).get();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Elements elements = doc.getElementsByAttributeValue("class", "loop-title");
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

        if(StoriesBank.howMany()>0)
            return true;
        else
            return false;
	}

}
