/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persediaanpc.tables;

import persediaanpc.tables.TableTes;
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
public class TblPegawai {
    private String title;
    private String query;
    private ResourceField fieldProp;
    private String tableName;
    //private JMPCTable list;
    private JMFormTableList tblList;
    private boolean isLookup;
    private boolean isEditable;
    private boolean isMasterDetail;
    
    public static TblPegawai create(String query,boolean isLookup){
        return new TblPegawai(query,isLookup);
    }
    
    public static TblPegawai create(String query){
        return new TblPegawai(query,false);
    }
    public static TblPegawai create(boolean isLookup){
        return new TblPegawai(null,isLookup);
    }
    public static TblPegawai create(){
        return new TblPegawai(null,false);
    }
    public TblPegawai(String query,boolean isLookup){
        this.title=R.label("TITLE_PEGAWAI");
        this.tableName="p_tb_pegawai";
        this.isLookup=isLookup;
        this.isEditable=Global.getEditor();
        this.query=query;
        if(this.query==null)this.query=QueryHelperPersediaan.qPegawai;
        this.fieldProp=new ResourceField();
        //this.list=JMPCTable.create();
        //JMFunctions.trace(JMFunctions.resourceToCache("img/true.png", this.getClass()).getPath());
        //JMFunctions.trace(JMFunctions.getResourcePath("img/true.png", this.getClass()).getPath());
        //Object[] boolImage={JMFunctions.getResourcePath("img/true.png", this.getClass()).getPath(),JMFunctions.getResourcePath("img/false.png", this.getClass()).getPath()};
        Object[] boolImage={JMFunctions.resourceToCache("img/true.png", this.getClass()).getPath(),JMFunctions.resourceToCache("img/false.png", this.getClass()).getPath()};
        this.tblList=JMFormTableList.create(
                this.title, 
                this.query, 
                this.fieldProp, 
                this.tableName, 
                new JMPCDBComponentWrapper(), 
                new DBNewRecordWrapper(),
                this.isLookup, 
                this.isEditable)
                .hideColumns(0,4)
                .setBoolImage(boolImage, 6)
                .setFieldAsSwitch(6, R.label("AKTIF_TRUE"), R.label("AKTIF_FALSE"))
                .excludeColumnsFromUpdate(5)
                .makeFieldsHidden(0,4)
                .makeFieldsDisabled(0,4)
                .setAllowPrint(false);
        this.tblList.pack();
    }
    public void show(){
        this.tblList.show();
    }
    public JMFormTableList getTableList(){
        return this.tblList;
    }
}
