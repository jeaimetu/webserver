package com.lge.pickitup;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class TmsCourierItem {
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";

    public String id;
    public String name;

    public TmsCourierItem() {
        // Default constructor required for calls to DataSnapshot.getValue(TmsParcelItem.class)
    }

    public TmsCourierItem(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put(KEY_ID, id);
        result.put(KEY_NAME, name);
        return result;
    }
}
