/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persediaanpc.util;

import com.thowo.jmjavaframework.JMFunctions;
import com.thowo.jmjavaframework.report.JMExcel;
import persediaanpc.tables.RptDistribusi;
import persediaanpc.tables.RptKartu;
import persediaanpc.tables.RptMutasi;
import persediaanpc.tables.RptOpname;
import persediaanpc.tables.RptPengadaan;
import persediaanpc.tables.TblPengadaan;

/**
 *
 * @author jimi
 */
public class ReportSementara {
    public static void mutasi(int tahun){
        JMFunctions.writeTableToExistingExcel(
                JMExcel.RPT_MODE_MASTER_DETAIL, 
                "raw/mutasi.xlsx",
                JMFunctions.getDocDir()+"/mutasi_jadi.xlsx", 
                RptMutasi.create(tahun).getTableList()
                    .setRptXlsSheetNameFromColIndex(16)
                , 
                true);
    }
    
    public static void kartu(int tahun){
        JMFunctions.writeTableToExistingExcel(
                JMExcel.RPT_MODE_MASTER_DETAIL, 
                "raw/kartu_persediaan.xlsx",
                JMFunctions.getDocDir()+"/kartu_persediaan_jadi.xlsx", 
                RptKartu.create(tahun).getTableList()
                    .setRptXlsSheetNameFromColIndex(3)
                , 
                true);
    }
    
    public static void opname(int tahun){
        JMFunctions.writeTableToExistingExcel(
                JMExcel.RPT_MODE_MASTER_DETAIL, 
                "raw/stock_opname.xlsx",
                JMFunctions.getDocDir()+"/stock_opname_jadi.xlsx", 
                RptOpname.create(tahun).getTableList()
                    .setRptXlsSheetNameFromColIndex(16)
                , 
                true);
    }
    
    public static void bastp(int tahun){
        JMFunctions.writeTableToExistingExcel(
                JMExcel.RPT_MODE_MASTER_DETAIL, 
                "raw/bastp.xlsx",
                JMFunctions.getDocDir()+"/bastp_jadi.xlsx", 
                RptPengadaan.create(tahun).getTableList()
                    .setRptXlsExcluded(0)
                    .setRptXlsSheetNameFromColIndex(4)
                , 
                true);
    }
    
    public static void bastd(int tahun){
        JMFunctions.writeTableToExistingExcel(
                JMExcel.RPT_MODE_MASTER_DETAIL, 
                "raw/bastd.xlsx",
                JMFunctions.getDocDir()+"/bastd_jadi.xlsx", 
                RptDistribusi.create(tahun).getTableList()
                    .setRptXlsExcluded(0)
                    .setRptXlsSheetNameFromColIndex(4)
                , 
                true);
    }
    
    public static void sppb(int tahun){
        JMFunctions.writeTableToExistingExcel(
                JMExcel.RPT_MODE_MASTER_DETAIL, 
                "raw/sppb.xlsx",
                JMFunctions.getDocDir()+"/sppb_jadi.xlsx", 
                RptDistribusi.create(tahun).getTableList()
                    .setRptXlsExcluded(0)
                    .setRptXlsSheetNameFromColIndex(4)
                , 
                true);
    }
    
    public static void spb(int tahun){
        JMFunctions.writeTableToExistingExcel(
                JMExcel.RPT_MODE_MASTER_DETAIL, 
                "raw/spb.xlsx",
                JMFunctions.getDocDir()+"/spb_jadi.xlsx", 
                RptDistribusi.create(tahun).getTableList()
                    .setRptXlsExcluded(0)
                    .setRptXlsSheetNameFromColIndex(4)
                , 
                true);
    }
}
