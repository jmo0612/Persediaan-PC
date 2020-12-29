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
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import persediaanpc.FormDetail;
import persediaanpc.FormTable;
import persediaanpc.util.QueryHelperPersediaan;
/**
 *
 * @author jimi
 */
public class InputPengadaan implements JMFormInterface {
    private final String title=R.label("TITLE_OPD");
    private final JMTable table;
    private final FormDetail form;
    private final FormTable parent;
    
    private JMPCInputStringTFWeblaf fIdMutasi;
    private JMPCInputStringTFWeblaf fIdTipe;
    private JMPCInputStringTFWeblaf fKetTipe;
    private JMPCInputStringTFWeblaf fTglMutasi;
    private JMPCInputStringTFWeblaf fNoBaMutasi;
    private JMPCInputStringTFWeblaf fIdJenisMutasi;
    private JMPCInputStringTFWeblaf fNmPihak2;
    private JMPCInputStringTFWeblaf fNipPihak2;
    private JMPCInputStringTFWeblaf fAlmtPihak2;
    private JMPCInputStringTFWeblaf fJabPihak2;
    private JMPCInputStringTFWeblaf fInstansiPihak2;
    private JMPCInputStringTFWeblaf fNmPenggunaBrg;
    private JMPCInputStringTFWeblaf fNipPenggunaBrg;
    private JMPCInputStringTFWeblaf fJabPenggunaBrg;
    private JMPCInputStringTFWeblaf fNmTuBrg;
    private JMPCInputStringTFWeblaf fNipTuBrg;
    private JMPCInputStringTFWeblaf fJabTuBrg;
    private JMPCInputStringTFWeblaf fNmPengurusBrg;
    private JMPCInputStringTFWeblaf fNipPengurusBrg;
    private JMPCInputStringTFWeblaf fJabPengurusBrg;
    private JMPCInputStringTFWeblaf fNoDokDasar;
    private JMPCInputStringTFWeblaf fTglDokDasar;
    private JMPCInputStringTFWeblaf fNoDokDasar2;
    private JMPCInputStringTFWeblaf fTglDokDasar2;
    private JMPCInputStringTFWeblaf fIdBidang;
    private JMPCInputStringTFWeblaf fNmBidang;
    private JMPCInputStringTFWeblaf fApproved;
    private JMPCInputStringTFWeblaf fPrinted;
    private JMPCInputStringTFWeblaf fTotal;

    
    private JMRow row;
    private final JMPCDBButtonGroup btnGroup;
    private boolean editMode=false;
    private boolean formClosing=false;
    
    TablePengadaanDetail detPengadaan;
    
    public static InputPengadaan create(JMTable table,FormTable parent,boolean editing,boolean adding){
        return new InputPengadaan(table,parent,editing,adding);
    }
    
    public InputPengadaan(JMTable table,FormTable parent,boolean editing,boolean adding){
        
        this.parent=parent;
        this.form=new FormDetail(null,true);
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
        this.fIdMutasi=JMPCInputStringTFWeblaf.create(R.label("ID_MUTASI"),R.label("PROMPT_ID_MUTASI"), 20, width, horizontal).setEditable(false);
        this.fIdTipe=JMPCInputStringTFWeblaf.create(R.label("ID_TIPE"),R.label("PROMPT_ID_TIPE"), 20, width, horizontal).setEditable(false);
        this.fKetTipe=JMPCInputStringTFWeblaf.create(R.label("KET_TIPE"),R.label("PROMPT_KET_TIPE"), 20, width, horizontal).setEditable(false);
        this.fTglMutasi=JMPCInputStringTFWeblaf.create(R.label("TGL_MUTASI"),R.label("PROMPT_TGL_MUTASI"), 20, width, horizontal).setEditable(false);
        this.fNoBaMutasi=JMPCInputStringTFWeblaf.create(R.label("NO_BA_MUTASI"),R.label("PROMPT_NO_BA_MUTASI"), 20, width, horizontal).setEditable(false);
        this.fIdJenisMutasi=JMPCInputStringTFWeblaf.create(R.label("ID_JENIS_MUTASI"),R.label("PROMPT_ID_JENIS_MUTASI"), 20, width, horizontal).setEditable(false);
        this.fNmPihak2=JMPCInputStringTFWeblaf.create(R.label("NM_PIHAK2"),R.label("PROMPT_NM_PIHAK2"), 20, width, horizontal).setEditable(false);
        this.fNipPihak2=JMPCInputStringTFWeblaf.create(R.label("NIP_PIHAK2"),R.label("PROMPT_NIP_PIHAK2"), 20, width, horizontal).setEditable(false);
        this.fAlmtPihak2=JMPCInputStringTFWeblaf.create(R.label("ALMT_PIHAK2"),R.label("PROMPT_ALMT_PIHAK2"), 20, width, horizontal).setEditable(false);
        this.fJabPihak2=JMPCInputStringTFWeblaf.create(R.label("JAB_PIHAK2"),R.label("PROMPT_JAB_PIHAK2"), 20, width, horizontal).setEditable(false);
        this.fInstansiPihak2=JMPCInputStringTFWeblaf.create(R.label("INSTANSI_PIHAK2"),R.label("PROMPT_INSTANSI_PIHAK2"), 20, width, horizontal).setEditable(false);
        this.fNmPenggunaBrg=JMPCInputStringTFWeblaf.create(R.label("NM_PENGGUNA_BRG"),R.label("PROMPT_NM_PENGGUNA_BRG"), 20, width, horizontal).setEditable(false);
        this.fNipPenggunaBrg=JMPCInputStringTFWeblaf.create(R.label("NIP_PENGGUNA_BRG"),R.label("PROMPT_NIP_PENGGUNA_BRG"), 20, width, horizontal).setEditable(false);
        this.fJabPenggunaBrg=JMPCInputStringTFWeblaf.create(R.label("JAB_PENGGUNA_BRG"),R.label("PROMPT_JAB_PENGGUNA_BRG"), 20, width, horizontal).setEditable(false);
        this.fNmTuBrg=JMPCInputStringTFWeblaf.create(R.label("NM_TU_BRG"),R.label("PROMPT_NM_TU_BRG"), 20, width, horizontal).setEditable(false);
        this.fNipTuBrg=JMPCInputStringTFWeblaf.create(R.label("NIP_TU_BRG"),R.label("PROMPT_NIP_TU_BRG"), 20, width, horizontal).setEditable(false);
        this.fJabTuBrg=JMPCInputStringTFWeblaf.create(R.label("JAB_TU_BRG"),R.label("PROMPT_JAB_TU_BRG"), 20, width, horizontal).setEditable(false);
        this.fNmPengurusBrg=JMPCInputStringTFWeblaf.create(R.label("NM_PENGURUS_BRG"),R.label("PROMPT_NM_PENGURUS_BRG"), 20, width, horizontal).setEditable(false);
        this.fNipPengurusBrg=JMPCInputStringTFWeblaf.create(R.label("NIP_PENGURUS_BRG"),R.label("PROMPT_NIP_PENGURUS_BRG"), 20, width, horizontal).setEditable(false);
        this.fJabPengurusBrg=JMPCInputStringTFWeblaf.create(R.label("JAB_PENGURUS_BRG"),R.label("PROMPT_JAB_PENGURUS_BRG"), 20, width, horizontal).setEditable(false);
        this.fNoDokDasar=JMPCInputStringTFWeblaf.create(R.label("NO_DOK_DASAR"),R.label("PROMPT_NO_DOK_DASAR"), 20, width, horizontal).setEditable(false);
        this.fTglDokDasar=JMPCInputStringTFWeblaf.create(R.label("TGL_DOK_DASAR"),R.label("PROMPT_TGL_DOK_DASAR"), 20, width, horizontal).setEditable(false);
        this.fNoDokDasar2=JMPCInputStringTFWeblaf.create(R.label("NO_DOK_DASAR2"),R.label("PROMPT_NO_DOK_DASAR2"), 20, width, horizontal).setEditable(false);
        this.fTglDokDasar2=JMPCInputStringTFWeblaf.create(R.label("TGL_DOK_DASAR2"),R.label("PROMPT_TGL_DOK_DASAR2"), 20, width, horizontal).setEditable(false);
        this.fIdBidang=JMPCInputStringTFWeblaf.create(R.label("ID_BIDANG"),R.label("PROMPT_ID_BIDANG"), 20, width, horizontal).setEditable(false);
        this.fNmBidang=JMPCInputStringTFWeblaf.create(R.label("NM_BIDANG"),R.label("PROMPT_NM_BIDANG"), 20, width, horizontal).setEditable(false);
        this.fApproved=JMPCInputStringTFWeblaf.create(R.label("APPROVED"),R.label("PROMPT_APPROVED"), 20, width, horizontal).setEditable(false);
        this.fPrinted=JMPCInputStringTFWeblaf.create(R.label("PRINTED"),R.label("PROMPT_PRINTED"), 20, width, horizontal).setEditable(false);
        this.fTotal=JMPCInputStringTFWeblaf.create(R.label("TOTAL"),R.label("PROMPT_TOTAL"), 20, width, horizontal).setEditable(false);

        
        
        this.table.setFormInterface(this.fIdMutasi, 0,true);
        this.table.setFormInterface(this.fIdTipe, 1,true);
        this.table.setFormInterface(this.fKetTipe, 2,true);
        this.table.setFormInterface(this.fTglMutasi, 3,true);
        this.table.setFormInterface(this.fNoBaMutasi, 4,true);
        this.table.setFormInterface(this.fIdJenisMutasi, 5,true);
        this.table.setFormInterface(this.fNmPihak2, 6,true);
        this.table.setFormInterface(this.fNipPihak2, 7,true);
        this.table.setFormInterface(this.fAlmtPihak2, 8,true);
        this.table.setFormInterface(this.fJabPihak2, 9,true);
        this.table.setFormInterface(this.fInstansiPihak2, 10,true);
        this.table.setFormInterface(this.fNmPenggunaBrg, 11,true);
        this.table.setFormInterface(this.fNipPenggunaBrg, 12,true);
        this.table.setFormInterface(this.fJabPenggunaBrg, 13,true);
        this.table.setFormInterface(this.fNmTuBrg, 14,true);
        this.table.setFormInterface(this.fNipTuBrg, 15,true);
        this.table.setFormInterface(this.fJabTuBrg, 16,true);
        this.table.setFormInterface(this.fNmPengurusBrg, 17,true);
        this.table.setFormInterface(this.fNipPengurusBrg, 18,true);
        this.table.setFormInterface(this.fJabPengurusBrg, 19,true);
        this.table.setFormInterface(this.fNoDokDasar, 20,true);
        this.table.setFormInterface(this.fTglDokDasar, 21,true);
        this.table.setFormInterface(this.fNoDokDasar2, 22,true);
        this.table.setFormInterface(this.fTglDokDasar2, 23,true);
        this.table.setFormInterface(this.fIdBidang, 24,true);
        this.table.setFormInterface(this.fNmBidang, 25,true);
        this.table.setFormInterface(this.fApproved, 26,true);
        this.table.setFormInterface(this.fPrinted, 27,true);
        this.table.setFormInterface(this.fTotal, 28,true);

        
        this.row.displayInterface(true);
        //this.fInt.setVisible(true);
        
        Box box=Box.createVerticalBox();
        box.add(this.fIdMutasi);
        box.add(this.fIdTipe);
        box.add(this.fKetTipe);
        box.add(this.fTglMutasi);
        box.add(this.fNoBaMutasi);
        box.add(this.fIdJenisMutasi);
        box.add(this.fNmPihak2);
        box.add(this.fNipPihak2);
        box.add(this.fAlmtPihak2);
        box.add(this.fJabPihak2);
        box.add(this.fInstansiPihak2);
        box.add(this.fNmPenggunaBrg);
        box.add(this.fNipPenggunaBrg);
        box.add(this.fJabPenggunaBrg);
        box.add(this.fNmTuBrg);
        box.add(this.fNipTuBrg);
        box.add(this.fJabTuBrg);
        box.add(this.fNmPengurusBrg);
        box.add(this.fNipPengurusBrg);
        box.add(this.fJabPengurusBrg);
        box.add(this.fNoDokDasar);
        box.add(this.fTglDokDasar);
        box.add(this.fNoDokDasar2);
        box.add(this.fTglDokDasar2);
        box.add(this.fIdBidang);
        box.add(this.fNmBidang);
        box.add(this.fApproved);
        box.add(this.fPrinted);
        box.add(this.fTotal);

        this.fIdMutasi.setVisible(false);
        this.fIdTipe.setVisible(false);
        this.fKetTipe.setVisible(false);
        //this.fTglMutasi.setVisible(false);
        //this.fNoBaMutasi.setVisible(false);
        this.fIdJenisMutasi.setVisible(false);
        //this.fNmPihak2.setVisible(false);
        this.fNipPihak2.setVisible(false);
        this.fAlmtPihak2.setVisible(false);
        this.fJabPihak2.setVisible(false);
        this.fInstansiPihak2.setVisible(false);
        this.fNmPenggunaBrg.setVisible(false);
        this.fNipPenggunaBrg.setVisible(false);
        this.fJabPenggunaBrg.setVisible(false);
        this.fNmTuBrg.setVisible(false);
        this.fNipTuBrg.setVisible(false);
        this.fJabTuBrg.setVisible(false);
        this.fNmPengurusBrg.setVisible(false);
        this.fNipPengurusBrg.setVisible(false);
        this.fJabPengurusBrg.setVisible(false);
        //this.fNoDokDasar.setVisible(false);
        //this.fTglDokDasar.setVisible(false);
        //this.fNoDokDasar2.setVisible(false);
        //this.fTglDokDasar2.setVisible(false);
        this.fIdBidang.setVisible(false);
        //this.fNmBidang.setVisible(false);
        this.fApproved.setVisible(false);
        this.fPrinted.setVisible(false);
        //this.fTotal.setVisible(false);

        
        
        form.getInputPanel().setLayout(new FlowLayout());
        form.getInputPanel().add(box);
        form.pack();
        this.addListener();
        
        
        this.setEditMode(editing);
        //this.table.getCurrentRow().displayInterface(false);
        
        this.lockAccess();
        
        this.btnGroup.getBtnPrint().setVisible(false);
        this.btnGroup.getBtnView().setVisible(false);
        
        
        this.refreshDetail();
        
        form.setVisible(true);
    }
    
    private void refreshDetail(){
        //JMFunctions.trace(QueryHelperPersediaan.qDetailPengadaan(this.row.getCells().get(0).getDBValue()));
        this.detPengadaan=TablePengadaanDetail.create(QueryHelperPersediaan.qDetailPengadaan(this.row.getCells().get(0).getDBValue()), this);
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
        this.fIdMutasi.setEditMode(editMode,this.row,0);
        this.fIdTipe.setEditMode(editMode,this.row,1);
        this.fKetTipe.setEditMode(editMode,this.row,2);
        this.fTglMutasi.setEditMode(editMode,this.row,3);
        this.fNoBaMutasi.setEditMode(editMode,this.row,4);
        this.fIdJenisMutasi.setEditMode(editMode,this.row,5);
        this.fNmPihak2.setEditMode(editMode,this.row,6);
        this.fNipPihak2.setEditMode(editMode,this.row,7);
        this.fAlmtPihak2.setEditMode(editMode,this.row,8);
        this.fJabPihak2.setEditMode(editMode,this.row,9);
        this.fInstansiPihak2.setEditMode(editMode,this.row,10);
        this.fNmPenggunaBrg.setEditMode(editMode,this.row,11);
        this.fNipPenggunaBrg.setEditMode(editMode,this.row,12);
        this.fJabPenggunaBrg.setEditMode(editMode,this.row,13);
        this.fNmTuBrg.setEditMode(editMode,this.row,14);
        this.fNipTuBrg.setEditMode(editMode,this.row,15);
        this.fJabTuBrg.setEditMode(editMode,this.row,16);
        this.fNmPengurusBrg.setEditMode(editMode,this.row,17);
        this.fNipPengurusBrg.setEditMode(editMode,this.row,18);
        this.fJabPengurusBrg.setEditMode(editMode,this.row,19);
        this.fNoDokDasar.setEditMode(editMode,this.row,20);
        this.fTglDokDasar.setEditMode(editMode,this.row,21);
        this.fNoDokDasar2.setEditMode(editMode,this.row,22);
        this.fTglDokDasar2.setEditMode(editMode,this.row,23);
        this.fIdBidang.setEditMode(editMode,this.row,24);
        this.fNmBidang.setEditMode(editMode,this.row,25);
        this.fApproved.setEditMode(editMode,this.row,26);
        this.fPrinted.setEditMode(editMode,this.row,27);
        this.fTotal.setEditMode(editMode,this.row,28);

    }
    
    
    
    
    private void addListener(){
        this.form.addWindowListener(new WindowListener(){
            @Override
            public void windowOpened(WindowEvent e) {
                
            }

            @Override
            public void windowClosing(WindowEvent e) {
                if(InputPengadaan.this.editMode){
                    InputPengadaan.this.formClosing=true;
                    InputPengadaan.this.btnGroup.btnCancelClick();
                }else{
                    InputPengadaan.this.form.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
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
    
    public FormDetail getDetailForm(){
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
    public void actionAfterDeleted(JMRow rowDeleted, boolean deleted) {
        this.setEditMode(false);
        this.row=this.table.getCurrentRow();
        this.refreshDetail();
    }

    @Override
    public void actionAfterSaved(String updateQuery,boolean saved) {
        this.setEditMode(!saved);
        this.btnGroup.stateNav();
        this.refreshDetail();
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
        this.refreshDetail();
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
        this.refreshDetail();
    }

    @Override
    public void actionAfterMovedPrev(JMRow prevRow) {
        this.row=prevRow;
        //this.setEditMode(false);
        this.refreshDetail();
    }

    @Override
    public void actionAfterMovedFirst(JMRow firstRow) {
        this.row=firstRow;
        //this.setEditMode(false);
        this.refreshDetail();
    }

    @Override
    public void actionAfterMovedLast(JMRow lastRow) {
        this.row=lastRow;
        //this.setEditMode(false);
        this.refreshDetail();
    }

    @Override
    public void actionAfterMovedtoRecord(JMRow currentRow) {
        this.row=currentRow;
        //this.setEditMode(false);
        this.refreshDetail();
    }

    @Override
    public void actionAfterCanceled(JMRow rowCanceled, boolean canceled) {
        if(this.formClosing){
            if(canceled){
                this.form.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            }else{
                InputPengadaan.this.form.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            }
        }else{
            this.setEditMode(!canceled);
            if(canceled)this.row=rowCanceled;
        }
        this.refreshDetail();
    }

    @Override
    public void actionBeforeRefresh(JMRow rowRefreshed) {
        
    }

    @Override
    public void actionAfterFiltered(String filter) {
        this.parent.setSearch(filter);
    }

    @Override
    public void actionBeforeFilter(String filter) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
