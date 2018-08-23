package semulajadi.v011;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Ryan on 15/11/2017.
 */

public class Tab1Content extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final View rootView = inflater.inflate(R.layout.tab1content,container,false);

        TextView openSelangor = (TextView)rootView.findViewById(R.id.tab1);

        openSelangor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(rootView.getContext(), SelangorActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }
}


