package biz.dealnote.messenger.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 19.09.2017.
 * phoenix
 */
public class DataWrapper<T> {

    private final List<T> data;

    private boolean enabled;

    public DataWrapper(List<T> data, boolean enabled) {
        this.data = data;
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public DataWrapper setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public int size(){
        return data.size();
    }

    public List<T> get() {
        return data;
    }

    public void clear(){
        this.data.clear();
    }

    public void addAll(List<T> append){
        this.data.addAll(append);
    }

    public void replace(List<T> data){
        this.data.clear();
        this.data.addAll(data);
        tryTrimToSize();
    }

    private void tryTrimToSize(){
        if(data instanceof ArrayList){
            ((ArrayList) data).trimToSize();
        }
    }
}