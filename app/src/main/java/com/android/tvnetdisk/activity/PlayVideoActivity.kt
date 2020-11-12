package com.android.tvnetdisk.activity

import android.content.pm.ActivityInfo
import android.view.View
import android.widget.ImageView
import com.android.tvnetdisk.R
import com.blankj.utilcode.util.LogUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.danikula.videocache.ProxyCacheUtils
import com.jijia.kotlinlibrary.base.BaseActivity
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.cache.ProxyCacheManager
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import kotlinx.android.synthetic.main.activity_play_video.*


const val VIDEOURL = "videoUrl"
const val VIDEONAME="videoName"

class PlayVideoActivity : BaseActivity() {

    var videoUrl: String =""

//    private lateinit var orientationUtils: OrientationUtils

    override fun initData() {
        videoUrl=intent.getStringExtra(VIDEOURL)
        init()
    }

    override fun initView() {
        titleBarHide()
    }

    override fun layoutResID(): Int {
        return R.layout.activity_play_video
    }


    private fun init() {
        playerVideo.setUp(videoUrl, true, "测试视频")
        //增加封面
        val imageView = ImageView(this)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        Glide.with(this).load(videoUrl).diskCacheStrategy(DiskCacheStrategy.DATA).into(imageView)
        playerVideo.thumbImageView = imageView

        //增加title
        playerVideo.titleTextView.visibility = View.VISIBLE
        //设置返回键
        playerVideo.backButton.visibility = View.VISIBLE
        playerVideo.isNeedLockFull=false
        //设置全屏按键功能,这是使用的是选择屏幕，隐藏全屏按钮
        playerVideo.fullscreenButton.visibility=View.GONE
        //是否可以滑动调整
        playerVideo.setIsTouchWiget(false)
        //设置返回按键功能
        playerVideo.backButton.setOnClickListener(View.OnClickListener { onBackPressed() })
        playerVideo.startPlayLogic()
        playerVideo.setVideoAllCallBack(object :GSYSampleCallBack(){
            override fun onPlayError(url: String?, vararg objects: Any?) {
                LogUtils.d("播放失败"+ "$objects")
            }
        })
    }

    override fun onPause() {
        super.onPause()
        playerVideo.onVideoPause()
    }

    override fun onResume() {
        super.onResume()
        playerVideo.onVideoResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        GSYVideoManager.releaseAllVideos()
//        orientationUtils.releaseListener()
    }

    override fun onBackPressed() {
        //先返回正常状态
//        if (orientationUtils.screenType == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
//            playerVideo.fullscreenButton.performClick()
//            return
//        }
        //释放所有
        playerVideo.setVideoAllCallBack(null)
        super.onBackPressed()
    }

}