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
    private final FormTable parent;
    
    public static TablePengadaan create(String query,FormTable parent){
        return new TablePengadaan(query,parent);
    }
    
    public TablePengadaan(String query,FormTable parent){
        this.parent=parent;
        this.parent.setTitle(this.title);
        this.parent.setFilterAction(new Runnable() {
            @Override
            public void run() {
                TablePengadaan.this.dbObject.filter(TablePengadaan.this.parent.getSearch().getText());
            }
        });
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
        
        
        JPanel pnlButtons=parent.getPanelButtons();
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
        InputPengadaan.create(TablePengadaan.this.dbObject,parent,editing,adding);
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
                    TablePengadaan.this.openInput(false,false);
                }
            }
        });
        
        this.table.addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount()==2 && !e.isConsumed()){
                    TablePengadaan.this.openInput(false,false);
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

    
    

    @Override
    public void actionAfterAdded(JMRow rowAdded) {
        
    }

    @Override
    public void actionAfterDeleted(JMRow rowDeleted, boolean deleted) {
        
    }

    @Override
    public void actionAfterSaved(String updateQuery,boolean saved) {
        
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
    public void actionAfterCanceled(JMRow rowCanceled, boolean canceled) {
        
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
