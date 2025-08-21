package co.firefly.languagechange

import android.content.res.Configuration
import android.content.res.Resources
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
    private val availableLanguage = arrayOf("zh", "en", "ru", "ko", "ja", "si", "system")
    private val availableLanguageDisplayName =
        arrayOf("Chinese", "English", "Russian", "korean", "Japanese", "Sri Lanka", "system")

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
        注意：此 API 适用于 AppCompatActivity 上下文，不适用于 Android 12（API 级别 32）及更早版本的其他上下文。
        如果需要在非 AppCompatActivity 上下文中获取符合每个应用语言环境的本地化字符串，
        请考虑使用 androidx.core.content.
        [ContextCompat.getString(Context, int) 或 androidx.core.content.ContextCompat.getContextForLanguage(Context)。
         */
        Log.e(
            "asd", "${
                ContextCompat.getString(
                    applicationContext, R.string.hello
                )
            }--${
                ContextCompat.getContextForLanguage(applicationContext).getString(R.string.hello)
            }--${getString(R.string.hello)} -- ${applicationContext.getString(R.string.hello)} -- ${
                ContextCompat.getString(
                    this, R.string.hello
                )
            }"
        )

        // 获取当前设置的语言
        currentLanguage = if (AppCompatDelegate.getApplicationLocales().isEmpty) {
            // 如果没有设置应用语言，则使用系统默认语言
            "system" //用来作为跟随系统语言的标志 设置的时候进行判断，如果选择system 则设置空集合代表跟随系统语言
        } else {
            // 获取应用设置的语言
            AppCompatDelegate.getApplicationLocales()[0]?.toLanguageTag() ?: ""
        }

        /**
         * 如果没有设置过AppCompatDelegate.setApplicationLocales，则跟随系统语言，
         * 假设系统当前是zh中文，那么第一次打开应用时，
         * Locale.getDefault().toLanguageTag() = zh 会返回系统当前语言的语言标签，
         * resources.getSystem().configuration.locales[0].toLanguageTag()=zh 也会返回系统当前语言的语言标签，
         * AppCompatDelegate.getApplicationLocales()会返回空列表
         * AppCompatDelegate.getApplicationLocales()[0]?.toLanguageTag() = null
         *
         * 如果设置过AppCompatDelegate.setApplicationLocales，则会返回设置的语言标签，假设设置为en英文，那么
         * 那么 AppCompatDelegate.getApplicationLocales()[0]?.toLanguageTag() = en
         * resources.getSystem().configuration.locales[0].toLanguageTag() = en
         * Locale.getDefault().toLanguageTag() = en
         */

        //下面三种都是可以获取当前应用设置的语言
        Log.e("asd0", currentLanguage)
        //当前应用设置的语言 第一次打开系统是什么语言就获取的是什么语言 如果设置了则返回设置的语言标签，否则返回系统默认语言标签
        Log.e("asd1", Locale.getDefault().toLanguageTag())
        //当前应用设置的语言 第一次打开系统是什么语言就获取的是什么语言 如果设置了则返回设置的语言标签，否则返回系统默认语言标签
        Log.e("asd2", Resources.getSystem().configuration.locales[0].toLanguageTag())
        //当前应用设置的语言 如果没有设置过AppCompatDelegate.setApplicationLocales，则返回null
        Log.e("asd3", AppCompatDelegate.getApplicationLocales()[0]?.toLanguageTag().toString())

        btnChangeLanguage.setOnClickListener {
            val current = availableLanguage.indexOf(currentLanguage)
            AlertDialog.Builder(this)
                .setSingleChoiceItems(availableLanguageDisplayName, current) { _, which ->
                    currentLanguage = availableLanguage[which]
                }.setPositiveButton("ok") { dialog, _ ->
                    dialog.cancel()
                    if (currentLanguage == "system") {
                        // 如果选择了系统语言，则使用系统默认语言 设置空集合即可
                        // 设置之后AppCompatDelegate.getApplicationLocales() 会返回空列表
                        AppCompatDelegate.setApplicationLocales(
                            LocaleListCompat.getEmptyLocaleList()
                        )
                    } else {
                        //否则直接使用选择的语言
                        AppCompatDelegate.setApplicationLocales(
                            LocaleListCompat.forLanguageTags(
                                currentLanguage
                            )
                        )
                    }
                }.setNeutralButton("cancel") { dialog, _ ->
                    dialog.cancel()
                }.show()
        }
    }
}