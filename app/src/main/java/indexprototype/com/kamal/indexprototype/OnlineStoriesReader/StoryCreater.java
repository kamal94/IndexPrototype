package indexprototype.com.kamal.indexprototype.OnlineStoriesReader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import indexprototype.com.kamal.indexprototype.StoriesBank;
import indexprototype.com.kamal.indexprototype.Story;


public class StoryCreater {

	private String mUrl;
	private String mStory = "";
	private String mImageUrl;
	private String authorName;
	private String title = "";
	private Document doc = null;
	private String imageURL = null;
	
	public void readStory(String url){

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
				//System.out.println(element.toString());
				authorName = authorTag.substring(start, end);
				//System.out.println("|" + authorName+ "|");
			}
			
		}
		//works every time
		for(Element element: titles){
			title = element.ownText();
		}

		for(Elements elementsCollection: innerElementsArray){
			for(Element element: elementsCollection){
				if(element.hasText())
					System.out.println(element);
					mStory += element.ownText() + "\n" + "\t";
				if(ImageContainerDeterminer.containsImage(element.toString())){
					imageURL = ImageContainerDeterminer.getImageURL(element.toString());
					System.out.println(imageURL);
				}
			}
			System.out.println(mStory);
		}
	}
	
	public void addStory(){
		Story story = new Story(authorName, title, mStory, imageURL);
		StoriesBank.addStory(story);
	}


}
