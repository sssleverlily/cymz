package com.zia.gankcqupt_mvp.View.Fragment.Page;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.zia.gankcqupt_mvp.R;
import com.zia.gankcqupt_mvp.View.Activity.Page.RecyclerActivity;

/**
 * Created by zia on 2017/5/18.
 */

public class SecondFragment extends Fragment {

    private View view;
    Button button_allGirl;//全部妹子
    Button button_wanggong,jsjkxyjs,dlxxkx,xxaq,znkxyjs,jsjkxyjszygcs,jsjkxyjslxs,kjxxyszjs,xxaqzygcs,wlgclxs,jsjyznkxl,xxaqlxs,jsj_all;//计算机
    Button gdgcxy;//光电工程
    Button jjglxy;//经济管理
    Button swxxxy;//生物信息
    Button xjzzxy;//先进制造
    Button tyxy;//体育
    Button lxy;//理学院
    Button cmysxy;//传媒艺术
    Button rjgcxy;//软件工程
    Button gjbdtxy;//国际半导体
    Button gjxy;//国际学院
    Button button_faxue;//安法
    Button button_foreign;//外国语
    Button button_cmGirl;//传媒
    Button txgc,dzxxgc,xxgc,gbdsgc,txgczygcsb,txxyitjyb,txgcgjsyb,txgclxs,dzxxgclxs,gdyszmtl,txyxxl,tx_symz;//通信
    Button zdh,ckjsyyq,dqgcjqzdh,wlwgc,zndwxxgc,zdhzygcsb,zdhydqgcl,zdh_symz;//自动化

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        findWidgets();
        setButton();
    }


    /**
     * 封装，传按钮和专业名或学院名
     * @param button
     * @param value
     */
    private void setButton(Button button, final String value){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "稍等！", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(),RecyclerActivity.class);
                intent.putExtra("flag",value);
                startActivity(intent);
            }
        });
    }

    private void setButton(){
        setButton(gdgcxy,"光电工程学院");
        setButton(jjglxy,"经济管理学院");
        setButton(swxxxy,"生物信息学院");
        setButton(xjzzxy,"先进制造工程学院");
        setButton(tyxy,"体育学院");
        setButton(lxy,"理学院");
        setButton(cmysxy,"传媒艺术学院");
        setButton(rjgcxy,"软件工程学院");
        setButton(gjbdtxy,"国际半导体学院");
        setButton(gjxy,"国际学院");
        setButton(zdh,"自动化");
        setButton(ckjsyyq,"测控技术与仪器");
        setButton(dqgcjqzdh,"电气工程及其自动化");
        setButton(wlwgc,"物联网工程");
        setButton(zndwxxgc,"智能电网信息工程");
        setButton(zdhzygcsb,"自动化专业卓越工程师班");
        setButton(zdhydqgcl,"自动化与电气工程类");
        setButton(zdh_symz,"自动化学院");
        setButton(tx_symz,"通信与信息工程学院");
        setButton(txgc,"通信工程");
        setButton(dzxxgc,"电子信息工程");
        setButton(xxgc,"信息工程");
        setButton(gbdsgc,"广播电视工程");
        setButton(txgczygcsb,"通信工程专业卓越工程师班");
        setButton(txxyitjyb,"通信学院IT精英班");
        setButton(txgcgjsyb,"通信工程国际实验班");
        setButton(txgclxs,"通信工程(留学生)");
        setButton(dzxxgclxs,"电子信息工程(留学生)");
        setButton(gdyszmtl,"广电与数字媒体类");
        setButton(txyxxl,"通信与信息类");
        setButton(button_cmGirl,"传媒艺术学院");
        setButton(button_allGirl,"2016全部妹子");
        setButton(button_faxue,"网络空间安全与信息法学院");
        setButton(button_foreign,"外国语学院");
        setButton(button_wanggong,"网络工程");
        setButton(jsjkxyjs,"计算机科学与技术");
        setButton(dlxxkx,"地理信息科学");
        setButton(xxaq,"信息安全");
        setButton(znkxyjs,"智能科学与技术");
        setButton(jsjkxyjszygcs,"计算机科学与技术专业卓越工程师班");
        setButton(jsjkxyjslxs,"计算机科学与技术(留学生)");
        setButton(kjxxyszjs,"空间信息与数字技术");
        setButton(xxaqzygcs,"信息安全专业卓越工程师班");
        setButton(wlgclxs,"网络工程(留学生)");
        setButton(jsjyznkxl,"计算机与智能科学类");
        setButton(xxaqlxs,"信息安全(留学生)");
        setButton(jsj_all,"计算机科学与技术学院");

    }

    private void findWidgets(){
        gdgcxy = (Button)view.findViewById(R.id.button_gdgcxy);
        jjglxy = (Button)view.findViewById(R.id.button_jjglxy);
        swxxxy = (Button)view.findViewById(R.id.button_swxxxy);
        tyxy = (Button)view.findViewById(R.id.button_tyxy);
        lxy = (Button)view.findViewById(R.id.button_lxy);
        cmysxy = (Button)view.findViewById(R.id.button_cmysxy);
        rjgcxy = (Button)view.findViewById(R.id.button_rjgcxy);
        gjbdtxy = (Button)view.findViewById(R.id.button_gjbdtxy);
        gjxy = (Button)view.findViewById(R.id.button_gjxy);
        xjzzxy = (Button)view.findViewById(R.id.button_xjzzgcxy);
        ckjsyyq = (Button)view.findViewById(R.id.button_zdh_ckjsyyq);
        dqgcjqzdh = (Button)view.findViewById(R.id.button_zdh_dqgcjqzdh);
        wlwgc = (Button)view.findViewById(R.id.button_zdh_wlwgc);
        zndwxxgc = (Button)view.findViewById(R.id.button_zdh_zndwxxgc);
        zdhzygcsb = (Button)view.findViewById(R.id.button_zdh_adhzygcsb);
        zdhydqgcl = (Button)view.findViewById(R.id.button_zdh_zdhydqgcl);
        zdh = (Button)view.findViewById(R.id.button_zdh_zdh);
        zdh_symz = (Button)view.findViewById(R.id.button_zdh_symz);
        tx_symz = (Button)view.findViewById(R.id.button_tx_symz);
        gdyszmtl = (Button)view.findViewById(R.id.button_tx_gdyszmtl);
        txyxxl = (Button)view.findViewById(R.id.button_tx_txyxxl);
        txgc = (Button)view.findViewById(R.id.button_tx_txgc);
        dzxxgc = (Button)view.findViewById(R.id.button_tx_dzxxgc);
        xxgc = (Button)view.findViewById(R.id.button_tx_xxgc);
        gbdsgc = (Button)view.findViewById(R.id.button_tx_gbdsgc);
        txgczygcsb = (Button)view.findViewById(R.id.button_tx_txgczyzygcsb);
        txxyitjyb = (Button)view.findViewById(R.id.button_tx_txxyitjyb);
        txgcgjsyb = (Button)view.findViewById(R.id.button_tx_txgcgjsyb);
        txgclxs = (Button)view.findViewById(R.id.button_tx_txgclxs);
        dzxxgclxs = (Button)view.findViewById(R.id.button_tx_dzxxgclxs);
        jsjkxyjslxs = (Button)view.findViewById(R.id.button_jsj_jsjkxyjslxs);
        kjxxyszjs = (Button)view.findViewById(R.id.button_jsj_kjxxyszjs);
        xxaqzygcs = (Button)view.findViewById(R.id.button_jsj_xxaqzyzygcsb);
        wlgclxs = (Button)view.findViewById(R.id.button_jsj_wlgclxs);
        jsjyznkxl = (Button)view.findViewById(R.id.button_jsj_jsjyznkxl);
        xxaqlxs = (Button)view.findViewById(R.id.button_jsj_xxaqlxs);
        button_cmGirl = (Button) view.findViewById(R.id.button_cmGirl);
        button_allGirl = (Button)view.findViewById(R.id.button_allGirl);
        button_foreign = (Button)view.findViewById(R.id.button_foreignGirl);
        button_faxue = (Button)view.findViewById(R.id.button_faxue);
        button_wanggong = (Button)view.findViewById(R.id.button_jsj_wlgcl);
        jsjkxyjs = (Button)view.findViewById(R.id.button_jsj_jsjkxyjs);
        dlxxkx = (Button)view.findViewById(R.id.button_jsj_dlxxl);
        xxaq = (Button)view.findViewById(R.id.button_jsj_xxaql);
        znkxyjs = (Button)view.findViewById(R.id.button_jsj_znkxyjs);
        jsjkxyjszygcs = (Button)view.findViewById(R.id.button_jsj_jsjkxyjszygcsb);
        jsj_all = (Button)view.findViewById(R.id.button_jsj_symz);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_second,container,false);
        return view;
    }
}
