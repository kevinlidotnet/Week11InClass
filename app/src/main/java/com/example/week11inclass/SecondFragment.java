package com.example.week11inclass;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.week11inclass.databinding.FragmentSecondBinding;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });

        //Get Employees from https://my-json-server.typicode.com/kevinlidotnet/jsondemo/db
        getEmployees();


    }

    private void getEmployees() {
        String url = "https://my-json-server.typicode.com/kevinlidotnet/jsondemo/db";

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        binding.textviewSecond.setText("Response is: " + response.substring(0,500));

                        // Deserialize JSON
                        Gson gson = new Gson();
                        ApiResponse apiResponse =  gson.fromJson(response,ApiResponse.class );

                        //Binding to the list view
                        ArrayList<String> employeeList = new ArrayList<>();
                        for (Employee employee: apiResponse.getEmployees()
                             ) {
                            employeeList.add(employee.toString());
                        }

                        ArrayAdapter<String> itemsAdapter =
                                new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,employeeList); //https://stackoverflow.com/questions/3663745/what-is-android-r-layout-simple-list-item-1
                        binding.listviewEmployees.setAdapter(itemsAdapter);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                binding.textviewSecond.setText("Request failed. Please try again.");
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}