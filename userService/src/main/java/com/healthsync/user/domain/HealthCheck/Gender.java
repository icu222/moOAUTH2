package com.healthsync.user.domain.HealthCheck;

public enum Gender {
    MALE(1, "남성"),
    FEMALE(2, "여성");

    private final int code;
    private final String description;

    Gender(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static Gender fromCode(Integer code) {
        if (code == null) {
            return null;
        }

        for (Gender gender : Gender.values()) {
            if (gender.code == code) {
                return gender;
            }
        }
        return null;
    }
}
