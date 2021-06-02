package lk.emsapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import lk.emsapplication.model.Employee;
import lk.emsapplication.model.Salary;
import lk.emsapplication.response.EmployeeManagementResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetAllEmployees extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageView btnGetAllBack;
    JsonPlaceHolderApi jsonPlaceHolderApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_all_employees);

        initViews();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.43.11:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        getAllAccounts();

        btnGetAllBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GetAllEmployees.this,DashboardActivity.class);
                startActivity(intent);
            }
        });
    }

    public void getAllAccounts() {

        Call<List<Employee>> call = jsonPlaceHolderApi.getAllAccounts();
        call.enqueue(new Callback<List<Employee>>() {
            @Override
            public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {
                if(!response.isSuccessful()){
                    return;
                }
                EmployeeRecViewAdapter myAdapter = new EmployeeRecViewAdapter(GetAllEmployees.this,response.body());
                recyclerView.setAdapter(myAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(GetAllEmployees.this));
            }

            @Override
            public void onFailure(Call<List<Employee>> call, Throwable t) {
                return;
            }
        });

    }

    private void initViews(){
        recyclerView = findViewById(R.id.recyclerView);
        btnGetAllBack = findViewById(R.id.btnGetAllBack);
    }
}