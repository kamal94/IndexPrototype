package indexprototype.com.kamal.indexprototype;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.UUID;


public class StoryReaderActivity extends ActionBarActivity {

    private TextView title;
    private TextView content;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_reader);

        UUID storyID = (UUID) getIntent().getSerializableExtra("STORY_ID");
        Story story = (StoriesBank.findById(storyID));

        image = (ImageView) findViewById(R.id.story_reader_storyImage);
        image.setImageDrawable(new BitmapDrawable(getResources(),story.getImageBitmap()));

        title = (TextView) findViewById(R.id.story_reader_story_title);
        title.setText(story.getTitle());

        content = (TextView) findViewById(R.id.story_reader_story_content);
        content.setText(story.getContent());

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
}
