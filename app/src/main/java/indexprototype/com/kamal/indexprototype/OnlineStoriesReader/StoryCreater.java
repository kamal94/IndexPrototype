package indexprototype.com.kamal.indexprototype.OnlineStoriesReader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import indexprototype.com.kamal.indexprototype.StoriesBank;
import indexprototype.com.kamal.indexprototype.Story;

/**
 * A class that creates a story by accessing its link.
 *
 * @author Kamal Kamalaldin
 * @version 11/14/2014
 */
public class StoryCreater {


    //private instance fields
	private String mUrl;    //The url of the Story website
	private String mStoryContent = ""; //The contents of the story
	private String mAuthorName = ""; //The name of the author
	private String mTitle = "";  //The title of the story
	private Document doc = null;    //The representation of the story website for Jsoup
	private String imageURL = null; //The URL string of the story's image
	
	public void readStory(String url){
        //reads the url as
		mUrl = url;
		try {
			 doc =   Jsoup.connect(mUrl).get();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Elements elements = doc.getElementsByAttributeValue("class", "entry clearfix");
		Elements author = doc.getElementsByAttributeValue("style", "color: #000000;");
		Elements titles = doc.getElementsByAttributeValueContaining("class", "entry-title");
		ArrayList<Elements> innerElementsArray = new ArrayList<Elements>();
		for(Element element: elements){
			Elements innerElements = element.children();
			innerElementsArray.add(innerElements);
		}
		for(Element element: author){
			String authorTag = element.toString();
			int start = authorTag.indexOf("By");
			int end = authorTag.indexOf("<", start);
			if(start!=-1){
				mAuthorName = authorTag.substring(start, end);
			}
			
		}

		for(Element element: titles){
			mTitle = element.ownText();
		}

		for(Elements elementsCollection: innerElementsArray){
			for(Element element: elementsCollection){
				if(element.hasText())
					System.out.println(element);
					mStoryContent += element.ownText() + "\n" + "\t";
				if(ImageContainerDeterminer.containsImage(element.toString())){
					imageURL = ImageContainerDeterminer.getImageURL(element.toString());
					System.out.println(imageURL);
				}
			}
			System.out.println(mStoryContent);
		}
	}
	
	public void addStory(){
		Story story = new Story(mAuthorName, mTitle, mStoryContent, imageURL);
		StoriesBank.addStory(story);
	}


}
