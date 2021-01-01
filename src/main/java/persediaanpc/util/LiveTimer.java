/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persediaanpc.util;

import com.thowo.jmjavaframework.JMDate;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.Timer;
import persediaanpc.Global;

/**
 *
 * @author jimi
 */
public class LiveTimer {
    private JMDate date=JMDate.now();
    private boolean ticking=true;
    private List<JLabel> labels=new ArrayList();
    private Timer timer;
    private boolean override=false;
    private FormTimerSetting form;
    
    public LiveTimer(){
        
        this.timer=new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LiveTimer.this.date=JMDate.now();
                LiveTimer.this.refreshDate();
            }
        });
        this.timer.start();
        
        this.form=new FormTimerSetting(null,true,this);
    }
    
    public void setOverride(boolean override){
        this.override=override;
    }
    
    public boolean isOverride(){
        return this.override;
    }
    
    private void refreshDate(){
        for(JLabel lbl:this.labels){
            lbl.setText(this.date.dateTimeFull24());
        }
    }
    
    public void setDate(JMDate date){
        this.date=date;
        this.refreshDate();
    }
    public JMDate getDate(){
        return this.date;
    }
    public void setTicking(boolean ticking){
        this.ticking=ticking;
        if(ticking){
            this.timer.start();
        }else{
            this.timer.stop();
        }
    }
    public boolean isTicking(){
        return this.ticking;
    }
    
    public void addLabelComponent(JLabel label){
        if(label==null)return;
        label.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount()==2){
                    if(!LiveTimer.this.form.isVisible() && Global.getItUser())LiveTimer.this.form.setVisible(true);
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
        this.labels.add(label);
    }
    public JLabel getLabelComponentAt(int index){
        if(index>=this.labels.size())return null;
        return this.labels.get(index);
    }
}
