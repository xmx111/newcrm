package com.ufo.core.common;

import java.util.List;

public class Pagination<T> {
    private Paginator paginator;
    private List<T> records;
    
    public Pagination(Paginator paginator, List<T> records) {
        this.paginator = paginator;
        this.records = records;
    }
    
    /**
     * @return the paginator
     */
    public Paginator getPaginator() {
        return paginator;
    }
    /**
     * @param paginator the paginator to set
     */
    public void setPaginator(Paginator paginator) {
        this.paginator = paginator;
    }
    /**
     * @return the records
     */
    public List<T> getRecords() {
        return records;
    }
    /**
     * @param records the records to set
     */
    public void setRecords(List<T> records) {
        this.records = records;
    }
}
