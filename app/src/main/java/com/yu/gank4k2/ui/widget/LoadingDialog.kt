package me.yu.drxx.ui.widget

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.view.Gravity
import android.widget.ImageView
import com.yu.gank4k2.R

class LoadingDialog private constructor(context: Context, theme: Int) : Dialog(context, theme) {

    override fun show() {
        super.show()
        animationDrawable?.start()
    }

    override fun dismiss() {
        super.dismiss()
        animationDrawable?.stop()
    }

    companion object {

        private var animationDrawable: AnimationDrawable? = null

        fun createDialog(context: Context): LoadingDialog {
            val mProgressDialog = LoadingDialog(context, R.style.LightDialog)
            mProgressDialog.setContentView(R.layout.progress_dialog)
            mProgressDialog.setCancelable(true)
            mProgressDialog.setCanceledOnTouchOutside(false)
            mProgressDialog.window!!.attributes.gravity = Gravity.CENTER
            val lp = mProgressDialog.window!!.attributes
            lp!!.dimAmount = 0.2f
            mProgressDialog.window!!.attributes = lp

            val imageView = mProgressDialog.findViewById(R.id.iv_loading) as ImageView
            animationDrawable = imageView.background as AnimationDrawable
            return mProgressDialog
        }
    }
}
