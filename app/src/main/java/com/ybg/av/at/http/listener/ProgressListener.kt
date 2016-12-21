package com.ybg.av.at.http.listener

import com.ybg.av.at.http.Model.Progress

interface ProgressListener {
    fun onProgress(progress: Progress)
}