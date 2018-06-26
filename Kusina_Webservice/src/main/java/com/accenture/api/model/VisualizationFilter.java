package com.accenture.api.model;

/**
 *
 * @author marlon.naraja
 */
public class VisualizationFilter {
    
    private String key;
    private long doc_count;
    private long hash;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getDoc_count() {
        return doc_count;
    }

    public void setDoc_count(long doc_count) {
        this.doc_count = doc_count;
    }

    public long getHash() {
        return hash;
    }

    public void setHash(long hash) {
        this.hash = hash;
    }

}
