package org.b07boys.walnut.database;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONObject;

public class DatabaseNode {

    private final DatabaseReference databaseReference;
    private final String node;

    public DatabaseNode(String node) {
        this.node = node;
        this.databaseReference = FirebaseDatabase.getInstance().getReference(node);
    }

    public boolean setValue(String path, JSONObject jsonObject) {
        return false;
    }

    public boolean setValue(String path, String value) {
        return false;
    }

    public boolean keyExists(String path, String key) {
        return nodeSnapshot(path).getResult().hasChild(key);
    }

    public <T> T getValue(String path, Class<T> clazz) {
        return nodeSnapshot(path).getResult().getValue(clazz);
    }

    public String getValue(String path) {
        return String.valueOf(nodeSnapshot(path).getResult().getValue());
    }

    public <T> T getValue(String path, Class<T> clazz, OnCompleteListener<DataSnapshot> listener) {
        return nodeSnapshot(path).addOnCompleteListener(listener).getResult().getValue(clazz);
    }

    public String getValue(String path, OnCompleteListener<DataSnapshot> listener) {
        return String.valueOf(nodeSnapshot(path).addOnCompleteListener(listener).getResult().getValue());
    }

    private Task<DataSnapshot> nodeSnapshot(String path) {
        return databaseReference.child(path).get();
    }

    public String getNode() {
        return node;
    }
}
