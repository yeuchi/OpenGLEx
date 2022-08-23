package com.ctyeung.openglex.basic

import android.content.Context
import android.opengl.GLDebugHelper
import android.opengl.GLU
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.ctyeung.openglex.TriangleSmallGLUT
import javax.microedition.khronos.egl.*
import javax.microedition.khronos.opengles.GL10

/*
 * Ported source from Java -> Kotlin and reduced.
 * Android Wireless Application Develop by Lauren Darcey and Shae Conder, 2nd Edition - Pearson Education
 * ISBN-13: 978-0-321-74967-3
 * ISBN-10: 0-321-74967-7
 */
class BasicGLSurfaceView internal constructor(context: Context?) :
    SurfaceView(context), SurfaceHolder.Callback {

    private var mAndroidHolder: SurfaceHolder = holder
    private var mGLThread: BasicGLThread? = null

    init {
        mAndroidHolder.addCallback(this)
        mAndroidHolder.setType(SurfaceHolder.SURFACE_TYPE_GPU)
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        if (mGLThread != null) {
            mGLThread?.requestStop()
        }
    }

    override fun surfaceChanged(
        holder: SurfaceHolder, format: Int, width: Int,
        height: Int
    ) {
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        mGLThread = BasicGLThread(this)
        mGLThread?.start()
    }

    class BasicGLThread internal constructor(var sv: SurfaceView) : Thread() {
        private var mDone = false
        private val doColorful = false

        override fun run() {
            try {
                initEGL()
                initGL()
                val triangle = TriangleSmallGLUT(3F)
                mGL?.apply {
                    glMatrixMode(GL10.GL_MODELVIEW)
                    glLoadIdentity()
                    GLU.gluLookAt(mGL, 0f, 0f, 10f, 0f, 0f, 0f, 0f, 1f, 0f)
                    glColor4f(1f, 0f, 0f, 1f)
                    while (!mDone) {
                        glClear(GL10.GL_COLOR_BUFFER_BIT or GL10.GL_DEPTH_BUFFER_BIT)
                        glRotatef(1f, 0f, 0f, 1f)
                        if (doColorful) {
                            triangle.drawColorful(this)
                        } else {
                            triangle.draw(this)
                        }
                        mEGL?.apply {
                            eglSwapBuffers(mGLDisplay, mGLSurface)
                        }
                    }
                }

            } catch (e: Exception) {
                Log.e(DEBUG_TAG, "GL Failure", e)
            } finally {
                cleanupGL()
            }
        }

        fun requestStop() {
            mDone = true
            try {
                join()
            } catch (e: InterruptedException) {
                Log.e(DEBUG_TAG, "failed to stop gl thread", e)
            }
            cleanupGL()
        }

        private fun cleanupGL() {
            mEGL?.apply {
                eglMakeCurrent(
                    mGLDisplay, EGL10.EGL_NO_SURFACE,
                    EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT
                )
                eglDestroySurface(mGLDisplay, mGLSurface)
                eglDestroyContext(mGLDisplay, mGLContext)
                eglTerminate(mGLDisplay)
            }
            Log.i(DEBUG_TAG, "GL Cleaned up")
        }

        private fun initGL() {
            val width = sv.width
            val height = sv.height
            mGL?.apply {
                glViewport(0, 0, width, height)
                glMatrixMode(GL10.GL_PROJECTION)
                glLoadIdentity()
                val aspect = width.toFloat() / height
                GLU.gluPerspective(this, 45.0f, aspect, 1.0f, 30.0f)
                glClearColor(0.5f, 0.5f, 0.5f, 1f)

                // the only way to draw primitives with OpenGL ES
                glEnableClientState(GL10.GL_VERTEX_ARRAY)
                Log.i(DEBUG_TAG, "GL initialized")
            }
        }

        @Throws(Exception::class)
        fun initEGL() {
            mEGL = GLDebugHelper.wrap(
                EGLContext.getEGL(), GLDebugHelper.CONFIG_CHECK_GL_ERROR
                        or GLDebugHelper.CONFIG_CHECK_THREAD, null
            ) as EGL10
            mEGL?.apply {
                mGLDisplay = eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY)
                if (mGLDisplay == null) {
                    throw Exception("Couldn't get display for GL")
                }
                val curGLVersion = IntArray(2)
                eglInitialize(mGLDisplay, curGLVersion)
                Log.i(
                    DEBUG_TAG, "GL version = " + curGLVersion[0] + "."
                            + curGLVersion[1]
                )
                val configs = arrayOfNulls<EGLConfig>(1)
                val num_config = IntArray(1)

                eglChooseConfig(
                    mGLDisplay, mConfigSpec, configs, 1,
                    num_config
                )
                mGLConfig = configs[0]
                mGLSurface = eglCreateWindowSurface(
                    mGLDisplay, mGLConfig, sv
                        .holder, null
                )
                if (mGLSurface == null) {
                    throw Exception("Couldn't create new surface")
                }
                mGLContext = eglCreateContext(
                    mGLDisplay, mGLConfig,
                    EGL10.EGL_NO_CONTEXT, null
                )
                if (mGLContext == null) {
                    throw Exception("Couldn't create new context")
                }
                if (!eglMakeCurrent(mGLDisplay, mGLSurface, mGLSurface, mGLContext)) {
                    throw Exception("Failed to eglMakeCurrent")
                }
                mGL = GLDebugHelper.wrap(
                    mGLContext?.gl,
                    GLDebugHelper.CONFIG_CHECK_GL_ERROR
                            or GLDebugHelper.CONFIG_CHECK_THREAD
                            or GLDebugHelper.CONFIG_LOG_ARGUMENT_NAMES, null
                ) as GL10
                if (mGL == null) {
                    throw Exception("Failed to get GL")
                }
                return
            }
            throw Exception("Couldn't get EGL")
        }

        // main OpenGL variables
        var mGL: GL10? = null
        var mEGL: EGL10? = null
        var mGLDisplay: EGLDisplay? = null
        var mGLConfig: EGLConfig? = null
        var mGLSurface: EGLSurface? = null
        var mGLContext: EGLContext? = null
        var mConfigSpec = intArrayOf(
            EGL10.EGL_RED_SIZE, 5,
            EGL10.EGL_GREEN_SIZE, 6, EGL10.EGL_BLUE_SIZE, 5,
            EGL10.EGL_DEPTH_SIZE, 16, EGL10.EGL_NONE
        )

        companion object {
            const val DEBUG_TAG = "BasicGLActivity\$BasicGLThread"
        }
    }
}