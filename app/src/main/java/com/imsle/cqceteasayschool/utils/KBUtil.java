package com.imsle.cqceteasayschool.utils;



import com.imsle.cqceteasayschool.model.KBDetail;

import java.util.ArrayList;

public class KBUtil {

    private KBDetail kbDetail;
    private ArrayList<KBDetail> kbDetails = new ArrayList<>();
    private ArrayList<KBDetail> weekKB = new ArrayList<>();
    public KBUtil(ArrayList<KBDetail> kbDetails){
        this.kbDetails.addAll(kbDetails);
    }

    public ArrayList<KBDetail> getWeekKB(int week){
        for(int i = 0;i < kbDetails.size();i ++){
            kbDetail = new KBDetail();
            kbDetail= kbDetails.get(i);
            if(kbDetail.getWeekDetail().contains(week)){
                weekKB.add(kbDetail);
            }
        }

        return weekKB;
    }
}
