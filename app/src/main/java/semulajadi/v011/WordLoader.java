package semulajadi.v011;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.util.List;

/**
 * Created by Dony Tama on 13/03/2018.
 */

public class WordLoader extends AsyncTaskLoader<List<Word>> {
    /** Tag for log messages */
    private static final String LOG_TAG = WordLoader.class.getName();

    /** Query URL */
    private String mUrl;

    public WordLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        Log.i(LOG_TAG, "Test: onStartLoading() called .....");
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public List<Word> loadInBackground() {
        Log.i(LOG_TAG, "Test: loadInBackground() called .....");
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of places.
        List<Word> words = WordListUtils.fetchData(mUrl);
        return words;
    }
}
