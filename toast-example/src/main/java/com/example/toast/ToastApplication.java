package com.example.toast;

import android.app.Application;
import timber.log.Timber;
import toast.util.ToasterTree;
import toast.util.Toaster;

/**
 * Created by huangwenhuan on 15-8-23.
 */
public class ToastApplication extends Application {
  @Override
  public void onCreate() {
    Toaster.initialize(this);
    if (BuildConfig.DEBUG) {
      Timber.plant(new Timber.DebugTree());
      Timber.plant(new ToasterTree(this));
    }
  }
}
