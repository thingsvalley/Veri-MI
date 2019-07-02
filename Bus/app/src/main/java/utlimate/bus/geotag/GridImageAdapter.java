package utlimate.bus.geotag;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.ArrayList;

import utlimate.bus.R;

/**
 * Created by Aisha on 3/19/2018.
 */

public class GridImageAdapter extends BaseAdapter {
    Context context1;
    ArrayList<GeotaggingDataModel> arrayList = new ArrayList<>();

    LayoutInflater inflter;

    public GridImageAdapter(Context context, ArrayList<GeotaggingDataModel> arrayList) {
        context1 = context;
        this.arrayList = arrayList;

        inflter = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private static class ViewHolder {
        ImageView imageView;
        ImageButton cancelBtn;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View rowView = view;
        if (rowView == null) {

            LayoutInflater inflater = (LayoutInflater) context1.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.geotag_list_layout, null);
        }
        ViewHolder holder = new ViewHolder();
        holder.imageView = (ImageView) rowView.findViewById(R.id.gridImages);
        holder.cancelBtn = (ImageButton) rowView.findViewById(R.id.cancelBtn);

        holder.imageView.setImageBitmap(arrayList.get(i).getImage());

        holder.cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList.remove(i);
                notifyDataSetChanged();
            }
        });


        return rowView;
    }

}
