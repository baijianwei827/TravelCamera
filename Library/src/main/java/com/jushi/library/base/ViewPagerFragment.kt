package com.muslim.pro.imuslim.azan.portion.common.base

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Fragment与ViewPager搭配时，Fragment在可见时才加载数据
 *
 * @author wyf
 */
abstract class ViewPagerFragment : Fragment() {

    /**
     * rootView是否初始化标志，防止回调函数在rootView为空的时候触发
     */
    private var hasCreateView = false
    /**
     * 当前Fragment是否处于可见状态标志，防止因ViewPager的缓存机制而导致回调函数的触发
     */
    private var isFragmentVisible = false
    /**
     * onCreateView()里返回的view，修饰为protected,所以子类继承该类时，在onCreateView里必须对该变量进行初始化
     */
    protected var rootView: View? = null

    var isInitDataSuccess = false  //数据是否加载成功

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return initRootView(inflater, container, savedInstanceState)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (rootView == null) return
        hasCreateView = true
        if (isVisibleToUser) {
            onFragmentVisibleChange(true)
            isFragmentVisible = true
            return
        }
        if (isFragmentVisible) {
            onFragmentVisibleChange(false)
            isFragmentVisible = false
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initWidget()
        setViewListener()
        if (!hasCreateView && userVisibleHint) {
            onFragmentVisibleChange(true)
            isFragmentVisible = true
        }
    }

    /**
     * 在此方法初始化布局文件
     */
    abstract fun initRootView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View

    abstract fun initWidget()

    /**
     * 设置View的监听事件
     */
    abstract fun setViewListener()

    /**
     * 子类重写此方法，在该方法中请求数据
     * @param isVisible   true 表示可见，false表示不可见
     */
    abstract fun onFragmentVisibleChange(isVisible: Boolean)

    /**
     * 不带值跳转
     * @param cls 目标 Activity
     */
    fun startActivity(cls: Class<*>) {
        val intent = Intent(context, cls)
        context?.startActivity(intent)
    }

    /**
     * 带值跳转
     * @param bundle 携带的参数
     * @param cls 目标 Activity
     */
    fun startActivity(bundle: Bundle, cls: Class<*>) {
        val intent = Intent(context, cls)
        intent.putExtra(cls.name, bundle)
        context?.startActivity(intent)
    }

    fun startActivity(cls: Class<*>, uri: Uri) {
        val intent = Intent(context, cls)
        intent.data = uri
        context?.startActivity(intent)
    }
}