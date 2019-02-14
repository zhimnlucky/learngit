package com.example.appsupport.smarthome.app.app.util;


/**
 * 睡眠时间表使用的工具
 * Created by niuGuangzhe on 2017/7/31.
 */
public class StrTime {
    int beginWeight1;
    int endWeight1;
    Boolean isAcrossOneDay=false;


    public StrTime(Integer beginHour,Integer beginMinute,Integer endHour,Integer endMinute){

        this.beginWeight1=beginHour*100+beginMinute;
        this.endWeight1=endHour*100+endMinute;

        if(beginWeight1>endWeight1){
            isAcrossOneDay=true;
            //开始时间比结束时间大，说明跨越了一天
            //那么结束时间需要加权值
            this.endWeight1+=2400;
        }
    }

    /**
     * 时间段是否合法
     * 不合法：开始时间与结束时间重合
     * 不合法：开始时间与结束时间相差12个小时以上
     * 不合法：开始时间与结束时间的分钟必须是0
     * @return
     */
    public boolean isLegal(){
        if(endWeight1-beginWeight1>1200){
            return false;
        }

        if(beginWeight1==endWeight1){
            return false;
        }
      return true;
    }
    public Integer getOffset(){
        return (endWeight1-beginWeight1)/2;
    }

    /**
     * 是否有重合时间段
     * 1:时间段不重合 0：时间段重合
     * @param strTime
     * @return
     */
    public Integer isCoincide(StrTime strTime){
        if((strTime.getAcrossOneDay()&&this.isAcrossOneDay)||(!strTime.getAcrossOneDay()&&!this.isAcrossOneDay)){
            //两个在同一个维度当中可以一起比较
            if(this.beginWeight1>=strTime.getEndWeight1()||this.endWeight1<=strTime.getBeginWeight1()){
                return 1;
            }
        }else{
            //有一个跨越天数，有一个没有跨越天数，需要特殊比较
            //如果存在跨越天数的话，一定是结束时间跨越了天数，所以需要做一下转换
            if(this.isAcrossOneDay){
                //我们这个跨越了天数，后面的没有跨越天数，开始计算
                if((strTime.getBeginWeight1()>=this.endWeight1-2400)&&strTime.getEndWeight1()<=this.beginWeight1){
                    return 1;
                }
            }else{
                //我们这个实例没有跨越天数，后边的跨越了天数，开始计算
                if((this.endWeight1<=strTime.getBeginWeight1())&&this.beginWeight1>=strTime.getEndWeight1()-2400){
                    return 1;
                }
            }
            return 0;
        }
        //其他情况都判定是重合状态
        return 0;
    }

    public Integer getBeginWeight1() {
        return beginWeight1;
    }

    public void setBeginWeight1(Integer beginWeight1) {
        this.beginWeight1 = beginWeight1;
    }

    public Integer getEndWeight1() {
        return endWeight1;
    }

    public void setEndWeight1(Integer endWeight1) {
        this.endWeight1 = endWeight1;
    }

    public Boolean getAcrossOneDay() {
        return isAcrossOneDay;
    }

    public void setAcrossOneDay(Boolean acrossOneDay) {
        isAcrossOneDay = acrossOneDay;
    }
}
