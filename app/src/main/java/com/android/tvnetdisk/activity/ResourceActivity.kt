package com.android.tvnetdisk.activity

import android.view.KeyEvent
import androidx.fragment.app.Fragment
import com.android.tvnetdisk.R
import com.android.tvnetdisk.fragment.TVBaseFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_resource.*

/**
 * 文件资源管理页面
 */
class ResourceActivity : TVBaseActivity() {
    override fun layoutTVResID(): Int {
        return R.layout.activity_resource
    }

    override fun initData() {
        titleBarHide()
        //接收文件夹id
        var folderId = intent.getStringExtra("folderId")
        var ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.flFragment, TVBaseFragment.newInstance(folderId))
        ft.commit()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        when(keyCode){
            KeyEvent.KEYCODE_MENU-> {
                //菜单键

                var fragment=supportFragmentManager.findFragmentById(R.id.flFragment) as TVBaseFragment
                if (event != null) {
                    fragment.clickMenu()
                }
            }

        }
        return super.onKeyDown(keyCode, event)
    }
}