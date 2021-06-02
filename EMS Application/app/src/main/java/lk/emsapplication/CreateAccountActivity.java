package lk.emsapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import lk.emsapplication.model.Employee;
import lk.emsapplication.model.Salary;
import lk.emsapplication.response.EmployeeManagementResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreateAccountActivity extends AppCompatActivity {


    EditText edtTxtPhoneNumber, edtTxtFirstName, edtTxtLastName, edtTxtAddress;
    DatePicker joinDate;
    JsonPlaceHolderApi jsonPlaceHolderApi;
    Button btnCreateAccount;
    ImageView createAccountBack;
    Dialog dialog;
    Button btnOk;
    TextView txtMessage, txtError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_create_account);

        dialog = new Dialog(CreateAccountActivity.this);
        dialog.setContentView(R.layout.costom_dialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        initViews();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.43.248:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        edtTxtPhoneNumber.addTextChangedListener(createAccountTextWatcher);
        edtTxtAddress.addTextChangedListener(createAccountTextWatcher);
        edtTxtFirstName.addTextChangedListener(createAccountTextWatcher);
        edtTxtLastName.addTextChangedListener(createAccountTextWatcher);


        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtTxtPhoneNumber.setText("");
                edtTxtAddress.setText("");
                edtTxtFirstName.setText("");
                edtTxtLastName.setText("");
                dialog.dismiss();
            }
        });
        createAccountBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateAccountActivity.this, DashboardActivity.class);
                startActivity(intent);
            }
        });



    }

    private void initViews() {
        joinDate = findViewById(R.id.joinDate);
        btnCreateAccount = findViewById(R.id.btnCreateAccount);
        createAccountBack = findViewById(R.id.btnCreateAccountBack);
        edtTxtPhoneNumber = findViewById(R.id.edtTxtPhoneNumber);
        edtTxtFirstName = findViewById(R.id.edtTxtFirstName);
        edtTxtLastName = findViewById(R.id.edtTxtLastName);
        edtTxtAddress = findViewById(R.id.edtTxtAddress);
        btnOk = dialog.findViewById(R.id.btnOk);
        txtMessage = dialog.findViewById(R.id.txtMessage);
        txtError = dialog.findViewById(R.id.txtError);
    }

    private void createAccount() {

        String phoneNumber = edtTxtPhoneNumber.getText().toString();
        txtError.setText("");
        txtMessage.setText("");
        if (phoneNumber.substring(0, 1).equals("0")) {
            txtError.setText("Please Enter the Phone Number Without '0' At Beginning!\n\n");
            dialog.show();
        } else if (phoneNumber.length()!=9) {
            txtError.setText("Phone Number is Incorrect!\n\n");
            dialog.show();
        } else {

            Employee e = new Employee(Integer.parseInt(edtTxtPhoneNumber.getText().toString()),
                    edtTxtAddress.getText().toString(), edtTxtFirstName.getText().toString(), edtTxtLastName.getText().toString()
                    , dateToString(joinDate));

            Call<EmployeeManagementResponse> call = jsonPlaceHolderApi.createAccount(e);
            call.enqueue(new Callback<EmployeeManagementResponse>() {
                @Override
                public void onResponse(Call<EmployeeManagementResponse> call, Response<EmployeeManagementResponse> response) {
                    if (!response.isSuccessful()) {
                        txtError.setText("CODE : " + response.code());
                        dialog.show();
                        return;
                    }

                    EmployeeManagementResponse AccountResponse = response.body();
                    String content = "";
                    content += AccountResponse.getMessage() + "\n\n";
                    String name = AccountResponse.getEmployee().getFirstname() + " " + AccountResponse.getEmployee().getLastname();
                    content += "Name : " + name;
                    content += "\nPhone Number : " + AccountResponse.getEmployee().getPhonenumber();
                    content += "\nJoin Date : " + AccountResponse.getEmployee().getJoindate();
                    content += "\nSalary Rs : " + AccountResponse.getEmployee().getSalary().getTotalsalary() + "\n\n";

                    txtMessage.setText(content);
                    dialog.show();
                }

                @Override
                public void onFailure(Call<EmployeeManagementResponse> call, Throwable t) {
                    txtError.setText(t.getMessage());
                    dialog.show();
                }
            });
        }

    }

    private String dateToString(DatePicker joindate) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(joindate.getYear(), joindate.getMonth(), joindate.getDayOfMonth());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(calendar.getTime());
        return date;
    }

    private TextWatcher createAccountTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String phoneNumberInput = edtTxtPhoneNumber.getText().toString();
            String addressInput = edtTxtAddress.getText().toString();
            String firstNameInput = edtTxtFirstName.getText().toString();
            String lastNameInput = edtTxtLastName.getText().toString();

            btnCreateAccount.setEnabled(!phoneNumberInput.isEmpty() &&
                    !addressInput.isEmpty() &&
                    !firstNameInput.isEmpty() &&
                    !lastNameInput.isEmpty()
            );

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}