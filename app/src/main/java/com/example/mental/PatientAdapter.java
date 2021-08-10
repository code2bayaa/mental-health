package com.example.mental;

import android.content.Context;
import android.database.DataSetObserver;
import android.text.Html;
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


public class PatientAdapter implements android.widget.ListAdapter {
    ArrayList<patientData> data;
    Context context;
    public PatientAdapter(Context context, ArrayList<patientData> data) {
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
        patientData subjectData = data.get(position);
        if(convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.patient_structure, null);
            convertView.setOnClickListener(new View.OnClickListener() {
               
                public void onClick(View v) {
                }
            });
            TextView title = convertView.findViewById(R.id.doctor_name);
            TextView medicine = convertView.findViewById(R.id.patient_medicine);
            TextView analysis = convertView.findViewById(R.id.patient_analysis);
            TextView date = convertView.findViewById(R.id.record_date);
            TextView sickness = convertView.findViewById(R.id.patient_sick);
            TextView height = convertView.findViewById(R.id.patient_height);
            TextView weight = convertView.findViewById(R.id.patient_weight);
            TextView symptoms = convertView.findViewById(R.id.patient_symptoms);
            ImageView image = convertView.findViewById(R.id.doctor_Img);

            title.setText(subjectData.getDoctorName());
            date.setText(subjectData.getDate());
            medicine.setText(subjectData.getMedication());
            analysis.setText(subjectData.getAnalysis());
            sickness.setText(subjectData.getSickness());
            height.setText(""+subjectData.getHeight());
            weight.setText(""+subjectData.getWeight());

            String[] symptomsArr = subjectData.getSymptoms().split(",");
            String symptomsAll = "";
            for(String symptom: symptomsArr){
                symptomsAll += Html.fromHtml(symptom + "<br>");
            }

            symptoms.setText(symptomsAll);

            Picasso.with(context)
                    .load("http://172.16.3.90/mentalImgs/" + subjectData.getDoctorImage())
                    .into(image);
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
