package lk.emsapplication.response;

import java.util.List;

import lk.emsapplication.model.Attendance;

public class AttendanceGetResponse {

    private String message;
    private String code;
    private List<Attendance> attendances;

    public AttendanceGetResponse(String message, String code, List<Attendance> attendances) {
        this.message = message;
        this.code = code;
        this.attendances = attendances;
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
}
