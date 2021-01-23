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
    //private String sDate;
    private List<String> successMut=new ArrayList();
    
    public static MutasiBuku create(){
        return new MutasiBuku();
    }
    
    public MutasiBuku(){
        
        this.init();
        JMFunctions.trace("ABIS");
    }
    
    
    
    
    private void addMutasi(String idMutasi){
        for(String s:this.successMut){
            if(s.equals(idMutasi))return;
        }
        this.successMut.add(idMutasi);
    }
    
    private void removeMutasi(String idMutasi){
        List<String> ret=new ArrayList();
        for(String s:this.successMut){
            if(!s.equals(idMutasi)){
                ret.add(s);
            }
        }
        this.successMut=ret;
    }
    
    private void init(){
        boolean success=true;
        JMTable tmp=JMTable.create("select * from p_tb_mutasi where approved ='0' order by tgl_mutasi asc, id_mutasi asc", JMTable.DBTYPE_MYSQL);
        if(!tmp.isEmpty()){
            String mut=tmp.firstRow(false).getCells().get(0).getDBValue();
            JMDate dt=tmp.firstRow(false).getCells().get(1).getValueDate();
            String dtStr=tmp.firstRow(false).getCells().get(1).getDBValue();
            JMTable searchTbl=JMTable.create("select * from p_tb_mutasi where tgl_mutasi >='"+dtStr+"' order by tgl_mutasi asc, id_mutasi asc", JMTable.DBTYPE_MYSQL);
            List<String> muts=new ArrayList();
            if(!searchTbl.isEmpty()){
                searchTbl.firstRow(false);
                boolean found=false;
                do{
                    if(found){
                        //unapproved
                        muts.add(searchTbl.getCurrentRow().getCells().get(0).getDBValue());
                    }else{
                        if(searchTbl.getCurrentRow().getCells().get(1).getDBValue().equals(mut)){
                            found=true;
                        }
                    }
                }while(searchTbl.nextRow(false)!=null);
            }else{
                JMFunctions.errorMessage("ERROR");
            }
            if(muts.size()>0){
                String mutQ="";
                for(String s:muts){
                    if(mutQ.equals("")){
                        mutQ="id_mutasi='"+s+"'";
                    }else{
                        mutQ+=" OR id_mutasi='"+s+"'";
                    }
                }
                mutQ="UPDATE p_tb_mutasi SET approved='0' where ("+mutQ+")";
                String mutQ2="DELETE FROM p_tb_mutasi_det_real where ("+mutQ+")";
                if(!mutQ.equals(""))if(!JMFunctions.getCurrentConnection().queryUpdateMySQL(mutQ, true))return;
                if(!mutQ2.equals(""))if(!JMFunctions.getCurrentConnection().queryUpdateMySQL(mutQ2, true))return;
            }
            dt.addSeconds(1);
            //this.sDate=tmp.firstRow(false).getCells().get(5).getDBValue();
            if(!JMFunctions.getCurrentConnection().queryUpdateMySQL(QueryHelperPersediaan.qDelDetBukuFromDate(dt.dateTimeDB()), true))return;
            if(!JMFunctions.getCurrentConnection().queryUpdateMySQL(QueryHelperPersediaan.qResetApprovalFromDate(dt.dateTimeDB()), true))return;
            //JMFunctions.traceAndShow("PAUSED");
            tmp=JMTable.create(QueryHelperPersediaan.qDetRealUnApproved, JMTable.DBTYPE_MYSQL);
            
            if(!tmp.isEmpty()){
                tmp.firstRow(false);
                List<String> successMut=new ArrayList();
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
                    
                    boolean failed=false;
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
                        JMFunctions.trace("\n\n\n\n\n\nDEBIT=====================================================\n"+str+"\n\n"+str2);
                        str="REPLACE INTO p_tb_subitem(id_subitem, wkt_masuk_item, id_item, harga_satuan) values"+str;
                        str2="REPLACE INTO p_tb_mutasi_det_buku(id_det_mutasi_buku, id_mutasi, id_subitem, qty, stok_awal, stok_akhir) values"+str2;
                        if(!JMFunctions.getCurrentConnection().queryUpdateMySQL(str, true))return;
                        if(!JMFunctions.getCurrentConnection().queryUpdateMySQL(str2, true))return;
                        this.addMutasi(curIdMut);
                    }else{
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
                        if(!failed){
                            this.addMutasi(curIdMut);
                        }else {
                            //REMOVE FROM LIST MUT
                            this.removeMutasi(curIdMut);
                            JMFunctions.getCurrentConnection().queryUpdateMySQL("delete from p_tb_mutasi_det_buku where id_mutasi='"+curIdMut+"'", true);
                            break;
                        }
                    }
                }while(tmp.nextRow(false)!=null);
                
            }
            String qApp="";
            for(String s:this.successMut){
                if(qApp.equals("")){
                    qApp="id_mutasi='"+s+"'";
                }else{
                    qApp+=" OR id_mutasi='"+s+"'";
                }
            }
            if(qApp.equals(""))return;
            qApp="UPDATE p_tb_mutasi SET approved='1' WHERE ("+qApp+")";
            if(!qApp.equals(""))JMFunctions.getCurrentConnection().queryUpdateMySQL(qApp, true);
            /*if(!failedDate.equals("")){
                JMFunctions.getCurrentConnection().queryUpdateMySQL("UPDATE p_tb_mutasi SET approved='0' WHERE tgl_mutasi>='"+failedDate+"'", true);
                JMFunctions.traceAndShow("Beberapa Permintaan Barang (sejak "+failedDate+") harus diteliti kembali");
            }else{
                JMFunctions.getCurrentConnection().queryUpdateMySQL("UPDATE p_tb_mutasi SET approved='1' WHERE tgl_mutasi>='"+this.sDate+"'", true);
            }*/
        }
    }
    
}
