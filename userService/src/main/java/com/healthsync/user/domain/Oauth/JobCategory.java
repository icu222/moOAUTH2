package com.healthsync.user.domain.Oauth;

public enum JobCategory {
    DEVELOPER(1, "개발"),
    PM(2, "PM"),
    MARKETING(3, "마케팅"),
    SALES(4, "영업"),
    INFRA_OPERATION(5, "인프라운영"),
    CUSTOMER_SERVICE(6, "고객상담"),
    ETC(7, "기타");

    private final int code;
    private final String name;

    JobCategory(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static JobCategory fromCode(int code) {
        for (JobCategory category : JobCategory.values()) {
            if (category.code == code) {
                return category;
            }
        }
        return ETC; // 기본값
    }
}
