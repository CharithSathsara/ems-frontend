package lk.emsapplication.model;

public class Payment {

    private Integer paymentid;
    private String paydate;
    private double amount;
    private Employee employee;

    public Payment() {
    }

    public Payment(String paydate, double amount, Employee employee) {
        this.paydate = paydate;
        this.amount = amount;
        this.employee = employee;
    }

    public String getPaydate() {
        return paydate;
    }

    public void setPaydate(String paydate) {
        this.paydate = paydate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
