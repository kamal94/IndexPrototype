package indexprototype.com.kamal.indexprototype.OnlineStoriesReader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import indexprototype.com.kamal.indexprototype.StoriesBank;


public class TestingStoryReader {

	public static boolean run() {
		Document doc = null;
		String url = "http://www.thekzooindex.com/category/news/";
		try {
			 doc =   Jsoup.connect(url).get();
		} catch (IOException e) {
			e.printStackTrace();
		}

	Elements elements = doc.getElementsByAttributeValue("class", "cp-widget-title");
	ArrayList<Elements> elementsArray = new ArrayList<Elements>();
		for(Element element: elements){
			Elements innerElements = element.children();
			elementsArray.add(innerElements);
	}
		for(Elements elementsCollection: elementsArray){
			for(Element element: elementsCollection){
				StoryCreater storyCreater = new StoryCreater();
				storyCreater.readStory(URLTokenizer.getURL(element.toString()));
				storyCreater.addStory();
			}
		}
        if(StoriesBank.howMany()>0)
		    return true;
        else
            return false;
	}

}
