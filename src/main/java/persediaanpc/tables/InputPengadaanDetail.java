/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persediaanpc.tables;

import persediaanpc.Global;
import persediaanpc.R;
import com.thowo.jmjavaframework.JMFormInterface;
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
import persediaanpc.FormDetail;
import persediaanpc.FormTableLookup;
import persediaanpc.util.QueryHelperPersediaan;
/**
 *
 * @author jimi
 */
public class InputPengadaanDetail implements JMFormInterface {
    private final String title=R.label("TITLE_PENGADAAN_DETAIL");
    private final JMTable table;
    private final FormInput form;
    private final FormDetail parent;
    
    private JMPCInputStringTFWeblaf fIdDetMutasiReal;
    private JMPCInputStringTFWeblaf fIdMutasi;
    private JMPCInputStringTFWeblaf fIdItem;
    private JMPCInputStringTFWeblaf fRealQty;
    private JMPCInputStringTFWeblaf fRealHargaPenerimaan;
    private JMPCInputStringTFWeblafBtn fNmItem;
    private JMPCInputStringTFWeblaf fSatuan;
    private JMPCInputStringTFWeblaf fSubtotal;

    private JMRow row;
    private final JMPCDBButtonGroup btnGroup;
    private boolean editMode=false;
    private boolean formClosing=false;
    
    TablePengadaanDetail detPengadaan;
    
    public static InputPengadaanDetail create(JMTable table,FormDetail parent,boolean editing,boolean adding){
        return new InputPengadaanDetail(table,parent,editing,adding);
    }
    
    public InputPengadaanDetail(JMTable table,FormDetail parent,boolean editing,boolean adding){
        
        this.parent=parent;
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
        this.fIdDetMutasiReal=JMPCInputStringTFWeblaf.create(R.label("ID_DET_MUTASI_REAL"),R.label("PROMPT_ID_DET_MUTASI_REAL"), 20, width, horizontal).setEditable(false);
        this.fIdMutasi=JMPCInputStringTFWeblaf.create(R.label("ID_MUTASI"),R.label("PROMPT_ID_MUTASI"), 20, width, horizontal).setEditable(false);
        this.fIdItem=JMPCInputStringTFWeblaf.create(R.label("ID_ITEM"),R.label("PROMPT_ID_ITEM"), 20, width, horizontal).setEditable(false);
        this.fNmItem=JMPCInputStringTFWeblafBtn.create(R.label("NM_ITEM"),R.label("PROMPT_NM_ITEM"), 20, width, horizontal).setEditable(true);
        this.fRealQty=JMPCInputStringTFWeblaf.create(R.label("REAL_QTY"),R.label("PROMPT_REAL_QTY"), 20, width, horizontal).setEditable(true);
        this.fSatuan=JMPCInputStringTFWeblaf.create(R.label("SATUAN"),R.label("PROMPT_SATUAN"), 20, width, horizontal).setEditable(false);
        this.fRealHargaPenerimaan=JMPCInputStringTFWeblaf.create(R.label("REAL_HARGA_PENERIMAAN"),R.label("PROMPT_REAL_HARGA_PENERIMAAN"), 20, width, horizontal).setEditable(true);
        this.fSubtotal=JMPCInputStringTFWeblaf.create(R.label("SUBTOTAL"),R.label("PROMPT_SUBTOTAL"), 20, width, horizontal).setEditable(false);

        
        this.table.setFormInterface(this.fIdDetMutasiReal, 0,true);
        this.table.setFormInterface(this.fIdMutasi, 1,true);
        this.table.setFormInterface(this.fIdItem, 2,true);
        this.table.setFormInterface(this.fNmItem, 3,true);
        this.table.setFormInterface(this.fRealQty, 4,true);
        this.table.setFormInterface(this.fSatuan, 5,true);
        this.table.setFormInterface(this.fRealHargaPenerimaan, 6,true);
        this.table.setFormInterface(this.fSubtotal, 7,true);


        
        this.row.displayInterface(true);
        //this.fInt.setVisible(true);
        
        Box box=Box.createVerticalBox();
        box.add(this.fIdDetMutasiReal);
        box.add(this.fIdMutasi);
        box.add(this.fIdItem);
        box.add(this.fNmItem);
        box.add(this.fRealQty);
        box.add(this.fSatuan);
        box.add(this.fRealHargaPenerimaan);
        box.add(this.fSubtotal);


        this.fIdDetMutasiReal.setVisible(false);
        this.fIdMutasi.setVisible(false);
        this.fIdItem.setVisible(false);
        //this.fNmItem.setVisible(false);
        //this.fRealQty.setVisible(false);
        //this.fSatuan.setVisible(false);
        //this.fRealHargaPenerimaan.setVisible(false);
        this.fSubtotal.setVisible(false);


        
        
        form.getInputPanel().setLayout(new FlowLayout());
        form.getInputPanel().add(box);
        form.pack();
        this.addListener();
        
        
        this.setEditMode(editing);
        //this.table.getCurrentRow().displayInterface(false);
        
        this.lockAccess();
        
        this.btnGroup.getBtnPrint().setVisible(false);
        this.btnGroup.getBtnView().setVisible(false);
        
        
        this.fNmItem.setButtonAction(new Runnable() {
            @Override
            public void run() {
                FormTableLookup frmLookItem=new FormTableLookup(null,true);
                TableItem tbItem=TableItem.create(QueryHelperPersediaan.qListItemForBidangFromDate(Global.getCurDate().dateTimeDB(), Global.getCurIdBidang()), frmLookItem);
                JMRow res=tbItem.select();
                if(res!=null){
                    InputPengadaanDetail.this.row.setValueFromString(2, res.getCells().get(0).getDBValue()); 
                    InputPengadaanDetail.this.row.setValueFromString(3, res.getCells().get(2).getDBValue()); 
                    InputPengadaanDetail.this.row.setValueFromString(5, res.getCells().get(5).getDBValue()); 
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
        this.fIdDetMutasiReal.setEditMode(editMode,this.row,0);
        this.fIdMutasi.setEditMode(editMode,this.row,1);
        this.fIdItem.setEditMode(editMode,this.row,2);
        this.fNmItem.setEditMode(editMode,this.row,3);
        this.fRealQty.setEditMode(editMode,this.row,4);
        this.fSatuan.setEditMode(editMode,this.row,5);
        this.fRealHargaPenerimaan.setEditMode(editMode,this.row,6);
        this.fSubtotal.setEditMode(editMode,this.row,7);


    }
    
    
    
    
    private void addListener(){
        this.form.addWindowListener(new WindowListener(){
            @Override
            public void windowOpened(WindowEvent e) {
                
            }

            @Override
            public void windowClosing(WindowEvent e) {
                if(InputPengadaanDetail.this.editMode){
                    InputPengadaanDetail.this.formClosing=true;
                    InputPengadaanDetail.this.btnGroup.btnCancelClick();
                }else{
                    InputPengadaanDetail.this.form.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
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
    
    
    private void updateCurentSubtotal(){
        double q=this.row.getCells().get(4).getValueDouble();
        double p=this.row.getCells().get(6).getValueDouble();
        double s=q*p;
        this.row.setValueFromString(7, String.valueOf(s));
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
        this.updateCurentSubtotal();
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
                InputPengadaanDetail.this.form.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
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
