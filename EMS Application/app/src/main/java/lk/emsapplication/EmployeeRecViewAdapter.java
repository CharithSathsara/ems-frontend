package lk.emsapplication;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import lk.emsapplication.model.Employee;
import lk.emsapplication.model.Salary;

public class EmployeeRecViewAdapter extends  RecyclerView.Adapter<EmployeeRecViewAdapter.MyViewHolder>{

    Context context;
    List<Employee> employees;

    public EmployeeRecViewAdapter(Context ct,List<Employee> employees){
        this.context = ct;
        this.employees = employees;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view= inflater.inflate(R.layout.employee_item_list,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Employee employee = employees.get(position);
        Salary salary = employees.get(position).getSalary();
        String name = employees.get(position).getFirstname() +" "+ employees.get(position).getLastname();
        holder.txtName.setText(name);
        holder.txtSalary.setText("Rs. " + employees.get(position).getSalary().getTotalsalary());

        holder.myLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,GetSpecificEmployee.class);
                intent.putExtra("employee",employee);
                intent.putExtra("salary",salary);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return employees.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView txtName;
        TextView txtSalary;
        LinearLayout myLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txtName);
            txtSalary = itemView.findViewById(R.id.txtSalary);
            myLayout = itemView.findViewById(R.id.myLayout);
        }
    }


}
