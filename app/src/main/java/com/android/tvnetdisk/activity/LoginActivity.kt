package com.android.tvnetdisk.activity

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.tvnetdisk.R
import com.android.tvnetdisk.viewmodel.LoginViewModel
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : TVBaseActivity() {
    override fun layoutTVResID(): Int {
        return R.layout.activity_login
    }


    private lateinit var loginViewModel: LoginViewModel

    override fun initView() {
        super.initView()
        titleBarHide()
        loginViewModel=ViewModelProvider(this).get(LoginViewModel::class.java)
        etKey.setText(SPUtils.getInstance().getString("appKey"))
        if (SPUtils.getInstance().getBoolean("isLogin")) {

            loginViewModel.httpGetToken(etKey.text.toString())
            SPUtils.getInstance().put("appKey", etKey.text.toString())
        }
        btnBinding.setOnClickListener {
            val textStr = etKey.text.toString()
            if (textStr.isEmpty()) {
                ToastUtils.showShort(etKey.hint.toString())
                return@setOnClickListener
            }

            //绑定
            loginViewModel.httpGetToken(textStr)
            SPUtils.getInstance().put("appKey", etKey.text.toString())
        }

        loginViewModel.bindingLiveData.observe(this, Observer {
            if (it.code == 200) {
                //绑定成功，进入首页
                SPUtils.getInstance().put("isLogin", true)
                ActivityUtils.startActivity(MainActivity::class.java)
                finish()
            }
        })
    }
}