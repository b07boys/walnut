package org.b07boys.walnut.database;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DatabaseNode {

     private final String node;
     protected final DatabaseReference databaseReference;

     public DatabaseNode(String node) {
         this.node = node;
         this.databaseReference = FirebaseDatabase.getInstance().getReference(node);
     }

     public String getNode() {
         return node;
     }

 }
