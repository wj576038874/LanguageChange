package co.firefly.languagechange

import android.os.Bundle
import android.os.LocaleList
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.os.LocaleListCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private var currentLanguage = "zh"
    private val availableLanguage = arrayOf("zh", "en", "ru", "ko", "ja")
    private val availableLanguageDisplayName =
        arrayOf("Chinese", "English", "Russian", "korean", "Japanese")

    private lateinit var btnChangeLanguage: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        btnChangeLanguage = findViewById(R.id.btn_change)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        /**
        Note: This API work with the AppCompatActivity context, not for others context, for Android 12 (API level 32) and earlier.
        If there is a requirement to get the localized string which respects the per-app locale in non-AppCompatActivity context,
        please consider using androidx. core. content.
        [ContextCompat.getString(Context, int) or androidx. core. content. ContextCompat.
        getContextForLanguage(Context).
         */
        Log.e(
            "asd", "${
                ContextCompat.getString(
                    applicationContext, R.string.hello
                )
            }--${
                ContextCompat.getContextForLanguage(applicationContext).getString(R.string.hello)
            }--${getString(R.string.hello)} -- ${applicationContext.getString(R.string.hello)}"
        )

        // 获取当前的语言
        currentLanguage = if (AppCompatDelegate.getApplicationLocales().isEmpty) {
            Locale.getDefault().toLanguageTag()
        } else {
            AppCompatDelegate.getApplicationLocales()[0]?.toLanguageTag() ?: ""
        }
        Log.e("asd", currentLanguage)

        btnChangeLanguage.setOnClickListener {
            val current = availableLanguage.indexOf(currentLanguage)
            AlertDialog.Builder(this)
                .setSingleChoiceItems(availableLanguageDisplayName, current) { _, which ->
                    currentLanguage = availableLanguage[which]
                }.setPositiveButton("ok") { dialog, _ ->
                    dialog.cancel()
                    AppCompatDelegate.setApplicationLocales(
                        LocaleListCompat.forLanguageTags(
                            currentLanguage
                        )
                    )
                    //将applicationContext获取的语言也同步
//                    val config = applicationContext.resources.configuration
//                    val locale = Locale(currentLanguage)  // 设置为你需要的语言代码
//                    config.setLocale(locale)
//                    applicationContext.createConfigurationContext(config)
//                    applicationContext.resources.updateConfiguration(config , applicationContext.resources.displayMetrics)
                }.setNeutralButton("cancel") { dialog, _ ->
                    dialog.cancel()
                }.show()
        }
    }


    private fun getApplicationContext2() {

    }
}