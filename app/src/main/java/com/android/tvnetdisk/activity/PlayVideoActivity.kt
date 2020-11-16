package com.android.tvnetdisk.activity

import android.content.pm.ActivityInfo
import android.view.KeyEvent
import android.view.View
import android.widget.ImageView
import com.android.tvnetdisk.R
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
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
    var videoName:String=""

//    private lateinit var orientationUtils: OrientationUtils

    override fun initData() {
        videoUrl=intent.getStringExtra(VIDEOURL)
        videoName=intent.getStringExtra(VIDEONAME)
        LogUtils.d("videoUrl=$videoUrl")
        init()
    }

    override fun initView() {
        titleBarHide()
    }

    override fun layoutResID(): Int {
        return R.layout.activity_play_video
    }


    private fun init() {
        playerVideo.setUp(videoUrl, true, videoName)
        //增加封面
        val imageView = ImageView(this)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        Glide.with(this).load(videoUrl).diskCacheStrategy(DiskCacheStrategy.DATA).into(imageView)
        playerVideo.thumbImageView = imageView

        //增加title
        playerVideo.titleTextView.visibility = View.VISIBLE
        //设置返回键
        playerVideo.backButton.visibility = View.GONE
        playerVideo.isNeedLockFull=false
        //设置全屏按键功能,这是使用的是选择屏幕，隐藏全屏按钮
        playerVideo.fullscreenButton.visibility=View.GONE

        //是否可以滑动调整
        playerVideo.setIsTouchWiget(true)
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

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        when(event?.action){
            KeyEvent.KEYCODE_DPAD_LEFT ->{
                ToastUtils.showShort("左键")
//                seekTo()
            }
            KeyEvent.KEYCODE_DPAD_RIGHT ->{
                ToastUtils.showShort("右键")
//                                seekTo()
            }
            KeyEvent.KEYCODE_DPAD_CENTER-> {
                ToastUtils.showShort("中间")
            }
        }
        return super.onKeyDown(keyCode, event)
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