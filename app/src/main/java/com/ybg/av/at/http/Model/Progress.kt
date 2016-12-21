package com.ybg.av.at.http.Model

import java.io.Serializable

class Progress(val currentBytes: Long, val totalBytes: Long, val isFinish: Boolean) : Serializable
