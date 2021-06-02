package lk.emsapplication.response;

import lk.emsapplication.model.Attendance;

public class AttendanceResponse {

    private String message;
    private String code;
    private Attendance attendance;

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

    public Attendance getAttendance() {
        return attendance;
    }

    public void setAttendance(Attendance attendance) {
        this.attendance = attendance;
    }
}
