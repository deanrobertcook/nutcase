package org.theronin.nutcase.domain.base;

import java.util.HashMap;
import java.util.Map;

public enum MapDept {

    NONE(0), FLAT(1), FULL(2), NESTED(3), DEEP_NESTED(4);

    private static final Map<Integer, MapDept> values = new HashMap<>();

    static {
        for (MapDept dept : MapDept.values()) {
            values.put(dept.getValue(), dept);
        }
    }

    public static MapDept getEnum(int value) {
        return values.get(value);
    }

    private final int value;

    private MapDept(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
