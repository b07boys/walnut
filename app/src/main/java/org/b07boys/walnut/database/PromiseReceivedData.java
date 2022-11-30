package org.b07boys.walnut.database;

public interface PromiseReceivedData<T> {

    void onReceive(T object);

}
