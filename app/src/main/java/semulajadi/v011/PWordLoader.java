package semulajadi.v011;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.util.List;

/**
 * Created by Ryan on 03/04/2018.
 */

public class PWordLoader extends AsyncTaskLoader<List<PWord>> {
    /** Tag for log messages */
    private static final String LOG_TAG = PWordLoader.class.getName();

    /** Query URL */
    private String pUrl;

    public PWordLoader(Context context, String url) {
        super(context);
        pUrl = url;
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
    public List<PWord> loadInBackground() {
        Log.i(LOG_TAG, "Test: loadInBackground() called .....");
        if (pUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of places.
        List<PWord> words = PWordListUtils.fetchData(pUrl);
        return words;
    }
}
