package com.example.rere.practice.launchmode;

import android.content.Context;
import android.content.Intent;

import com.example.rere.practice.launchmode.base.TestLaunchModeBaseActivity;

/**
 * Created by rere on 18-7-10.
 */

public class SingleTaskAct2 extends TestLaunchModeBaseActivity {

    public static void start(Context context) {
        context.startActivity(new Intent(context, SingleTaskAct2.class));
    }

}
