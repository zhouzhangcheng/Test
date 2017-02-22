package com.newgame.sdk.yyaost;
import com.umeng.message.PushAgent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("--------------------", "¿ª»úÆô¶¯");
        PushAgent mPushAgent = PushAgent.getInstance(context);
        mPushAgent.onAppStart();
    
        mPushAgent.getMessageHandler();
    }

	
}


