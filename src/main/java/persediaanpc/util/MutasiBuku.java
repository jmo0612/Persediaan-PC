/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persediaanpc.util;

import com.thowo.jmjavaframework.JMDate;
import com.thowo.jmjavaframework.JMFunctions;
import com.thowo.jmjavaframework.table.JMTable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jimi
 */
public class MutasiBuku {
    private String sDate;
    
    public static MutasiBuku create(){
        return new MutasiBuku();
    }
    
    public MutasiBuku(){
        
        this.init();
        JMFunctions.trace("ABIS");
    }
    
    private void init(){
        JMTable tmp=JMTable.create(QueryHelperPersediaan.qDetRealUnApproved, JMTable.DBTYPE_MYSQL);
        if(!tmp.isEmpty()){
            this.sDate=tmp.firstRow(false).getCells().get(5).getDBValue();
            if(!JMFunctions.getCurrentConnection().queryUpdateMySQL(QueryHelperPersediaan.qDelDetRealFromDate(this.sDate), true))return;
            if(!JMFunctions.getCurrentConnection().queryUpdateMySQL(QueryHelperPersediaan.qResetApprovalFromDate(this.sDate), true))return;
            JMFunctions.traceAndShow("PAUSED");
            tmp=JMTable.create(QueryHelperPersediaan.qDetRealUnApprovedDebit, JMTable.DBTYPE_MYSQL);
            String str="";
            String str2="";
            if(!tmp.isEmpty()){
                tmp.firstRow(false);
                do{
                    String strTmp="('SITEM_"+tmp.getCurrentRow().getCells().get(0).getDBValue()+"'";
                    strTmp+=",'"+tmp.getCurrentRow().getCells().get(5).getDBValue()+"'";
                    strTmp+=",'"+tmp.getCurrentRow().getCells().get(2).getDBValue()+"'";
                    strTmp+=",'"+tmp.getCurrentRow().getCells().get(4).getDBValue()+"')";
                    if(str.equals("")){
                        str=strTmp;
                    }else{
                        str+=","+strTmp;
                    }

                    strTmp="('DMB___"+tmp.getCurrentRow().getCells().get(1).getDBValue()+"SITEM_"+tmp.getCurrentRow().getCells().get(0).getDBValue()+"'";
                    strTmp+=",'"+tmp.getCurrentRow().getCells().get(1).getDBValue()+"'";
                    strTmp+=",'SITEM_"+tmp.getCurrentRow().getCells().get(0).getDBValue()+"'";
                    strTmp+=",'"+tmp.getCurrentRow().getCells().get(3).getDBValue()+"'";
                    strTmp+=",'0'";
                    strTmp+=",'"+tmp.getCurrentRow().getCells().get(3).getDBValue()+"')";
                    if(str2.equals("")){
                        str2=strTmp;
                    }else{
                        str2+=","+strTmp;
                    }
                }while(tmp.nextRow(false)!=null);
                str="REPLACE INTO p_tb_subitem(id_subitem, wkt_masuk_item, id_item, harga_satuan) values"+str;
                str2="REPLACE INTO p_tb_mutasi_det_buku(id_det_mutasi_buku, id_mutasi, id_subitem, qty, stok_awal, stok_akhir) values"+str2;
                if(!JMFunctions.getCurrentConnection().queryUpdateMySQL(str, true))return;
                if(!JMFunctions.getCurrentConnection().queryUpdateMySQL(str2, true))return;
            }
            String failedDate="";
            tmp=JMTable.create(QueryHelperPersediaan.qDetRealUnApprovedKredit, JMTable.DBTYPE_MYSQL);
            str="";
            if(!tmp.isEmpty()){
                tmp.firstRow(false);
                do{
                    String idDet=tmp.getCurrentRow().getCells().get(0).getDBValue();
                    String idMut=tmp.getCurrentRow().getCells().get(1).getDBValue();
                    String idItem=tmp.getCurrentRow().getCells().get(2).getDBValue();
                    Double rQty=tmp.getCurrentRow().getCells().get(3).getValueDouble();
                    Double hrg=tmp.getCurrentRow().getCells().get(4).getValueDouble();
                    String tglMut=tmp.getCurrentRow().getCells().get(5).getDBValue();
                    //JMFunctions.trace(QueryHelperPersediaan.qItemStockNotZero(tglMut, idItem));
                    JMTable tbStock=JMTable.create(QueryHelperPersediaan.qItemStockNotZero(tglMut, idItem), JMTable.DBTYPE_MYSQL);
                    if(!tbStock.isEmpty()){
                        List<String> idSubItem=new ArrayList();
                        List<Double> qtyUsed=new ArrayList();
                        List<Double> qty0=new ArrayList();
                        tbStock.firstRow(false);
                        Double qtyRemain=rQty;
                        do{
                            String curIdSubItem=tbStock.getCurrentRow().getCells().get(0).getDBValue();
                            Double curStock=tbStock.getCurrentRow().getCells().get(4).getValueDouble();
                            idSubItem.add(curIdSubItem);
                            if(curStock>qtyRemain){
                                qtyUsed.add(qtyRemain);
                            }else{
                                qtyUsed.add(curStock);
                            }
                            qty0.add(curStock);
                            qtyRemain-=curStock;
                            if(qtyRemain<=0)break;
                        }while(tbStock.nextRow(false)!=null);
                        String qDetBukuStr="";
                        for(int i=0;i<idSubItem.size();i++){
                            String s="("
                                    + "'DMB___"+ idMut + idSubItem.get(i) + "',"
                                    + "'"+ idMut + "',"
                                    + "'"+ idSubItem.get(i) + "',"
                                    + "'"+ qtyUsed.get(i) + "',"
                                    + "'"+ qty0.get(i) + "',"
                                    + "'"+ (qty0.get(i)-qtyUsed.get(i)) + "'"
                                    + ")";
                            if(qDetBukuStr.equals("")){
                                qDetBukuStr=s;
                            }else{
                                qDetBukuStr+=","+s;
                            }
                        }
                        qDetBukuStr="REPLACE INTO p_tb_mutasi_det_buku(id_det_mutasi_buku, id_mutasi, id_subitem, qty, stok_awal, stok_akhir) VALUES"+qDetBukuStr;
                        if(!JMFunctions.getCurrentConnection().queryUpdateMySQL(qDetBukuStr, true)){
                            failedDate=tglMut;
                            break;
                        }
                    }else{
                        failedDate=tglMut;
                        break;
                    }


                }while(tmp.nextRow(false)!=null);

                //BREAK failedDate
            }
            if(!failedDate.equals("")){
                JMFunctions.getCurrentConnection().queryUpdateMySQL("UPDATE p_tb_mutasi SET approved='0' WHERE tgl_mutasi>='"+failedDate+"'", true);
                JMFunctions.traceAndShow("Beberapa Permintaan Barang (sejak "+failedDate+") harus diteliti kembali");
            }else{
                JMFunctions.getCurrentConnection().queryUpdateMySQL("UPDATE p_tb_mutasi SET approved='1' WHERE tgl_mutasi>='"+this.sDate+"'", true);
            }
        }
    }
    
}
