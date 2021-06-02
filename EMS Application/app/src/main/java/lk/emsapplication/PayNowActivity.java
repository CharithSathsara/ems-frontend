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
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import lk.emsapplication.model.Employee;
import lk.emsapplication.model.Payment;
import lk.emsapplication.model.Salary;
import lk.emsapplication.response.EmployeeManagementResponse;
import lk.emsapplication.response.PaymentResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PayNowActivity extends AppCompatActivity {

    private DatePicker payDate;
    private EditText edtTxtAmount;
    private ImageView payNowBack;
    private Button btnPayNow;
    private Dialog dialog;
    private Button btnOk;
    private TextView txtMessage, txtError;

    private JsonPlaceHolderApi jsonPlaceHolderApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_now);

        dialog = new Dialog(PayNowActivity.this);
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
        edtTxtAmount.addTextChangedListener(payNowTextWatcher);

        btnPayNow.setText("PAY TO " + employee.getFirstname().toUpperCase());
        btnPayNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payNow(employee);
            }
        });
        payNowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PayNowActivity.this, GetSpecificEmployee.class);
                intent.putExtra("employee", employee);
                intent.putExtra("salary", salary);
                startActivity(intent);
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtTxtAmount.setText("");
                dialog.dismiss();
            }
        });

    }

    private void initViews() {
        payDate = findViewById(R.id.payDate);
        edtTxtAmount = findViewById(R.id.edtTxtAmount);
        btnPayNow = findViewById(R.id.btnPayNow);
        payNowBack = findViewById(R.id.btnPayNowBack);
        btnOk = dialog.findViewById(R.id.btnOk);
        txtMessage = dialog.findViewById(R.id.txtMessage);
        txtError = dialog.findViewById(R.id.txtError);
    }

    private void payNow(Employee employee) {

        txtError.setText("");
        txtMessage.setText("");
        Payment p = new Payment(dateToString(payDate), Double.parseDouble(edtTxtAmount.getText().toString()), employee);

        Call<PaymentResponse> call = jsonPlaceHolderApi.payNow(p);
        call.enqueue(new Callback<PaymentResponse>() {
            @Override
            public void onResponse(Call<PaymentResponse> call, Response<PaymentResponse> response) {
                if (!response.isSuccessful()) {
                    txtError.setText("CODE : " + response.code());
                    dialog.show();
                    return;
                }
                PaymentResponse paymentResponse = response.body();

                String content = "";
                if (paymentResponse.getPayment() == null) {
                    content += paymentResponse.getMessage() + "\n";
                    content += "\nName : " + employee.getFirstname() + " " + employee.getLastname();
                    txtError.setText(content);
                } else {
                    String name = paymentResponse.getPayment().getEmployee().getFirstname() + " " +
                            paymentResponse.getPayment().getEmployee().getLastname();
                    content += "\nName : " + name;
                    content += "\n\n" + paymentResponse.getMessage() + "\n\n";
                    txtMessage.setText(content);
                }
                dialog.show();
            }

            @Override
            public void onFailure(Call<PaymentResponse> call, Throwable t) {
                txtError.setText(t.getMessage());
                dialog.show();
            }
        });

    }

    private String dateToString(DatePicker payDate) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(payDate.getYear(), payDate.getMonth(), payDate.getDayOfMonth());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(calendar.getTime());
        return date;
    }

    private TextWatcher payNowTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String amount = edtTxtAmount.getText().toString();
            btnPayNow.setEnabled(!amount.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}