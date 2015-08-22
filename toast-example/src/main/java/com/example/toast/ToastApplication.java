package com.example.toast;

import android.app.Application;
import toast.util.Toaster;

/**
 * Created by huangwenhuan on 15-8-23.
 */
public class ToastApplication extends Application {
  @Override
  public void onCreate() {
    Toaster.initialize(this);
  }
}
