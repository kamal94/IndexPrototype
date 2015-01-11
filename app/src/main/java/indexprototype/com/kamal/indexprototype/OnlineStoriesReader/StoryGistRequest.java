package indexprototype.com.kamal.indexprototype.OnlineStoriesReader;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import indexprototype.com.kamal.indexprototype.StoriesBank;

/**
 * Created by Kamal on 1/4/2015.
 */
public class StoryGistRequest extends StringRequest {

    private StoriesBank.Section mSection;
    private StoryGistResponseListener mStoryGistResponseListener;

    public StoryGistRequest(String url, StoriesBank.Section section, StoryGistResponseListener listener, Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
        mStoryGistResponseListener = listener;
        mSection = section;
    }

    public StoryGistRequest(int method, String url, StoriesBank.Section section, StoryGistResponseListener listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
        mStoryGistResponseListener = listener;
        mSection = section;
    }

    /**
     * Replaces the default listener call with the custom listener call
     * inside the </Class>StoryGistResponseListener<Class>
     * @param response  Teh response fetched from the request.
     */
    @Override
    protected void deliverResponse(String response) {
        mStoryGistResponseListener.onResponse(response, mSection);
    }

    /**
     * A custom response method that passes the section of the story.
     */
    public interface StoryGistResponseListener extends Response.Listener{
        public void onResponse(Object response, StoriesBank.Section section);
    }
}
