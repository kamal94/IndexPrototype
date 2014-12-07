package indexprototype.com.kamal.indexprototype;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import indexprototype.com.kamal.indexprototype.OnlineStoriesReader.DataFetcher;
import indexprototype.com.kamal.indexprototype.OnlineStoriesReader.ImageDownloadThread;


public class MainActivity extends ActionBarActivity {

    private SectionsAdapter mSectionsAdapter;
    private ViewPager mViewPager;
    private SlidingTabLayout mSlidingTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.e("MainActivity", "onCreate called.");
        setContentView(R.layout.activity_main);
        getSupportActionBar().setElevation(0);

        mSectionsAdapter = new SectionsAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.main_activity_view_pager);
        mViewPager.setAdapter(mSectionsAdapter);

        mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.main_activity_sliding_tabs);
        mSlidingTabLayout.setViewPager(mViewPager);
        mSlidingTabLayout.setCustomTabColorizer(new CustomSlidingTabColors());


        for(int i=0; i<6; i++){
            new DownloadData().execute(i);
        }

        for(int i=0; i<6; i++){
            new DownloadStoryImages().execute(i);
        }


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

        mSectionsAdapter.notifyDataSetChanged();
        mSectionsAdapter.refreshFragment(mViewPager.getCurrentItem());
        mSectionsAdapter.refreshFragment(mViewPager.getCurrentItem());
//        storyRecyclerViewAdapter.notifyDataSetChanged();
    }

    /**
     * A method that deletes all entries in the feed
     */
    private void actionBarRemoveClicked(){
        StoriesBank.clear();
        mSectionsAdapter.notifyDataSetChanged();
    }













    /**
     * A class that runs the download for the stories from the internet on a separate thread.
     */
    private class DownloadData extends AsyncTask<Integer, Integer, Boolean>{

        private int runningThread;
        @Override
        protected Boolean doInBackground(Integer... params) {
            runningThread = params[0];
            return DataFetcher.run(params[0]);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                mSectionsAdapter.notifyDataSetChanged();
                mSectionsAdapter.refreshFragment(runningThread);
                Log.d("MainActivity", "running thread: " + runningThread);
            }
        }
    }


    /**
     * A class that downloads images of stories.
     */
    private class DownloadStoryImages extends AsyncTask<Integer, Integer, Boolean>{

        private int runningThread;
        @Override
        protected Boolean doInBackground(Integer... params) {

            runningThread = params[0];
            for(Story story: StoriesBank.getStories()){
                ImageDownloadThread imageDownloadThread = new ImageDownloadThread(getApplicationContext(), story);
                imageDownloadThread.run();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            mSectionsAdapter.notifyDataSetChanged();
            mSectionsAdapter.refreshFragment(runningThread);
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


//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        Log.e("MainActivity", "OnPause called.");
//    }
//
//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    @Override
//    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
//        if(Build.VERSION.SDK_INT> Build.VERSION_CODES.FROYO)
//            super.onSaveInstanceState(outState, outPersistentState);
//
//        Log.e("MainActivity", "onSaveInstanceState called.");
//    }
