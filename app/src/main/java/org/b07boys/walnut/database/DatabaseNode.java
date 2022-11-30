package org.b07boys.walnut.database;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DatabaseNode {

    protected final DatabaseReference databaseReference;
    private final String node;

    public DatabaseNode(String node) {
        this.node = node;
        this.databaseReference = FirebaseDatabase.getInstance().getReference(node);
    }

    public String getNode() {
        return node;
    }
}
