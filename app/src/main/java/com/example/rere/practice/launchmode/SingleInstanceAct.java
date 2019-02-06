package com.example.rere.practice.launchmode;

import com.example.rere.practice.launchmode.base.TestLaunchModeBaseActivity;

import android.content.Context;
import android.content.Intent;

/**
 * Created by rere on 18-7-10.
 */

public class SingleInstanceAct extends TestLaunchModeBaseActivity {

    public static void start(Context context) {
        context.startActivity(new Intent(context, SingleInstanceAct.class));
    }

}
