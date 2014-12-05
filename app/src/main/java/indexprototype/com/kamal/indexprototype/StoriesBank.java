package indexprototype.com.kamal.indexprototype;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;

/**
 * A singleton class that stores that stories that are fetched by the application and
 * manages their sorting and access.
 * Created by Kamal on 10/19/2014.
 *
 * @author Kamal Kamalaldin
 * @version 12/02/2014
 */
public class StoriesBank {

    private static ArrayList<Story> stories = new ArrayList<Story>();
    private static Queue<Story> sNewsStories = new LinkedList();
    private static Queue<Story> sFeaturesStories = new LinkedList();
    private static Queue<Story> sOpinionsStories = new LinkedList();
    private static Queue<Story> sArtsStories = new LinkedList();
    private static Queue<Story> sSportsStories = new LinkedList();
    private static Queue<Story> sBuzzKillStories = new LinkedList();

    public static ArrayList<Story> getStories() {
        return stories;
    }

    /**
     * Adds a story to the central stories database and to the story's
     * perspective section. If both operations are achieved successfully,
     * the method returns true. If either fails, the method returns false.
     * @param story The story to be added to the database
     * @return  boolean True if the story was added to the database and to the
     * respective section of the story. False if the story was not added to either
     * of the banks. The story is deleted from the central story bank if false is returned.
     *
     */
    public static boolean addStory(Story story) {
        int progress = 0;
        if(stories.add(story))
            progress++;
        switch (story.getSection()){
            case(StoriesBank.NEWS):
                sNewsStories.add(story);
                progress++;
                break;
            case(StoriesBank.FEATURES):
                sFeaturesStories.add(story);
                progress++;
                break;
            case(StoriesBank.ARTS):
                sArtsStories.add(story);
                progress++;
                break;
            case(StoriesBank.ARTS_SHORT):
                sArtsStories.add(story);
                progress++;
                break;
            case(StoriesBank.ARRTS_FULL):
                sArtsStories.add(story);
                progress++;
                break;
            case(StoriesBank.OPINIONS):
                sOpinionsStories.add(story);
                progress++;
                break;
            case(StoriesBank.SPORTS):
                sSportsStories.add(story);
                progress++;
                break;
            case(StoriesBank.BUZZKILL):
                sBuzzKillStories.add(story);
                progress++;
                break;
            default:
                return false;
        }
        if(progress==2)
            return true;
        else{
            remove(story);
        }

        return false;
    }

    /**
     * Finds the story in the bank that has the same UUID
     *
     * @param uuid The UUID of the story to be found
     * @return story    The story with the specified UUID.
     */
    public static Story findById(UUID uuid) {
        for (Story story : stories) {
            if (story.getID().equals(uuid))
                return story;
        }

        return null;
    }

    /**
     * Finds the story in the bank with the story index.
     * Used mainly for the <Code>StoryRecyclerViewAdaoter</Code> to
     * keep the cards and stories insynch.
     *
     * @param index The index of the story in the stories arraylist
     * @return  story   The story that occupies the List index in the database.
     */
    public static Story findByIndex(int index) {
        return stories.get(index);
    }

    public static void clear(){
        Iterator<Story> itr = stories.iterator();
        while(itr.hasNext()){
            itr.next();
            itr.remove();
        }

    }

    /**
     * Provides the current number of stories in the database.
     * @return count The current number of stories in the database.
     */
    public static int howMany(){
        return stories.size();
    }

    /**
     * Prints all the stories in the bank
     */
    public static void printStories(){
        for(Story story: stories){
            if (story.hasContent())
                System.out.println(story.toString());
        }
    }

    /**
     * Removes a story from the database. This method only removes the storyies
     * from the central database, and not from the sections database.
     * @param story The story to be removed from the central database.
     * @return  story The story that was removed from the database. If the story
     * does not exist in the database, the method returns null.
     */
    private static Story remove(Story story){
        int result = stories.indexOf(story);
        if (result==-1)
            return null;
        else
            return stories.remove(result);
    }


    //Constants that are used by other classes to define story sections
    //and their perspective URL strings.
    public static final String NEWS = "News";
    public static final String NEWS_URL = "http://www.thekzooindex.com/category/news/";
    public static final String FEATURES = "Features";
    public static final String FEATURES_URL = "http://www.thekzooindex.com/category/features/";
    public static final String ARTS = "Arts";
    public static final String ARTS_URL = "http://www.thekzooindex.com/category/arts/";
    public static final String ARRTS_FULL = "Arts and Entertainment";
    public static final String ARTS_SHORT = "A&E";
    public static final String OPINIONS = "Opinions";
    public static final String OPINIONS_URL = "http://www.thekzooindex.com/category/opinions/";
    public static final String SPORTS = "Sports";
    public static final String SPORTS_URL = "http://www.thekzooindex.com/category/sports/";
    public static final String BUZZKILL = "BUZKILL";
    public static final String BUZZKILL_URL = "http://www.thekzooindex.com/category/buzzkill/";
}
