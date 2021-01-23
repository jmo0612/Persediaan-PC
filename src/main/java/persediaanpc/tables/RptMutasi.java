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
public class RptMutasi {
    private String title;
    private String query;
    private ResourceField fieldProp;
    private String tableName;
    //private JMPCTable list;
    private JMFormTableList tblList;
    private boolean isLookup;
    private boolean isEditable;
    private boolean isMasterDetail;
    
    public static RptMutasi create(String date0,String date1){
        return new RptMutasi(date0,date1);
    }
    
    public RptMutasi(String date0,String date1){
        //this.title=R.label("TITLE_PENGADAAN");
        this.title="RPT";
        this.tableName="";
        this.isLookup=false;
        this.isEditable=Global.getEditor();
        this.query=QueryHelperPersediaan.qRptMutasiMaster(date0, date1);
        //JMFunctions.trace(this.query);
        this.fieldProp=new ResourceField();
        
        
        this.tblList=JMFormTableList.create(
                this.title, 
                this.query, 
                this.fieldProp, 
                this.tableName, 
                new JMPCDBComponentWrapper(), 
                new DBNewRecordWrapper(),
                this.isLookup, 
                false
                );
        this.tblList.setFormActionsWrapper(new FormActionsWrapper());
        //this.tblList.setDelDependencyMasterColIndices(0);
        //this.tblList.setDelDependencyDetailColIndices(1);
        this.tblList.setId("RPT_MUTASI_MASTER");
        this.tblList.pack();
        
        JMFormTableList det=JMFormTableList.create(
                    this.title, 
                    QueryHelperPersediaan.qRptMutasi(date0, date1), 
                    this.fieldProp, 
                    "", 
                    new JMPCDBComponentWrapper(), 
                    new DBNewRecordWrapper(),
                    false, 
                    false);
        det.setFormActionsWrapper(new FormActionsWrapper());
        det.setQueryTemplate(QueryHelperPersediaan.qRptMutasi(date0, date1));
        //det.setNewIdDependencyMasterColIndices(0);
        this.tblList.setDetailTable(det);
        det.setMasterTable(this.tblList);
        det.setId("RPT_MUTASI");
        det.pack();
    }
    public void show(){
        this.tblList.show();
    }
    public JMFormTableList getTableList(){
        return this.tblList;
    }
}
