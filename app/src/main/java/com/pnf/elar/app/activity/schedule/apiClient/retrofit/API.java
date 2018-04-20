package com.pnf.elar.app.activity.schedule.apiClient.retrofit;

import com.bumptech.glide.request.Request;
import com.pnf.elar.app.Bo.TodaysNoteBO;
import com.pnf.elar.app.Bo.schedule.ActivityGroupCourseBo;
import com.pnf.elar.app.Bo.schedule.AspectResponseBo;
import com.pnf.elar.app.Bo.schedule.ChildrensBo;
import com.pnf.elar.app.Bo.schedule.FoodMenuBo;
import com.pnf.elar.app.Bo.schedule.FoodNoteBo;
import com.pnf.elar.app.Bo.schedule.ScheduleMonthList;
import com.pnf.elar.app.Bo.schedule.StudentsStudyPartnerBo;
import com.pnf.elar.app.Bo.schedule.ViewActivityBo;
import com.pnf.elar.app.activity.schedule.apiClient.Event;

import java.util.Calendar;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface API {
    /*
      * To validate login
      * */
    @Headers({
            "Content-Type: application/json"
    })

    @POST("mobile_api/SchemaDetailsForMonth")
    Call<List<Event>> getEventList(@Body RequestBody fields);

    @POST("mobile_api/schemaDetailsForCurrentDay")
    Call<ScheduleMonthList> getActivityList(@Body RequestBody fields);


    @POST("mobile_api/foodNote")
    Call<FoodNoteBo> getFootNote(@Body RequestBody fields);

    @POST("mobile_api/FoodMenuPDF")
    Call<FoodMenuBo> getFoodMenuLIst(@Body RequestBody fields);

    @POST("mobile_api/addFoodMenu")
    Call<FoodMenuBo> addFoodMenuLIst(@Body RequestBody fields);

    @POST("mobile_api/deleteFoodMenu")
    Call<FoodMenuBo> removeFoodMneu(@Body RequestBody fields);

    @POST("mobile_api/addVacationForTeacher")
    Call<FoodMenuBo> addVacation(@Body RequestBody fields);

    @POST("mobile_api/getCoursesAndGroups")
    Call<ActivityGroupCourseBo> getCoursesAndGroups(@Body RequestBody fields);


    @POST("mobile_api/getAspectsList")
    Call<AspectResponseBo> getAspectsList(@Body RequestBody fields);

    @POST("mobile_api/getGroupStudentsList")
    Call<StudentsStudyPartnerBo> getGroupStudentsList(@Body RequestBody fields);

    @POST("mobile_api/getStudyPartnersList")
    Call<StudentsStudyPartnerBo> getStudyPartnersList(@Body RequestBody fields);

    @POST("mobile_api/addActivityForTeacher")
    Call<String> addActivityForTeacher(@Body RequestBody fields);

    @POST("mobile_api/get_Activity")
    Call<ViewActivityBo> getActivity(@Body RequestBody fields);

    @POST("mobile_api/delete_Activity")
    Call<String> deleteActivity(@Body RequestBody fields);

    @POST("mobile_api/viewParentUser")
    Call<ChildrensBo> viewParentUser(@Body RequestBody fields);

    @POST("mobile_api/addactivityforParent")
    Call<String> addactivityforParent(@Body RequestBody body);

    @POST("mobile_api/addactivityforStudent")
    Call<String> addactivityforStudent(@Body RequestBody body);

    @POST("mobile_api/addwholedaysickforParent")
    Call<String> addwholedaysickforParent(@Body RequestBody body);

    @POST("mobile_api/delete_absent_desc")
    Call<String> delete_absent_desc(@Body RequestBody body);

    @POST("mobile_api/delete_retriever_desc")
    Call<String> delete_retriever_desc(@Body RequestBody body);

    @POST("mobile_api/getSchemadetailsforstudent")
   Call<ScheduleMonthList.SchemaCalendarEntity> getSchemadetailsforstudent(@Body RequestBody body);

    @POST("mobile_api/getWholedaySickdetailsforstudent")
    Call<String> getWholedaySickdetailsforstudent(@Body RequestBody body);

    @POST("mobile_api/getAuthenticationTokenforSCM")
    Call<String> loadWebView(@Body RequestBody body);

    @POST("lms_api/users/webgui_get_setting")
    Call<String> getWebVersionSettings(@Body RequestBody body);

    @POST("lms_api/users/webgui_Update_setting")
    Call<String> updateWebVersionSettings(@Body RequestBody body);

    @POST("mobile_api/getTodaysNote")
    Call<TodaysNoteBO> getTodaysNote(@Body RequestBody body);
}
