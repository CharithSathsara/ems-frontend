package lk.emsapplication.response;

import java.util.List;

import lk.emsapplication.model.Attendance;
import lk.emsapplication.model.Payment;

public class DetailsViaDateResponse {

    private String message;
    private String code;
    private List<Attendance> attendances;
    private List<Payment> payments;

    public DetailsViaDateResponse(String message, String code, List<Attendance> attendances, List<Payment> payments) {
        this.message = message;
        this.code = code;
        this.attendances = attendances;
        this.payments = payments;
    }

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

    public List<Attendance> getAttendances() {
        return attendances;
    }

    public void setAttendances(List<Attendance> attendances) {
        this.attendances = attendances;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }
}
