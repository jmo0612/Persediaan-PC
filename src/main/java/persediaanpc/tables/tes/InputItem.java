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
public class InputItem implements JMTableInterface {
    private final String title=R.label("TITLE_ITEM");
    private final JMTable table;
    private final FormInput form;
    private final FormTableLookup parentTableLookup;
    private final FormTable parentTable;
    
    private JMPCInputStringTFWeblaf fIdItem;
    private JMPCInputStringTFWeblafBtn fKetKat;
    private JMPCInputStringTFWeblaf fNmItem;
    private JMPCInputStringTFWeblaf fTotalStock;
    private JMPCInputStringTFWeblaf fBidangStock;
    private JMPCInputStringTFWeblaf fSatuan;
    private JMPCInputStringTFWeblaf fIdKat;

    private JMRow row;
    private final JMPCDBButtonGroup btnGroup;
    private boolean editMode=false;
    private boolean formClosing=false;
    
    
    public static InputItem create(JMTable table,FormTableLookup parent,boolean editing,boolean adding){
        return new InputItem(table,null,parent,editing,adding);
    }
    
    public static InputItem create(JMTable table,FormTable parent,boolean editing,boolean adding){
        return new InputItem(table,parent,null,editing,adding);
    }
    
    public InputItem(JMTable table,FormTable parentTable,FormTableLookup parentTableLookup,boolean editing,boolean adding){
        
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
        this.fIdItem=JMPCInputStringTFWeblaf.create(R.label("ID_ITEM"),R.label("PROMPT_ID_ITEM"), 20, width, horizontal).setEditable(false);
        this.fKetKat=JMPCInputStringTFWeblafBtn.create(R.label("KET_KAT"),R.label("PROMPT_KET_KAT"), 20, width, horizontal).setEditable(true);
        this.fNmItem=JMPCInputStringTFWeblaf.create(R.label("NM_ITEM"),R.label("PROMPT_NM_ITEM"), 20, width, horizontal).setEditable(true);
        this.fTotalStock=JMPCInputStringTFWeblaf.create(R.label("TOTAL_STOCK"),R.label("PROMPT_TOTAL_STOCK"), 20, width, horizontal).setEditable(false);
        this.fBidangStock=JMPCInputStringTFWeblaf.create(R.label("BIDANG_STOCK"),R.label("PROMPT_BIDANG_STOCK"), 20, width, horizontal).setEditable(false);
        this.fSatuan=JMPCInputStringTFWeblaf.create(R.label("SATUAN"),R.label("PROMPT_SATUAN"), 20, width, horizontal).setEditable(true);
        this.fIdKat=JMPCInputStringTFWeblaf.create(R.label("ID_KAT"),R.label("PROMPT_ID_KAT"), 20, width, horizontal).setEditable(true);
        

        this.table.setFormInterface(this.fIdItem, 0,true);
        this.table.setFormInterface(this.fKetKat, 1,true);
        this.table.setFormInterface(this.fNmItem, 2,true);
        this.table.setFormInterface(this.fTotalStock, 3,true);
        this.table.setFormInterface(this.fBidangStock, 4,true);
        this.table.setFormInterface(this.fSatuan, 5,true);
        this.table.setFormInterface(this.fIdKat, 6,true);


        
        this.row.displayInterface(true);
        //this.fInt.setVisible(true);
        
        
        
        Box box=Box.createVerticalBox();
        box.add(this.fIdItem);
        box.add(this.fKetKat);
        box.add(this.fNmItem);
        box.add(this.fTotalStock);
        box.add(this.fBidangStock);
        box.add(this.fSatuan);
        box.add(this.fIdKat);


        this.fIdItem.setVisible(false);
        //this.fKetKat.setVisible(false);
        //this.fNmItem.setVisible(false);
        this.fTotalStock.setVisible(false);
        this.fBidangStock.setVisible(false);
        //this.fSatuan.setVisible(false);
        this.fIdKat.setVisible(false);


        
        
        form.getInputPanel().setLayout(new FlowLayout());
        form.getInputPanel().add(box);
        form.pack();
        this.addListener();
        
        
        this.setEditMode(editing);
        //this.table.getCurrentRow().displayInterface(false);
        
        this.lockAccess();
        
        this.btnGroup.getBtnPrint().setVisible(false);
        this.btnGroup.getBtnView().setVisible(false);
        
        
        this.fKetKat.setButtonAction(new Runnable() {
            @Override
            public void run() {
                FormTableLookup frmLookKat=new FormTableLookup(null,true);
                TableKategori tbKat=TableKategori.create("select * from p_ref_kat", frmLookKat);
                JMRow res=tbKat.select();
                if(res!=null){
                    InputItem.this.row.setValueFromString(6, res.getCells().get(0).getDBValue()); 
                    InputItem.this.row.setValueFromString(1, res.getCells().get(1).getDBValue()); 
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
        this.fIdItem.setEditMode(editMode,this.row,0);
        this.fKetKat.setEditMode(editMode,this.row,1);
        this.fNmItem.setEditMode(editMode,this.row,2);
        this.fTotalStock.setEditMode(editMode,this.row,3);
        this.fBidangStock.setEditMode(editMode,this.row,4);
        this.fSatuan.setEditMode(editMode,this.row,5);
        this.fIdKat.setEditMode(editMode,this.row,6);

    }
    
    
    
    
    private void addListener(){
        this.form.addWindowListener(new WindowListener(){
            @Override
            public void windowOpened(WindowEvent e) {
                
            }

            @Override
            public void windowClosing(WindowEvent e) {
                if(InputItem.this.editMode){
                    InputItem.this.formClosing=true;
                    InputItem.this.btnGroup.btnCancelClick();
                }else{
                    InputItem.this.form.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
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
                InputItem.this.form.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
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
