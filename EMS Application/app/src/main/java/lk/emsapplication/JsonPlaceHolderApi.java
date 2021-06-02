package lk.emsapplication;

import java.util.List;

import lk.emsapplication.model.Attendance;
import lk.emsapplication.model.Employee;
import lk.emsapplication.model.Payment;
import lk.emsapplication.response.AttendanceGetResponse;
import lk.emsapplication.response.AttendanceResponse;
import lk.emsapplication.response.DetailsViaDateResponse;
import lk.emsapplication.response.EmployeeManagementResponse;
import lk.emsapplication.response.PaymentResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface JsonPlaceHolderApi {

    @GET("getAllAccountList")
    Call<List<Employee>> getAllAccounts();

    @POST("createAccount")
    Call<EmployeeManagementResponse> createAccount(@Body Employee post);

    @POST("attendanceMark")
    Call<AttendanceResponse> attendanceMark(@Body Attendance post);

    @POST("payToEmployee")
    Call<PaymentResponse> payNow(@Body Payment post);

    @GET("detailsViaDate/{date}")
    Call<DetailsViaDateResponse> detailsByDate(@Path("date") String date);

    @POST("lastMonthAttendances")
    Call<AttendanceGetResponse> getDetails(@Body Employee post);


}
