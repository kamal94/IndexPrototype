package indexprototype.com.kamal.indexprototype.OnlineStoriesReader;

import indexprototype.com.kamal.indexprototype.StoriesBank;
import indexprototype.com.kamal.indexprototype.Story;

/**
 * A listener between an activity (or fragment) responsible for the
 * UI thread and <Class>DataFetcher</Class>. This listener enables
 * <Class>DataFetcher</Class> to notify the activity (or fragment)
 * of changes in the StoriesDatabase.
 * @author Kamal Kamalaldin
 * @version 12/04/2015
 */
public interface Communicator {
    public void storyRead(Story story);
    public void sectionGistsDownloaded(StoriesBank.Section section);
}
