package com.jeeplus.common.utils;


public class Transformation {

   public static String null2String(Object object){
        if(object==null){
          return "" ;
        }else if(object.equals("null")){
            return "" ;
        }else{
            return String.valueOf(object) ;
        }
    }

    public static String null2ZeroString(Object object){
        if(object==null){
            return "0" ;
        }else if(object.equals("null")){
            return "0" ;
        }else if(object.equals("")){
            return "0" ;
        }else{
            return String.valueOf(object) ;
        }
    }

    public static int null2Integer(Object object){
        if(object==null){
            return 0 ;
        }else if(object.equals("null")){
            return 0 ;
        }else if(object.equals("")){
            return 0 ;
        }else{
            try{
                return Integer.parseInt((String) object) ;
            }catch (Exception e){
                return 0 ;
            }

        }
    }


    public static Float null2Float(Object object){
        if(object==null){
            return 0.0f ;
        }else if(object.equals("null")){
            return 0.0f ;
        }else if(object.equals("")){
            return 0.0f ;
        }else{
            try {
                return Float.parseFloat(String.valueOf(object)) ;
            }catch (Exception e){
                return 0.0f ;
            }
        }
    }

    public static Double null2Double(Object object){
        if(object==null){
            return 0.0 ;
        }else if(object.equals("null")){
            return 0.0 ;
        }else if(object.equals("")){
            return 0.0 ;
        }else{
            try {
                return Double.parseDouble(String.valueOf(object)) ;
            }catch (Exception e){
                return 0.0 ;
            }
        }
    }


    public static Boolean null_1_2Boolean(Object object){
        if(object==null){
            return false ;
        }else if(object.equals("null")){
            return false;
        }else if(object.equals("")){
            return false ;
        }else{
            return String.valueOf(object).equals("1") ;
        }
    }




    public static String Date2day(Object object){
        if(object==null){
            return "0000-00-00" ;
        }else if(object.equals("null")){
            return "0000-00-00" ;
        }else if(object.equals("")){
            return "0000-00-00" ;
        }else{
            String date=String.valueOf(object);
            return  date.substring(0,10);
        }
    }

    public static String removeNonBmpUnicode(String str) {
        if (str == null) {
            return null;
        }
        str = str.replaceAll("[^\\u0000-\\uFFFF]", "");
        return str;
    }

}
