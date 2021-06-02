package lk.emsapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import lk.emsapplication.model.Attendance;
import lk.emsapplication.model.Employee;
import lk.emsapplication.model.Salary;
import lk.emsapplication.response.AttendanceGetResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetSpecificEmployee extends AppCompatActivity {

    TextView txtAccountName, txtEmpName, txtEmpSalary, txtEmpPhoneNumber;
    Button btnMarkAttendance, btnPayNow, btnGetDetails;
    ImageView btnGetSpecificEmployeeBack;
    JsonPlaceHolderApi jsonPlaceHolderApi;
    Dialog dialog;
    Button btnOk;
    TextView txtMessage, txtError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_specific_employee);

        dialog = new Dialog(GetSpecificEmployee.this);
        dialog.setContentView(R.layout.costom_dialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        initViews();
        Employee employee = getIntent().getParcelableExtra("employee");
        Salary salary = getIntent().getParcelableExtra("salary");
        txtAccountName.setText(employee.getFirstname() + "'s Account");
        txtEmpName.setText(employee.getFirstname() + " " + employee.getLastname());
        txtEmpSalary.setText("Rs. " + salary.getTotalsalary());
        txtEmpPhoneNumber.setText(employee.getPhonenumber().toString());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.43.11:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);


        btnGetSpecificEmployeeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GetSpecificEmployee.this, GetAllEmployees.class);
                startActivity(intent);
            }
        });

        btnMarkAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(GetSpecificEmployee.this, "GO TO MARK ATTENDANCE", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(GetSpecificEmployee.this, AttendanceActivity.class);
                intent.putExtra("employee", employee);
                intent.putExtra("salary", salary);
                startActivity(intent);
            }
        });
        btnPayNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(GetSpecificEmployee.this, "GO TO PAY NOW", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(GetSpecificEmployee.this, PayNowActivity.class);
                intent.putExtra("employee", employee);
                intent.putExtra("salary", salary);
                startActivity(intent);
            }
        });
        btnGetDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDetails(employee);
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    private void initViews() {
        txtAccountName = findViewById(R.id.txtAccountName);
        txtEmpName = findViewById(R.id.txtEmpName);
        txtEmpSalary = findViewById(R.id.txtEmpSalary);
        txtEmpPhoneNumber = findViewById(R.id.txtEmpPhoneNumber);
        btnMarkAttendance = findViewById(R.id.btnMarkAttendance);
        btnPayNow = findViewById(R.id.btnPayNow);
        btnGetDetails = findViewById(R.id.btnGetDetails);
        btnGetSpecificEmployeeBack = findViewById(R.id.btnGetSpecificEmployeeBack);
        btnOk = dialog.findViewById(R.id.btnOk);
        txtMessage = dialog.findViewById(R.id.txtMessage);
        txtError = dialog.findViewById(R.id.txtError);

    }

    private void getDetails(Employee employee) {

        txtError.setText("");
        txtMessage.setText("");

        Call<AttendanceGetResponse> call = jsonPlaceHolderApi.getDetails(employee);
        call.enqueue(new Callback<AttendanceGetResponse>() {
            @Override
            public void onResponse(Call<AttendanceGetResponse> call, Response<AttendanceGetResponse> response) {
                if (!response.isSuccessful()) {
                    txtError.setText("CODE : " + response.code());
                    dialog.show();
                    return;
                }

                AttendanceGetResponse attendanceGetResponse = response.body();

                if (attendanceGetResponse.getCode().equals("-1")) {
                    txtError.setText("\n" + attendanceGetResponse.getMessage());
                } else {

                    String content = "";
                    content += attendanceGetResponse.getMessage() + "\n\n";
                    String name = employee.getFirstname() + " " + employee.getLastname();
                    content += "Name : " + name;
                    content += "\n\nATTENDANCES OF LAST MONTH : \n";

                    for (Attendance attendance : attendanceGetResponse.getAttendances()) {
                        content += "\t\t\t" + attendance.getDate() + "\n";
                    }
                    txtMessage.setText(content);
                }
                dialog.show();
            }

            @Override
            public void onFailure(Call<AttendanceGetResponse> call, Throwable t) {
                txtError.setText(t.getMessage());
                dialog.show();
            }
        });
    }

}
