package com.example.rides.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.rides.R;
import com.example.rides.common.Constants;
import com.example.rides.databinding.FragmentVehicledetailBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class VehicleDetailsFragment extends Fragment {
    private FragmentVehicledetailBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_vehicledetail,
//                container, false);
        this.binding = FragmentVehicledetailBinding.inflate(getLayoutInflater());
        setdata();
        return binding.getRoot();
    }
    private void setdata() {
        binding.tvVin.setText(Html.fromHtml("<font><b>" + "Vin : " + "</b></font>" +getArguments().getString(Constants.VIN)));
        binding.tvMakeModel.setText(Html.fromHtml("<font><b>" + "Make and Model : " + "</b></font>" +getArguments().getString(Constants.MAKEANDMODEL)));
        binding.tvColor.setText(Html.fromHtml("<font><b>" + "Color : " + "</b></font>" +getArguments().getString(Constants.COLOR)));
        binding.tvCarType.setText(Html.fromHtml("<font><b>" + "Car_type : " + "</b></font>" +getArguments().getString(Constants.CARTYPE)));
    }


}