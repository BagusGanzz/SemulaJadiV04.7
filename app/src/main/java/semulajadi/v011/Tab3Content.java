package semulajadi.v011;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

/**
 * Created by Ryan on 15/11/2017.
 */

public class Tab3Content extends Fragment {

    TextView labelp4;
    View rootview;
    Spinner jmlCategory1, jmlKriteria1, prioritas1, prioritas2, prioritas3, prioritas4;
    Button proses;
    ViewGroup mContainer;
    Fragment fragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
//        final View rootView = inflater.inflate(R.layout.tab3content,container,false);
//
//        TextView openDss = (TextView)rootView.findViewById(R.id.proses_dss);
//
//        openDss.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(rootView.getContext(), DssContent.class);
//                startActivity(intent);
//            }
//        });

        rootview = inflater.inflate(R.layout.tab3content, container, false);
        mContainer = container;
        fragment = this;

        ImageView dssImg = (ImageView)rootview.findViewById(R.id.dss_img);
        Picasso.with(rootview.getContext()).load(R.drawable.dss_img).into(dssImg);

        jmlCategory1 = (Spinner)rootview.findViewById(R.id.jc);
        jmlKriteria1 = (Spinner)rootview.findViewById(R.id.jk1);
        prioritas1 = (Spinner)rootview.findViewById(R.id.p1);
        prioritas2 = (Spinner)rootview.findViewById(R.id.p2);
        prioritas3 = (Spinner)rootview.findViewById(R.id.p3);
        prioritas4 = (Spinner)rootview.findViewById(R.id.p4);
        labelp4 = (TextView)rootview.findViewById(R.id.label_p4);

        final ArrayAdapter<CharSequence> jmlCategori = ArrayAdapter.createFromResource(getContext(),
                R.array.category_dss, android.R.layout.simple_spinner_item);
        jmlCategori.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        jmlCategory1.setAdapter(jmlCategori);

        final ArrayAdapter<CharSequence> jmlKriteria = ArrayAdapter.createFromResource(getContext(),
                R.array.kriteria_dss, android.R.layout.simple_spinner_item);
        jmlKriteria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        jmlKriteria1.setAdapter(jmlKriteria);

        ArrayAdapter<CharSequence> priority = ArrayAdapter.createFromResource(getContext(),
                R.array.priority_dss, android.R.layout.simple_spinner_item);
        priority.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        prioritas1.setAdapter(priority);
        prioritas2.setAdapter(priority);
        prioritas3.setAdapter(priority);
        prioritas4.setAdapter(priority);

        prioritas2.setSelection(1);
        prioritas3.setSelection(2);
        prioritas4.setSelection(3);

        jmlKriteria1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (jmlKriteria1.getSelectedItemPosition()+3 == 4){
                    prioritas4.setVisibility(View.VISIBLE);
                    labelp4.setVisibility(View.VISIBLE);
                }else {
                    prioritas4.setVisibility(View.GONE);
                    labelp4.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        proses = (Button)rootview.findViewById(R.id.proses_dss);

        proses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int p1,p2,p3,p4, jk, jc;
                String sp1,sp2,sp3,sp4, sjk, sjc;
                p1 = prioritas1.getSelectedItemPosition()+1;
                p2 = prioritas2.getSelectedItemPosition()+1;
                p3 = prioritas3.getSelectedItemPosition()+1;
                p4 = prioritas4.getSelectedItemPosition()+1;
                jk = jmlKriteria1.getSelectedItemPosition()+3;
                jc = jmlCategory1.getSelectedItemPosition()+1;

                sp1 = String.valueOf(p1);
                sp2 = String.valueOf(p2);
                sp3 = String.valueOf(p3);
                sp4 = String.valueOf(p4);
                sjk = String.valueOf(jk);
                sjc = String.valueOf(jc);

                Intent dssProses = new Intent(getContext(), DSSActivity.class);
                dssProses.putExtra("category", sjc); //ganti ama variable lu
                dssProses.putExtra("jumlahkriteria", sjk);
                dssProses.putExtra("p1", sp1);
                dssProses.putExtra("p2", sp2);
                dssProses.putExtra("p3", sp3);
                dssProses.putExtra("p4", sp4);

                if (jk==3){
                    if (p1 == p2 || p1 == p3 || p2 == p3){
                        Toast.makeText(getContext(), "Priority Should Be Different", Toast.LENGTH_SHORT).show();
                    }else {
                        startActivity(dssProses);
                    }
                }else {
                    if (p1 == p2 || p1 == p3 || p1 == p4 || p2 == p3 || p2 == p4 || p3 == p4){
                        Toast.makeText(getContext(), "Priority Should Be Different", Toast.LENGTH_SHORT).show();
                    }else {
                        startActivity(dssProses);
                    }
                }
            }
        });
        return rootview;
    }
}
