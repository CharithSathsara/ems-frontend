package lk.emsapplication.model;

public class Attendance {

    private Integer attendanceid;
    private String date;
    private double dailysalary;
    private String worktype;
    private Employee employee;

    public Attendance() {
    }

    public Attendance(String date, double dailysalary, String worktype, Employee employee) {
        this.date = date;
        this.dailysalary = dailysalary;
        this.worktype = worktype;
        this.employee = employee;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getDailysalary() {
        return dailysalary;
    }

    public void setDailysalary(double dailysalary) {
        this.dailysalary = dailysalary;
    }

    public String getWorktype() {
        return worktype;
    }

    public void setWorktype(String worktype) {
        this.worktype = worktype;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
