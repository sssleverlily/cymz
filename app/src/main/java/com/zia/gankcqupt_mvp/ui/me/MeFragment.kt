package com.zia.gankcqupt_mvp.ui.me

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.zia.gankcqupt_mvp.R
import com.zia.gankcqupt_mvp.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_me_mvp.*

class MeFragment : BaseFragment<IMe>(), MeView {
    override fun setClick() {
        me_favorite.setOnClickListener { presenter.goFavorite() }
        me_loginOut.setOnClickListener { presenter.loginOut() }
        me_upDate.setOnClickListener { presenter.update() }
        me_userLayout.setOnClickListener { presenter.showUserPop(it) }
    }

    override fun createPresenter(): IMe {
        return MePresenter(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setClick()
        presenter.setUserInfo()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_me_mvp, container, false)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        presenter.onActivityResult(requestCode, resultCode, data)
    }

}
