package com.busylee.devpanelsample;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.busylee.devpanel.DevPanel;
import com.busylee.devpanel.info.ButtonInfo;
import com.busylee.devpanel.info.preferences.IntPreferenceInfo;
import com.busylee.devpanel.mutable.BooleanMutable;
import com.busylee.devpanel.mutable.SetStringMutableEntry;

import java.util.HashSet;
import java.util.Random;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

/**
 * Created by busylee on 23.10.15.
 */
public class SampleApplication extends Application {

    private Random random;

    @Override
    public void onCreate() {
        super.onCreate();
        DevPanel.Companion.init(this);
        random = new Random();
        // mutable info
        DevPanel.Companion.info()
            .mutable(new Function0<Object>() {
                @Override
                public Object invoke() {
                    return String.valueOf(random.nextFloat());
                }
            })
            .title("Random mutable value").add();
        // simple string info
        DevPanel.Companion.info()
            .simple(getString(R.string.app_name))
            .title("App name")
            .add();
        // preference info
        DevPanel.Companion.info()
            .pref("your_preferences_name")
            .title("Int pref example")
            .key("int_key")
            .integer(100)
            .add();
        // preference info
        DevPanel.Companion.info()
            .pref("your_preferences_name")
            .key("long_key")
            .llong(100L)
            .add();

        //Boolean mutable
        DevPanel.Companion.mutable()
            .bool(false)
            .title("Use strict mode")
            .key("bool_key")
            .add();

        //String
        DevPanel.Companion.mutable()
            .edit("Hello")
            .title("Welcome text")
            .key("welcome_text")
            .add("Custom category");

        // info in category
        DevPanel.Companion.info()
            .mutable(new Function0<Object>() {
            @Override
            public Object invoke() {
                return "Bit rate is: " + String.valueOf(random.nextFloat() * 100 % 10);
            }
        })
            .title("Current bit rate")
            .add("Network category");

        //String set
        final SetStringMutableEntry environment = DevPanel.Companion.mutable()
            .set()
            .key("environment")
            .values("test", "prod")
            .add("Network category");

        //simple button
        DevPanel.Companion.button()
            .title("Clear cache")
            .onClick(new Function1<Context, Unit>() {
                @Override
                public Unit invoke(Context context) {
                    Toast.makeText(
                        context,
                        "Cache has been successfully cleared",
                        Toast.LENGTH_LONG
                    ).show();
                    return null;
                }
            })
            .add("Network category");

        //button which can change mutable value
        DevPanel.Companion.button()
            .title("Set prod environment")
            .onClick(new Function1<Context, Unit>() {
                @Override
                public Unit invoke(Context context) {
                    environment.change("prod", context);
                    return null;
                }
            })
            .add("Network category");

    }
}
