/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.lang.reflect.ParameterizedType;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import util.FacesUtils;
import util.GenericLazyDataModel;
import util.Util;

/**
 *
 * @author Maycon
 */
public abstract class GenericController <T> implements GenericControllerInterface {
    protected T value;
    protected T selected;
    protected T edit;
    protected boolean newValue;
    protected Class<T> Class;
    protected GenericLazyDataModel<T, Integer> lazyData;    

    public GenericController() {
        try {
            this.Class = (Class<T>)((ParameterizedType) getClass()
                    .getGenericSuperclass()).getActualTypeArguments()[0];
            value = this.Class.newInstance();
            edit = this.Class.newInstance();
            newValue = false;
            lazyData = new GenericLazyDataModel<T, Integer>(Util.getTableName(this.Class), this.Class);
        } catch (InstantiationException ex) {
            Logger.getLogger(GenericController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(GenericController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public boolean getMenu(){
        return selected == null ? false : true;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public boolean isNewValue() {
        return newValue;
    }

    public void setNewValue(boolean newValue) {
        this.newValue = newValue;
    }

    public T getSelected() {
        return selected;
    }

    public void setSelected(T selected) {
        this.selected = selected;
    }

    public T getEdit() {
        return edit;
    }

    public void setEdit(T edit) {
        this.edit = edit;
    }

    @Override
    public void onSelectListener(){
        edit = selected;
    }

    public GenericLazyDataModel<T, Integer> getLazyData() {
        return lazyData;
    }

    public void setLazyData(GenericLazyDataModel<T, Integer> lazyData) {
        this.lazyData = lazyData;
    }

    @Override
    public void addFacesMsg(String msg, FacesMessage.Severity level) {
       FacesUtils.addFacesMsg(msg, level);
    }        

}
