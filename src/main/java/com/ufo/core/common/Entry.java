package com.ufo.core.common;

public class Entry<K, V> implements java.util.Map.Entry<K, V> {
    private K k;
    private V v;

    public Entry() {
    }

    public Entry(K k, V v) {
        this.k = k;
        this.v = v;
    }

    public K setKey(K k) {
        this.k = k;
        return this.k;
    }

    @Override
    public K getKey() {
        return k;
    }

    @Override
    public V getValue() {
        return v;
    }

    @Override
    public V setValue(V paramV) {
        this.v = paramV;
        return this.v;
    }

}
