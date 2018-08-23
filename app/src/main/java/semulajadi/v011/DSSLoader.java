package semulajadi.v011;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.util.List;

public class DSSLoader extends AsyncTaskLoader<List<Word>> {
    /** Tag for log messages */
    private static final String LOG_TAG = DSSLoader.class.getName();

    /** Query URL */
    private String mUrl;

    public DSSLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public List<Word> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of places.
        List<Word> dss = DSSListUtils.fetchData(mUrl);
        return dss;
    }
}
