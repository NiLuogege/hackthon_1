package com.niluogege.example.fastcodeframe

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.WindowManager
import com.alibaba.android.arouter.launcher.ARouter
import com.niluogege.example.commonsdk.utils.ARoutePath
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



//        btn.setOnClickListener { riv.x = }
//
//        riv.x = 100f
//        riv.y = 100f


    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    companion object {

        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib")
        }
    }


    /**
     * 获取屏幕尺寸
     *
     * @return
     */
    fun getScreenSize(context: Context): IntArray {
        val screenSize = IntArray(2)
        val manager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val outMetrics = DisplayMetrics()
        manager.defaultDisplay.getMetrics(outMetrics)
        val width = outMetrics.widthPixels
        val height = outMetrics.heightPixels

        screenSize[0] = width
        screenSize[1] = height
        return screenSize
    }
}
