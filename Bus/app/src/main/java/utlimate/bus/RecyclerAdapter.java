package utlimate.bus;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import utlimate.bus.data_model.TaskDataModel;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    ArrayList<TaskDataModel> taskArrayList=new ArrayList<>();
    Context context;

    public RecyclerAdapter(Context context, ArrayList<TaskDataModel> taskArrayList) {

        this.context = context;
        this.taskArrayList=taskArrayList;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name,mims,phone,address;

        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            mims = itemView.findViewById(R.id.mimsNo);
            address = itemView.findViewById(R.id.address);
            phone = itemView.findViewById(R.id.phoneNo);
            cardView = itemView.findViewById(R.id.card);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final TaskDataModel taskDataModel=taskArrayList.get(position);
        holder.name.setText(taskDataModel.getName());
        holder.address.setText(taskDataModel.getAddress());
        holder.phone.setText(taskDataModel.getPhoneNumber());
        holder.mims.setText(taskDataModel.getMims());


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Seat)context).checkInternet(taskDataModel.getMims());
            }
        });


    }

    @Override
    public int getItemCount() {
        return taskArrayList.size();
    }
}
