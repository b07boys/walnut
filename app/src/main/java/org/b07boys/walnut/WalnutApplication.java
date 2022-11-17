package org.b07boys.walnut;

import android.app.Application;
import android.os.Build;

import com.google.android.material.color.DynamicColors;

public class WalnutApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            DynamicColors.applyToActivitiesIfAvailable(this);
        }
    }
}
