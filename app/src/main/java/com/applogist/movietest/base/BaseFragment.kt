package com.applogist.movietest.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.applogist.movietest.R
import com.applogist.movietest.utils.ProgressDialog

abstract class BaseFragment : Fragment() {

    abstract val layoutId: Int
    var bBinding: ViewDataBinding? = null
    private var dialog: ProgressDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        dialog = context?.let { ProgressDialog(it, R.style.LoadingDialogStyle) }!!
        bBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        return bBinding?.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpBinding()
        super.onViewCreated(view, savedInstanceState)
    }

    abstract fun setUpBinding()

    fun showProgressDialog() {
        try {
            if (!dialog!!.isShowing) {
                dialog!!.show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Hide Progress Dialog
     */
    fun hideProgressDialog() {
        try {
            if (dialog!!.isShowing) {
                dialog!!.dismiss()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }



}