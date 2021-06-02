package lk.emsapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Salary implements Parcelable {

    private Integer salaryid;
    private String lastpaydate;
    private double totalsalary;


    public Salary(String lastpaydate, double totalsalary) {
        this.lastpaydate = lastpaydate;
        this.totalsalary = totalsalary;
    }

    protected Salary(Parcel in) {
        if (in.readByte() == 0) {
            salaryid = null;
        } else {
            salaryid = in.readInt();
        }
        lastpaydate = in.readString();
        totalsalary = in.readDouble();
    }

    public static final Creator<Salary> CREATOR = new Creator<Salary>() {
        @Override
        public Salary createFromParcel(Parcel in) {
            return new Salary(in);
        }

        @Override
        public Salary[] newArray(int size) {
            return new Salary[size];
        }
    };

    public Integer getSalaryid() {
        return salaryid;
    }

    public void setSalaryid(Integer salaryid) {
        this.salaryid = salaryid;
    }

    public String getLastpaydate() {
        return lastpaydate;
    }

    public void setLastpaydate(String lastpaydate) {
        this.lastpaydate = lastpaydate;
    }

    public double getTotalsalary() {
        return totalsalary;
    }

    public void setTotalsalary(double totalsalary) {
        this.totalsalary = totalsalary;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (salaryid == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(salaryid);
        }
        dest.writeString(lastpaydate);
        dest.writeDouble(totalsalary);
    }
}
