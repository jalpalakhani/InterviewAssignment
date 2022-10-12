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

public class VehicleDetailsFragment extends Fragment implements View.OnClickListener {
    private FragmentVehicledetailBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_vehicledetail,
//                container, false);
        this.binding = FragmentVehicledetailBinding.inflate(getLayoutInflater());
        setdata();
        binding.btnCarbonEmission.setOnClickListener(this);
        return binding.getRoot();
    }
    private void setdata() {
        binding.tvVin.setText(Html.fromHtml("<font><b>" + "Vin : " + "</b></font>" +getArguments().getString(Constants.VIN)));
        binding.tvMakeModel.setText(Html.fromHtml("<font><b>" + "Make and Model : " + "</b></font>" +getArguments().getString(Constants.MAKEANDMODEL)));
        binding.tvColor.setText(Html.fromHtml("<font><b>" + "Color : " + "</b></font>" +getArguments().getString(Constants.COLOR)));
        binding.tvCarType.setText(Html.fromHtml("<font><b>" + "Car_type : " + "</b></font>" +getArguments().getString(Constants.CARTYPE)));
    }
    @Override
    public void onClick(View view) {
        if (view != null) {
            switch (view.getId()) {
                case R.id.btn_carbonEmission: {
                    showBottomSheetDialog();
                    break;
                }
            }
        }
    }
    private void showBottomSheetDialog() {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity());
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog_layout);

        TextView tv_kilometrage = bottomSheetDialog.findViewById(R.id.tv_kilometrage);
        TextView tv_CarbonEmission = bottomSheetDialog.findViewById(R.id.tv_CarbonEmission);
        ImageView iv_cancel =bottomSheetDialog.findViewById(R.id.iv_cancel);
        int kilometrage =getArguments().getInt(Constants.KILOMETRAGE);
        tv_kilometrage.setText(Html.fromHtml("<font><b>" + "Kilometrage : " + "</b></font>" + kilometrage + " km"));
        tv_CarbonEmission.setText(Html.fromHtml("<font><b>" + "Calculated Carbon Emission : " + "</b></font>" + calculateCarbonEmission(kilometrage)+" units"));

        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });
        bottomSheetDialog.show();
    }

    private int calculateCarbonEmission(int km) {
        int carbonEmision = 0 ;
        double extraKM;
           if (km<=5000){
               carbonEmision = km;
           }else{
               extraKM = km - 5000;
               carbonEmision = (int) ((extraKM * 1.5) + 5000);
           }
       return carbonEmision;
    }
}