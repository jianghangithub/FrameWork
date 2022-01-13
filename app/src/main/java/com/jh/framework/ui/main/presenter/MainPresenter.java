package com.jh.framework.ui.main.presenter;

import com.jh.framework.base.BasePresenter;
import com.jh.framework.retrofit.observer.NoBaseObserver;
import com.jh.framework.ui.main.contract.IMainPresenter;
import com.jh.framework.ui.main.model.HomeData;
import com.jh.framework.ui.main.view.MainView;

public class MainPresenter extends BasePresenter<MainView> implements IMainPresenter {

    public MainPresenter(MainView baseView) {
        super(baseView);
    }

    @Override
    public void getMainPageInfo() {
        manager.getHomeData()
                .subscribe(new NoBaseObserver<HomeData>(getView()) {

                    @Override
                    public void onSuccess(HomeData o) {
                        getView().onSuccess(o);
                    }

                    @Override
                    protected void onError(String msg) {
                        getView().showError(msg);
                    }
                });
    }


}
