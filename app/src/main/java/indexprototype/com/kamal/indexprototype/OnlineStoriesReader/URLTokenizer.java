package indexprototype.com.kamal.indexprototype.OnlineStoriesReader;

public class URLTokenizer {

	public static String getURL(String string){
		int start = string.indexOf("http:");
		int end = string.indexOf("\"", start+1);
		String outputURL = string.substring(start, end);
		
		
		return outputURL;
	}
}
