package com.example.toast;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.widget.Button;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.OnClick;
import toast.util.Toaster;

public class ToastActivity extends Activity {
  final Handler handler = new Handler();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
  }

  @OnClick({R.id.hello, R.id.string_format, R.id.res_id})
  void helloClicked(final Button button) {
    switch (button.getId()) {
      case R.id.hello:
        Toaster.show("A button %s was clicked to say %s",
            button.getId(), button.getText());

        handler.postDelayed(new Runnable() {
          @Override public void run() {
            Toaster.show(Toast.LENGTH_LONG, " - A button %s was clicked to say %s",
                button.getId(), button.getText());
          }
        }, 1000);

        break;
      case R.id.string_format:
        Toaster.show(new Throwable("throwable"), "A button %s was clicked to say",
            button.getId(), button.getText());

        handler.postDelayed(new Runnable() {
          @Override public void run() {
            Toaster.show(Toast.LENGTH_LONG, new Throwable("throwable"), " - A button %s was clicked to say",
                button.getId(), button.getText());
          }
        }, 1000);
        break;
      case R.id.res_id:
        Toaster.show(R.string.display_res_id, button.getId(), button.getText());

        handler.postDelayed(new Runnable() {
          @Override public void run() {
            Toaster.show(new Throwable("throwable"), R.string.display_res_id, button.getId(), button.getText());
          }
        }, 1000);
        break;
    }
  }
}
