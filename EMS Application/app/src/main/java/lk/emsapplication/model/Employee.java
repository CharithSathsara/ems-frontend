package lk.emsapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Employee implements Parcelable {

    private Integer empid;
    private Integer phonenumber;
    private String address;
    private String firstname;
    private String lastname;
    private String joindate;
    private Salary salary;


    public Employee(Integer phonenumber, String address, String firstname, String lastname, String joindate) {
        this.phonenumber = phonenumber;
        this.address = address;
        this.firstname = firstname;
        this.lastname = lastname;
        this.joindate = joindate;
    }

    protected Employee(Parcel in) {
        if (in.readByte() == 0) {
            empid = null;
        } else {
            empid = in.readInt();
        }
        if (in.readByte() == 0) {
            phonenumber = null;
        } else {
            phonenumber = in.readInt();
        }
        address = in.readString();
        firstname = in.readString();
        lastname = in.readString();
        joindate = in.readString();
    }

    public static final Creator<Employee> CREATOR = new Creator<Employee>() {
        @Override
        public Employee createFromParcel(Parcel in) {
            return new Employee(in);
        }

        @Override
        public Employee[] newArray(int size) {
            return new Employee[size];
        }
    };

    public Integer getEmpid() {
        return empid;
    }

    public void setEmpid(Integer empid) {
        this.empid = empid;
    }

    public Integer getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(Integer phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getJoindate() {
        return joindate;
    }

    public void setJoindate(String joindate) {
        this.joindate = joindate;
    }

    public Salary getSalary() {
        return salary;
    }

    public void setSalary(Salary salary) {
        this.salary = salary;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (empid == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(empid);
        }
        if (phonenumber == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(phonenumber);
        }
        dest.writeString(address);
        dest.writeString(firstname);
        dest.writeString(lastname);
        dest.writeString(joindate);
    }
}
