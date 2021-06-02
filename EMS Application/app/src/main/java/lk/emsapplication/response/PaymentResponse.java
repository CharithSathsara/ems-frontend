package lk.emsapplication.response;

import lk.emsapplication.model.Payment;

public class PaymentResponse {

    private String message;
    private String code;
    private Payment payment;

    public PaymentResponse(String message, String code, Payment payment) {
        this.message = message;
        this.code = code;
        this.payment = payment;
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

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}
