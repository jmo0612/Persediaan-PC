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
public class TblTipeRef {
    private String title;
    private String query;
    private ResourceField fieldProp;
    private String tableName;
    //private JMPCTable list;
    private JMFormTableList tblList;
    private boolean isLookup;
    private boolean isEditable;
    private boolean isMasterDetail;
    
    public static TblTipeRef create(String query,boolean isLookup){
        return new TblTipeRef(query,isLookup);
    }
    
    public static TblTipeRef create(String query){
        return new TblTipeRef(query,false);
    }
    public static TblTipeRef create(boolean isLookup){
        return new TblTipeRef(null,isLookup);
    }
    public static TblTipeRef create(){
        return new TblTipeRef(null,false);
    }
    public TblTipeRef(String query,boolean isLookup){
        this.title=R.label("TITLE_TIPE");
        this.tableName="p_ref_tipe";
        this.isLookup=isLookup;
        this.isEditable=Global.getEditor();
        this.query=query;
        if(this.query==null)this.query="select * from p_ref_tipe";
        this.fieldProp=new ResourceField();
        //this.list=JMPCTable.create();
        //Object[] boolImage={JMFunctions.getResourcePath("img/true.png", this.getClass()).getPath(),JMFunctions.getResourcePath("img/false.png", this.getClass()).getPath()};
        
        
        this.tblList=JMFormTableList.create(
                this.title, 
                this.query, 
                this.fieldProp, 
                this.tableName, 
                new JMPCDBComponentWrapper(), 
                new DBNewRecordWrapper(),
                this.isLookup, 
                this.isEditable)
                .hideColumns(0)
                .makeFieldsHidden(0)
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
