package com.ybg.av.at.http.listener

import com.ybg.av.at.http.Model.Progress

interface UIProgressListener {

    fun onUIProgress(progress: Progress)

    fun onUIStart()

    fun onUIFinish()
}
