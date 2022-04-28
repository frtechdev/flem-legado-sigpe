package br.org.flem.primeiroemprego.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author tscortes
 */
public class LazyGenericDataModel<T> extends LazyDataModel<T>  {
    
    private List<T> datasource;
    private Map<String, Object> filters;

    public LazyGenericDataModel(List<T> datasource) {
        this.datasource = datasource;
    }

    @Override
    public T getRowData(String rowKey) {
//        for (T iter : datasource) {
//            long LongKey = Long.parseLong(rowKey);
//            if (iter.getId().equals(LongKey)) {
//                return iter;
//            }
//        }

        return null;
    }

    @Override
    public Object getRowKey(T lookup) {
        return lookup;
    }

    @Override
    public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        List<T> data = new ArrayList<T>();

        //filter
        for (T iter : datasource) {
            boolean match = true;

            if (filters != null) {
                this.filters = filters;
                for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
                    try {
                        String filterProperty = it.next();
                        Object filterValue = filters.get(filterProperty);
//                        String fieldValue = "";
                        if (filterProperty.equals("data")) {
                            String[] splitVal = filterValue.toString().split("/");
                            filterValue = splitVal[2]+"-"+splitVal[1]+"-"+splitVal[0];
//                            fieldValue = "data";
                        }
                        
                        Field field1 = iter.getClass().getDeclaredField(filterProperty);
                        field1.setAccessible(true);
                        String fieldValue = String.valueOf(field1.get(iter));
                        
                        if (filterValue == null || fieldValue.contains(filterValue.toString())) {
                            match = true;
                        } else {
                            match = false;
                            break;
                        }
                    } catch (Exception e) {
                        match = false;
                    }
                }
            }

            if (match) {
                data.add(iter);
            }
        }

        //sort
        if (sortField != null) {
            Collections.sort(data, new LazySorter<T>(sortField, sortOrder));
        }

        //rowCount
        int dataSize = data.size();
        this.setRowCount(dataSize);

        //paginate
        if (dataSize > pageSize) {
            try {
                return data.subList(first, first + pageSize);
            } catch (IndexOutOfBoundsException e) {
                return data.subList(first, first + (dataSize % pageSize));
            }
        } else {
            return data;
        }
    }

    public Map<String, Object> getFilters() {
        return filters;
    }
    
}
