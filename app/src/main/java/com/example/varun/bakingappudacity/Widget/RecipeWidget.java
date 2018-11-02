package com.example.varun.bakingappudacity.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.varun.bakingappudacity.Activities.DetailsActivity;
import com.example.varun.bakingappudacity.Constants.Constants;
import com.example.varun.bakingappudacity.Models.Recipe;
import com.example.varun.bakingappudacity.R;

public class RecipeWidget extends AppWidgetProvider {

    static Recipe recipe;

    static void updateWidget(Context context, AppWidgetManager manager,int id){
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.layout_widget_rv_items);
        if (recipe!=null){
            remoteViews.setTextViewText(R.id.recipeName,recipe.getName());
        }

        Intent intent =new Intent(context,WidgetService.class);
        remoteViews.setRemoteAdapter(R.id.rv_widget,intent);
        Intent appIntent = new Intent(context,DetailsActivity.class);
        appIntent.putExtra(Constants.RECIPE,recipe);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,appIntent, 0);
        remoteViews.setPendingIntentTemplate(R.id.rv_widget,pendingIntent);
        manager.updateAppWidget(id,remoteViews);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateWidget(context, appWidgetManager, appWidgetId);
        }
    }
    public static void onUpdateWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateWidget(context, appWidgetManager, appWidgetId);
        }
    }
    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, RecipeWidget.class));
        String bundle = intent.getAction();
        if( bundle.equals("android.appwidget.action.APPWIDGET_UPDATE_INGREDIENTS")) {
            recipe = (Recipe) intent.getExtras().getSerializable(Constants.RECIPE);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.rv_widget);
            RecipeWidget.onUpdateWidgets(context, appWidgetManager, appWidgetIds);
            super.onReceive(context, intent);
        }

    }
}
