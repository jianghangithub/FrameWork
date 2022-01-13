package com.jh.framework.base;

import com.jh.framework.retrofit.manager.DataManager;
import com.jh.framework.retrofit.observer.BaseObserver;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jh on 2018/5/2.
 */

public class BasePresenter<V extends BaseView> {

    private CompositeDisposable compositeDisposable;
    private V baseView;
     protected DataManager manager;

    public BasePresenter(V baseView) {
        this.baseView = baseView;
        manager = new DataManager();
    }

    /**
     * 解除绑定
     */
    void detachView() {
        baseView = null;
        removeDisposable();
    }

    /**
     * 返回 view
     *
     * @return View
     */
    public V getView() {
        return baseView;
    }

    public void addDisposable(Observable<?> observable, BaseObserver observer) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer));
    }

    public void addDisposable(Disposable disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    private void removeDisposable() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }
}
