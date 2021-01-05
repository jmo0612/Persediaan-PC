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


/**
 *
 * @author jimi
 */


public class TablePBJ implements JMFormInterface{
    private final String title=R.label("TITLE_PBJ");
    private final String queryView;
    private final JMTable dbObject;
    private final JMPCTable table;
    private final JMPCDBButtonGroup btnGroup;
    private final List<Integer> primaryKeys;
    private final FormTableLookup parentTableLookup;
    private final FormTable parentTable;
    
    private JMRow selectedRow=null;
    
    public static TablePBJ create(String query,FormTableLookup parent){
        return new TablePBJ(query,null,parent);
    }
    
    public static TablePBJ create(String query,FormTable parent){
        return new TablePBJ(query,parent,null);
    }
    
    public TablePBJ(String query,FormTable parentTable,FormTableLookup parentTableLookup){
        this.parentTable=parentTable;
        this.parentTableLookup=parentTableLookup;
        
        if(parentTableLookup!=null){
            this.parentTableLookup.setTitle(this.title);
            this.parentTableLookup.setFilterAction(new Runnable() {
                @Override
                public void run() {
                    TablePBJ.this.dbObject.filter(TablePBJ.this.parentTableLookup.getSearch().getText());
                }
            });
        }
        
        if(parentTable!=null){
            this.parentTable.setTitle(this.title);
            this.parentTable.setFilterAction(new Runnable() {
                @Override
                public void run() {
                    TablePBJ.this.dbObject.filter(TablePBJ.this.parentTable.getSearch().getText());
                }
            });
        }
        
        this.queryView=query;
        
        //Object[] boolImg={JMFunctions.getResourcePath("img/true.png", this.getClass()).getPath(),JMFunctions.getResourcePath("img/false.png", this.getClass()).getPath()};
        
        this.dbObject=JMTable.create(this.queryView,JMTable.DBTYPE_MYSQL);
        
        this.dbObject.getStyle()
                .setLabel(0,R.label("ID_PJ_PBJ"))
                .setLabel(1,R.label("NM_PJ_PBJ"))
                .setLabel(2,R.label("NIP_PJ_PBJ"))
                .setLabel(3,R.label("ID_JAB_PBJ"))
                .setLabel(4,R.label("SINGKAT_JAB_PBJ"))
                .setLabel(5,R.label("NM_JAB_PBJ"))
                .setLabel(6,R.label("ID_BIDANG"))
                .setLabel(7,R.label("NM_BIDANG"))
                .setLabel(8,R.label("NO_SK_JAB_PBJ"))
                .setLabel(9,R.label("TGL_SK_JAB_PBJ"))
                .setColHidden(0)
                .setColHidden(3)
                .setColHidden(6)
                .setAllowNull(9, true);
        this.dbObject.refresh();
        
        List<Integer> excluded=new ArrayList();
        excluded.add(4);
        excluded.add(5);
        excluded.add(7);
        this.dbObject.excludeColumnsFromUpdate(excluded);
        
        
        this.dbObject.addInterface(this);
        this.dbObject.setName("p_tb_pj_pbj");
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
                TablePBJ.this.selectedRow=TablePBJ.this.dbObject.getCurrentRow();
            }
        });
        okCancelRunnables.add(new Runnable() {
            @Override
            public void run() {
                TablePBJ.this.selectedRow=null;
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
        if(this.parentTable!=null)InputPBJ.create(TablePBJ.this.dbObject,this.parentTable,editing,adding);
        if(this.parentTableLookup!=null)InputPBJ.create(TablePBJ.this.dbObject,this.parentTableLookup,editing,adding);
    }
    
    private void selectThis(){
        if(this.parentTableLookup==null)return;
        this.parentTableLookup.closeMe(true);
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
                    if(TablePBJ.this.parentTableLookup!=null){
                        TablePBJ.this.selectThis();
                    }else TablePBJ.this.openInput(false,false);
                }
            }
        });
        
        this.table.addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount()==2 && !e.isConsumed()){
                    if(TablePBJ.this.parentTableLookup!=null){
                        TablePBJ.this.selectThis();
                    }else TablePBJ.this.openInput(false,false);
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
                TablePBJ.this.openInput(true,true);
            }
        });
        this.btnGroup.getBtnEdit().addAction(new Runnable(){
            @Override
            public void run() {
                TablePBJ.this.openInput(true,false);
            }
        });
        this.btnGroup.getBtnView().addAction(new Runnable(){
            @Override
            public void run() {
                TablePBJ.this.openInput(false,false);
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
                TablePBJ.this.dbObject.filter(textField.getText());
            }
        };
    }

    public static String newId(){
        JMDate d=JMDate.now();
        String id="PJPBJ_"+d.getYearFull()
                +JMFormatCollection.leadingZero(d.getMonth(), 2)
                +JMFormatCollection.leadingZero(d.getDayOfMonth(), 2)
                +JMFormatCollection.leadingZero(d.getHour24Int(), 2)
                +JMFormatCollection.leadingZero(d.getMinuteInt(), 2)
                +JMFormatCollection.leadingZero(d.getSecondInt(), 2);
        JMTable tmp=JMTable.create("select id_pj_pbj from p_tb_pj_pbj where id_pj_pbj like '"+id+"%' order by id_pj_pbj desc limit 1", JMTable.DBTYPE_MYSQL);
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
    }

    @Override
    public void actionAfterDeleted(JMRow rowDeleted, boolean deleted, String extra) {
        
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
