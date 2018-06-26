package com.accenture.api.model;

/**
 *
 * @author marlon.naraja
 */
public class VisitOverTimeFilter {

    private String key_as_string;
    private long doc_count;
    private long key;

    private long visits;
    private long user;

    /**
     * @return the visits
     */
    public long getVisits() {
        return visits;
    }

    /**
     * @param visits the visits to set
     */
    public void setVisits(long visits) {
        this.visits = visits;
    }

    /**
     * @return the user
     */
    public long getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(long user) {
        this.user = user;
    }

    public String getKey_as_string() {
        return key_as_string;
    }

    public void setKey_as_string(String key_as_string) {
        this.key_as_string = key_as_string;
    }

    public long getDoc_count() {
        return doc_count;
    }

    public void setDoc_count(long doc_count) {
        this.doc_count = doc_count;
    }

    public long getKey() {
        return key;
    }

    public void setKey(long key) {
        this.key = key;
    }

}
