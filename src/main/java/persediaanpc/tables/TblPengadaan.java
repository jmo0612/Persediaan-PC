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
public class TblPengadaan {
    private String title;
    private String query;
    private ResourceField fieldProp;
    private String tableName;
    //private JMPCTable list;
    private JMFormTableList tblList;
    private boolean isLookup;
    private boolean isEditable;
    private boolean isMasterDetail;
    
    public static TblPengadaan create(String query,boolean isLookup){
        return new TblPengadaan(query,isLookup);
    }
    
    public static TblPengadaan create(String query){
        return new TblPengadaan(query,false);
    }
    public static TblPengadaan create(boolean isLookup){
        return new TblPengadaan(null,isLookup);
    }
    public static TblPengadaan create(){
        return new TblPengadaan(null,false);
    }
    
    public TblPengadaan(String query,boolean isLookup){
        this.title=R.label("TITLE_PENGADAAN");
        this.tableName="p_tb_mutasi";
        this.isLookup=isLookup;
        this.isEditable=Global.getEditor();
        this.query=query;
        if(this.query==null)this.query=QueryHelperPersediaan.qListPengadaan;
        //JMFunctions.trace(this.query);
        this.fieldProp=new ResourceField();
        Object[] boolImage={JMFunctions.getResourcePath("img/true.png", this.getClass()).getPath(),JMFunctions.getResourcePath("img/false.png", this.getClass()).getPath()};
        
        TblPjPBJ tblPjPBJ=TblPjPBJ.create(true);
        TblItem tblItem=TblItem.create(QueryHelperPersediaan.qListItemForBidangFromDate("[3]", "[24]"),true);
        tblItem.getTableList().setQueryTemplate(QueryHelperPersediaan.qListItemForBidangFromDate("[3]", "[24]"));
        
        
        
        this.tblList=JMFormTableList.create(
                this.title, 
                this.query, 
                this.fieldProp, 
                this.tableName, 
                new JMPCDBComponentWrapper(), 
                new DBNewRecordWrapper(),
                this.isLookup, 
                this.isEditable
                )
                .makeAllowNulls(21,23)
                .hideColumns(0,1,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24)
                .setBoolImage(boolImage, 26)
                .setBoolImage(boolImage, 27)
                .excludeColumnsFromUpdate(1,2,25,28)
                .setFieldAsLookup(6, tblPjPBJ.getTableList(), List.of(6,7,9,24,25), List.of(1,2,5,6,7))
                .makeFieldsHidden(1,2,5,7,8,9,10,11,12,13,14,15,16,17,18,19,24,26,27);
        this.tblList.setFormActionsWrapper(new FormActionsWrapper());
        this.tblList.setDelDependencyMasterColIndices(0);
        this.tblList.setDelDependencyDetailColIndices(1);
        this.tblList.setId("PENGADAAN");
        this.tblList.addLookupTable(tblItem.getTableList());
        this.tblList.pack();
        
        JMFormTableList det=JMFormTableList.create(
                    this.title, 
                    QueryHelperPersediaan.qDetailPengadaan("[0]"), 
                    this.fieldProp, 
                    "p_tb_mutasi_det_real", 
                    new JMPCDBComponentWrapper(), 
                    new DBNewRecordWrapper(),
                    false, 
                    true)
                .hideColumns(0,1,2)
                .excludeColumnsFromUpdate(3,5,7)
                .setFieldAsLookup(3, tblItem.getTableList(), List.of(2,3,5), List.of(0,2,5))
                .makeFieldsHidden(0,1,2);
        det.setFormActionsWrapper(new FormActionsWrapper());
        det.setQueryTemplate(QueryHelperPersediaan.qDetailPengadaan("[0]"));
        det.setNewIdDependencyMasterColIndices(0);
        this.tblList.setDetailTable(det);
        det.setMasterTable(this.tblList);
        det.setId("DETAIL_PENGADAAN");
        det.pack();
    }
    public void show(){
        this.tblList.show();
    }
    public JMFormTableList getTableList(){
        return this.tblList;
    }
}
