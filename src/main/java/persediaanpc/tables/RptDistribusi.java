/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persediaanpc.tables;

import com.thowo.jmjavaframework.JMButtonInterface;
import com.thowo.jmjavaframework.JMFieldInterface;
import com.thowo.jmjavaframework.JMFunctions;
import com.thowo.jmjavaframework.JMPanelInterface;
import com.thowo.jmjavaframework.form.JMFormInterface;
import com.thowo.jmjavaframework.form.JMFormTableList;
import com.thowo.jmjavaframework.form.JMLocaleInterface;
import com.thowo.jmjavaframework.table.JMDBListInterface;
import com.thowo.jmpcframework.JMPCDBComponentWrapper;
import com.thowo.jmpcframework.component.JMPCButtonSimple;
import com.thowo.jmpcframework.component.JMPCTable;
import com.thowo.jmpcframework.defaults.JMPCFormInput;
import com.thowo.jmpcframework.defaults.JMPCFormTable;
import java.util.ArrayList;
import java.util.List;
import persediaanpc.Global;
import persediaanpc.R;
import persediaanpc.util.DBNewRecordWrapper;
import persediaanpc.util.FormActionsWrapper;
import persediaanpc.util.QueryHelperPersediaan;
import persediaanpc.util.ResourceField;

/**
 *
 * @author jimi
 */
public class RptDistribusi {
    private String title;
    private String query;
    private ResourceField fieldProp;
    private String tableName;
    //private JMPCTable list;
    private JMFormTableList tblList;
    private boolean isLookup;
    private boolean isEditable;
    private boolean isMasterDetail;
    
    public static RptDistribusi create(int tahun){
        return new RptDistribusi(tahun);
    }
    
    public RptDistribusi(int tahun){
        this.title=R.label("TITLE_PERMINTAAN");
        this.tableName="p_tb_mutasi";
        this.isLookup=false;
        this.isEditable=false;
        this.query=QueryHelperPersediaan.qListPermintaanFilter(tahun);
        //JMFunctions.trace(this.query);
        this.fieldProp=new ResourceField();
        //Object[] boolImage={JMFunctions.getResourcePath("img/true.png", this.getClass()).getPath(),JMFunctions.getResourcePath("img/false.png", this.getClass()).getPath()};
        
        //TblPegawai tblPegawai=TblPegawai.create(QueryHelperPersediaan.qPegawaiAktif, true);
        //TblItem tblItem=TblItem.create(QueryHelperPersediaan.qListItemForBidangFromDate("[3]", "[24]"),true);
        //tblItem.getTableList().setQueryTemplate(QueryHelperPersediaan.qListItemForBidangFromDate("[3]", "[24]"));
        
        
        
        this.tblList=JMFormTableList.create(
                this.title, 
                this.query, 
                this.fieldProp, 
                this.tableName, 
                new JMPCDBComponentWrapper(), 
                new DBNewRecordWrapper(),
                this.isLookup, 
                this.isEditable
                );
        this.tblList.setFormActionsWrapper(new FormActionsWrapper());
        //this.tblList.setDelDependencyMasterColIndices(0);
        //this.tblList.setDelDependencyDetailColIndices(1);
        this.tblList.setId("PERMINTAAN");
        //this.tblList.addLookupTable(tblItem.getTableList());
        this.tblList.pack();
        
        JMFormTableList det=JMFormTableList.create(
                    this.title, 
                    QueryHelperPersediaan.qDetailPermintaan("[0]"), 
                    this.fieldProp, 
                    "", 
                    new JMPCDBComponentWrapper(), 
                    new DBNewRecordWrapper(),
                    false, 
                    true);
        det.setFormActionsWrapper(new FormActionsWrapper());
        det.setQueryTemplate(QueryHelperPersediaan.qDetailPermintaan("[0]"));
        //det.setNewIdDependencyMasterColIndices(0);
        this.tblList.setDetailTable(det);
        det.setMasterTable(this.tblList);
        det.setId("DETAIL_PERMINTAAN");
        det.pack();
    }
    public void show(){
        this.tblList.show();
    }
    public JMFormTableList getTableList(){
        return this.tblList;
    }
}
