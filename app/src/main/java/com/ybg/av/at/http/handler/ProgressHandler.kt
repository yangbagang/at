package com.ybg.av.at.http.handler

import android.os.Handler
import android.os.Looper
import android.os.Message

import com.ybg.av.at.http.Model.Progress
import com.ybg.av.at.http.listener.UIProgressListener

import java.lang.ref.WeakReference

abstract class ProgressHandler(uiProgressListener: UIProgressListener) : Handler(Looper.getMainLooper()) {

    private val mListenerWeakRef: WeakReference<UIProgressListener>

    init {
        mListenerWeakRef = WeakReference(uiProgressListener)
    }

    override fun handleMessage(msg: Message) {
        val listener = mListenerWeakRef.get()
        when (msg.what) {
            UPDATE -> if (listener != null)
                progress(listener, msg.obj as Progress)
            START -> if (mListenerWeakRef.get() != null)
                start(listener)
            FINISH -> if (mListenerWeakRef.get() != null)
                finish(listener)
            else -> super.handleMessage(msg)
        }
    }

    abstract fun start(listener: UIProgressListener)

    abstract fun progress(listener: UIProgressListener, progress: Progress)

    abstract fun finish(listener: UIProgressListener)

    companion object {

        val UPDATE = 0x01
        val START = 0x02
        val FINISH = 0x03
    }
}
