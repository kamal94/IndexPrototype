package indexprototype.com.kamal.indexprototype.OnlineStoriesReader;

import android.content.Context;

import com.squareup.picasso.Picasso;

import java.io.IOException;

import indexprototype.com.kamal.indexprototype.Story;

/**
 * Created by Kamal on 11/15/2014.
 */
public class ImageDownloadThread implements Runnable{

    private Context mContext;
    private Story mStory;

    public ImageDownloadThread(Context context, Story story){
        mContext = context;
        mStory = story;
    }


    @Override
    public void run() {

        if(!mStory.getImageURL().equals(Story.NO_IMAGE))
            try {
                mStory.setImageBitmap(Picasso.with(mContext).load(mStory.getImageURL()).get());
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}
