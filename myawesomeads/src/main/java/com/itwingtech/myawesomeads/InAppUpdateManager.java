package com.itwingtech.myawesomeads;

import android.content.Context;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.appupdate.AppUpdateOptions;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;

public class InAppUpdateManager {

    private static AppUpdateManager appUpdateManager;
    private static ActivityResultLauncher<IntentSenderRequest> activityResultLauncher;

    // Initialize the update manager
    public static void init(Context activity, ActivityResultLauncher<IntentSenderRequest> launcher) {
        appUpdateManager = AppUpdateManagerFactory.create(activity);
        activityResultLauncher = launcher;
        checkForUpdates();
    }

    // Check for available updates
    private static void checkForUpdates() {
        appUpdateManager.getAppUpdateInfo().addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                requestUpdate(appUpdateInfo);
            }
        }).addOnFailureListener(e -> {
            // Handle failure here
            // Example: e.printStackTrace();
        });
    }

    // Request an update
    private static void requestUpdate(AppUpdateInfo appUpdateInfo) {
        AppUpdateOptions updateOptions = AppUpdateOptions.newBuilder(AppUpdateType.IMMEDIATE).build();
        appUpdateManager.startUpdateFlowForResult(
                appUpdateInfo,
                activityResultLauncher,
                updateOptions
        );
    }

    // Register a listener for install state updates
    public static void registerListener(InstallStateUpdatedListener listener) {
        appUpdateManager.registerListener(listener);
    }

    // Unregister the listener for install state updates
    public static void unregisterListener(InstallStateUpdatedListener listener) {
        appUpdateManager.unregisterListener(listener);
    }
}
