package com.example.jpery.dentalclinic.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jpery.dentalclinic.utils.ArrangementsController;
import com.example.jpery.dentalclinic.utils.Constants;
import com.example.jpery.dentalclinic.R;
import com.example.jpery.dentalclinic.model.Arrangement;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class ArrangementAdapter extends RecyclerView.Adapter<ArrangementAdapter.ViewHolder> {
    private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(Constants.DATE_FORMAT_STRING, Locale.US);

    public interface OnItemClickListener {
        void onItemClick(Arrangement item);     //Type of the element to be returned
    }

    private final OnItemClickListener listener;

    // Provide a suitable constructor (depends on the kind of dataset)
    public ArrangementAdapter(OnItemClickListener listener) {
        this.listener = listener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.arrangement,parent,false);
        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(ArrangementsController.getInstance().getList().get(position),listener);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return ArrangementsController.getInstance().getList().size();
    }

    public void add(Arrangement item) {
        ArrangementsController.getInstance().getList().add(item);
        notifyDataSetChanged();
    }

    public void clear(){
        ArrangementsController.getInstance().getList().clear();
        notifyDataSetChanged();
    }

    public Object getItem(int pos) {
        return ArrangementsController.getInstance().getList().get(pos);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView dateView;

        private ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.titleView);
            dateView = (TextView) itemView.findViewById(R.id.dateView);
        }

        private void bind(final Arrangement toDoItem, final OnItemClickListener listener) {
            title.setText(toDoItem.getTitle());
            dateView.setText(DATE_FORMAT.format(toDoItem.getDate()));
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    listener.onItemClick(toDoItem);
                }
            });
        }
    }
}
