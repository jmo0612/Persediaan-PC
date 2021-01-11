/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persediaanpc.util;

import com.thowo.jmjavaframework.JMDate;
import com.thowo.jmjavaframework.JMFormatCollection;
import com.thowo.jmjavaframework.JMFunctions;
import com.thowo.jmjavaframework.db.JMDBNewRecordInterface;
import com.thowo.jmjavaframework.table.JMRow;
import com.thowo.jmjavaframework.table.JMTable;
import java.util.List;
import persediaanpc.Global;

/**
 *
 * @author jimi
 */
public class DBNewRecordWrapper implements JMDBNewRecordInterface {

    private JMRow newBidang(JMRow row){
        JMDate d=JMDate.now();
        String id="BID___"+d.getYearFull()
                +JMFormatCollection.leadingZero(d.getMonth(), 2)
                +JMFormatCollection.leadingZero(d.getDayOfMonth(), 2)
                +JMFormatCollection.leadingZero(d.getHour24Int(), 2)
                +JMFormatCollection.leadingZero(d.getMinuteInt(), 2)
                +JMFormatCollection.leadingZero(d.getSecondInt(), 2);
        JMTable tmp=JMTable.create("select id_bidang from p_tb_bidang where id_bidang like '"+id+"%' order by id_bidang desc limit 1", JMTable.DBTYPE_MYSQL);
        int nInt=1;
        if(!tmp.isEmpty()){
            String tmpId=tmp.firstRow(false).getCells().get(0).getDBValue();
            tmpId=tmpId.substring(id.length());
            nInt=JMFormatCollection.strToInteger(tmpId)+1;
        }
        row.setValueFromString(0, id+JMFormatCollection.leadingZero(nInt, 6));
        return row;
    }
    
    
    private JMRow newItem(JMRow row){
        JMDate d=JMDate.now();
        String id="ITEM__"+d.getYearFull()
                +JMFormatCollection.leadingZero(d.getMonth(), 2)
                +JMFormatCollection.leadingZero(d.getDayOfMonth(), 2)
                +JMFormatCollection.leadingZero(d.getHour24Int(), 2)
                +JMFormatCollection.leadingZero(d.getMinuteInt(), 2)
                +JMFormatCollection.leadingZero(d.getSecondInt(), 2);
        JMTable tmp=JMTable.create("select id_item from p_tb_item where id_item like '"+id+"%' order by id_item desc limit 1", JMTable.DBTYPE_MYSQL);
        int nInt=1;
        if(!tmp.isEmpty()){
            String tmpId=tmp.firstRow(false).getCells().get(0).getDBValue();
            tmpId=tmpId.substring(id.length());
            nInt=JMFormatCollection.strToInteger(tmpId)+1;
        }
        row.setValueFromString(0, id+JMFormatCollection.leadingZero(nInt, 6));
        return row;
    }
    
    private JMRow newPBJ(JMRow row){
        JMDate d=JMDate.now();
        String id="PJPBJ_"+d.getYearFull()
                +JMFormatCollection.leadingZero(d.getMonth(), 2)
                +JMFormatCollection.leadingZero(d.getDayOfMonth(), 2)
                +JMFormatCollection.leadingZero(d.getHour24Int(), 2)
                +JMFormatCollection.leadingZero(d.getMinuteInt(), 2)
                +JMFormatCollection.leadingZero(d.getSecondInt(), 2);
        JMTable tmp=JMTable.create("select id_pj_pbj from p_tb_pj_pbj where id_pj_pbj like '"+id+"%' order by id_pj_pbj desc limit 1", JMTable.DBTYPE_MYSQL);
        int nInt=1;
        if(!tmp.isEmpty()){
            String tmpId=tmp.firstRow(false).getCells().get(0).getDBValue();
            tmpId=tmpId.substring(id.length());
            nInt=JMFormatCollection.strToInteger(tmpId)+1;
        }
        row.setValueFromString(0, id+JMFormatCollection.leadingZero(nInt, 6));
        return row;
    }
    
    private JMRow newPeg(JMRow row){
        JMDate d=JMDate.now();
        String id="PEG___"+d.getYearFull()
                +JMFormatCollection.leadingZero(d.getMonth(), 2)
                +JMFormatCollection.leadingZero(d.getDayOfMonth(), 2)
                +JMFormatCollection.leadingZero(d.getHour24Int(), 2)
                +JMFormatCollection.leadingZero(d.getMinuteInt(), 2)
                +JMFormatCollection.leadingZero(d.getSecondInt(), 2);
        JMTable tmp=JMTable.create("select id_pegawai from p_tb_pegawai where id_pegawai like '"+id+"%' order by id_pegawai desc limit 1", JMTable.DBTYPE_MYSQL);
        int nInt=1;
        if(!tmp.isEmpty()){
            String tmpId=tmp.firstRow(false).getCells().get(0).getDBValue();
            tmpId=tmpId.substring(id.length());
            nInt=JMFormatCollection.strToInteger(tmpId)+1;
        }
        row.setValueFromString(0, id+JMFormatCollection.leadingZero(nInt, 6));
        return row;
    }
    private JMRow newMutasi(JMRow row){
        JMDate d=JMDate.now();
        String id="MUT___"+d.getYearFull()
                +JMFormatCollection.leadingZero(d.getMonth(), 2)
                +JMFormatCollection.leadingZero(d.getDayOfMonth(), 2)
                +JMFormatCollection.leadingZero(d.getHour24Int(), 2)
                +JMFormatCollection.leadingZero(d.getMinuteInt(), 2)
                +JMFormatCollection.leadingZero(d.getSecondInt(), 2);
        JMTable tmp=JMTable.create("select id_mutasi from p_tb_mutasi where id_mutasi like '"+id+"%' order by id_mutasi desc limit 1", JMTable.DBTYPE_MYSQL);
        int nInt=1;
        if(!tmp.isEmpty()){
            String tmpId=tmp.firstRow(false).getCells().get(0).getDBValue();
            tmpId=tmpId.substring(id.length());
            nInt=JMFormatCollection.strToInteger(tmpId)+1;
        }
        //JMFunctions.traceAndShow(id+JMFormatCollection.leadingZero(nInt, 6));
        row.setValueFromString(0,id+JMFormatCollection.leadingZero(nInt, 6));
        row.setValueFromString(3, Global.liveTimer.getDate().dateTimeDB()); 
        row.setValueFromString(5, "MUTASI_001"); 
        row.setValueFromString(8, "Kantor Dinas PUPR Minut"); 
        row.setValueFromString(10, "Dinas Pekerjaan Umum dan Penataan Ruang"); 
        tmp=JMTable.create("select * from p_tb_pj_barang where id_jab_barang='PgunaB'", JMTable.DBTYPE_MYSQL);
        row.setValueFromString(11, tmp.firstRow(false).getCells().get(2).getDBValue()); 
        row.setValueFromString(12, tmp.firstRow(false).getCells().get(3).getDBValue()); 
        row.setValueFromString(13, tmp.firstRow(false).getCells().get(1).getDBValue()); 
        tmp=JMTable.create("select * from p_tb_pj_barang where id_jab_barang='TUB'", JMTable.DBTYPE_MYSQL);
        row.setValueFromString(14, tmp.firstRow(false).getCells().get(2).getDBValue()); 
        row.setValueFromString(15, tmp.firstRow(false).getCells().get(3).getDBValue()); 
        row.setValueFromString(16, tmp.firstRow(false).getCells().get(1).getDBValue()); 
        tmp=JMTable.create("select * from p_tb_pj_barang where id_jab_barang='PurusB'", JMTable.DBTYPE_MYSQL);
        row.setValueFromString(17, tmp.firstRow(false).getCells().get(2).getDBValue()); 
        row.setValueFromString(18, tmp.firstRow(false).getCells().get(3).getDBValue()); 
        row.setValueFromString(19, tmp.firstRow(false).getCells().get(1).getDBValue()); 
        row.setValueFromString(26, "false");
        row.setValueFromString(27, "false"); 
        return row;
    }
    private JMRow newMutasiDetReal(JMRow row,String idMutasi){
        //JMFunctions.traceAndShow(idMutasi);
        JMDate d=JMDate.now();
        String id="DMR___"+idMutasi+d.getYearFull()
                +JMFormatCollection.leadingZero(d.getMonth(), 2)
                +JMFormatCollection.leadingZero(d.getDayOfMonth(), 2)
                +JMFormatCollection.leadingZero(d.getHour24Int(), 2)
                +JMFormatCollection.leadingZero(d.getMinuteInt(), 2)
                +JMFormatCollection.leadingZero(d.getSecondInt(), 2);
        JMTable tmp=JMTable.create("select id_det_mutasi_real from p_tb_mutasi_det_real where id_det_mutasi_real like '"+id+"%' order by id_det_mutasi_real desc limit 1", JMTable.DBTYPE_MYSQL);
        int nInt=1;
        if(!tmp.isEmpty()){
            String tmpId=tmp.firstRow(false).getCells().get(0).getDBValue();
            tmpId=tmpId.substring(id.length());
            nInt=JMFormatCollection.strToInteger(tmpId)+1;
        }
        row.setValueFromString(0,id+JMFormatCollection.leadingZero(nInt, 6));
        row.setValueFromString(1,idMutasi);
        return row;
    }
    
    private JMRow newElse(JMRow row){
        return row;
    }

    @Override
    public JMRow newDefaultRow(String tableName,JMRow newRow, List<String> params) {
        if(tableName.equals("p_tb_bidang")){
            return this.newBidang(newRow);
        }else if(tableName.equals("p_tb_item")){
            return this.newItem(newRow);
        }else if(tableName.equals("p_tb_pj_pbj")){
            return this.newPBJ(newRow);
        }else if(tableName.equals("p_tb_pegawai")){
            return this.newPeg(newRow);
        }else if(tableName.equals("p_tb_mutasi")){
            return this.newMutasi(newRow);
        }else if(tableName.equals("p_tb_mutasi_det_real") && params.size()>0){
            return this.newMutasiDetReal(newRow,params.get(0));
        }else{
            return this.newElse(newRow);
        }
    }
    
}
