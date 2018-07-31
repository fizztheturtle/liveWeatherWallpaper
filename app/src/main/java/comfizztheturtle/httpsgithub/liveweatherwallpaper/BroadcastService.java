package comfizztheturtle.httpsgithub.liveweatherwallpaper;

import android.os.CountDownTimer;
//import comfizztheturtle.httpsgithub.liveweatherwallpaper.CountDownTimerActivity;
//import com.dev2qa.example.CountDownTimerActivity;

/**
 * Created by Jerry on 10/31/2017.
 * This is CountDownTimer sub class, which will override it's abstract methods.
 */

public class BroadcastService extends CountDownTimer {

    // This variable refer to the source activity which use this CountDownTimer object.
    private tabbed_activity sourceActivity;

    public void setSourceActivity(tabbed_activity sourceActivity) {
        this.sourceActivity = sourceActivity;
    }

    public BroadcastService(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    @Override
    public void onTick(long millisUntilFinished ) {
        if(this.sourceActivity!=null) {
            // Invoke source activity's tick event method.
            this.sourceActivity.onCountDownTimerTickEvent(millisUntilFinished);
        }
    }

    @Override
    public void onFinish() {
        if(this.sourceActivity!=null)
        {
            // Invoke source activity's tick event method.
            this.sourceActivity.onCountDownTimerFinishEvent();
        }
    }
}