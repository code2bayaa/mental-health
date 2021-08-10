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

import static android.text.Html.fromHtml;


public class CustomAdapter extends ArrayList<patientsData> implements android.widget.ListAdapter {
    ArrayList<patientsData> data;
    Context context;
    public CustomAdapter(int a, Context context, ArrayList<patientsData> data) {
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
        patientsData subjectData = data.get(position);

        ViewHolder holder = null;

        if(convertView == null) {

            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.patients_structure, null);
            convertView.setOnClickListener(new View.OnClickListener() {
               
                public void onClick(View v) {
                }
            });


            holder = new ViewHolder();

             holder.title = (TextView)convertView.findViewById(R.id.patient_name);
             holder.age = (TextView)convertView.findViewById(R.id.patient_age);
             holder.email = (TextView)convertView.findViewById(R.id.patient_email);
             holder.date = (TextView)convertView.findViewById(R.id.patient_date);
             holder.telephone = (TextView)convertView.findViewById(R.id.patient_telephone);
             holder.height = (TextView)convertView.findViewById(R.id.patient_height);
             holder.weight = (TextView)convertView.findViewById(R.id.patient_weight);
             holder.symptoms = (TextView)convertView.findViewById(R.id.patient_symptoms);
             holder.image = (ImageView)convertView.findViewById(R.id.patient_Img);
             holder.treatP = (Button)convertView.findViewById(R.id.treat_button);
            convertView.setTag(holder);
            holder.treatP.setTag(position);
            holder.treatP.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View view) {
                                                 String patientE = subjectData.getEmail();
                                                 String recordE = subjectData.getUniqueRecord();

                                                 SharedPreferences pref = context.getSharedPreferences("patient", Context.MODE_PRIVATE);
                                                 SharedPreferences.Editor edit = pref.edit();
                                                 edit.putString("patient", patientE);
                                                 edit.putString("record", recordE);
                                                 edit.commit();

                                                 Toast.makeText(context,"Please wait...",Toast.LENGTH_SHORT).show();

                                                 context.startActivity(new Intent(context, patientsRecords.class));                                             }
                                         });
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.title.setText(subjectData.getName());
        holder.age.setText(""+subjectData.getAge());
        holder.email.setText(subjectData.getEmail());
        holder.telephone.setText(subjectData.getTelephone());
        holder.height.setText(""+subjectData.getHeight());
        holder.weight.setText(""+subjectData.getWeight());
        holder.date.setText(subjectData.getDate()+""+getCount());

        String[] symptomsArr = subjectData.getSymptoms().split("^");
        String symptomsAll = "";
        for(String symptom: symptomsArr){
            symptomsAll += fromHtml(symptom + "<br>");
        }

        holder.symptoms.setText(symptomsAll);

        Picasso.with(context)
                .load("http://172.16.3.90/mentalImgs/" + subjectData.getImage())
                .into(holder.image);

        return convertView;
    }

    static class ViewHolder {
        TextView title;
        TextView age;
        TextView email;
        TextView date;
        TextView telephone;
        TextView height;
        TextView weight;
        TextView symptoms;
        ImageView image;
        Button treatP;
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
