package semulajadi.v011;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
/**
 * Created by Ryan on 03/04/2018.
 */

public class PWordAdapter extends ArrayAdapter<PWord> {

    String PimgUrl;
    public PWordAdapter(Activity context, ArrayList<PWord> pwords) {
        super(context, 0, pwords);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View plistItemView = convertView;
        if (plistItemView == null) {
            plistItemView = LayoutInflater.from(getContext()).inflate(R.layout.package_list_item, parent, false);
        }

        PWord pcurrentWord = getItem(position);

        TextView packageTextView = (TextView) plistItemView.findViewById(R.id.name_package);
        packageTextView.setText(pcurrentWord.getNamePackage());

        TextView pricePTextView = (TextView) plistItemView.findViewById(R.id.price_package);
        String price = pcurrentWord.getPricePackage()+"";
        pricePTextView.setText("RM "+price);

//        ImageView imageView = (ImageView) plistItemView.findViewById(R.id.image_package);
//        String photoUrl = "http://appsemulajadi.com/images/pg/"+pcurrentWord.getPhotoPackage();
//        Picasso.with(getContext()).load(PimgUrl).placeholder(R.drawable.poto2).into(imageView);

        ImageView imageView = (ImageView) plistItemView.findViewById(R.id.image_package);
        String photoUrl = "http://appsemulajadi.com/images/pg/"+pcurrentWord.getPhotoPackage();
        Picasso.with(getContext()).load(photoUrl).placeholder(R.drawable.poto2).into(imageView);
        Log.v("Adapter : ", pcurrentWord.getPricePackage()+"");

//        Log.v("Adapter : ", pcurrentWord.getPricePackage()+"");
//
//        Button DetailPackage = (Button)plistItemView.findViewById(R.id.dtl_pck);
//        DetailPackage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                PWord pcurrentWord = getItem(position);
//
//                Intent intent = null;
//                intent = new Intent(getContext(), PackageContent.class);
//
//                intent.putExtra("package_id", pcurrentWord.getIdPackage());
//                intent.putExtra("package_name", pcurrentWord.getNamePackage());
//                intent.putExtra("package_photo", pcurrentWord.getPhotoPackage());
//                intent.putExtra("package_price", pcurrentWord.getPricePackage());
//                intent.putExtra("package_detail", pcurrentWord.getDetailPackage());
//
//                getContext().startActivity(intent);
//            }
//        });

        return plistItemView;
    }
}

