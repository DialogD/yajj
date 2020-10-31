package com.dialogd.yajj.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class DoubleUtil {
    //默认除法运算精度
    private static final int DEF_DIV_SCALE = 10;
    //这个类不能实例化
    private DoubleUtil(){
    }

    /**
     * 提供精确的加法运算。
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static double add(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }
    /**
     * 提供精确的减法运算。
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static double sub(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }
    /**
     * 提供精确的乘法运算。
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static double mul(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
     * 小数点以后10位，以后的数字四舍五入。
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static double div(double v1,double v2){
        return div(v1,v2,DEF_DIV_SCALE);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字四舍五入。
     * @param v1 被除数
     * @param v2 除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static double div(double v1,double v2,int scale){
        if(scale<0){
            throw new IllegalArgumentException(
                "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供精确的小数位四舍五入处理。
     * @param v 需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static double round(double v,int scale){
        if(scale<0){
            throw new IllegalArgumentException(
                "The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    //格式化小数，不进行四舍五入，如果后面不足两位小数则补0
    public static String subZeroAndDot(double d){
		String s = String.format("%.20f", d);
        if(s.indexOf(".") > 0){  
            s = s.replaceAll("0+?$", "");//去掉多余的0  
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉  
        }
        if(s.indexOf(".") < 0) {
        	s += ".00";
        }
        if(s.split("\\.")[1].length() == 1) {
        	s += "0";
        }
        
        return s;  
    }
    public static double cale(double amount, double amountExt) {
		return add(DoubleUtil.div(amount, 100d) , div(Math.round(div(amountExt, 10000d)), 100d));
	}

    public static double cale2(double amount, double amountExt) {
		return DoubleUtil.add(DoubleUtil.div(amount, 100d) , DoubleUtil.div(DoubleUtil.div(amountExt, 10000d), 100d));
	}

    public static String formatDouble(long loNum,Double divisor){
        DecimalFormat df = new DecimalFormat("#,##0.00");
        double douNum = loNum/divisor;
        return df.format(douNum).toString();
    }

    public static String formatDoubleNew(long loNum,Double divisor){
        DecimalFormat df = new DecimalFormat("#.##");
        double douNum = loNum/divisor;
        return df.format(douNum).toString();
    }

    //格式化小数，保留两位小数，如果小数点后为0则只显示整数
    public static String formatDouble(double value){
		DecimalFormat df = new DecimalFormat("#.##");
		return df.format(value);
    }

    //根据位数  设置保留  小数点后几位
    public static String formatDoubleByPoint(long loNum,Double divisor,int point){
    	String pointStr = "0.";
    	for(int i=0;i<point;i++){
    		pointStr = pointStr + "0";
    	}
		DecimalFormat df = new DecimalFormat("#,##" + pointStr);
		double douNum = loNum/divisor;
		return df.format(douNum).toString();
    }

    public static String millionCentToYuanStr(Long millionCent) {
        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.FLOOR);
        if (null == millionCent) {
            return "0.00";
        }
        return df.format(millionCent / 100000000.00);
    }

    public static String centToYuanStr(Long cent) {
        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.FLOOR);
        if (null == cent) {
            return "0.00";
        }
        return df.format(cent / 100.00);
    }
    
    /**
     * 提供精确的乘法运算。
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static double mul(String v1,String v2){
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.multiply(b2).doubleValue();
    }
    
    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字四舍五入。
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static double div(String v1,String v2){
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.divide(b2,DEF_DIV_SCALE,BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    
	public static String formatLong(long loNum, Double divisor) {
		DecimalFormat df = new DecimalFormat("0.00");
		double douNum = loNum / divisor;
		return df.format(douNum).toString();
	}    

    public static void main(String[] args){
    	String dStr = formatDouble(35,1000.0);
    	System.out.println("dStr--->" + dStr);
    	dStr = formatDoubleByPoint(35,1000.0,4);
    	System.out.println("dStr--->" + dStr);
    	//System.out.println(mul(0.35d,9.00d));
    	
    	System.out.println((int)mul("23", "10000"));
    	System.out.println(mul(23, 10000));
    	System.out.println(Double.parseDouble("23"));
    }
}
