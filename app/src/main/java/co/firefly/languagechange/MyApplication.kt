package co.firefly.languagechange

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate
import java.util.Locale

/**
 * Created by wenjie on 2024/08/23.
 */
class MyApplication : Application() {

//    override fun getApplicationContext(): Context {
//        return updateLocale(super.getApplicationContext(), Locale.getDefault())
//    }
//
//    fun updateLocale(context: Context, locale: Locale): Context {
//        Locale.setDefault(locale)
//        val config = Configuration(context.resources.configuration)
//        config.setLocale(locale)
//        return context.createConfigurationContext(config)
//    }

    override fun onCreate() {
        super.onCreate()
//        val currentLanguage = if (AppCompatDelegate.getApplicationLocales().isEmpty) {
//            Locale.getDefault().toLanguageTag()
//        } else {
//            AppCompatDelegate.getApplicationLocales()[0]?.toLanguageTag() ?: ""
//        }
//        updateLocale(this.applicationContext, Locale(currentLanguage))
    }
}