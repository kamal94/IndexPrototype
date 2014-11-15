package indexprototype.com.kamal.indexprototype.OnlineStoriesReader;

/**
 * A static class that determines whether a string parsed from HTML contains a URL link to an image.
 * If the string contains a URL link to an image, this class can parse the link and
 * return it. This class does not check if the image is functional, but only checks if
 * the link has a class of "img" and a "src" value.
 *
 * @author Kamal Kamalaldin
 * @version 11/13/2014
 */
public class ImageContainerDeterminer {

    /**
     * Determines whether or not the string contains a URL link to an image
     * @param string   The string in which an image URL link will be searched for
     * @return  boolean True if the string contains a URL link to an image, false if it does not.
     */
	public static boolean containsImage(String string){
		if(string.contains("img") && string.contains("src"))
			return true;
		
		return false;
	}

    /**
     * Parses the image link from a string, only if the string contains an image URL link.
     * @param string    The string in which an image URL link will be searched for
     * @return  returnString    A String representation of a URL. If the string passes as a
     * parameter does not contain a url, a <code>null</code> is returned.
     */
	public static String getImageURL(String string){
		if(containsImage(string)){
			int start = string.indexOf("src=")+5;
			int end = string.indexOf("\"", start+4);
			String link = string.substring(start, end);
			return link;
		}
		return null;
	}
}
