package org.b07boys.walnut.database;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;

import org.json.JSONObject;

public class DatabaseNodeEditor extends DatabaseNode {

    public DatabaseNodeEditor(String node) {
        super(node);
    }

    public boolean setValue(String path, JSONObject jsonObject) {
        return false;
    }

    public boolean setValue(String path, String value) {
        return false;
    }

    public <T> void getValue(String path, Class<T> clazz, PromiseReceivedData<T> promise) {
        nodeSnapshot(path).addOnCompleteListener(task -> promise.onReceive(task.getResult().getValue(clazz)));
    }

    public void getStringValue(String path, PromiseReceivedData<String> promise) {
        nodeSnapshot(path).addOnCompleteListener(task -> promise.onReceive(String.valueOf(task.getResult().getValue())));
    }

    public Task<DataSnapshot> nodeSnapshot(String path) {
        return databaseReference.child(path).get();
    }

}
