package com.example.rides.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.rides.R;
import com.example.rides.common.LogUtils;
import com.example.rides.RetrofitApi.APIClient;
import com.example.rides.RetrofitApi.GsonUtils;
import com.example.rides.RetrofitApi.RequestAPI;
import com.example.rides.common.Utils;
import com.example.rides.adapter.VehicleListAdapter;
import com.example.rides.databinding.FragmentVehicleBinding;
import com.example.rides.responseModels.RandomVehicleResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class VehicleFragment extends Fragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private FragmentVehicleBinding binding;
    private Context context;
    public RequestAPI requestAPI;
    public GsonUtils gsonUtils;
    public LogUtils log;
    private Toast toast;
    private AlertDialog dialog;
    private VehicleListAdapter adapter;
    int noOfRecords = 1;

    public static VehicleFragment newInstance() {
        return new VehicleFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_vehicle, container, false);
        this.binding = FragmentVehicleBinding.inflate(getLayoutInflater());
        context = getActivity();
        toast = Toast.makeText(getActivity(), "", Toast.LENGTH_LONG);
        gsonUtils = GsonUtils.getInstance();
        log = new LogUtils(getActivity().getClass());
        binding.btnGetData.setOnClickListener(this);
        binding.swipeContainer.setOnRefreshListener(this);
        binding.swipeContainer.setColorSchemeResources(R.color.color_car,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        return binding.getRoot();
    }

    @Override
    public void onClick(View view) {
        if (view != null) {
            switch (view.getId()) {
                case R.id.btn_getData: {
                     if(getActivity().getCurrentFocus()!=null) {
                         InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                         inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                     }
                     binding.swipeContainer.post(new Runnable() {
                        @Override
                        public void run() {
                            // Fetching data from server
                            if (validateData()) {
                                callVehicleDataApi(noOfRecords);
                            }
                        }
                    });

                    break;
                }
            }
        }
    }


    private void callVehicleDataApi(int noOfRecords) {
        if (Utils.checkConnection(context)) {
            showDialog(context);
            binding.swipeContainer.setRefreshing(true);
            requestAPI = APIClient.getClient().create(RequestAPI.class);
            Call<ArrayList<RandomVehicleResponse>> call = requestAPI.getRandomVehicleRequest(noOfRecords);
            call.enqueue(new Callback<ArrayList<RandomVehicleResponse>>() {
                @Override
                public void onResponse(Call<ArrayList<RandomVehicleResponse>> call, Response<ArrayList<RandomVehicleResponse>> response) {
                    //do Success functionality
                    if (response.isSuccessful()) {
                        dismissDialog(context);
                        binding.tvEnterNumber.setText("");
                        log.LOGI(" Response => " + response.body());
                        ArrayList<RandomVehicleResponse> records = response.body();
                        log.LOGI(" Records => " + records.size());
                        if (records != null && records.size() > 0) {
                            Collections.sort(records, new Comparator<RandomVehicleResponse>() {
                                @Override
                                public int compare(RandomVehicleResponse lhs, RandomVehicleResponse rhs) {
                                    return lhs.getVin().compareTo(rhs.getVin());
                                }
                            });
                            generateDataList(records);
                        }
                    } else {
                        binding.swipeContainer.setRefreshing(false);
                        showToast(response.message(), true);
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<RandomVehicleResponse>> call, Throwable t) {
                    dismissDialog(context);
                    log.LOGE(t.getMessage(), t);
                    binding.swipeContainer.setRefreshing(false);
                    showToast(context.getResources().getString(R.string.something_went_wrong), true);
                }
            });
        } else {
            binding.swipeContainer.setRefreshing(false);
            showNoInternetDialog();
        }
    }

    private void generateDataList(ArrayList<RandomVehicleResponse> vehicleResponseList) {
        adapter = new VehicleListAdapter(getActivity());
        adapter.addAll(vehicleResponseList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        binding.rvVehicleList.setLayoutManager(layoutManager);
        binding.rvVehicleList.setAdapter(adapter);
        // Stopping swipe refresh
        binding.swipeContainer.setRefreshing(false);
    }

    @Override
    public void onRefresh() {

        // Fetching data from server
        callVehicleDataApi(noOfRecords);
    }

    public void showDialog(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_progress, null);
        dialog = new AlertDialog.Builder(context)
                .setView(view)
                .create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCancelable(false);
        dialog.show();
    }

    public void dismissDialog(Context context) {
        if (context != null) {
            if (!getActivity().isFinishing()) {
                if (dialog != null) {
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                        dialog = null;
                    }
                }
            }
        }
    }

    private Boolean validateData() {
        if (this.binding.tvEnterNumber.getText().toString().isEmpty() || binding.tvEnterNumber.getText().equals("")) {
            showToast(getResources().getString(R.string.empty_error_msg),true);
            return false;
        }
        noOfRecords = Integer.parseInt(binding.tvEnterNumber.getText().toString());
        if (noOfRecords < 1 || noOfRecords > 100) {
           showToast(getResources().getString(R.string.value_error_msg),true);
           return false;
        }
        return true;
    }

    public void showNoInternetDialog() {
        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle(getString(R.string.title_no_internet))
                .setMessage(getString(R.string.msg_no_internet))
                .setCancelable(false)
                .setPositiveButton(getString(android.R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
//                        finishAffinity();
                    }
                }).create();
        dialog.show();
    }

    public void showToast(final String text, final boolean isShort) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                toast.setText(text);
                toast.setDuration(isShort ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }
}