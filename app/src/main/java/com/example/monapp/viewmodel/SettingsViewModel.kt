package com.example.monapp.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs = application.getSharedPreferences("app_settings", Context.MODE_PRIVATE)
    private val _darkTheme = MutableStateFlow(prefs.getBoolean("dark_theme", false))
    val darkTheme: StateFlow<Boolean> = _darkTheme

    fun setDarkTheme(enabled: Boolean) {
        _darkTheme.value = enabled
        prefs.edit().putBoolean("dark_theme", enabled).apply()
    }
    private val _notificationsEnabled = MutableStateFlow(prefs.getBoolean("notifications", true))
    val notificationsEnabled: StateFlow<Boolean> = _notificationsEnabled

    fun setNotificationsEnabled(enabled: Boolean) {
        _notificationsEnabled.value = enabled
        prefs.edit().putBoolean("notifications", enabled).apply()
    }

    // Mise à jour automatique
    private val _autoUpdate = MutableStateFlow(prefs.getBoolean("auto_update", true))
    val autoUpdate: StateFlow<Boolean> = _autoUpdate

    fun setAutoUpdate(enabled: Boolean) {
        _autoUpdate.value = enabled
        prefs.edit().putBoolean("auto_update", enabled).apply()
    }
}