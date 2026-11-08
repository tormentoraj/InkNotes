package com.inknotes.inknotes.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.inknotes.inknotes.R
import com.inknotes.inknotes.ui.MainActivity

class InkNotesWidget : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
    }

    override fun onDisabled(context: Context) {
    }

    companion object {
        fun updateAppWidget(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {
            val intent = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            val pendingIntent = PendingIntent.getActivity(
                context, 0, intent, 
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            val views = RemoteViews(context.packageName, R.layout.widget_inknotes)
            
            val newNoteIntent = Intent(context, MainActivity::class.java).apply {
                action = "NEW_NOTE"
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            val newNotePendingIntent = PendingIntent.getActivity(
                context, 1, newNoteIntent, 
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            views.setOnClickPendingIntent(R.id.widget_new_note, newNotePendingIntent)

            val pinnedIntent = Intent(context, MainActivity::class.java).apply {
                action = "SHOW_PINNED"
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            val pinnedPendingIntent = PendingIntent.getActivity(
                context, 2, pinnedIntent, 
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            views.setOnClickPendingIntent(R.id.widget_pinned_notes, pinnedPendingIntent)

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}