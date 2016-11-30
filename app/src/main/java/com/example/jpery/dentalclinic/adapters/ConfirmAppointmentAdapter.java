package com.example.jpery.dentalclinic.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jpery.dentalclinic.R;
import com.example.jpery.dentalclinic.model.Arrangement;
import com.example.jpery.dentalclinic.utils.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ConfirmAppointmentAdapter extends RecyclerView.Adapter<ConfirmAppointmentAdapter.ViewHolder> {
    private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(Constants.DATE_FORMAT_STRING, Locale.US);
    private List<Arrangement> list = new ArrayList<>();

    public interface OnItemClickListener {
        void onItemClick(Arrangement item);
    }

    private final OnItemClickListener listener;

    public ConfirmAppointmentAdapter(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.confirm_arrangement,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(list.get(position),listener);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void add(Arrangement item) {
        list.add(item);
        notifyDataSetChanged();
    }

    public void clear(){
        list.clear();
        notifyDataSetChanged();
    }

    public Object getItem(int pos) {
        return list.get(pos);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView dateView;

        private ViewHolder(View itemView) {
            super(itemView);
            dateView = (TextView) itemView.findViewById(R.id.titleView);
        }

        private void bind(final Arrangement toDoItem, final OnItemClickListener listener) {
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
