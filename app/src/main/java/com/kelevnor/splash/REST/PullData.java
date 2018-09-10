package com.kelevnor.splash.REST;

import android.app.Activity;

import com.kelevnor.splash.MODELS.Message;
import com.kelevnor.splash.UTILITY.Config;
import com.kelevnor.splash.UTILITY.UtilityHelper;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by kelevnor on 9/10/18.
 */

public class PullData {

    OnAsyncResult onAsyncResult;

    public PullData(Activity act, final OnAsyncResult onAsyncResult){
        this.onAsyncResult = onAsyncResult;

        if(UtilityHelper.isNetworkAvailable(act)){
            Retrofit retrofit = new Retrofit.Builder().baseUrl(Config.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()).build();
            PullTestData testData = retrofit.create(PullTestData.class);

            Call<List<Message>> call= testData.getTestData();
            call.enqueue(new Callback<List<Message>>() {
                @Override
                public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                    List<Message> res = response.body();
                    onAsyncResult.onResultSuccess(Config.RESULT_PULLDATA_SUCCESS, res);

                }
                @Override
                public void onFailure(Call<List<Message>> call, Throwable t){
                    onAsyncResult.onResultFail(Config.RESULT_PULLDATA_FAIL, t.getMessage());
                }
            });
        }
        else{
            this.onAsyncResult.onResultFail(Config.RESULT_INTERNET_FAIL, "");
        }
    }

    //This is our Interface for the RetrofitCall
    public interface PullTestData{
        @GET("img/events/splash/splash-messages.v1.1.json.txt")
        Call<List<Message>> getTestData();
    }

    //This is our Interface for listener on Activity
    public interface OnAsyncResult {
        void onResultSuccess(int resultCode, List<Message> objList);
        void onResultFail(int resultCode, String errorMessage);
    }
}
