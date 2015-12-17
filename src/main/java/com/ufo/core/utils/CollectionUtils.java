package com.ufo.core.utils;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import com.ufo.core.dto.TreeDTO;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class CollectionUtils {
    public static <T extends TreeDTO> void deepSort(List<T> coll) {
        Collections.sort(coll, new Comparator<T>() {
            @Override
            public int compare(T t1, T t2) {
                Serializable id1 = t1.getId();
                Serializable id2 = t2.getId();
                if (id1 instanceof Integer && id2 instanceof Integer) {
                    return ((Integer) id1).compareTo((Integer) id2);
                } else if (id1 instanceof Integer && id2 instanceof Integer) {
                    return ((Integer) id1).compareTo((Integer) id2);
                }
                return 0;
            }
        });
        for (T t : coll) {
            List<T> children = t.getChildren();
            if (isNotEmpty(children)) {
                deepSort(children);
            }
        }
    }

    public static boolean isNotEmpty(Collection<?> coll) {
        return coll != null && coll.size() != 0;
    }

    public static boolean isEmpty(Collection<?> coll) {
        return coll == null || coll.isEmpty();
    }
    
    public static void iterableToList(Iterable iterable, List list) {
        Iterator it = iterable.iterator();
        while (it.hasNext()){
            list.add(it.next());
        }
    }
}
