package com.example.varun.bakingappudacity.Widget;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.varun.bakingappudacity.Models.Recipe;

public class WidgetUpdateService extends IntentService {
    public WidgetUpdateService() {
        super("WidgetUpdateService");
    }

    public static void updatingWidget(Context context, Recipe recipe) {
        Intent intent = new Intent(context, WidgetUpdateService.class);
        intent.putExtra("Recipe", recipe);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d("WidgetUpdate", "Success");
        if( intent != null) {
            Recipe recipe = (Recipe) intent.getExtras().getSerializable("Recipe");
            Intent serviceIntent = new Intent("android.appwidget.action.APPWIDGET_UPDATE_INGREDIENTS");
            serviceIntent.setAction("android.appwidget.action.APPWIDGET_UPDATE_INGREDIENTS");
            serviceIntent.putExtra("Recipe",recipe);
            sendBroadcast(serviceIntent);
        }
    }
}
