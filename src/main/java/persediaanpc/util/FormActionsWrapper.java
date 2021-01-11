/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persediaanpc.util;

import com.thowo.jmjavaframework.JMFunctions;
import com.thowo.jmjavaframework.form.JMFormActionsWrapperInterface;
import com.thowo.jmjavaframework.form.JMFormTableList;
import com.thowo.jmjavaframework.table.JMRow;
import com.thowo.jmjavaframework.table.JMTable;

/**
 *
 * @author jimi
 */
public class FormActionsWrapper implements JMFormActionsWrapperInterface {

    private void setPengadaanAutoField(JMFormTableList me){
        JMRow master=me.getMasterTable().getSelectedRow();
        JMRow detail=me.getSelectedRow();
        JMTable detTbl=me.getDbObject();
        detail.setValueFromString(7, String.valueOf(detail.getCells().get(4).getValueDouble()*detail.getCells().get(6).getValueDouble()));
        Double tot=0.0;
        if(!detTbl.isEmpty()){
            JMRow bu=detTbl.getCurrentRow();
            detTbl.firstRow(false);
            do{
                tot+=detTbl.getCurrentRow().getCells().get(7).getValueDouble();
            }while(detTbl.nextRow(false)!=null);
        }
        master.setValueFromString(28, String.valueOf(tot));
    }
    private void setMutasiEdited(JMFormTableList me){
        me.getSelectedRow().setValueFromString(26, "false");
        me.getSelectedRow().setValueFromString(27, "false");
    }
    private void prosesBuku(){
        MutasiBuku.create();
    }
    
    @Override
    public void formOnSaved(JMFormTableList me, String id) {
        if(me.getTableName().equals("p_tb_mutasi_det_real") && id.equals("DETAIL_PENGADAAN")){
            this.setPengadaanAutoField(me);
        }else if(me.getTableName().equals("p_tb_mutasi")){
            this.prosesBuku();
            me.getDbObject().refresh();
        }
    }

    @Override
    public void formOnEdited(JMFormTableList me, String id) {
        if(me.getTableName().equals("p_tb_mutasi")){
            this.setMutasiEdited(me);
        }
    }
    
}
