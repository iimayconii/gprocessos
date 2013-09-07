/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import dao.services.HibernateUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.annotations.common.util.StringHelper;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.Component;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.type.Type;
import org.hibernate.mapping.Property;

/**
 *
 * @author maycon
 */
public class QueryModel {

    private String nomeDaPropriedade;
    private String nomeDaColuna;
    private String nomeDaTabela;
    private String id;
    private String clazz;
    private Type type;
    List<QueryModel> childrens;
    QueryModel parent;
    QueryModel lastChildren;

    public static QueryModel setModel(Class clazz, String name, QueryModel parent) {
        QueryModel model = new QueryModel();
        PersistentClass classMapping = HibernateUtil.getConfiguration().getClassMapping(clazz.getName());
        Property property = classMapping.getProperty(name);
        String columnName = getColumnName(property, name);
        model.setNomeDaColuna(columnName);
        model.setNomeDaPropriedade(name);
        model.setNomeDaTabela(classMapping.getTable().getName());
        model.setId(classMapping.getIdentifierProperty().getName());
        model.setType(classMapping.getProperty(name).getType());
        if (parent != null) {
            parent.getChildrens().add(model);
            parent.setLastChildren(model);
            model.setParent(parent);
        } else {
            parent = model;
            parent.setChildrens(new ArrayList<QueryModel>());
            parent.setLastChildren(parent);
            parent.setParent(null);
        }
        return parent;

    }

    public static String getIdentifierName(Class clazz) {
        PersistentClass classMapping = HibernateUtil.getConfiguration().getClassMapping(clazz.getName());
        return classMapping.getIdentifierProperty().getName();
    }

    public static String createQuery(QueryModel model) {
        String query = "";
        QueryModel parent = model;
        for (int i = 0; i < model.getChildrens().size(); i++) {
            QueryModel children = model.getChildrens().get(i);
            //query += "left join "+children.nomeDaTabela+ " as _"+children.nomeDaTabela+" on _"+children.nomeDaTabela+"."+children.id+" = _"+parent.nomeDaTabela+"."+parent.nomeDaColuna;
            query += "left join " + children.nomeDaTabela + " on " + children.nomeDaTabela + "." + children.id + " = " + parent.nomeDaTabela + "." + parent.nomeDaColuna;
            parent = children;
        }
        return query;
    }

    public static String createOrder(QueryModel model, String sortFilder) {
        String query = "";
        QueryModel parent = model;
        QueryModel lastChildren1 = model.getLastChildren();
        System.out.println("Nome da propriedade" + lastChildren1.nomeDaPropriedade);
//        String[] split;
//        if (sortFilder.contains(".")) {
//             split = sortFilder.split("\\.");
//            
//        }else{
//            split = new String[1];
//            split[0] = sortFilder;
//        }
//
//
//        for (int i = 0; i < model.getChildrens().size(); i++) {
//            QueryModel children = model.getChildrens().get(i);
//            if(children.getNomeDaPropriedade().equalsIgnoreCase(split[0])){
//                if(split.length > 1){
//                    query += children.getNomeDaColuna() + "." + split[1];
//                }else{
//                    query += children.getNomeDaColuna();
//                }
//                
//            }            
//            //query += "left join "+children.nomeDaTabela+ " as _"+children.nomeDaTabela+" on _"+children.nomeDaTabela+"."+children.id+" = _"+parent.nomeDaTabela+"."+parent.nomeDaColuna;
//            //query += "left join " + children.nomeDaTabela + " on " + children.nomeDaTabela + "." + children.id + " = " + parent.nomeDaTabela + "." + parent.nomeDaColuna;
//            parent = children;
//        }
        return query;
    }

    public static String getColumnName(Property property, String name) {
        if (property.isComposite()) {
            Component comp = (Component) property.getValue();
            property = comp.getProperty(StringHelper.unroot(name));
            assert !property.isComposite();
        }
        Iterator<?> columnIterator = property.getColumnIterator();
        Column col = (Column) columnIterator.next();
        assert !columnIterator.hasNext();
        return col.getName();
    }

    public QueryModel getLastChildren() {
        return lastChildren;
    }

    public void setLastChildren(QueryModel lastChildren) {
        this.lastChildren = lastChildren;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getNomeDaPropriedade() {
        return nomeDaPropriedade;
    }

    public void setNomeDaPropriedade(String nomeDaPropriedade) {
        this.nomeDaPropriedade = nomeDaPropriedade;
    }

    public String getNomeDaColuna() {
        return nomeDaColuna;
    }

    public void setNomeDaColuna(String nomeDaColuna) {
        this.nomeDaColuna = nomeDaColuna;
    }

    public String getNomeDaTabela() {
        return nomeDaTabela;
    }

    public void setNomeDaTabela(String nomeDaTabela) {
        this.nomeDaTabela = nomeDaTabela;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<QueryModel> getChildrens() {
        return childrens;
    }

    public void setChildrens(List<QueryModel> childrens) {
        this.childrens = childrens;
    }

    public QueryModel getParent() {
        return parent;
    }

    public void setParent(QueryModel parent) {
        this.parent = parent;
    }        
}
