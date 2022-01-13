package com.jh.framework.retrofit;


import com.jh.framework.retrofit.model.BaseModel;
import com.jh.framework.ui.main.model.HomeData;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by JH on 2017/11/22.
 */

public interface RetrofitService {


    @GET("v2/feed?")
    Observable<HomeData> getHomeData(@Query("num") int num);

    @GET("v4/categories/videoList?")
    Observable<HomeData.Issue> getCategoryDetailList(@Query("id") long id);

}
