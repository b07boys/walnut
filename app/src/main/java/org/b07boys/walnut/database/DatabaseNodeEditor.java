package org.b07boys.walnut.database;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;

import java.util.HashMap;
import java.util.Map;

public class DatabaseNodeEditor extends DatabaseNode {

    public DatabaseNodeEditor(String node) {
        super(node);
    }

    public <T extends DatabaseAdapter> void writeAsChild(String path, T object) {

        String key = databaseReference.child(path).push().getKey();
        Map<String, Object> childValues = object.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/" + key, childValues);

        databaseReference.updateChildren(childUpdates);
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
