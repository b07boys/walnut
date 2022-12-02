package org.b07boys.walnut.database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public abstract class DatabaseSingleValueChange<T> extends DatabaseNode implements ValueEventListener {

    private Class<T> clazz;

    public DatabaseSingleValueChange(String node, Class<T> clazz) {
        super(node);
        this.clazz = clazz;
    }

    protected abstract void onChange(T object);

    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        onChange(snapshot.getValue(clazz));
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {
        Log.w("cancel", "value listener at node: " + getNode() + ":onCancelled", error.toException());
    }

    public void startListening() {
        attachListener();
    }

    public void attachListener() {
        databaseReference.addValueEventListener(this);
    }

    public void detatchListener() {
        databaseReference.removeEventListener(this);
    }
}
