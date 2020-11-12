package com.android.tvnetdisk.help

import com.blankj.utilcode.util.ToastUtils
import com.shuyu.gsyvideoplayer.utils.Debuger
import com.zhy.http.okhttp.OkHttpUtils
import com.zhy.http.okhttp.callback.Callback
import okhttp3.Response
import java.io.IOException
import java.io.InputStream


abstract class MemoryCallBack : Callback<Boolean>() {
    override fun parseNetworkResponse(response: Response, id: Int): Boolean {
        return saveFile(response, id)
    }

    //// 模拟下载，这样可以和 proxy cache 公用一个本地cache
    open fun saveFile(response: Response, id: Int): Boolean {
        var `is`: InputStream? = null
        val buf = ByteArray(2048)
        var len = 0
        return try {
            `is` = response.body?.byteStream()
            val total = response.body?.contentLength()
            var sum: Long = 0
            while (`is`?.read(buf).also {
                    if (it != null) {
                        len = it
                    }
                } != -1) {
                sum += len.toLong()
                val finalSum = sum
                OkHttpUtils.getInstance().delivery.execute {
                    Debuger.printfLog("######### inProgress" + finalSum * 1.0f / total!!)
                    inProgress(finalSum * 1.0f / total, total, id)
                }
            }
            true
        } finally {
            try {
                response.body?.close()
                `is`?.close()
            } catch (e: IOException) {
                ToastUtils.showShort("缓存失败")
            }
        }
    }

}