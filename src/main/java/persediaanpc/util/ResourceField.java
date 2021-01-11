/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persediaanpc.util;

import com.thowo.jmjavaframework.form.JMLocaleInterface;
import persediaanpc.R;

/**
 *
 * @author jimi
 */
public class ResourceField implements JMLocaleInterface {


    @Override
    public String getFieldLabel(String fieldName) {
        fieldName=fieldName.toUpperCase();
        return R.label(fieldName);
    }

    @Override
    public String getFieldPrompt(String fieldName) {
        fieldName=fieldName.toUpperCase();
        return R.label("PROMPT_"+fieldName);
    }

    @Override
    public String getLabel(String label) {
        return R.label(label);
    }
    
}
