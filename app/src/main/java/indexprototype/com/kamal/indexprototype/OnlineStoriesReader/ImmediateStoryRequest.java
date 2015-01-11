package indexprototype.com.kamal.indexprototype.OnlineStoriesReader;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

/**
 * Created by Kamal on 1/3/2015.
 */
public class ImmediateStoryRequest extends StringRequest {

    public ImmediateStoryRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
    }

    public ImmediateStoryRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }

    @Override
    public Priority getPriority() {
        return Priority.IMMEDIATE;
    }
}
