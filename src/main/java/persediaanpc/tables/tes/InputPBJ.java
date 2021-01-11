/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persediaanpc.tables.tes;

import com.thowo.jmjavaframework.JMDate;
import persediaanpc.Global;
import persediaanpc.R;
import com.thowo.jmjavaframework.JMTableInterface;
import com.thowo.jmjavaframework.JMFormatCollection;
import com.thowo.jmjavaframework.JMFunctions;
import com.thowo.jmjavaframework.table.JMRow;
import com.thowo.jmjavaframework.table.JMTable;
import com.thowo.jmpcframework.component.form.JMPCDBButtonGroup;
import com.thowo.jmpcframework.component.form.JMPCInputStringTFWeblaf;
import com.thowo.jmpcframework.component.form.JMPCInputStringTFWeblafAC;
import com.thowo.jmpcframework.component.form.JMPCInputStringTFWeblafBtn;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import persediaanpc.FormInput;
import persediaanpc.FormTable;
import persediaanpc.FormTableLookup;
import persediaanpc.FormTableLookup;
import persediaanpc.util.QueryHelperPersediaan;
/**
 *
 * @author jimi
 */
public class InputPBJ implements JMTableInterface {
    private final String title=R.label("TITLE_PBJ");
    private final JMTable table;
    private final FormInput form;
    private final FormTableLookup parentTableLookup;
    private final FormTable parentTable;
    
    private JMPCInputStringTFWeblaf fIdPjPbj;
    private JMPCInputStringTFWeblafBtn fNmPjPbj;
    private JMPCInputStringTFWeblaf fNipPjPbj;
    private JMPCInputStringTFWeblaf fIdJabPbj;
    private JMPCInputStringTFWeblafBtn fSingkatJabPbj;
    private JMPCInputStringTFWeblaf fNmJabPbj;
    private JMPCInputStringTFWeblaf fIdBidang;
    private JMPCInputStringTFWeblafBtn fNmBidang;
    private JMPCInputStringTFWeblaf fNoSkJabPbj;
    private JMPCInputStringTFWeblaf fTglSkJabPbj;

    private JMRow row;
    private final JMPCDBButtonGroup btnGroup;
    private boolean editMode=false;
    private boolean formClosing=false;
    
    
    public static InputPBJ create(JMTable table,FormTableLookup parent,boolean editing,boolean adding){
        return new InputPBJ(table,null,parent,editing,adding);
    }
    
    public static InputPBJ create(JMTable table,FormTable parent,boolean editing,boolean adding){
        return new InputPBJ(table,parent,null,editing,adding);
    }
    
    public InputPBJ(JMTable table,FormTable parentTable,FormTableLookup parentTableLookup,boolean editing,boolean adding){
        
        this.parentTable=parentTable;
        this.parentTableLookup=parentTableLookup;
        this.form=new FormInput(null,true);
        this.form.setTitle(this.title);
        this.table=table;
        this.table.addInterface(this);
        this.btnGroup=JMPCDBButtonGroup.create(table,this.title,editing,adding);
        this.btnGroup.getBtnAdd().setText(R.label("DB_ADD"));
        this.btnGroup.getBtnDelete().setText(R.label("DB_DELETE"));
        this.btnGroup.getBtnEdit().setText(R.label("DB_EDIT"));
        this.btnGroup.getBtnSave().setText(R.label("DB_SAVE"));
        this.btnGroup.getBtnCancel().setText(R.label("DB_CANCEL"));
        this.btnGroup.getBtnRefresh().setText(R.label("DB_REFRESH"));
        this.btnGroup.getBtnPrint().setText(R.label("DB_PRINT"));
        this.btnGroup.getBtnFirst().setText(R.label("DB_FIRST"));
        this.btnGroup.getBtnLast().setText(R.label("DB_LAST"));
        this.btnGroup.getBtnNext().setText(R.label("DB_NEXT"));
        this.btnGroup.getBtnPrev().setText(R.label("DB_PREV"));
        
        JPanel pnlButtons=form.getButtonsPanel();
        pnlButtons.setLayout(new BorderLayout());
        pnlButtons.add(this.btnGroup.getEditorPanel(),BorderLayout.WEST);
        pnlButtons.add(this.btnGroup.getNavigationPanel(),BorderLayout.EAST);
        this.row=this.table.getCurrentRow();
        this.view(editing,adding);
    }
    
    
    public void view(boolean editing,boolean adding){
        int width=400;
        boolean horizontal=true;
        this.fIdPjPbj=JMPCInputStringTFWeblaf.create(R.label("ID_PJ_PBJ"),R.label("PROMPT_ID_PJ_PBJ"), 20, width, horizontal).setEditable(false);
        this.fNmPjPbj=JMPCInputStringTFWeblafBtn.create(R.label("NM_PJ_PBJ"),R.label("PROMPT_NM_PJ_PBJ"), 20, width, horizontal).setEditable(true);
        this.fNipPjPbj=JMPCInputStringTFWeblaf.create(R.label("NIP_PJ_PBJ"),R.label("PROMPT_NIP_PJ_PBJ"), 20, width, horizontal).setEditable(false);
        this.fIdJabPbj=JMPCInputStringTFWeblaf.create(R.label("ID_JAB_PBJ"),R.label("PROMPT_ID_JAB_PBJ"), 20, width, horizontal).setEditable(false);
        this.fSingkatJabPbj=JMPCInputStringTFWeblafBtn.create(R.label("SINGKAT_JAB_PBJ"),R.label("PROMPT_SINGKAT_JAB_PBJ"), 20, width, horizontal).setEditable(true);
        this.fNmJabPbj=JMPCInputStringTFWeblaf.create(R.label("NM_JAB_PBJ"),R.label("PROMPT_NM_JAB_PBJ"), 20, width, horizontal).setEditable(false);
        this.fIdBidang=JMPCInputStringTFWeblaf.create(R.label("ID_BIDANG"),R.label("PROMPT_ID_BIDANG"), 20, width, horizontal).setEditable(false);
        this.fNmBidang=JMPCInputStringTFWeblafBtn.create(R.label("NM_BIDANG"),R.label("PROMPT_NM_BIDANG"), 20, width, horizontal).setEditable(true);
        this.fNoSkJabPbj=JMPCInputStringTFWeblaf.create(R.label("NO_SK_JAB_PBJ"),R.label("PROMPT_NO_SK_JAB_PBJ"), 20, width, horizontal).setEditable(true);
        this.fTglSkJabPbj=JMPCInputStringTFWeblaf.create(R.label("TGL_SK_JAB_PBJ"),R.label("PROMPT_TGL_SK_JAB_PBJ"), 20, width, horizontal).setEditable(true);

        

        this.table.setFormInterface(this.fIdPjPbj, 0,true);
        this.table.setFormInterface(this.fNmPjPbj, 1,true);
        this.table.setFormInterface(this.fNipPjPbj, 2,true);
        this.table.setFormInterface(this.fIdJabPbj, 3,true);
        this.table.setFormInterface(this.fSingkatJabPbj, 4,true);
        this.table.setFormInterface(this.fNmJabPbj, 5,true);
        this.table.setFormInterface(this.fIdBidang, 6,true);
        this.table.setFormInterface(this.fNmBidang, 7,true);
        this.table.setFormInterface(this.fNoSkJabPbj, 8,true);
        this.table.setFormInterface(this.fTglSkJabPbj, 9,true);



        
        this.row.displayInterface(true);
        //this.fInt.setVisible(true);
        
        
        
        Box box=Box.createVerticalBox();
        box.add(this.fIdPjPbj);
        box.add(this.fNmPjPbj);
        box.add(this.fNipPjPbj);
        box.add(this.fIdJabPbj);
        box.add(this.fSingkatJabPbj);
        box.add(this.fNmJabPbj);
        box.add(this.fIdBidang);
        box.add(this.fNmBidang);
        box.add(this.fNoSkJabPbj);
        box.add(this.fTglSkJabPbj);



        this.fIdPjPbj.setVisible(false);
        //this.fNmPjPbj.setVisible(false);
        //this.fNipPjPbj.setVisible(false);
        this.fIdJabPbj.setVisible(false);
        //this.fSingkatJabPbj.setVisible(false);
        //this.fNmJabPbj.setVisible(false);
        this.fIdBidang.setVisible(false);
        //this.fNmBidang.setVisible(false);
        //this.fNoSkJabPbj.setVisible(false);
        //this.fTglSkJabPbj.setVisible(false);



        
        
        form.getInputPanel().setLayout(new FlowLayout());
        form.getInputPanel().add(box);
        form.pack();
        this.addListener();
        
        
        this.setEditMode(editing);
        //this.table.getCurrentRow().displayInterface(false);
        
        this.lockAccess();
        
        this.btnGroup.getBtnPrint().setVisible(false);
        this.btnGroup.getBtnView().setVisible(false);
        
        this.fNmPjPbj.setButtonAction(new Runnable() {
            @Override
            public void run() {
                FormTableLookup frmLookPeg=new FormTableLookup(null,true);
                TablePegawai tbPeg=TablePegawai.create(QueryHelperPersediaan.qPegawaiAktif, frmLookPeg);
                JMRow res=tbPeg.select();
                if(res!=null){
                    InputPBJ.this.row.setValueFromString(1, res.getCells().get(1).getDBValue()); 
                    InputPBJ.this.row.setValueFromString(2, res.getCells().get(2).getDBValue()); 
                }
            }
        });
        
        this.fSingkatJabPbj.setButtonAction(new Runnable() {
            @Override
            public void run() {
                FormTableLookup frmLookPBJRef=new FormTableLookup(null,true);
                TablePBJRef tbPBJRef=TablePBJRef.create("select * from p_ref_jab_pbj", frmLookPBJRef);
                JMRow res=tbPBJRef.select();
                if(res!=null){
                    InputPBJ.this.row.setValueFromString(3, res.getCells().get(0).getDBValue()); 
                    InputPBJ.this.row.setValueFromString(4, res.getCells().get(1).getDBValue()); 
                    InputPBJ.this.row.setValueFromString(5, res.getCells().get(2).getDBValue()); 
                }
            }
        });
        
        this.fNmBidang.setButtonAction(new Runnable() {
            @Override
            public void run() {
                FormTableLookup frmLookBid=new FormTableLookup(null,true);
                TableBidang tbBid=TableBidang.create(QueryHelperPersediaan.qBidang, frmLookBid);
                JMRow res=tbBid.select();
                if(res!=null){
                    InputPBJ.this.row.setValueFromString(6, res.getCells().get(0).getDBValue()); 
                    InputPBJ.this.row.setValueFromString(7, res.getCells().get(1).getDBValue()); 
                }
            }
        });
        
        
        form.setVisible(true);
        this.table.removeInterface(this);
    }
    
    
    private void lockAccess(){
        this.btnGroup.getBtnAdd().setVisible(Global.getEditor());
        this.btnGroup.getBtnDelete().setVisible(Global.getEditor());
        this.btnGroup.getBtnEdit().setVisible(Global.getEditor());
        this.btnGroup.getBtnSave().setVisible(Global.getEditor());
        this.btnGroup.getBtnCancel().setVisible(Global.getEditor());
        this.btnGroup.getBtnPrint().setVisible(Global.getEditor());
    }
    
    private void setEditMode(boolean editMode){
        this.editMode=editMode;
        this.fIdPjPbj.setEditMode(editMode,this.row,0);
        this.fNmPjPbj.setEditMode(editMode,this.row,1);
        this.fNipPjPbj.setEditMode(editMode,this.row,2);
        this.fIdJabPbj.setEditMode(editMode,this.row,3);
        this.fSingkatJabPbj.setEditMode(editMode,this.row,4);
        this.fNmJabPbj.setEditMode(editMode,this.row,5);
        this.fIdBidang.setEditMode(editMode,this.row,6);
        this.fNmBidang.setEditMode(editMode,this.row,7);
        this.fNoSkJabPbj.setEditMode(editMode,this.row,8);
        this.fTglSkJabPbj.setEditMode(editMode,this.row,9);


    }
    
    
    
    
    private void addListener(){
        this.form.addWindowListener(new WindowListener(){
            @Override
            public void windowOpened(WindowEvent e) {
                
            }

            @Override
            public void windowClosing(WindowEvent e) {
                if(InputPBJ.this.editMode){
                    InputPBJ.this.formClosing=true;
                    InputPBJ.this.btnGroup.btnCancelClick();
                }else{
                    InputPBJ.this.form.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                }
            }

            @Override
            public void windowClosed(WindowEvent e) {
                
            }

            @Override
            public void windowIconified(WindowEvent e) {
                
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
                
            }

            @Override
            public void windowActivated(WindowEvent e) {
                
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
                
            }
        });
        /*this.btnGroup.getBtnEdit().setAction(new Runnable(){
            @Override
            public void run() {
                InputTes.this.setEditMode(true);
            }
        });*/
    }
    
    public FormInput getDetailForm(){
        return this.form;
    }
    
    public JMTable getMasterTable(){
        return this.table;
    }
    
    
    
    @Override
    public void actionAfterAdded(JMRow rowAdded) {
        this.row=rowAdded;
        this.setEditMode(true);
    }

    @Override
    public void actionAfterDeleted(JMRow rowDeleted, boolean deleted, String extra) {
        this.setEditMode(false);
        this.row=this.table.getCurrentRow();
    }

    @Override
    public void actionAfterSaved(String updateQuery,boolean saved) {
        this.setEditMode(!saved);
        this.btnGroup.stateNav();
    }

    @Override
    public void actionAfterEdited(JMRow rowEdited) {
        this.row=rowEdited;
        this.setEditMode(true);
    }

    @Override
    public void actionAfterPrinted(JMRow rowPrinted) {
        this.row=rowPrinted;
        this.setEditMode(false);
    }

    @Override
    public void actionAfterRefreshed(JMRow rowRefreshed) {
        this.row=rowRefreshed;
        this.setEditMode(false);
    }

    @Override
    public void actionAfterViewed(JMRow rowViewed) {
        this.row=rowViewed;
        this.setEditMode(false);
    }

    @Override
    public void actionAfterMovedNext(JMRow nextRow) {
        this.row=nextRow;
        //this.setEditMode(false);
    }

    @Override
    public void actionAfterMovedPrev(JMRow prevRow) {
        this.row=prevRow;
        //this.setEditMode(false);
    }

    @Override
    public void actionAfterMovedFirst(JMRow firstRow) {
        this.row=firstRow;
        //this.setEditMode(false);
    }

    @Override
    public void actionAfterMovedLast(JMRow lastRow) {
        this.row=lastRow;
        //this.setEditMode(false);
    }

    @Override
    public void actionAfterMovedtoRecord(JMRow currentRow) {
        this.row=currentRow;
        //this.setEditMode(false);
    }

    @Override
    public void actionAfterCanceled(JMRow newCurrentRow, boolean canceled, JMRow canceledRow) {
        if(this.formClosing){
            if(canceled){
                this.form.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            }else{
                InputPBJ.this.form.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            }
        }else{
            this.setEditMode(!canceled);
            if(canceled)this.row=newCurrentRow;
        }
    }

    @Override
    public void actionBeforeRefresh(JMRow rowRefreshed) {
        
    }

    @Override
    public void actionAfterFiltered(String filter) {
        //this.parent.setSearch(filter);
    }

    @Override
    public void actionBeforeFilter(String filter) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
