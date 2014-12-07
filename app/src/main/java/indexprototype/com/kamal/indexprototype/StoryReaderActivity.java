package indexprototype.com.kamal.indexprototype;

import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.UUID;

import indexprototype.com.kamal.indexprototype.OnlineStoriesReader.StoryBuilder;


public class StoryReaderActivity extends ActionBarActivity {

    private TextView title;
    private TextView section;
    private TextView author;
    private TextView content;
    private ImageView image;
    private Story story;


    public static final String TRANSITION_IMAGE_NAME = "imageTransitionName";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_reader);

        UUID storyID = (UUID) getIntent().getSerializableExtra("STORY_ID");
        story = (StoriesBank.findById(storyID));

        new StoryDownloader().execute(story);
        image = (ImageView) findViewById(R.id.story_reader_storyImage);
        image.setImageDrawable(new BitmapDrawable(getResources(),story.getImageBitmap()));

        ViewCompat.setTransitionName(image, TRANSITION_IMAGE_NAME);
        title = (TextView) findViewById(R.id.story_reader_story_title);
        title.setText(story.getTitle());

        section = (TextView) findViewById(R.id.story_list_view_story_section);
        section.setText(story.getSection());

        author = (TextView) findViewById(R.id.story_list_view_story_author);
        author.setText(story.getAuthor());

        content = (TextView) findViewById(R.id.story_reader_story_content);
//        content.setText(story.getContent());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.story_reader, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class StoryDownloader extends AsyncTask<Story, Integer, Boolean>{

        @Override
        protected Boolean doInBackground(Story... params) {
            StoryBuilder storyBuilder = new StoryBuilder();
            storyBuilder.readStory( params[0].getID());
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            content.setText(story.getContent());
            author.setText(story.getAuthor());
        }
    }
}
