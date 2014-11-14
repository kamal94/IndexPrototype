package indexprototype.com.kamal.indexprototype.OnlineStoriesReader;

public class ImageContainerDeterminer {

	
	public static boolean containsImage(String string){
		if(string.contains("img") && string.contains("src"))
			return true;
		
		return false;
	}
	
	public static String getImageURL(String string){
		if(containsImage(string)){
			int start = string.indexOf("src=")+5;
			int end = string.indexOf("\"", start+4);
			System.out.println("Start: " + start + " end : " + end);
			String link = string.substring(start, end);
			return link;
		}
		return null;
	}
}
