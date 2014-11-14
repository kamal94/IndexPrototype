package indexprototype.com.kamal.indexprototype.OnlineStoriesReader;

public class URLTokenizer {

	public static String getURL(String string){
		int start = string.indexOf("http:");
		int end = string.indexOf("\" title");
		String outputURL = string.substring(start, end);
		
		
		return outputURL;
	}
}
