package lk.emsapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import lk.emsapplication.model.Attendance;
import lk.emsapplication.model.Employee;
import lk.emsapplication.model.Payment;
import lk.emsapplication.response.DetailsViaDateResponse;
import lk.emsapplication.response.EmployeeManagementResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailsByDateActivity extends AppCompatActivity {

    DatePicker detailsDate;
    Button btnGetDetails;
    JsonPlaceHolderApi jsonPlaceHolderApi;
    Dialog dialog;
    Button btnOk;
    ImageView detailsByDateBack;
    TextView txtMessage,txtError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_by_date);

        dialog = new Dialog(DetailsByDateActivity.this);
        dialog.setContentView(R.layout.costom_dialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        initViews();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.43.11:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        btnGetDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailsByDate();
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        detailsByDateBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailsByDateActivity.this,DashboardActivity.class);
                startActivity(intent);
            }
        });

    }

    private void initViews() {
        detailsDate = findViewById(R.id.detailsDate);
        btnGetDetails = findViewById(R.id.btnGetDetails);
        detailsByDateBack = findViewById(R.id.btnDetailsByDateBack);
        btnOk = dialog.findViewById(R.id.btnOk);
        txtMessage = dialog.findViewById(R.id.txtMessage);
        txtError = dialog.findViewById(R.id.txtError);
    }

    private void detailsByDate(){

        txtError.setText("");
        txtMessage.setText("");
        Call<DetailsViaDateResponse> call = jsonPlaceHolderApi.detailsByDate(dateToString(detailsDate));
        call.enqueue(new Callback<DetailsViaDateResponse>() {
            @Override
            public void onResponse(Call<DetailsViaDateResponse> call, Response<DetailsViaDateResponse> response) {
                if(!response.isSuccessful()){
                    txtError.setText("CODE : "+response.code());
                    dialog.show();
                    return;
                }

                DetailsViaDateResponse detailsResponse = response.body();
                String content = "\n\tDATE : " + dateToString(detailsDate) + "\n\n";
                content += detailsResponse.getMessage().toString();

                if(detailsResponse.getAttendances()==null){
                    content += "\nNo Attendances This Day!\n\nIt may be a holiday!";
                }else{
                    content += "\n\nAttendances : \n\n";
                    for(Attendance attendance:detailsResponse.getAttendances()){
                        content +="\t" + attendance.getEmployee().getFirstname() + " "+attendance.getEmployee().getLastname()+"\n";
                    }
                }
                if(detailsResponse.getPayments()==null){
                    content += "\n\nNo Payments This Day!\n\n";
                }else{
                    content += "\n\nPayments : \n\n";
                    for(Payment payment:detailsResponse.getPayments()){
                        content +="\t" + payment.getEmployee().getFirstname() + " "+payment.getEmployee().getLastname()
                        +"\n\tAMOUNT  Rs : " + payment.getAmount() + "\n___________________\n\n";
                    }
                }
                txtMessage.setText(content);
                dialog.show();
            }

            @Override
            public void onFailure(Call<DetailsViaDateResponse> call, Throwable t) {
                txtError.setText(t.getMessage());
                dialog.show();
            }
        });

    }
    private String dateToString(DatePicker joindate){

        Calendar calendar = Calendar.getInstance();
        calendar.set(joindate.getYear(),joindate.getMonth(),joindate.getDayOfMonth());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(calendar.getTime());
        return date;
    }

}