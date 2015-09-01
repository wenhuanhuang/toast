package toast.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by huangwenhuan on 15-6-12.
 */
public final class Toaster {

  private static final Handler MAIN_HANDLER = new Handler(Looper.getMainLooper());
  private static final String EMPTY = "";
  private static final int DURATION = Toast.LENGTH_SHORT;

  private static Toast toast;
  private static Context context;
  private static ToastRunnable toastRunnable;
  private static volatile boolean initialized = false;

  public static void initialize(Context context) {
    synchronized (Toaster.class) {
      if (initialized) return;
      Toaster.context = context;
      Toaster.toast = Toast.makeText(context, "", DURATION);
      Toaster.toastRunnable = new ToastRunnable();
      Toaster.initialized = true;
    }
  }

  /**
   * Display a string resource id with optional format args on a floating view.
   *
   * @param resId Resource id for the format string
   * @param args the list of arguments passed to the formatter. If there are
   * more arguments than required by {@code format},
   * additional arguments are ignored.
   */
  public static void show(int resId, Object... args) {
    checkIsInitialized();
    String msg = context.getString(resId);
    show0(false, DURATION, null, msg, args);
  }

  /**
   * Display a exception and a string resource id with optional format args on a floating view.
   *
   * @param resId Resource id for the format string
   * @param args the list of arguments passed to the formatter. If there are
   * more arguments than required by {@code format},
   * additional arguments are ignored.
   */
  public static void show(Throwable t, int resId, Object... args) {
    checkIsInitialized();
    String msg = context.getString(resId);
    show0(false, DURATION, t, msg, args);
  }

  /**
   * Display a string resource id with optional format args on a floating view.
   *
   * @param duration How long to show the view for (see {@link Toast#setDuration(int)}).
   * @param resId Resource id for the format string
   * @param args
   *            the list of arguments passed to the formatter. If there are
   *            more arguments than required by {@code format},
   *            additional arguments are ignored.
   */
  //  public static void show(final int duration, int resId, Object... args) {
  //    checkIsInitialized();
  //    String msg = context.getString(resId);
  //    show0(false, duration, null, msg, args);
  //  }

  /**
   * Display a exception and a string resource id with optional format args on a floating view.
   *
   * @param duration How long to show the view for (see {@link Toast#setDuration(int)}).
   * @param t exception that will be displayed
   * @param resId Resource id for the format string
   * @param args
   *            the list of arguments passed to the formatter. If there are
   *            more arguments than required by {@code format},
   *            additional arguments are ignored.
   * @see Toast#setDuration(int)
   */
  //  public static void show(final int duration, Throwable t, int resId, Object... args) {
  //    checkIsInitialized();
  //    String msg = context.getString(resId);
  //    show0(false, duration, t, msg, args);
  //  }

  /**
   * Display a message with optional format args on a floating view.
   *
   * @param msg the format string (see {@link java.util.Formatter#format})
   * @param args the list of arguments passed to the formatter. If there are
   * more arguments than required by {@code format},
   * additional arguments are ignored.
   */
  public static void show(String msg, Object... args) {
    show(null, msg, args);
  }

  /**
   * Display a message with optional format args on a floating view.
   *
   * @param msg the format string (see {@link java.util.Formatter#format})
   * @param args the list of arguments passed to the formatter. If there are
   * more arguments than required by {@code format},
   * additional arguments are ignored.
   */
  public static void show(Throwable t, String msg, Object... args) {
    show(DURATION, t, msg, args);
  }

  /**
   * Display a message with optional format args on a floating view.
   *
   * @param duration How long to show the view for (see {@link Toast#setDuration(int)}).
   * @param msg the format string (see {@link java.util.Formatter#format})
   * @param args the list of arguments passed to the formatter. If there are
   * more arguments than required by {@code format},
   * additional arguments are ignored.
   */
  public static void show(final int duration, String msg, Object... args) {
    show(duration, null, msg, args);
  }

  /**
   * Display a exception and a string resource id with optional format args on a floating view.
   *
   * @param duration How long to show the view for (see {@link Toast#setDuration(int)}).
   * @param t exception that will be displayed
   * @param msg the format string (see {@link java.util.Formatter#format})
   * @param args the list of arguments passed to the formatter. If there are
   * more arguments than required by {@code format},
   * additional arguments are ignored.
   * @see Toast#setDuration(int)
   */
  public static void show(final int duration, Throwable t, String msg, Object... args) {
    show0(true, duration, t, msg, args);
  }

  /**
   * Display a exception and a string resource id with optional format args on a floating view.
   *
   * @param check wheather check <code>initialized</code> status
   * @param duration How long to show the view for (see {@link Toast#setDuration(int)}).
   * @param t exception that will be displayed
   * @param msg the format string (see {@link java.util.Formatter#format})
   * @param args the list of arguments passed to the formatter. If there are
   * more arguments than required by {@code format},
   * additional arguments are ignored.
   * @see Toast#setDuration(int)
   */
  private static void show0(boolean check, final int duration, Throwable t, String msg,
      Object... args) {
    if (check) checkIsInitialized();
    msg = prepareMessage(t, msg, args);
    if (msg == EMPTY) return;

    final String display = msg;
    final Runnable runnable = new Runnable() {
      @Override public void run() {
        MAIN_HANDLER.removeCallbacks(toastRunnable);
        toastRunnable.setMsgInfo(display, duration, Gravity.BOTTOM, 0, 0);
        MAIN_HANDLER.post(toastRunnable);
      }
    };
    runOnUiThread(runnable);
  }

  private static String prepareMessage(Throwable t, String msg, Object... args) {
    if (msg != null && msg.length() == 0) {
      msg = null;
    }
    if (msg == null) {
      if (t == null) {
        // Swallow message if it's null and there's no throwable.
        return EMPTY;
      }
      msg = Log.getStackTraceString(t);
    } else {
      if (args.length > 0) {
        msg = String.format(msg, args);
      }
      if (t != null) {
        msg += "\n" + Log.getStackTraceString(t);
      }
    }
    return msg;
  }

  static void runOnUiThread(Runnable runnable) {
    if (Looper.myLooper() != Looper.getMainLooper()) {
      MAIN_HANDLER.post(runnable);
    } else {
      runnable.run();
    }
  }

  static void checkIsInitialized() {
    if (!initialized) {
      throw new IllegalStateException("Toaster must be initialize.");
    }
  }

  private Toaster() {
    throw new AssertionError("No instances.");
  }

  private static class ToastRunnable implements Runnable {

    private int duration;
    private int gravity;
    private int offsetX;
    private int offsetY;
    private CharSequence msg;

    void setMsgInfo(CharSequence msg, int duration, int gravity, int x, int y) {
      if (!msg.equals(this.msg)) {
        toast.cancel();
      }
      this.msg = msg;
      this.duration = duration;
      this.gravity = gravity;
      this.offsetX = x;
      this.offsetY = y;
    }

    @Override public void run() {
      toast.setText(msg);
      toast.setDuration(duration);
      toast.setGravity(gravity, offsetX, offsetY);
      toast.setMargin(0f, 0.1f);
      toast.show();
    }
  }
}