package com.jh.framework.retrofit.manager;

import com.jh.framework.retrofit.RetrofitHelper;
import com.jh.framework.retrofit.RetrofitService;
import com.jh.framework.retrofit.model.BaseModel;
import com.jh.framework.ui.main.model.HomeData;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class DataManager {
    private RetrofitService mRetrofitService;
    // private Context mContext;

    public DataManager() {
        this.mRetrofitService = RetrofitHelper.getInstance().getServer();
        //mContext = context;
    }


    public Observable<HomeData> getHomeData() {
        return mRetrofitService.getHomeData(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<HomeData.Issue> getCategoryDetailList(long id) {
        return mRetrofitService.getCategoryDetailList(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


}
