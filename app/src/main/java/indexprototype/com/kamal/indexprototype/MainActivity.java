package indexprototype.com.kamal.indexprototype;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;

import indexprototype.com.kamal.indexprototype.OnlineStoriesReader.TestingStoryReader;
import indexprototype.com.kamal.indexprototype.TextFileReader.StoriesCreater;
import indexprototype.com.kamal.indexprototype.recyclerViewTesting.StoryRecyclerViewAdapter;


public class MainActivity extends ActionBarActivity {


    private RecyclerView recyclerView;
    private ArrayAdapter arrayAdapter;
    private StoriesCreater storiesCreater;
    private RecyclerView.LayoutManager layoutManager;
    private StoryRecyclerViewAdapter storyRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setElevation(0);

        new DownloadData().execute();
        new DownloadStoryImages().execute();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        storyRecyclerViewAdapter = new StoryRecyclerViewAdapter(this);
        recyclerView.setAdapter(storyRecyclerViewAdapter);
        storyRecyclerViewAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver(){});
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch(item.getItemId()){
            case R.id.action_bar_settings:
                actionBarSettingsClicked();
                break;
            case R.id.action_bar_refresh_feed:
                actionBarRefreshClicked();
                break;
            case R.id.action_bar_remove_feed:
                actionBarRemoveClicked();
            default:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A STUB method to indicate the clicking of the settings button/icon
     * on the main activity
     */
    private void actionBarSettingsClicked(){
        Toast.makeText(getApplicationContext(),"Settings Clicked", Toast.LENGTH_SHORT).show();
    }

    /**
     * A STUB method to indicate the clicking of the refresh button/icon
     * on the main activity
     */
    private void actionBarRefreshClicked(){
        storyRecyclerViewAdapter.notifyDataSetChanged();
    }

    /**
     * A method that deletes all entries in the feed
     */
    private void actionBarRemoveClicked(){
        StoriesBank.clear();
        storyRecyclerViewAdapter.notifyDataSetChanged();
    }

    /**
     * A class that runs the download for the stories from the internet on a separate thread.
     */
    private class DownloadData extends AsyncTask<String, Integer, Boolean>{

        @Override
        protected Boolean doInBackground(String... params) {
            return TestingStoryReader.run();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result)
                storyRecyclerViewAdapter.notifyDataSetChanged();
        }
    }

    private class DownloadStoryImages extends AsyncTask<Story, Integer, Boolean>{

        @Override
        protected Boolean doInBackground(Story... params) {

            for(Story story: StoriesBank.getStories()){
                try {
                    System.out.println(story.getImageURL());
                    if(!story.getImageURL().equals(Story.NO_IMAGE))
                        story.setImageBitmap(Picasso.with(getApplicationContext()).load(story.getImageURL()).get());
                    System.out.println(story.getImageURL());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            storyRecyclerViewAdapter.notifyDataSetChanged();
        }
    }
}






/*

STORY ADAPTER IF NEEDED
    private class StoryArrayAdapter extends ArrayAdapter<Story> {

        public StoryArrayAdapter(Context context, int resource, List<Story> objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView ==null){
                convertView = View.inflate(getApplicationContext(),R.layout.story_list_view, null);
            }
            Story story = getItem(position);

            ImageView imageView  = (ImageView) convertView.findViewById(R.id.story_list_view_image_view);
            imageView.setImageResource(R.drawable.image_clip);

            TextView largeTextView = (TextView) convertView.findViewById(R.id.story_list_view_large_text_view);
            largeTextView.setText(story.getTitle());

            TextView smallTextView = (TextView) convertView.findViewById(R.id.story_list_view_small_text_view);
            smallTextView.setText(story.getContent());

            return convertView;
        }

 */