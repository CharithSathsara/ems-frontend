package lk.emsapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import lk.emsapplication.model.Attendance;
import lk.emsapplication.model.Employee;
import lk.emsapplication.model.Salary;
import lk.emsapplication.response.AttendanceResponse;
import lk.emsapplication.response.EmployeeManagementResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AttendanceActivity extends AppCompatActivity {

    private ImageView btnAttendanceBack;
    private EditText edtTxtDailyPay;
    private DatePicker attendanceDate;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private Button btnAttendanceMark;
    private Dialog dialog;
    private Button btnOk;
    private TextView txtMessage, txtError;

    private JsonPlaceHolderApi jsonPlaceHolderApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        dialog = new Dialog(AttendanceActivity.this);
        dialog.setContentView(R.layout.costom_dialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        initViews();
        Employee employee = getIntent().getParcelableExtra("employee");
        Salary salary = getIntent().getParcelableExtra("salary");
        employee.setSalary(salary);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.43.11:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        edtTxtDailyPay.addTextChangedListener(attendanceTextWatcher);

        btnAttendanceMark.setText("MARK " + employee.getFirstname().toUpperCase());
        btnAttendanceMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attendanceMark(employee);
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtTxtDailyPay.setText("");
                dialog.dismiss();
            }
        });
        btnAttendanceBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AttendanceActivity.this, GetSpecificEmployee.class);
                intent.putExtra("employee", employee);
                intent.putExtra("salary", salary);
                startActivity(intent);
            }
        });


    }

    public void checkButton(View v) {
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        Toast.makeText(this, radioButton.getText() + " Selected", Toast.LENGTH_SHORT).show();
    }

    private void initViews() {
        btnAttendanceBack = findViewById(R.id.btnAttendanceBack);
        attendanceDate = findViewById(R.id.attendanceDate);
        radioGroup = findViewById(R.id.radioGroup);
        btnAttendanceMark = findViewById(R.id.btnAttendanceMark);
        attendanceDate = findViewById(R.id.attendanceDate);
        edtTxtDailyPay = findViewById(R.id.edtTxtDailyPay);
        btnOk = dialog.findViewById(R.id.btnOk);
        txtMessage = dialog.findViewById(R.id.txtMessage);
        txtError = dialog.findViewById(R.id.txtError);
    }

    private void attendanceMark(Employee employee) {

        txtError.setText("");
        txtMessage.setText("");
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        Attendance attendance = new Attendance(dateToString(attendanceDate), Double.parseDouble(edtTxtDailyPay.getText().toString())
                , radioButton.getText().toString(), employee);

        Call<AttendanceResponse> call = jsonPlaceHolderApi.attendanceMark(attendance);
        call.enqueue(new Callback<AttendanceResponse>() {
            @Override
            public void onResponse(Call<AttendanceResponse> call, Response<AttendanceResponse> response) {
                if (!response.isSuccessful()) {
                    txtError.setText("CODE : " + response.code());
                    dialog.show();
                    return;
                }
                AttendanceResponse attendanceResponse = response.body();
                if (attendanceResponse.getCode().equals("-1")) {
                    String error = "";
                    error += attendanceResponse.getMessage() + "\n\n";
                    String name = employee.getFirstname() + " " + employee.getLastname();
                    error += "\nName : " + name;
                    error += "\nDate : " + dateToString(attendanceDate)+ "\n";
                    txtError.setText(error);
                } else {
                    String content = "";
                    content += attendanceResponse.getMessage() + "\n\n";
                    String name = attendanceResponse.getAttendance().getEmployee().getFirstname() + " "
                            + attendanceResponse.getAttendance().getEmployee().getLastname();
                    content += "\nName : " + name;
                    content += "\nDate : " + attendanceResponse.getAttendance().getDate() + "\n";
                    content += "\nSalary Rs. " + attendanceResponse.getAttendance().getEmployee().getSalary().getTotalsalary();
                    txtMessage.setText(content);
                }
                dialog.show();
            }

            @Override
            public void onFailure(Call<AttendanceResponse> call, Throwable t) {
                txtError.setText(t.getMessage());
                dialog.show();
            }
        });

    }

    private String dateToString(DatePicker joindate) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(joindate.getYear(), joindate.getMonth(), joindate.getDayOfMonth());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(calendar.getTime());
        return date;
    }

    private TextWatcher attendanceTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            String dailyPay = edtTxtDailyPay.getText().toString();
            btnAttendanceMark.setEnabled(!dailyPay.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}