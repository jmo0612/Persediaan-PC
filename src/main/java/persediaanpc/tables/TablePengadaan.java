/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persediaanpc.tables;
import com.thowo.jmjavaframework.JMDate;
import persediaanpc.Global;
import persediaanpc.R;
import com.thowo.jmjavaframework.JMFormInterface;
import com.thowo.jmjavaframework.JMFormatCollection;
import com.thowo.jmjavaframework.JMFunctions;
import com.thowo.jmjavaframework.db.JMResultSet;
import com.thowo.jmjavaframework.db.JMResultSetStyle;
import com.thowo.jmjavaframework.table.JMRow;
import com.thowo.jmjavaframework.table.JMTable;
import com.thowo.jmpcframework.component.JMPCTable;
import com.thowo.jmpcframework.component.form.JMPCDBButtonGroup;
import com.thowo.jmpcframework.component.form.JMPCInputStringTFWeblaf;
import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import persediaanpc.FormTable;
import persediaanpc.FormTableLookup;
import persediaanpc.util.QueryHelperPersediaan;


/**
 *
 * @author jimi
 */


public class TablePengadaan implements JMFormInterface{
    private final String title=R.label("TITLE_PENGADAAN");
    private final String queryView;
    private final JMTable dbObject;
    private final JMPCTable table;
    private final JMPCDBButtonGroup btnGroup;
    private final List<Integer> primaryKeys;
    private final FormTableLookup parentTableLookup;
    private final FormTable parentTable;
    
    private JMRow selectedRow=null;
    private JMTable detailBackup;
    
    
    public static TablePengadaan create(String query,FormTableLookup parent){
        return new TablePengadaan(query,null,parent);
    }
    
    public static TablePengadaan create(String query,FormTable parent){
        return new TablePengadaan(query,parent,null);
    }
    
    public TablePengadaan(String query,FormTable parentTable,FormTableLookup parentTableLookup){
        this.parentTable=parentTable;
        this.parentTableLookup=parentTableLookup;
        
        if(parentTableLookup!=null){
            this.parentTableLookup.setTitle(this.title);
            this.parentTableLookup.setFilterAction(new Runnable() {
                @Override
                public void run() {
                    TablePengadaan.this.dbObject.filter(TablePengadaan.this.parentTableLookup.getSearch().getText());
                }
            });
        }
        
        if(parentTable!=null){
            this.parentTable.setTitle(this.title);
            this.parentTable.setFilterAction(new Runnable() {
                @Override
                public void run() {
                    TablePengadaan.this.dbObject.filter(TablePengadaan.this.parentTable.getSearch().getText());
                }
            });
        }
        this.queryView=query;
        
        Object[] boolImg={JMFunctions.getResourcePath("img/true.png", this.getClass()).getPath(),JMFunctions.getResourcePath("img/false.png", this.getClass()).getPath()};
        
        this.dbObject=JMTable.create(this.queryView,JMTable.DBTYPE_MYSQL);
        
        this.dbObject.getStyle()
                .setLabel(0,R.label("ID_MUTASI"))
                .setLabel(1,R.label("ID_TIPE"))
                .setLabel(2,R.label("KET_TIPE"))
                .setLabel(3,R.label("TGL_MUTASI"))
                .setLabel(4,R.label("NO_BA_MUTASI"))
                .setLabel(5,R.label("ID_JENIS_MUTASI"))
                .setLabel(6,R.label("NM_PIHAK2"))
                .setLabel(7,R.label("NIP_PIHAK2"))
                .setLabel(8,R.label("ALMT_PIHAK2"))
                .setLabel(9,R.label("JAB_PIHAK2"))
                .setLabel(10,R.label("INSTANSI_PIHAK2"))
                .setLabel(11,R.label("NM_PENGGUNA_BRG"))
                .setLabel(12,R.label("NIP_PENGGUNA_BRG"))
                .setLabel(13,R.label("JAB_PENGGUNA_BRG"))
                .setLabel(14,R.label("NM_TU_BRG"))
                .setLabel(15,R.label("NIP_TU_BRG"))
                .setLabel(16,R.label("JAB_TU_BRG"))
                .setLabel(17,R.label("NM_PENGURUS_BRG"))
                .setLabel(18,R.label("NIP_PENGURUS_BRG"))
                .setLabel(19,R.label("JAB_PENGURUS_BRG"))
                .setLabel(20,R.label("NO_DOK_DASAR"))
                .setLabel(21,R.label("TGL_DOK_DASAR"))
                .setLabel(22,R.label("NO_DOK_DASAR2"))
                .setLabel(23,R.label("TGL_DOK_DASAR2"))
                .setLabel(24,R.label("ID_BIDANG"))
                .setLabel(25,R.label("NM_BIDANG"))
                .setLabel(26,R.label("APPROVED"))
                .setLabel(27,R.label("PRINTED"))
                .setLabel(28,R.label("TOTAL"))
                .setColHidden(0)
                .setColHidden(1)
                .setColHidden(5)
                .setColHidden(6)
                .setColHidden(7)
                .setColHidden(8)
                .setColHidden(9)
                .setColHidden(10)
                .setColHidden(11)
                .setColHidden(12)
                .setColHidden(13)
                .setColHidden(14)
                .setColHidden(15)
                .setColHidden(16)
                .setColHidden(17)
                .setColHidden(18)
                .setColHidden(19)
                .setColHidden(20)
                .setColHidden(21)
                .setColHidden(22)
                .setColHidden(23)
                .setColHidden(24)
                .addFormat(26, JMResultSetStyle.FORMAT_IMAGE, boolImg)
                .addFormat(27, JMResultSetStyle.FORMAT_IMAGE, boolImg);
        this.dbObject.refresh();
        
        List<Integer> excluded=new ArrayList();
        excluded.add(1);
        excluded.add(2);
        excluded.add(25);
        excluded.add(28);
        this.dbObject.excludeColumnsFromUpdate(excluded);
        
        this.dbObject.addInterface(this);
        this.dbObject.setName("p_tb_mutasi");
        this.primaryKeys=new ArrayList();
        this.primaryKeys.add(0);
        this.dbObject.setKeyColumns(this.primaryKeys);
        
        this.table=JMPCTable.create(this.dbObject);
        JScrollPane sp=new JScrollPane(this.table);
        
        JPanel pnlTable;
        if(this.parentTable!=null){
            pnlTable=this.parentTable.getPanelTable();
        }else{
            pnlTable=this.parentTableLookup.getPanelTable();
        }
        
        
        pnlTable.removeAll();
        pnlTable.setLayout(new BorderLayout());
        pnlTable.add(sp,BorderLayout.CENTER);
        
        this.btnGroup=new JMPCDBButtonGroup(this.dbObject,this.title,false,false);
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
        
        
        JPanel pnlButtons;
        if(this.parentTable!=null){
            pnlButtons=this.parentTable.getPanelButtons();
        }else{
            pnlButtons=this.parentTableLookup.getPanelButtons();
        }
        
        
        pnlButtons.removeAll();
        pnlButtons.setLayout(new BorderLayout());
        pnlButtons.add(this.btnGroup.getEditorPanel(),BorderLayout.WEST);
        pnlButtons.add(this.btnGroup.getNavigationPanel(),BorderLayout.EAST);
        
        this.addListener();
        
        //GOTO FIRST RECORD
        if(!this.dbObject.isEmpty()){
            this.dbObject.firstRow(false);
            this.btnGroup.stateInit();
        }
        
        this.lockAccess();
        
        
        this.btnGroup.getBtnPrint().setVisible(false);
        
    }
    
    public JMRow select(){
        if(this.parentTableLookup==null)return null;
        List<Runnable> okCancelRunnables=new ArrayList();
        okCancelRunnables.add(new Runnable() {
            @Override
            public void run() {
                TablePengadaan.this.selectedRow=TablePengadaan.this.dbObject.getCurrentRow();
            }
        });
        okCancelRunnables.add(new Runnable() {
            @Override
            public void run() {
                TablePengadaan.this.selectedRow=null;
            }
        });
        this.parentTableLookup.setOkCancelRunnables(okCancelRunnables);
        this.parentTableLookup.setVisible(true);
        return this.selectedRow;
    }
    
    private void lockAccess(){
        this.btnGroup.getBtnAdd().setVisible(Global.getEditor());
        this.btnGroup.getBtnDelete().setVisible(Global.getEditor());
        this.btnGroup.getBtnEdit().setVisible(Global.getEditor());
        this.btnGroup.getBtnSave().setVisible(Global.getEditor());
        this.btnGroup.getBtnCancel().setVisible(Global.getEditor());
        this.btnGroup.getBtnPrint().setVisible(Global.getEditor());
    }
    
    private void openInput(boolean editing, boolean adding){
        //InputOPD.create(TablePengadaan.this.dbObject,parent,editing,adding);
        if(this.parentTable!=null)InputPengadaan.create(TablePengadaan.this.dbObject,this.parentTable,editing,adding);
        if(this.parentTableLookup!=null)InputPengadaan.create(TablePengadaan.this.dbObject,this.parentTableLookup,editing,adding);
    }
    
    private void selectThis(){
        if(this.parentTableLookup==null)return;
        this.parentTableLookup.closeMe(true);
    }
    
    private void backupDetail(){
        this.detailBackup=JMTable.create(QueryHelperPersediaan.qDetailPengadaan(this.selectedRow.getCells().get(0).getDBValue()), JMTable.DBTYPE_MYSQL);
        this.detailBackup.setName("p_tb_mutasi_det_real");
        List<Integer> excluded=new ArrayList();
        excluded.add(3);
        excluded.add(5);
        excluded.add(7);
        this.detailBackup.excludeColumnsFromUpdate(excluded);
    }
    
    private void restoreDetail(JMRow canceledRow){
        JMFunctions.trace("delete from p_tb_mutasi_det_real where id_mutasi='"+canceledRow.getCells().get(0).getDBValue()+"'");
        JMFunctions.getCurrentConnection().queryUpdateMySQL("delete from p_tb_mutasi_det_real where id_mutasi='"+canceledRow.getCells().get(0).getDBValue()+"'", false);
        if(this.detailBackup==null)return;
        if(!this.detailBackup.isEmpty()){
            String q="";
            this.detailBackup.firstRow(false);
            do{
                JMFunctions.getCurrentConnection().queryUpdateMySQL(this.detailBackup.getCurrentRow().getUpdateSQL(), false);
            }while(this.detailBackup.nextRow(false)!=null);
        }
        this.detailBackup=null;
    }
    
    
    private void addListener(){
        this.table.addKeyListener(new KeyListener(){
            @Override
            public void keyTyped(KeyEvent e) {
                
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==e.VK_ENTER)e.consume();
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode()==e.VK_ENTER){
                    if(TablePengadaan.this.parentTableLookup!=null){
                        TablePengadaan.this.selectThis();
                    }else TablePengadaan.this.openInput(false,false);
                }
            }
        });
        
        this.table.addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount()==2 && !e.isConsumed()){
                    if(TablePengadaan.this.parentTableLookup!=null){
                        TablePengadaan.this.selectThis();
                    }else TablePengadaan.this.openInput(false,false);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                
            }

            @Override
            public void mouseExited(MouseEvent e) {
                
            }
        });
        
        this.btnGroup.getBtnAdd().addAction(new Runnable(){
            @Override
            public void run() {
                TablePengadaan.this.openInput(true,true);
            }
        });
        this.btnGroup.getBtnEdit().addAction(new Runnable(){
            @Override
            public void run() {
                TablePengadaan.this.openInput(true,false);
            }
        });
        this.btnGroup.getBtnView().addAction(new Runnable(){
            @Override
            public void run() {
                TablePengadaan.this.openInput(false,false);
            }
        });
    }
    
    public JMPCDBButtonGroup getButtonGroup(){
        return this.btnGroup;
    }
    public JMPCTable getTable(){
        return this.table;
    }
    public Runnable filter(JMPCInputStringTFWeblaf textField){
        return new Runnable(){
            @Override
            public void run() {
                TablePengadaan.this.dbObject.filter(textField.getText());
            }
        };
    }

    public static String newId(){
        JMDate d=JMDate.now();
        String id="MUT___"+d.getYearFull()
                +JMFormatCollection.leadingZero(d.getMonth(), 2)
                +JMFormatCollection.leadingZero(d.getDayOfMonth(), 2)
                +JMFormatCollection.leadingZero(d.getHour24Int(), 2)
                +JMFormatCollection.leadingZero(d.getMinuteInt(), 2)
                +JMFormatCollection.leadingZero(d.getSecondInt(), 2);
        JMTable tmp=JMTable.create("select id_mutasi from p_tb_mutasi where id_mutasi like '"+id+"%' order by id_mutasi desc limit 1", JMTable.DBTYPE_MYSQL);
        int nInt=1;
        if(!tmp.isEmpty()){
            String tmpId=tmp.firstRow(false).getCells().get(0).getDBValue();
            tmpId=tmpId.substring(id.length());
            nInt=JMFormatCollection.strToInteger(tmpId)+1;
        }
        //JMFunctions.traceAndShow(id+JMFormatCollection.leadingZero(nInt, 6));
        return id+JMFormatCollection.leadingZero(nInt, 6);
    }
    

    @Override
    public void actionAfterAdded(JMRow rowAdded) {
        this.selectedRow=rowAdded;
        rowAdded.setValueFromString(0, this.newId()); 
        rowAdded.setValueFromString(3, Global.liveTimer.getDate().dateTimeDB()); 
        rowAdded.setValueFromString(5, "MUTASI_001"); 
        rowAdded.setValueFromString(8, "Kantor Dinas PUPR Minut"); 
        rowAdded.setValueFromString(10, "Dinas Pekerjaan Umum dan Penataan Ruang"); 
        JMTable tmp=JMTable.create("select * from p_tb_pj_barang where id_jab_barang='PgunaB'", JMTable.DBTYPE_MYSQL);
        rowAdded.setValueFromString(11, tmp.firstRow(false).getCells().get(2).getDBValue()); 
        rowAdded.setValueFromString(12, tmp.firstRow(false).getCells().get(3).getDBValue()); 
        rowAdded.setValueFromString(13, tmp.firstRow(false).getCells().get(1).getDBValue()); 
        tmp=JMTable.create("select * from p_tb_pj_barang where id_jab_barang='TUB'", JMTable.DBTYPE_MYSQL);
        rowAdded.setValueFromString(14, tmp.firstRow(false).getCells().get(2).getDBValue()); 
        rowAdded.setValueFromString(15, tmp.firstRow(false).getCells().get(3).getDBValue()); 
        rowAdded.setValueFromString(16, tmp.firstRow(false).getCells().get(1).getDBValue()); 
        tmp=JMTable.create("select * from p_tb_pj_barang where id_jab_barang='PurusB'", JMTable.DBTYPE_MYSQL);
        rowAdded.setValueFromString(17, tmp.firstRow(false).getCells().get(2).getDBValue()); 
        rowAdded.setValueFromString(18, tmp.firstRow(false).getCells().get(3).getDBValue()); 
        rowAdded.setValueFromString(19, tmp.firstRow(false).getCells().get(1).getDBValue()); 
        rowAdded.setValueFromString(26, "false");
        rowAdded.setValueFromString(27, "false"); 
        this.backupDetail();
    }

    @Override
    public void actionAfterDeleted(JMRow rowDeleted, boolean deleted, String extra) {
        if(deleted && extra==null){
            //JMFunctions.trace("DELETED");
            JMFunctions.getCurrentConnection().queryUpdateMySQL("delete from p_tb_mutasi_det_real where id_mutasi='"+rowDeleted.getCells().get(0).getDBValue()+"'", false);
            this.detailBackup=null;
        }
    }

    @Override
    public void actionAfterSaved(String updateQuery,boolean saved) {
        if(saved)this.detailBackup=null;
    }

    @Override
    public void actionAfterEdited(JMRow rowEdited) {
        this.backupDetail();
    }

    @Override
    public void actionAfterPrinted(JMRow rowPrinted) {
        
    }

    @Override
    public void actionAfterRefreshed(JMRow rowRefreshed) {
        this.detailBackup=null;
    }

    @Override
    public void actionAfterViewed(JMRow rowViewed) {
        
    }

    @Override
    public void actionAfterMovedNext(JMRow nextRow) {
        this.selectedRow=nextRow;
        this.detailBackup=null;
    }

    @Override
    public void actionAfterMovedPrev(JMRow prevRow) {
        this.selectedRow=prevRow;
        this.detailBackup=null;
    }

    @Override
    public void actionAfterMovedFirst(JMRow firstRow) {
        this.selectedRow=firstRow;
        this.detailBackup=null;
    }

    @Override
    public void actionAfterMovedLast(JMRow lastRow) {
        this.selectedRow=lastRow;
        this.detailBackup=null;
    }

    @Override
    public void actionAfterMovedtoRecord(JMRow currentRow) {
        this.selectedRow=currentRow;
        this.detailBackup=null;
    }

    @Override
    public void actionAfterCanceled(JMRow newCurrentRow, boolean canceled, JMRow canceledRow) {
        if(canceled){
            this.restoreDetail(canceledRow);
        }
        
    }

    @Override
    public void actionBeforeRefresh(JMRow rowRefreshed) {
        this.selectedRow=rowRefreshed;
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
