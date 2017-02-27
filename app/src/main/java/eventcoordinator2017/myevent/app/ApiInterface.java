package eventcoordinator2017.myevent.app;


import java.util.List;

import eventcoordinator2017.myevent.model.data.Event;
import eventcoordinator2017.myevent.model.data.Location;
import eventcoordinator2017.myevent.model.data.Package;
import eventcoordinator2017.myevent.model.data.User;
import eventcoordinator2017.myevent.model.response.LoginResponse;
import eventcoordinator2017.myevent.model.response.ResultResponse;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


public interface ApiInterface {

    @FormUrlEncoded
    @POST(Endpoints.LOGIN)
    Call<LoginResponse> login(@Field(Constants.EMAIL) String username,
                              @Field(Constants.PASSWORD) String password);

    @FormUrlEncoded
    @POST(Endpoints.SAVE_USER_TOKEN)
    Call<ResultResponse> saveUserToken(@Field(Constants.USER_ID) String username,
                                       @Field("reg_token") String reg_token);

    @FormUrlEncoded
    @POST(Endpoints.DELETE_USER_TOKEN)
    Call<ResultResponse> deleteUserToken(@Field("reg_token") String reg_token);

    @FormUrlEncoded
    @POST(Endpoints.REGISTER)
    Call<ResultResponse> register(@Field(Constants.EMAIL) String username,
                                  @Field(Constants.PASSWORD) String password,
                                  @Field(Constants.FIRST_NAME) String firstName,
                                  @Field(Constants.LAST_NAME) String lastName,
                                  @Field(Constants.CONTACT) String contact,
                                  @Field(Constants.BIRTHDAY) String birthday,
                                  @Field(Constants.ADDRESS) String address
    );

    @FormUrlEncoded
    @POST(Endpoints.VERIFY)
    Call<LoginResponse> verify(@Field(Constants.USER_ID) String user_id,
                               @Field(Constants.VER_CODE) String verification_code);

    @FormUrlEncoded
    @POST(Endpoints.VERIFY_RESEND_EMAIL)
    Call<ResultResponse> verifyResendEmail(@Field(Constants.USER_ID) String user_id);


    @FormUrlEncoded
    @POST(Endpoints.GET_USER_EVENTS)
    Call<List<Event>> getUserEvents(@Field(Constants.USER_ID) int user_id);


    @FormUrlEncoded
    @POST(Endpoints.GET_PACKAGES)
    Call<List<Package>> getPackages(@Field("") String ah);

    @FormUrlEncoded
    @POST(Endpoints.GET_LOCATIONS)
    Call<List<Location>> getLocations(@Field("") String ah);

    @Multipart
    @POST
    Call<ResultResponse> uploadImage(@Part MultipartBody.Part image, @Part("name") RequestBody name);

    @FormUrlEncoded
    @POST(Endpoints.ADD_EVENT)
    Call<ResultResponse> createEvent(@Field(Constants.EVENT_USER_ID) String user_id,
                                     @Field(Constants.EVENT_PACKAGE_ID) String package_id,
                                     @Field(Constants.EVENT_NAME) String event_name,
                                     @Field(Constants.EVENT_DATE_FROM) String event_date_from,
                                     @Field(Constants.EVENT_DATE_TO) String event_date_to,
                                     @Field(Constants.EVENT_DESCRIPTION) String event_description,
                                     @Field(Constants.EVENT_TAGS) String event_tags,
                                     @Field(Constants.EVENT_LOC_ID) String event_loc_id,
                                     @Field(Constants.EVENT_IMAGE) String event_image);

    @FormUrlEncoded
    @POST
    Call<ResultResponse> editEvent(@Field(Constants.EVENT_USER_ID) String user_id,
                                   @Field(Constants.EVENT_PACKAGE_ID) String package_id,
                                   @Field(Constants.EVENT_NAME) String event_name,
                                   @Field(Constants.EVENT_DATE_FROM) String event_date_from,
                                   @Field(Constants.EVENT_DATE_TO) String event_date_to,
                                   @Field(Constants.EVENT_DESCRIPTION) String event_description,
                                   @Field(Constants.EVENT_TAGS) String event_tags,
                                   @Field(Constants.EVENT_LOC_ID) String event_loc_id,
                                   @Field(Constants.EVENT_IMAGE) String event_image);
/*
    //pangcheck kung nageexist yung email
    @FormUrlEncoded
    @POST
    Call<ResultResponse> checkGuestEmail(@Field("email") String email);

    */

    // pang check ng kung existing yung query pwedeng email o contact
    // pag existing iadd sha as guest dun sa event_id
    // tapos magnonotif sa nainvite na user
    @FormUrlEncoded
    @POST("inviteGuest")
    Call<User> inviteGuest(@Field("query") String query,
                           @Field("event_id") String event_id);

    //kukunin lahat ng guest ng event
    @FormUrlEncoded
    @POST("getAllGuestsFromEvent")
    Call<List<User>> getGuests(@Field("event_id") String event_id);

    //response sa event, M = Maybe G = Going I = Ignore
    @FormUrlEncoded
    @POST
    Call<ResultResponse> eventResponse(@Field("user_id") String user_id,
                                       @Field("event_id") String event_id,
                                       @Field("response") String response);

    //magiiba status ng event para di sha maquery
    @FormUrlEncoded
    @POST
    Call<ResultResponse> cancelEvent(@Field("event_id") String event_id);
}
