package com.example.group_project_0_1.Fragment;

import com.example.group_project_0_1.Notifications.MyResponse;
import com.example.group_project_0_1.Notifications.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {

    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAQabNOmw:APA91bHf3IiazwrBiUge9NJ5lnpw7oJMmwVpGEuUijLXFrhhzsAgZxId7yGaq7WqnKPM0K-wCqKsDR_175iBXc8oVajas8Aldu_ZUgju7xmHE7lo2KNRw4xpj__w1e_9YN91Kn0yRNGD"
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
