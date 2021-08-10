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


public class CasesAdapter implements android.widget.ListAdapter {
    ArrayList<patientsData> data;
    Context context;
    public CasesAdapter(Context context, ArrayList<patientsData> data) {
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
        if(convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.patients_structure, null);
            convertView.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                }
            });
            TextView title = convertView.findViewById(R.id.patient_cases_name);
            TextView age = convertView.findViewById(R.id.patient_cases_age);
            TextView email = convertView.findViewById(R.id.patient_cases_email);
            TextView date = convertView.findViewById(R.id.patient_cases_date);
            TextView telephone = convertView.findViewById(R.id.patient_cases_telephone);
            TextView height = convertView.findViewById(R.id.patient_cases_height);
            TextView weight = convertView.findViewById(R.id.patient_cases_weight);
            TextView symptoms = convertView.findViewById(R.id.patient_cases_symptoms);
            TextView sickness = convertView.findViewById(R.id.patient_cases_sickness);
            ImageView image = convertView.findViewById(R.id.patient_cases_Img);

            title.setText(subjectData.getName());
            age.setText(subjectData.getAge());
            email.setText(subjectData.getEmail());
            telephone.setText(subjectData.getTelephone());
            height.setText((int) subjectData.getHeight());
            weight.setText((int) subjectData.getWeight());
            date.setText(subjectData.getDate());
            sickness.setText(subjectData.getUniqueRecord());

            String[] symptomsArr = subjectData.getSymptoms().split(",");
            String symptomsAll = "";
            for(String symptom: symptomsArr){
                symptomsAll += Html.fromHtml(symptom + "<br>");
            }

            symptoms.setText(Html.fromHtml("<b>SYMPTOMS</b><br>")+""+symptomsAll);

            Picasso.with(context)
                    .load("http://192.168.0.22/mentalImgs/" + subjectData.getImage())
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

