package toast.util;

import android.content.Context;
import android.util.Log;
import timber.log.Timber;

/**
 * Created by huangwenhuan on 15-8-23.
 */
public class ToasterTree extends Timber.DebugTree {

  public ToasterTree(Context context) {
    Toaster.initialize(context);
  }

  @Override
  protected void log(int priority, String tag, String message, Throwable t) {
    message = tag + "/" + priorityToString(priority) + ": " + message;
    Toaster.show(message);
  }

  private static String priorityToString(int priority) {
    switch (priority) {
      case Log.ERROR:
        return "E";
      case Log.WARN:
        return "W";
      case Log.INFO:
        return "I";
      case Log.DEBUG:
        return "D";
      case Log.VERBOSE:
        return "V";
      default:
        return String.valueOf(priority);
    }
  }
}
