package com.zia.gankcqupt_mvp.ui.empty


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.zia.gankcqupt_mvp.R


public class ContentFragment : Fragment() {

    private var tv: TextView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_content, container, false)
        tv = view.findViewById(R.id.content)
        return view
    }

    fun setText(text: String) {
        tv?.text = text
    }
}
