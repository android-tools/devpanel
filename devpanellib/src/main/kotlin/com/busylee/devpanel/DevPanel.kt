package com.busylee.devpanel

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorManager

import com.busylee.devpanel.info.Category
import com.busylee.devpanel.shake.ShakeDetector
import com.busylee.devpanel.ui.DevPanelActivity

/**
 * Created by busylee on 14.10.15.
 */
class DevPanel(private val mContext: Context) : ShakeDetector.OnShakeListener {
    private var mShakeDetector: ShakeDetector? = null
    private val mCategoryManager = CategoryManager(this)

    internal var mRootCategory = Category(ROOT_CATEGORY_NAME)

    private val shakeDetector: ShakeDetector
        get() {
            return mShakeDetector ?: ShakeDetector()
                .apply {
                    mShakeDetector = this
                    setOnShakeListener(this@DevPanel)
                }
        }

    private fun registerDetector(context: Context) {
        // ShakeDetector initialization
        val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        sensorManager.registerListener(shakeDetector, accelerometer, SensorManager.SENSOR_DELAY_UI)
    }

    private fun unregisterDetector(context: Context) {
        val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensorManager.unregisterListener(shakeDetector)
    }

    override fun onShake(count: Int) {
        startDevPanel(mContext)
    }

    companion object {

        const val ROOT_CATEGORY_NAME = "DevPanel_root_category_name"

        @SuppressLint("StaticFieldLeak")
        private var sInstance: DevPanel? = null

        fun init(context: Context) {
            sInstance = DevPanel(context)
        }

        /**
         * =====
         * Get root category outside
         * =====
         */

        internal val rootCategory: Category
            get() {
                checkInitAndThrow()
                return panel!!.mRootCategory
            }

        /**
         * =====
         * Facades methods to create and add mutables infos and buttons
         * =====
         */
        fun mutable(): MutableBuilderResolver {
            return mutableResolver
        }

        fun button(): InfoBuilderResolver.ButtonAdder {
            return infoBuilderResolver.button()
        }

        fun info(): InfoBuilderResolver {
            return infoBuilderResolver
        }

        fun category(name: String, collapsible: Boolean, collapsedByDefault: Boolean) {
            categoryManager.category(name)
                .apply {
                    this.collapsible = collapsible
                    this.collapsedByDefault = collapsedByDefault
                }
        }

        private val categoryManager: CategoryManager
            get() {
                checkInitAndThrow()
                return sInstance!!.mCategoryManager
            }

        private val mutableResolver: MutableBuilderResolver
            get() {
                checkInitAndThrow()
                return MutableBuilderResolver(manager, sInstance!!.mContext)
            }

        private val infoBuilderResolver: InfoBuilderResolver
            get() {
                checkInitAndThrow()
                return InfoBuilderResolver(manager, sInstance!!.mContext)
            }

        /**
         * =====
         * Util internal methods
         * =====
         */
        private val panel: DevPanel?
            get() {
                checkInitAndThrow()
                return sInstance
            }

        private val manager: CategoryManager
            get() {
                checkInitAndThrow()
                return panel!!.mCategoryManager
            }

        private fun checkInitAndThrow() {
            if (sInstance == null) {
                throw IllegalStateException("Panel has not been initialized")
            }
        }

        /*=====*/

        /**
         * =====
         * Shake detection
         * =====
         */

        fun onResume(context: Context) {
            checkInitAndThrow()
            panel!!.registerDetector(context)
        }

        fun onPause(context: Context) {
            checkInitAndThrow()
            panel!!.unregisterDetector(context)
        }

        /*=====*/

        /**
         * Start DevPanel activity directly
         * @param context
         */
        fun startDevPanel(context: Context) {
            val intent = Intent(context, DevPanelActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }

    /*=====*/

}
