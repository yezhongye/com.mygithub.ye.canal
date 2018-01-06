package com.ye.hkrs.jiekou.demo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by zjx on 2018/1/3.
 */
@NoArgsConstructor
@Data
public class HKSysBnfssData {

    /**
     *受益人类别
     */
    public String bnfType;
    /**
     *受益人姓名
     */
    public String bnfssName;
    /**
     *受益人证件类型
     */
    public String bnfssIdType;
    /**
     *受益人证件号码
     */
    public String bnfssIdNo;
    /**
     *受益比例
     */
    public String bnfssBnfLot;
    /**
     *受益人序号
     */
    public String bnfssBnfNo;
    /**
     *受益人与被保人关系
     */
    public String bnfssRelation;

}
