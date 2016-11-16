package com.example.jpery.dentalclinic.adapters;

import android.content.Context;
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
    private static Context mContext;
    public interface OnItemClickListener {
        void onItemClick(Arrangement item);
    }

    private final OnItemClickListener listener;
    public ArrangementAdapter(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        mContext= parent.getContext();
        View v = LayoutInflater.from(mContext).inflate(R.layout.arrangement,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(ArrangementsController.getInstance().getList().get(position),listener);

    }

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

        private void bind(final Arrangement item, final OnItemClickListener listener) {
            String[] title_array = mContext.getResources().getStringArray(R.array.interventions_array);
            title.setText(title_array[item.getKindOfIntervention()-1]);
            dateView.setText(DATE_FORMAT.format(item.getDate()));
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }
}
