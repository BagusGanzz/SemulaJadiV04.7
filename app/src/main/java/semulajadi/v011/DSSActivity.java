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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DSSActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Word>> {

    String url = "http://appsemulajadi.com/android/dss_proses.php";

    private static final int DSS_LOADER_ID = 1;

    View loadingIndicator;
    private TextView mEmptyStateText;
    ListView mListView;
    private DssAdapter mAdapter;

    Button back;

    LoaderManager loaderManager;
    ConnectivityManager connMgr;
    NetworkInfo networkInfo;

    //parameter
    String category, jumlahkriteria, p1, p2, p3, p4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dss);

//        Button btnback = (Button) findViewById(R.id.back_dss);
//
//        btnback.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(DSSActivity.this, Tab3Content.class));
//
//                finish();
//            }
//        });
        back = (Button) findViewById(R.id.back_dss);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DSSActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Intent previousIntent = getIntent();
        if (previousIntent.hasExtra("category")){
            category = previousIntent.getStringExtra("category");
            jumlahkriteria = previousIntent.getStringExtra("jumlahkriteria");
            p1 = previousIntent.getStringExtra("p1");
            p2 = previousIntent.getStringExtra("p2");
            p3 = previousIntent.getStringExtra("p3");
            p4 = previousIntent.getStringExtra("p4");
        }

        mListView = findViewById(R.id.list_dss);

        mEmptyStateText = findViewById(R.id.dss_view_tv_empty);
        mListView.setEmptyView(mEmptyStateText);

        loadingIndicator = findViewById(R.id.dssloading_indicator);

        mAdapter = new DssAdapter(this, new ArrayList<Word>());
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_SHORT).show();
                Word currentWord = mAdapter.getItem(i);
                Intent intent = new Intent(mListView.getContext(),DiscoverContent.class);
                intent.putExtra("idTourism", currentWord.getIdTourism());
                startActivity(intent);
            }
        });

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
            loaderManager.initLoader(DSS_LOADER_ID, null, this);
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
        mEmptyStateText.setVisibility(View.GONE);

        Uri baseUri;
        Uri.Builder uriBuilder;

        baseUri = Uri.parse(url);
        uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("category", category);
        uriBuilder.appendQueryParameter("jumlahkriteria", jumlahkriteria);
        uriBuilder.appendQueryParameter("p1", p1);
        uriBuilder.appendQueryParameter("p2", p2);
        uriBuilder.appendQueryParameter("p3", p3);
        uriBuilder.appendQueryParameter("p4", p4);

        Log.v("DSS String : ", uriBuilder.toString());

        return new DSSLoader(getApplicationContext(), uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<Word>> loader, List<Word> data) {
        // Hide loading indicator because the data has been loaded
        loadingIndicator.setVisibility(View.GONE);

        // Clear the adapter of previous earthquake data
        mAdapter.clear();

        // If there is a valid list of {@link Kos}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (data != null && !data.isEmpty()) {
            mAdapter.addAll(data);
        }else {
            // Set empty state text to display
            mEmptyStateText.setVisibility(View.VISIBLE);
            mEmptyStateText.setText("No Data Found.");
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Word>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }

    public void backdss (View view){
        Intent intent = new Intent(this, Tab1Content.class);
        startActivity(intent);
    }

}
