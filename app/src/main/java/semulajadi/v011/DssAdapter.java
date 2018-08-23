package semulajadi.v011;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Ryan on 18/04/2018.
 */

public class DssAdapter extends ArrayAdapter<Word> {

    public DssAdapter(Activity context, ArrayList<Word> words) {
        super(context,0,words);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View dsslistItemView = convertView;
        if (dsslistItemView == null) {
            dsslistItemView = LayoutInflater.from(getContext()).inflate(R.layout.dss_list_item, parent, false);
        }

        Word currentWord = getItem(position);

        TextView tourismTextView = (TextView) dsslistItemView.findViewById(R.id.name_tourism_dss);
        tourismTextView.setText(currentWord.getNameTourism());

        TextView priceTTextView = (TextView) dsslistItemView.findViewById(R.id.price_tourism_dss);
        String price = currentWord.getPriceTourism()+"";
        priceTTextView.setText("RM "+price);

        ImageView imageView = (ImageView) dsslistItemView.findViewById(R.id.image_tourism_dss);
        String photoUrl = "http://appsemulajadi.com/images/tourism/"+currentWord.getPhotoTourism();
        Picasso.with(getContext()).load(photoUrl).placeholder(R.drawable.poto2).into(imageView);
        Log.v("Adapter : ", currentWord.getPriceTourism()+"");

        return dsslistItemView;
    }
}
