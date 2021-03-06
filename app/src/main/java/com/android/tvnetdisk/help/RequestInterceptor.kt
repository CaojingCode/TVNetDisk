package com.android.tvnetdisk.help

import com.blankj.utilcode.util.SPUtils
import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var build = chain.request().newBuilder()
        var request = build.build()
        if (request.url.toString().contains("api/campus/binding") || request.url.toString()
                .contains("api/campus/getColumn") || request.url.toString()
                .contains("api/campus/getCampusResources")
        )
            request = request.newBuilder()
                .header("Authorization", "Bearer ${SPUtils.getInstance().getString("token")}")
                .build()
        return chain.proceed(request)
    }

}