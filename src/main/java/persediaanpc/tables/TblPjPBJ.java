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
import persediaanpc.util.QueryHelperPersediaan;
import persediaanpc.util.ResourceField;

/**
 *
 * @author jimi
 */
public class TblPjPBJ {
    private String title;
    private String query;
    private ResourceField fieldProp;
    private String tableName;
    //private JMPCTable list;
    private JMFormTableList tblList;
    private boolean isLookup;
    private boolean isEditable;
    private boolean isMasterDetail;
    
    public static TblPjPBJ create(String query,boolean isLookup){
        return new TblPjPBJ(query,isLookup);
    }
    
    public static TblPjPBJ create(String query){
        return new TblPjPBJ(query,false);
    }
    public static TblPjPBJ create(boolean isLookup){
        return new TblPjPBJ(null,isLookup);
    }
    public static TblPjPBJ create(){
        return new TblPjPBJ(null,false);
    }
    public TblPjPBJ(String query,boolean isLookup){
        this.title=R.label("TITLE_PBJ");
        this.tableName="p_tb_pj_pbj";
        this.isLookup=isLookup;
        this.isEditable=Global.getEditor();
        this.query=query;
        if(this.query==null)this.query=QueryHelperPersediaan.qPBJ;
        this.fieldProp=new ResourceField();
        //this.list=JMPCTable.create();
        //Object[] boolImage={JMFunctions.getResourcePath("img/true.png", this.getClass()).getPath(),JMFunctions.getResourcePath("img/false.png", this.getClass()).getPath()};
        TblJabPBJRef tblJabPBJRef=TblJabPBJRef.create(true);
        TblBidang tblBidang=TblBidang.create(true);
        
        this.tblList=JMFormTableList.create(
                this.title, 
                this.query, 
                this.fieldProp, 
                this.tableName, 
                new JMPCDBComponentWrapper(), 
                new DBNewRecordWrapper(),
                this.isLookup, 
                this.isEditable)
                .hideColumns(0,3,6)
                .excludeColumnsFromUpdate(4,5,7)
                .setFieldAsLookup(4, tblJabPBJRef.getTableList(), List.of(3,4,5), List.of(0,1,2))
                .setFieldAsLookup(7, tblBidang.getTableList(), List.of(6,7), List.of(0,1))
                .makeFieldsHidden(0,3,6);
        this.tblList.pack();
    }
    public void show(){
        this.tblList.show();
    }
    public JMFormTableList getTableList(){
        return this.tblList;
    }
}
