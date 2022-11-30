package org.b07boys.walnut.database;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;

import java.util.HashMap;
import java.util.Map;

public class DatabaseNodeEditor extends DatabaseNode {

    public DatabaseNodeEditor(String node) {
        super(node);
    }

    public <T extends DatabaseAdapter> Task<Void> writeAsChild(String path, T object) {

        String key = databaseReference.child(path).push().getKey();

        return modifyChild(path, key, object);
    }

    public <T extends DatabaseAdapter> Task<Void> modifyChild(String path, String key, T object) {

        Map<String, Object> childValues = object.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/" + key, childValues);

        return databaseReference.child(path).updateChildren(childUpdates);
    }

    public Task<Void> deleteValue(String path) {
        return databaseReference.child(path).removeValue();
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
