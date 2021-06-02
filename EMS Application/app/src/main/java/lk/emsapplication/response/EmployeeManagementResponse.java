package lk.emsapplication.response;

import lk.emsapplication.model.Employee;

public class EmployeeManagementResponse {

    private String message;
    private String code;
    private Employee employee;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
