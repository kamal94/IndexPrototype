package indexprototype.com.kamal.indexprototype.OnlineStoriesReader;

import indexprototype.com.kamal.indexprototype.StoriesBank;

/**
 * A listener to Volley's <Class>Request</Class> onResponse().
 * This enables the manipulation of non-final objects within
 * the class that uses A Volley <Class>Request</Class>.
 *
 * @author Kamal Kamalaldin
 * @version 01/04/2015
 */

public interface VolleyRequestReceiver {
    public void receive(String response, StoriesBank.Section section);
}
