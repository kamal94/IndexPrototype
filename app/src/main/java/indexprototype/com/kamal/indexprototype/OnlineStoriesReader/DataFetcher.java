package indexprototype.com.kamal.indexprototype.OnlineStoriesReader;

import android.app.Activity;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import indexprototype.com.kamal.indexprototype.StoriesBank;

/**
 * A class that fetches stories and their images from different categories in the the index website.
 * This class depends on the final URLs that are specified by the <class>StoriesBank</class>. and is dependent
 * on that class and the <class>StoryBuilder</class>.
 *
 * DISCLAIMER: This class uses functionality from the  JSOUP library (http://jsoup.org/)
 * DISCLAIMER: This class uses functionality from the  Volley library (https://android.googlesource.com/platform/frameworks/volley/)
 *
 *
 * Modifications:
 *
 * 01.04.2014   Kamal Kamalaldin        (1) Switched handling network requests from the JSOUP to
 *                                      the Volley library. The JSOUP library still handles
 *                                      parsing the data.
 *                                      (2) Added a constructor and removed the static
 *                                      implementation of the class.
 *                                      (3) Added a convenient method that fetches story gists
 *                                      for all sections, and compartmentalized other methods.
 * @author Kamal Kamalaldin
 * @version 01/04/2015
 */
public class DataFetcher implements VolleyRequestReceiver {

    //private instance fields
    private Communicator mCommunicator;
    private RequestQueue requestQueue;
    private VolleyRequestReceiver mVolleyRequestReceiver = this;
    private StoriesBank.Section section;
    private String url;
    private Document doc;
    private String tag = "loop-wrap clearfix";   //instance field created for easily
    //changing the tag to look for stories
    //if the website HTML changes.

    public DataFetcher(Activity activity){
        requestQueue = Volley.newRequestQueue(activity);
        mCommunicator = (Communicator) activity;
    }
    /**
     * Reads different categories in the index website, as specified by constant
     * Strings in <class>StoriesDatabase</class>. Then searches for links with the tag
     * specified, and opens those links and creates stories from them using <class>StoryBuilder</class>
     * @return
     */
    private void run(int i) {
        //Goes through every section...
        switch (i){
            case 0: section = StoriesBank.Section.NEWS;
                url = StoriesBank.NEWS_URL;
                Log.d("DataFetcher", "News reached on Case operator");
                break;
            case 1: section = StoriesBank.Section.FEATURES;
                url = StoriesBank.FEATURES_URL;
                Log.d("DataFetcher", "Features reached on Case operator");
                break;
            case 2: section = StoriesBank.Section.ARTS;
                url = StoriesBank.ARTS_URL;
                Log.d("DataFetcher", "Arts reached on Case operator");
                break;
            case 3: section = StoriesBank.Section.OPINIONS;
                url = StoriesBank.OPINIONS_URL;
                Log.d("DataFetcher", "Opinions reached on Case operator");
                break;
            case 4: section = StoriesBank.Section.SPORTS;
                url = StoriesBank.SPORTS_URL;
                Log.d("DataFetcher", "Sports reached on Case operator");
                break;
            case 5: section = StoriesBank.Section.BUZZKILL;
                url = StoriesBank.BUZZKILL_URL;
                Log.d("DataFetcher", "Buzzkill reached on Case operator");
                break;
            default:
                Log.d("DataFetcher", "Defaulted on Case statement operation!!!");
        }

        //Stars a request to fetch the section's stories
        final StoryGistRequest storyGistRequest = new StoryGistRequest(Request.Method.GET, url, section
                , new StoryGistRequest.StoryGistResponseListener() {
            @Override
            public void onResponse(Object response, StoriesBank.Section section) {
                receive((String)response, section);
            }
            //This method should never be called.
            @Override
            public void onResponse(Object response) {
                Log.e("DataFetcher", "A FORBIDDEN METHOD HAS BEEN CALLED: onResponse(Object Response)");
            }
        }
                , new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        //adds the requests to the queue
        Log.d("DataFetching", "Request made for the " + section + " section");
        requestQueue.add(storyGistRequest);

    }


    /**
     * Receives the response sent from the RequestQueue, parses it, and
     * notifies the Activity/Fragment responsible for the UI Thread.
     * @param response
     * @param section
     */
    @Override
    public void receive(String response, StoriesBank.Section section) {

        doc =   Jsoup.parse(response);

        //fetches story elements and creates gists from them using a StoryBuilder
        Elements elements = doc.getElementsByAttributeValue("class", tag);
        for(Element element: elements){
            StoryBuilder storyBuilder = new StoryBuilder();
            mCommunicator.storyRead(storyBuilder.getStoryGist(element, section));
        }

        Log.d("DataFetching", "Response received for section: " + section);
        mCommunicator.sectionGistsDownloaded(section);
    }


    /**
     * A convenient method that downloads story gists of all the sections of The Index
     * into the StoriesDataBase
     */
    public void runAll() {
        for(int i = 0; i<6; i++){
            this.run(i);
        }
    }
}
