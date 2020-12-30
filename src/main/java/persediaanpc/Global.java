/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persediaanpc;

import com.thowo.jmjavaframework.JMDate;

/**
 *
 * @author jimi
 */
public class Global {
    private static String user;
    private static boolean editor=true;
    private static boolean admin;
    private static int activeYear=2020;
    private static JMDate liveDate=JMDate.now();
    
    private static JMDate curDate=JMDate.now();
    private static String curIdBidang="";
    
    public static void setLiveDate(JMDate date){
        liveDate=date;
    }
    
    public static JMDate getLiveDate(){
        return liveDate;
    }
    
    public static void setCurDate(JMDate date){
        curDate=date;
    }
    
    public static JMDate getCurDate(){
        return curDate;
    }
    
    public static void setCurIdBidang(String idBidang){
        curIdBidang=idBidang;
    }
    
    public static String getCurIdBidang(){
        return curIdBidang;
    }
    
    public static int getActiveYear(){
        return activeYear;
    }
    public static void setActiveYear(int year){
        activeYear=year;
    }
    public static String getUser(){
        return user;
    }
    public static void setUser(String userId){
        user=userId;
    }
    public static boolean getAdmin(){
        return admin;
    }
    public static void setAdmin(boolean isAdmin){
        admin=isAdmin;
    }
    public static boolean getEditor(){
        return editor;
    }
    public static void setEditor(boolean isEditor){
        editor=isEditor;
    }
}
