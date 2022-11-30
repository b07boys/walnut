package org.b07boys.walnut.database;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Map;

@IgnoreExtraProperties
public interface DatabaseAdapter {

    @Exclude
    Map<String, Object> toMap();

}
