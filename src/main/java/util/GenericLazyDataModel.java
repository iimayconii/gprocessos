    /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import dao.services.HibernateUtil;
import java.math.BigInteger;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import model.GenericEntity;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author maycon
 */
public class GenericLazyDataModel<T, ID> extends LazyDataModel<T> {

    List<T> dataSource;
    String tableName;
    String byId = "";
    String filter = "";
    private Class<T> persistentClass;

    public GenericLazyDataModel(String tablename, Class<T> aClass) {
        persistentClass = aClass;
        tableName = tablename;
        System.out.println(tablename);
        System.out.println(persistentClass);
    }

    public GenericLazyDataModel(String tablename, Class<T> aClass, String filter) {
        persistentClass = aClass;
        tableName = tablename;
        this.filter = filter;
    }

    public String getById() {
        return byId;
    }

    public void setById(String byId) {
        this.byId = byId;
    }

    @Override
    public ID getRowKey(T entity) {
        return (ID) ((GenericEntity) entity).getId();
    }

    public T getRowData(Integer id) {
        System.out.println("Erro id");
        if (dataSource == null) {
            return null;
        }
        for (T value : dataSource) {
            if (((GenericEntity) value).getId().intValue() == id.intValue()) {
                return value;
            }
        }
        return null;
    }

    @Override
    public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
        String initQuery = "SELECT * FROM " + tableName + " ";
        String where = "";
        String id = "";
        QueryModel k = null;
        if(sortField != null){
            filters.put(sortField, null);
        }        
        for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
            String filterProperty = it.next();
            String filterValue = filters.get(filterProperty);
            if (filterProperty.contains(".")) {
                String[] split = filterProperty.split("\\.");
                k = QueryModel.setModel(persistentClass, split[0], null);
                for (int i = 1; i < split.length; i++) {
                    QueryModel.setModel(k.lastChildren.getType().getReturnedClass(), split[i], k);
                }
            } else {
                k = QueryModel.setModel(persistentClass, filterProperty, null);
            }
            initQuery += "" + QueryModel.createQuery(k);
            if (filterValue != null) {
                if (k.getLastChildren().getType().getReturnedClass().getSimpleName().equalsIgnoreCase("String")) {
                    where += k.getLastChildren().getNomeDaTabela() + "." + k.getLastChildren().getNomeDaColuna() + " LIKE '%" + filterValue + "%'";
                } else {
                    where += "CAST(" + k.getLastChildren().getNomeDaTabela() + "." + k.getLastChildren().getNomeDaColuna() + " AS text) LIKE '%" + filterValue + "%'";
                }
                if (it.hasNext()) {
                    where += " and ";
                }
            }
        }
        String query = initQuery;
        if (!where.isEmpty()) {
            query += " where " + where;
        }
        if (!filter.isEmpty() && !where.isEmpty()) {
            query += " and " + tableName + "." + filter;
        } else if (!filter.isEmpty()) {
            query += " where " + tableName + "." + filter;
        }

        String orderBy = " order by " + tableName + "." + QueryModel.getIdentifierName(persistentClass);
       
        if (sortField != null && k != null) {
            orderBy = " order by " + k.lastChildren.getNomeDaTabela() + "." +k.lastChildren.getNomeDaColuna();            
        }        
        String querycount = query.replaceFirst("\\*", "count(*) as value");
        query +=  orderBy + " asc offset " + (first) + " limit " + (first + pageSize);
        System.out.println("Query:\n\t" + query);
        dataSource = HibernateUtil.getSessionFactory().getCurrentSession().createSQLQuery(query).addEntity(persistentClass).list();
        setRowCount(((BigInteger) HibernateUtil.getSessionFactory().getCurrentSession().createSQLQuery(querycount).uniqueResult()).intValue());
        System.out.println("Query Count:\n\t" + querycount);

        return dataSource;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }
}
