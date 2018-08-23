package semulajadi.v011;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

public class Tab2Content extends Fragment implements LoaderManager.LoaderCallbacks<List<PWord>> {

    String url = "http://appsemulajadi.com/android/andro_pck.php";

    private static final int WORD_LOADER_ID = 2;

    View rootView;
    View loadingIndicator;
    private TextView pgEmptyStateText;
    ListView pListView;
    private PWordAdapter pAdapter;

    LoaderManager ploaderManager;
    ConnectivityManager connMgr;
    NetworkInfo networkInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.tab2content,container,false);
        pListView = (ListView) rootView.findViewById(R.id.list_package);

        pgEmptyStateText = rootView.findViewById(R.id.listpg_view_tv_empty);
        pListView.setEmptyView(pgEmptyStateText);

        loadingIndicator = rootView.findViewById(R.id.pgloading_indicator);

        pAdapter = new PWordAdapter(getActivity(), new ArrayList<PWord>());
        pListView.setAdapter(pAdapter);
        pListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> mListView, View view, int position, long id){
                PWord currentWord = pAdapter.getItem(position);
                Intent intent = new Intent(mListView.getContext(),PackageContent.class);
                intent.putExtra("idPackage", currentWord.getIdPackage());
                startActivity(intent);
            }

        } );

        // Get a reference to the ConnectivityManager to check state of network connectivity
        connMgr = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            ploaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            ploaderManager.initLoader(WORD_LOADER_ID, null, this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            pgEmptyStateText.setText("No Internet Connection.");
        }
        return rootView;
    }



    @Override
    public Loader<List<PWord>> onCreateLoader(int id, Bundle args) {
        return new PWordLoader(getApplicationContext(), url);
    }

    @Override
    public void onLoadFinished(Loader<List<PWord>> loader, List<PWord> data) {
        // Hide loading indicator because the data has been loaded
        loadingIndicator.setVisibility(View.GONE);

        // Clear the adapter of previous earthquake data
        pAdapter.clear();

        // Set empty state text to display
        pgEmptyStateText.setText("No Data Found.");

        // If there is a valid list of {@link Kos}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (data != null && !data.isEmpty()) {
            pAdapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<PWord>> loader) {
        // Loader reset, so we can clear out our existing data.
        pAdapter.clear();
    }



}