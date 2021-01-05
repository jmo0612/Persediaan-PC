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
import persediaanpc.FormDetail;
import persediaanpc.FormTable;


/**
 *
 * @author jimi
 */


public class TablePengadaanDetail implements JMFormInterface{
    private final String title=R.label("TITLE_PENGADAAN_DETAIL");
    private final String queryView;
    private final JMTable dbObject;
    private final JMPCTable table;
    private final JMPCDBButtonGroup btnGroup;
    private final List<Integer> primaryKeys;
    private final FormDetail parent;
    
    private InputPengadaan pengadaan;
    
    public static TablePengadaanDetail create(String query,InputPengadaan pengadaan){
        return new TablePengadaanDetail(query,pengadaan);
    }
    
    public TablePengadaanDetail(String query,InputPengadaan pengadaan){
        this.pengadaan=pengadaan;
        this.parent=pengadaan.getDetailForm();
        this.parent.setTitle(this.title);
        this.queryView=query;
        
        //Object[] boolImg={JMFunctions.getResourcePath("img/true.png", this.getClass()).getPath(),JMFunctions.getResourcePath("img/false.png", this.getClass()).getPath()};
        
        this.dbObject=JMTable.create(this.queryView,JMTable.DBTYPE_MYSQL);
        
        this.dbObject.getStyle()
                .setLabel(0,R.label("ID_DET_MUTASI_REAL"))
                .setLabel(1,R.label("ID_MUTASI"))
                .setLabel(2,R.label("ID_ITEM"))
                .setLabel(3,R.label("NM_ITEM"))
                .setLabel(4,R.label("REAL_QTY"))
                .setLabel(5,R.label("SATUAN"))
                .setLabel(6,R.label("REAL_HARGA_PENERIMAAN"))
                .setLabel(7,R.label("SUBTOTAL"))
                .setColHidden(0)
                .setColHidden(1)
                .setColHidden(2);
        this.dbObject.refresh();
        
        List<Integer> excluded=new ArrayList();
        excluded.add(3);
        excluded.add(5);
        excluded.add(7);
        this.dbObject.excludeColumnsFromUpdate(excluded);
        
        this.dbObject.addInterface(this);
        this.dbObject.setName("p_tb_mutasi_det_real");
        this.primaryKeys=new ArrayList();
        this.primaryKeys.add(0);
        this.dbObject.setKeyColumns(this.primaryKeys);
        
        this.table=JMPCTable.create(this.dbObject);
        JScrollPane sp=new JScrollPane(this.table);
        JPanel pnlTable=parent.getPanelTable();
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
        
        
        JPanel pnlButtons=parent.getDetailPanelButtons();
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
        
        this.lockAccess(false);
        
        
        this.btnGroup.getBtnPrint().setVisible(false);
        
    }
    
    public void lockAccess(boolean editable){
        this.btnGroup.getBtnAdd().setVisible(Global.getEditor() && editable);
        this.btnGroup.getBtnDelete().setVisible(Global.getEditor() && editable);
        this.btnGroup.getBtnEdit().setVisible(Global.getEditor() && editable);
        this.btnGroup.getBtnSave().setVisible(Global.getEditor() && editable);
        this.btnGroup.getBtnCancel().setVisible(Global.getEditor() && editable);
        this.btnGroup.getBtnPrint().setVisible(Global.getEditor());
    }
    
    
    private void openInput(boolean editing, boolean adding){
        //InputOPD.create(TablePengadaanDetail.this.dbObject,parent,editing,adding);
        InputPengadaanDetail.create(TablePengadaanDetail.this.dbObject,parent,editing,adding);
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
                    TablePengadaanDetail.this.openInput(false,false);
                }
            }
        });
        
        this.table.addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount()==2 && !e.isConsumed()){
                    TablePengadaanDetail.this.openInput(false,false);
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
                TablePengadaanDetail.this.openInput(true,true);
            }
        });
        this.btnGroup.getBtnEdit().addAction(new Runnable(){
            @Override
            public void run() {
                TablePengadaanDetail.this.openInput(true,false);
            }
        });
        this.btnGroup.getBtnView().addAction(new Runnable(){
            @Override
            public void run() {
                TablePengadaanDetail.this.openInput(false,false);
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
                TablePengadaanDetail.this.dbObject.filter(textField.getText());
            }
        };
    }

    public String newId(){
        JMDate d=JMDate.now();
        String id="DMR___"+this.pengadaan.getCurIdMutasi()+d.getYearFull()
                +JMFormatCollection.leadingZero(d.getMonth(), 2)
                +JMFormatCollection.leadingZero(d.getDayOfMonth(), 2)
                +JMFormatCollection.leadingZero(d.getHour24Int(), 2)
                +JMFormatCollection.leadingZero(d.getMinuteInt(), 2)
                +JMFormatCollection.leadingZero(d.getSecondInt(), 2);
        JMTable tmp=JMTable.create("select id_det_mutasi_real from p_tb_mutasi_det_real where id_det_mutasi_real like '"+id+"%' order by id_det_mutasi_real desc limit 1", JMTable.DBTYPE_MYSQL);
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
        rowAdded.setValueFromString(0, this.newId()); 
        rowAdded.setValueFromString(1, this.pengadaan.getCurIdMutasi()); 
    }

    @Override
    public void actionAfterDeleted(JMRow rowDeleted, boolean deleted, String extra) {
        
    }

    @Override
    public void actionAfterSaved(String updateQuery,boolean saved) {
        this.pengadaan.overrideTotal();
    }

    @Override
    public void actionAfterEdited(JMRow rowEdited) {
        
    }

    @Override
    public void actionAfterPrinted(JMRow rowPrinted) {
        
    }

    @Override
    public void actionAfterRefreshed(JMRow rowRefreshed) {
        
    }

    @Override
    public void actionAfterViewed(JMRow rowViewed) {
        
    }

    @Override
    public void actionAfterMovedNext(JMRow nextRow) {
        
    }

    @Override
    public void actionAfterMovedPrev(JMRow prevRow) {
        
    }

    @Override
    public void actionAfterMovedFirst(JMRow firstRow) {
        
    }

    @Override
    public void actionAfterMovedLast(JMRow lastRow) {
        
    }

    @Override
    public void actionAfterMovedtoRecord(JMRow currentRow) {
        
    }

    @Override
    public void actionAfterCanceled(JMRow newCurrentRow, boolean canceled, JMRow canceledRow) {
        
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
