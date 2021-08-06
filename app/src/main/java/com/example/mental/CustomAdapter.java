package com.example.mental;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class CustomAdapter implements android.widget.ListAdapter {
    ArrayList<String> data;
    Context context;
    public CustomAdapter(Context context, ArrayList<String> data) {
        this.data = data;
        this.context = context;
    }
    public boolean areAllItemsEnabled() {
        return false;
    }

    public boolean isEnabled(int position) {
        return true;
    }
   
    public void registerDataSetObserver(DataSetObserver observer) {
    }
   
    public void unregisterDataSetObserver(DataSetObserver observer) {
    }
   
    public int getCount() {
        return data.size();
    }
   
    public Object getItem(int position) {
        return position;
    }
   
    public long getItemId(int position) {
        return position;
    }
   
    @Override
    public boolean hasStableIds() {
        return false;
    }
   
    public View getView(int position, View convertView, ViewGroup parent) {
        String subjectData = data.get(position);
        if(convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.profile_structure, null);
            convertView.setOnClickListener(new View.OnClickListener() {
               
                public void onClick(View v) {
                }
            });
            TextView title = convertView.findViewById(R.id.profile_name);
            ImageView imag = convertView.findViewById(R.id.profile_Img);
            title.setText(subjectData);
            Picasso.with(context)
                    .load(subjectData)
                    .into(imag);
        }
        return convertView;
    }

    @NonNull
    @NotNull
   
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return null;
    }

   
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {

    }

   
    public int getItemViewType(int position) {
        return position;
    }
   
    public int getViewTypeCount() {
        return data.size();
    }
   
    public boolean isEmpty() {
        return false;
    }

    @Nullable
    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }
}
