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
import persediaanpc.R;

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
            //JMFunctions.traceAndShow("PAUSED");
            tmp=JMTable.create(QueryHelperPersediaan.qDetRealUnApproved, JMTable.DBTYPE_MYSQL);
            
            if(!tmp.isEmpty()){
                tmp.firstRow(false);
                String lastIdMut="";
                do{
                    String curIdDet=tmp.getCurrentRow().getCells().get(0).getDBValue();
                    String curIdMut=tmp.getCurrentRow().getCells().get(1).getDBValue();
                    String curIdItem=tmp.getCurrentRow().getCells().get(2).getDBValue();
                    Double curQty=tmp.getCurrentRow().getCells().get(3).getValueDouble();
                    Double curHrg=tmp.getCurrentRow().getCells().get(4).getValueDouble();
                    String curTglMut=tmp.getCurrentRow().getCells().get(5).getDBValue();
                    boolean curDebit=tmp.getCurrentRow().getCells().get(6).getValueBoolean();
                    String curNmItem=tmp.getCurrentRow().getCells().get(7).getDBValue();
                    //JMFunctions.trace(QueryHelperPersediaan.qItemStockNotZero(tglMut, idItem));
                    
                    
                    if(curDebit){
                        String str="";
                        String str2="";
                        String strTmp="('SITEM_"+curIdDet+"'";
                        strTmp+=",'"+curTglMut+"'";
                        strTmp+=",'"+curIdItem+"'";
                        strTmp+=",'"+curHrg+"')";
                        if(str.equals("")){
                            str=strTmp;
                        }else{
                            str+=","+strTmp;
                        }

                        strTmp="('DMB___"+curIdMut+"SITEM_"+curIdDet+"'";
                        strTmp+=",'"+curIdMut+"'";
                        strTmp+=",'SITEM_"+curIdDet+"'";
                        strTmp+=",'"+curQty+"'";
                        strTmp+=",'0'";
                        strTmp+=",'"+curQty+"')";
                        if(str2.equals("")){
                            str2=strTmp;
                        }else{
                            str2+=","+strTmp;
                        }
                        JMTable tmpLast=JMTable.create(QueryHelperPersediaan.qSubLastBuku(curIdItem, curTglMut,curIdMut), JMTable.DBTYPE_MYSQL);
                        if(!tmpLast.isEmpty()){
                            tmpLast.firstRow(false);
                            do{
                                strTmp="('DMB___"+curIdMut+tmpLast.getCurrentRow().getCells().get(0).getDBValue()+"'";
                                strTmp+=",'"+curIdMut+"'";
                                strTmp+=",'"+tmpLast.getCurrentRow().getCells().get(0).getDBValue()+"'";
                                strTmp+=",'0'";
                                strTmp+=",'"+tmpLast.getCurrentRow().getCells().get(1).getDBValue()+"'";
                                strTmp+=",'"+tmpLast.getCurrentRow().getCells().get(1).getDBValue()+"')";
                                if(str2.equals("")){
                                    str2=strTmp;
                                }else{
                                    str2+=","+strTmp;
                                }
                            }while(tmpLast.nextRow(false)!=null);
                        }
                        JMFunctions.trace("\n\n\n\n\n\nDEBIT=====================================================\n"+str+"\n\n"+str2);
                        str="REPLACE INTO p_tb_subitem(id_subitem, wkt_masuk_item, id_item, harga_satuan) values"+str;
                        str2="REPLACE INTO p_tb_mutasi_det_buku(id_det_mutasi_buku, id_mutasi, id_subitem, qty, stok_awal, stok_akhir) values"+str2;
                        if(!JMFunctions.getCurrentConnection().queryUpdateMySQL(str, true))return;
                        if(!JMFunctions.getCurrentConnection().queryUpdateMySQL(str2, true))return;
                    }else{
                        boolean failed=false;
                        JMTable tbStock=JMTable.create(QueryHelperPersediaan.qSubLastBuku(curIdItem, curTglMut,curIdMut), JMTable.DBTYPE_MYSQL);
                        if(!tbStock.isEmpty()){
                            List<String> idSubItem=new ArrayList();
                            List<Double> qtyUsed=new ArrayList();
                            List<Double> qty0=new ArrayList();
                            tbStock.firstRow(false);
                            Double qtyRemain=curQty;
                            do{
                                String curIdSubItem=tbStock.getCurrentRow().getCells().get(0).getDBValue();
                                Double curStock=tbStock.getCurrentRow().getCells().get(1).getValueDouble();
                                idSubItem.add(curIdSubItem);
                                qty0.add(curStock);
                                if(qtyRemain<=0){
                                    qtyUsed.add(0.0);
                                }else{
                                    if(curStock>qtyRemain){
                                        qtyUsed.add(qtyRemain);
                                    }else{
                                        qtyUsed.add(curStock);
                                    }
                                    qtyRemain-=curStock;
                                }
                            }while(tbStock.nextRow(false)!=null);
                            if(qtyRemain>0){
                                failed=true;
                                JMFunctions.errorMessage(R.error("ERRMSG_STOK_KURANG")+". ("+curNmItem+", "+curTglMut+")");
                            }else{
                                String qDetBukuStr="";
                                for(int i=0;i<idSubItem.size();i++){
                                    String s="("
                                            + "'DMB___"+ curIdMut + idSubItem.get(i) + "',"
                                            + "'"+ curIdMut + "',"
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
                                    failed=true;
                                }
                                JMFunctions.trace("\n\n\n\n\n\nKREDIT=====================================================\n"+qDetBukuStr);
                            }
                            
                        }else{
                            //JMFunctions.trace(QueryHelperPersediaan.qSubLastBuku(idItem, tglMut,idMut));
                            failed=true;
                        }
                        if(!lastIdMut.equals("")){
                            //NOT FIRST RECORD
                            if(!lastIdMut.equals(curIdMut)){
                                //NEW MUTASI
                                //TO DO SET APPROVED LAST
                                JMFunctions.getCurrentConnection().queryUpdateMySQL("UPDATE p_tb_mutasi SET approved='1' WHERE id_mutasi='"+lastIdMut+"'", true);
                                if(!failed){
                                    //DO NOTHING
                                    lastIdMut=curIdMut;
                                }else{
                                    //UNDO APPROVE MUTASI FROM CURRENT
                                    break;
                                }
                            }else{
                                //SAME MUTASI
                                if(!failed){
                                    //DO NOTHING
                                    lastIdMut=curIdMut;
                                }else{
                                    //UNDO APPROVE MUTASI FROM CURRENT
                                    break;
                                }
                            }
                        }else{
                            //FIRST RECORD
                            lastIdMut=curIdMut;
                        }
                    }
                }while(tmp.nextRow(false)!=null);
                
            }
            /*if(!failedDate.equals("")){
                JMFunctions.getCurrentConnection().queryUpdateMySQL("UPDATE p_tb_mutasi SET approved='0' WHERE tgl_mutasi>='"+failedDate+"'", true);
                JMFunctions.traceAndShow("Beberapa Permintaan Barang (sejak "+failedDate+") harus diteliti kembali");
            }else{
                JMFunctions.getCurrentConnection().queryUpdateMySQL("UPDATE p_tb_mutasi SET approved='1' WHERE tgl_mutasi>='"+this.sDate+"'", true);
            }*/
        }
    }
    
}
