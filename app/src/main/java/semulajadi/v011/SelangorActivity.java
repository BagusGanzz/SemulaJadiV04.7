package semulajadi.v011;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SelangorActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Word>> {

    String url = "http://appsemulajadi.com/android/andro_disc.php";

    private static final int WORD_LOADER_ID = 1;

    View loadingIndicator;
    private TextView mEmptyStateText;
    ListView mListView;
    private WordAdapter mAdapter;

    LoaderManager loaderManager;
    ConnectivityManager connMgr;
    NetworkInfo networkInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selangor);

        mListView = findViewById(R.id.list_selangor);

        mEmptyStateText = findViewById(R.id.list_view_tv_empty);
        mListView.setEmptyView(mEmptyStateText);

        loadingIndicator = findViewById(R.id.loading_indicator);

        mAdapter = new WordAdapter(this, new ArrayList<Word>());
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> mListView, View view, int position, long id){
                Word currentWord = mAdapter.getItem(position);
                Intent intent = new Intent(mListView.getContext(),DiscoverContent.class);
                intent.putExtra("idTourism", currentWord.getIdTourism());
                startActivity(intent);
            }

        } );


        // Get a reference to the ConnectivityManager to check state of network connectivity
        connMgr = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            loaderManager = getSupportLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(WORD_LOADER_ID, null, this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            mEmptyStateText.setText("No Internet Connection.");
        }
    }

    @Override
    public Loader<List<Word>> onCreateLoader(int id, Bundle args) {
        return new WordLoader(getApplicationContext(), url);
    }

    @Override
    public void onLoadFinished(Loader<List<Word>> loader, List<Word> data) {
        // Hide loading indicator because the data has been loaded
        loadingIndicator.setVisibility(View.GONE);

        // Clear the adapter of previous earthquake data
        mAdapter.clear();

        // Set empty state text to display
        mEmptyStateText.setText("No Data Found.");

        // If there is a valid list of {@link Kos}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (data != null && !data.isEmpty()) {
            mAdapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Word>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }
}
