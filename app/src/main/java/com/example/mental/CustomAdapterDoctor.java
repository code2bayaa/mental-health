package com.example.mental;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class CustomAdapterDoctor extends ArrayList<doctorsData> implements android.widget.ListAdapter {
    ArrayList<doctorsData> data;
    Context context;
    public CustomAdapterDoctor(int a, Context context, ArrayList<doctorsData> data) {
        super(data);
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
        doctorsData subjectData = data.get(position);

        ViewHolder holder = null;

        if(convertView == null) {

            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.doctors_structure, null);
            convertView.setOnClickListener(new View.OnClickListener() {
               
                public void onClick(View v) {
                }
            });


            holder = new ViewHolder();

             holder.title = (TextView)convertView.findViewById(R.id.nameTxt_doctor);
             holder.age = (TextView)convertView.findViewById(R.id.ageTxt_doctor);
             holder.email = (TextView)convertView.findViewById(R.id.emailTxt_doctor);
             holder.biography = (TextView)convertView.findViewById(R.id.biographyTxt_doctor);
             holder.telephone = (TextView)convertView.findViewById(R.id.telephoneTxt_doctor);
             holder.certification = (TextView)convertView.findViewById(R.id.specialTxt_doctor);
             holder.work = (TextView)convertView.findViewById(R.id.workTxt_doctor);
             holder.address = (TextView)convertView.findViewById(R.id.homeTxt_doctor);
             holder.image = (ImageView)convertView.findViewById(R.id.passport_doctor);
             holder.consultB = (Button)convertView.findViewById(R.id.consult_button);
            convertView.setTag(holder);
            holder.consultB.setTag(position);
            holder.consultB.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View view) {
                                                 String patientE = subjectData.getEmail();

                                                 SharedPreferences pref = context.getSharedPreferences("doctor", Context.MODE_PRIVATE);
                                                 SharedPreferences.Editor edit = pref.edit();
                                                 edit.putString("doctor", patientE);
                                                 edit.commit();

                                                 Toast.makeText(context,"Please wait...",Toast.LENGTH_SHORT).show();

                                                 context.startActivity(new Intent(context, consultDoctor.class));                                             }
                                         });
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.title.setText(subjectData.getName());
        holder.age.setText(""+subjectData.getAge());
        holder.email.setText(subjectData.getEmail());
        holder.telephone.setText(subjectData.getTelephone());
        holder.address.setText(subjectData.getAddress());
        holder.work.setText(subjectData.getwAddress());
        holder.biography.setText(subjectData.getbiography());
        holder.certification.setText(subjectData.getcertification());

        Picasso.with(context)
                .load("http://172.16.3.90/mentalImgs/" + subjectData.getImage())
                .into(holder.image);

        return convertView;
    }

    static class ViewHolder {
        TextView title;
        TextView age;
        TextView email;
        TextView certification;
        TextView telephone;
        TextView address;
        TextView work;
        TextView biography;
        ImageView image;
        Button consultB;
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
