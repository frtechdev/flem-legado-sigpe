package br.org.flem.primeiroemprego.util;

import java.lang.reflect.Field;
import java.util.Comparator;
import org.primefaces.model.SortOrder;

/**
 *
 * @author tscortes
 */
public class LazySorter<T> implements Comparator<T> { 
 
    private String sortField; 
     
    private SortOrder sortOrder; 
     
    public LazySorter(String sortField, SortOrder sortOrder) { 
        this.sortField = sortField; 
        this.sortOrder = sortOrder; 
    } 
 
    public int compare(T obj, T obj2) { 
        try { 
            
            Field field1 = obj.getClass().getDeclaredField(sortField);
            field1.setAccessible(true);
            Object value1 = field1.get(obj);
            
            Field field2 = obj2.getClass().getDeclaredField(sortField);
            field2.setAccessible(true);
            Object value2 = field2.get(obj2);
 
            int value = ((Comparable)value1).compareTo(value2); 
             
            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value; 
        } 
        catch(Exception e) { 
            throw new RuntimeException(); 
        } 
    } 
}
