package org.b07boys.walnut.database;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

public abstract class DatabaseNodeSync<T> extends DatabaseNode implements ChildEventListener {

    private final Class<T> clazz;

    public DatabaseNodeSync(String node, Class<T> clazz) {
        super(node);
        this.clazz = clazz;
    }

    protected abstract void childAdded(T object, String key);
    protected abstract void childChanged(T object, String key);
    protected abstract void childRemoved(T object, String key);
    protected abstract void childMoved(T object, String key);

    @Override
    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
        T object = snapshot.getValue(clazz);
        String key = snapshot.getKey();
        childAdded(object, key);
    }

    @Override
    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
        T object = snapshot.getValue(clazz);
        String key = snapshot.getKey();
        childChanged(object, key);
    }

    @Override
    public void onChildRemoved(@NonNull DataSnapshot snapshot) {
        T object = snapshot.getValue(clazz);
        String key = snapshot.getKey();
        childRemoved(object, key);
    }

    @Override
    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
        T object = snapshot.getValue(clazz);
        String key = snapshot.getKey();
        childMoved(object, key);
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {
        Log.w("cancel", "listener at node: " + getNode() + ":onCancelled", error.toException());
    }

    public void startListening() {
        attachListener();
    }

    public void attachListener() {
        databaseReference.addChildEventListener(this);
    }

    public void detatchListener() {
        databaseReference.removeEventListener(this);
    }

}
