package com.newgame.sdk.yyaost;

import android.content.Context;
import android.widget.Toast;

public class Global {
	private static int n = 0;

    public static int getN(){
        return n;

    }

    public static void setN(Context context, int m){

        n  = m;

        Toast.makeText(context, String.format("n = %d", n), 1000).show();
    }
}
