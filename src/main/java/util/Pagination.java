package util;

import java.util.List;

/**
 * Classe abstrata para paginação.
 * 
 * @author Maycon
 * @version 1.0
 * 
 */
public abstract class Pagination {
    private int pageSize;
    private int page;
    private int rowCount;
    private String query;

    public Pagination(int pageSize) {
        this.pageSize = pageSize;        
    }

    public int getItemsCount(){
        return rowCount;
    }        

    // Função de retorno dos dados do banco de dados
    abstract public List getPageDataModel();    

    public int getPageFirstItem() {
        return page * pageSize;
    }

    public int getPageLastItem() {
        int i = getPageFirstItem() + pageSize - 1;
        int count = getItemsCount() - 1;
        if (i > count) {
            i = count;
        }
        if (i < 0) {
            i = 0;
        }
        return i;
    }

    public boolean isHasNextPage() {
        return (page + 1) * pageSize + 1 <= getItemsCount();
    }

    public void nextPage() {
        if (isHasNextPage()) {
            page++;
        }
    }

    public boolean isHasPreviousPage() {
        return page > 0;
    }

    public void previousPage() {
        if (isHasPreviousPage()) {
            page--;
        }
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getPage() {
        return page;
    }        

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }                
}
