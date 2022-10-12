package com.example.rides.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rides.MainActivity;
import com.example.rides.R;
import com.example.rides.common.Constants;
import com.example.rides.fragment.VehicleDetailsFragment;
import com.example.rides.responseModels.RandomVehicleResponse;
import java.util.ArrayList;

public class VehicleListAdapter extends RecyclerView.Adapter<VehicleListAdapter.ViewHolder> {

    private final Activity context;
    private LayoutInflater inflater;
    private ArrayList<RandomVehicleResponse> data = new ArrayList<>();

    public VehicleListAdapter(Activity context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.item_video_list, parent, false);
        return new ViewHolder(v);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final RandomVehicleResponse record = data.get(position);
        holder.txtVin.setText(record.getVin());
        holder.txtMake_model.setText(record.getMakeAndModel());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(Constants.VIN, record.getVin());
                bundle.putString(Constants.MAKEANDMODEL, record.getMakeAndModel());
                bundle.putString(Constants.COLOR, record.getColor());
                bundle.putString(Constants.CARTYPE, record.getCarType());
                bundle.putInt(Constants.KILOMETRAGE, record.getKilometrage());
                replaceFragment(new VehicleDetailsFragment(),bundle);
            }
        });
    }


    public void replaceFragment(Fragment fragment, Bundle bundle) {

        if (bundle != null)
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = ((MainActivity)context).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment oldFragment = fragmentManager.findFragmentByTag(fragment.getClass().getName());
        //if oldFragment already exits in fragmentManager use it
        if (oldFragment != null) {
            fragment = oldFragment;
        }
        fragmentTransaction.add(R.id.frameContainer, fragment, fragment.getClass().getName());
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.addToBackStack(null).commit();
    }

    public void remove(int position) {
        data.remove(position);
        notifyDataSetChanged();
    }

    public void clear() {
        data.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public boolean checkSize() {
        return data.size() > 0;
    }

    public void addAll(ArrayList<RandomVehicleResponse> lines) {
        data.clear();
        data.addAll(lines);
        notifyDataSetChanged();
    }

    public void add(RandomVehicleResponse lines) {
        data.add(lines);
        notifyDataSetChanged();
    }

    public ArrayList<RandomVehicleResponse> getAll() {
        return data;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtMake_model,txtVin;

        ViewHolder(View view) {
            super(view);
            txtMake_model = view.findViewById(R.id.txtMake_model);
            txtVin = view.findViewById(R.id.txtVin);
        }
    }
}
