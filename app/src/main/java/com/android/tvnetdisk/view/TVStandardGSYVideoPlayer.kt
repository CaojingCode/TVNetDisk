package com.android.tvnetdisk.view

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import com.blankj.utilcode.util.ToastUtils
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer

class TVStandardGSYVideoPlayer(context: Context?, attrs: AttributeSet?) :
    StandardGSYVideoPlayer(context, attrs) {

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        when(event.action){
            KeyEvent.KEYCODE_DPAD_LEFT ->{
                ToastUtils.showShort("左键")
//                seekTo()
            }
            KeyEvent.KEYCODE_DPAD_RIGHT ->{
                ToastUtils.showShort("右键")

                //                seekTo()
            }
            KeyEvent.KEYCODE_DPAD_CENTER-> {
                ToastUtils.showShort("中间")
                clickStartIcon()
            }
        }
        return super.onKeyDown(keyCode, event)
    }
}