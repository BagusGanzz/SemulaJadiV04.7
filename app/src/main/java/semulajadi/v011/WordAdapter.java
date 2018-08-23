package semulajadi.v011;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.Activity;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
/**
 * Created by Ryan on 18/11/2017.
 */

public class WordAdapter extends ArrayAdapter<Word>{

    public WordAdapter(Activity context, ArrayList<Word> words) {
        super(context, 0, words);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        Word currentWord = getItem(position);

        TextView tourismTextView = (TextView) listItemView.findViewById(R.id.name_tourism);
        tourismTextView.setText(currentWord.getNameTourism());

        TextView priceTTextView = (TextView) listItemView.findViewById(R.id.price_tourism);
        String price = currentWord.getPriceTourism()+"";
        priceTTextView.setText("RM "+price);

        ImageView imageView = (ImageView) listItemView.findViewById(R.id.image_tourism);
        String photoUrl = "http://appsemulajadi.com/images/tourism/"+currentWord.getPhotoTourism();
        Picasso.with(getContext()).load(photoUrl).placeholder(R.drawable.poto2).into(imageView);
        Log.v("Adapter : ", currentWord.getPriceTourism()+"");

        return listItemView;
    }
}
