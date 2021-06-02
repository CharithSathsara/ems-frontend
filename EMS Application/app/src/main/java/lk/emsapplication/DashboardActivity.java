package lk.emsapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class DashboardActivity extends AppCompatActivity {

    private CardView cardViewCreateAccount,cardViewGetAccounts,cardViewGetInfoByDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        initViews();

        cardViewCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DashboardActivity.this, "GO TO CREATE ACCOUNT", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(DashboardActivity.this,CreateAccountActivity.class);
                startActivity(intent);
            }
        });
        cardViewGetAccounts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DashboardActivity.this, "GO TO ALL ACCOUNTS", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(DashboardActivity.this,GetAllEmployees.class);
                startActivity(intent);
            }
        });
        cardViewGetInfoByDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DashboardActivity.this, "GO TO GET INFO!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(DashboardActivity.this,DetailsByDateActivity.class);
                startActivity(intent);
            }
        });

    }
    private void initViews(){
        cardViewCreateAccount = findViewById(R.id.cardViewCreateAccount);
        cardViewGetAccounts = findViewById(R.id.cardViewGetAccounts);
        cardViewGetInfoByDate = findViewById(R.id.cardViewGetInfoByDate);
    }
}