package org.b07boys.walnut.database;

    private final String node;

    public DatabaseNode(String node) {
        this.node = node;
        this.databaseReference = FirebaseDatabase.getInstance().getReference(node);
    }

    public String getNode() {
        return node;
    }
}
