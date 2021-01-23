/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persediaanpc.util;

import com.thowo.jmjavaframework.JMFunctions;
import com.thowo.jmjavaframework.report.JMExcel;
import persediaanpc.tables.RptMutasi;
import persediaanpc.tables.TblPengadaan;

/**
 *
 * @author jimi
 */
public class ReportSementara {
    public static void mutasi(String dtAwal, String dtAkhir){
        JMFunctions.writeTableToExistingExcel(
                JMExcel.RPT_MODE_MASTER_DETAIL, 
                JMFunctions.getResourcePath("raw/mutasi.xlsx").getPath(),
                JMFunctions.getDocDir()+"/mutasi_jadi.xlsx", 
                RptMutasi.create("2020-01-01 00:00:00","2020-12-31 23:59:59").getTableList()
                    .setRptXlsSheetNameFromColIndex(0)
                , 
                true);
    }
}
