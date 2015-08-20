package toast.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by huangwenhuan on 15-6-12.
 */
public final class ToastHelper {

  private static final Handler MAIN_HANDLER =
      new Handler(Looper.getMainLooper());

  private static Toast mToast;
  private static ShowMSGRunnable mMSGRunnable;
  private static Context mContext;
  private static boolean mInited = false;

  private ToastHelper() {
  }

  public static void initialize(Context context) {
    if (mInited) return;
    mContext = context;
    mToast = Toast.makeText(mContext, "", Toast.LENGTH_SHORT);
    mMSGRunnable = new ShowMSGRunnable();
    mInited = true;
  }

  private static void checkIsInitialized() {
    if (!mInited) {
      throw new IllegalStateException("ToastHelper must be initialize.");
    }
  }

  public static void show(final CharSequence msg) {
    show(msg, Toast.LENGTH_SHORT);
  }

  public static void show(final CharSequence msg, final int duration) {
    final Runnable runnable = new Runnable() {
      @Override
      public void run() {
        if (msg == null) return;
        checkIsInitialized();
        MAIN_HANDLER.removeCallbacks(mMSGRunnable);
        mMSGRunnable.setMSGInfo(msg, duration, Gravity.BOTTOM, 0, 0);
        MAIN_HANDLER.post(mMSGRunnable);
      }
    };
    runOnMainThread(runnable);
  }

  public static void show(final int stringId) {
    show(stringId, Toast.LENGTH_SHORT);
  }

  public static void show(final int stringId, final int duration) {
    final Runnable runnable = new Runnable() {
      @Override
      public void run() {
        checkIsInitialized();
        MAIN_HANDLER.removeCallbacks(mMSGRunnable);
        mMSGRunnable.setMSGInfo(stringId, duration, Gravity.BOTTOM, 0, 0);
        MAIN_HANDLER.post(mMSGRunnable);
      }
    };
    runOnMainThread(runnable);
  }

  private static void runOnMainThread(Runnable runnable) {
    if (Looper.myLooper() != Looper.getMainLooper()) {
      MAIN_HANDLER.post(runnable);
    } else {
      runnable.run();
    }
  }

  static class ShowMSGRunnable implements Runnable {

    private int mResid;
    private int mDuration;
    private int mGravity;
    private int mOffsetX;
    private int mOffsetY;
    private CharSequence mMSG;

    void setMSGInfo(int resId, int duration, int gravity, int x, int y) {
      if (mResid != resId) {
        mToast.cancel();
      }
      this.mResid = resId;
      this.mDuration = duration;
      this.mGravity = gravity;
      this.mOffsetX = x;
      this.mOffsetY = y;
      mMSG = null;
    }

    void setMSGInfo(CharSequence msg, int duration, int gravity, int x, int y) {
      if (!msg.equals(this.mMSG)) {
        mToast.cancel();
      }
      this.mMSG = msg;
      this.mDuration = duration;
      this.mGravity = gravity;
      this.mOffsetX = x;
      this.mOffsetY = y;
    }

    @Override
    public void run() {
      if (mMSG == null) {
        mToast.setText(mContext.getString(mResid));
      } else {
        mToast.setText(mMSG);
      }

      mToast.setDuration(mDuration);
      mToast.setGravity(mGravity, mOffsetX, mOffsetY);
      mToast.setMargin(0f, 0.1f);
      mToast.show();
    }
  }
}