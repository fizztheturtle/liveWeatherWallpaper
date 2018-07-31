//package comfizztheturtle.httpsgithub.liveweatherwallpaper;
//
//import android.os.Bundle;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.app.AppCompatActivity;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.Button;
//
//public class CountDownTimerActivity extends AppCompatActivity {
//
//    private MenuItem buttonSendVerifyCode;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
////        setContentView(R.layout.activity_count_down_timer);
//
//        setTitle("CountDownTimer Example");
//
//        // Create a count down timer which will count 60 seconds and invoke the timer object onTick() method every second.
//        final BroadcastService myCountDownTimer = new BroadcastService(60*1000, 1000);
//        // Set count down timer source activity.
//        myCountDownTimer.setSourceActivity(this);
//
//        // Get the send verify code button.
//        buttonSendVerifyCode = (MenuItem)findViewById(R.id.refresh);
//        buttonSendVerifyCode.setAllCaps(false);
//        buttonSendVerifyCode.setTextSize(25);
//        buttonSendVerifyCode.setOnMenuItemClickListener(new View.() {
//            @Override
//            public void onClick(View view) {
//                // Start the count down timer.
//                myCountDownTimer.start();
//                // Disable send verify code button.
//                buttonSendVerifyCode.setEnabled(false);
//
//                AlertDialog alertDialog = new AlertDialog.Builder(CountDownTimerActivity.this).create();
//                alertDialog.setMessage("Verification code has been send through sms. " +
//                        "Click this button to resend 60 seconds later.");
//                alertDialog.show();
//            }
//        });
//    }
//
//    /* This method will be invoked when CountDownTimer finish. */
//    public void onCountDownTimerFinishEvent()
//    {
//        this.buttonSendVerifyCode.setEnabled(true);
//    }
//
//    /* This method will be invoked when CountDownTimer tick event happened.*/
//    public void onCountDownTimerTickEvent(long millisUntilFinished)
//    {
//        // Calculate left seconds.
//        long leftSeconds = millisUntilFinished / 1000;
//
//        String sendButtonText = "Left " + leftSeconds + " (s)";
//
//        if(leftSeconds==0)
//        {
//            sendButtonText = "Send Verification Code";
//        }
//
//        // Show left seconds in send button.
//        this.buttonSendVerifyCode.setText(sendButtonText);
//    }
//}