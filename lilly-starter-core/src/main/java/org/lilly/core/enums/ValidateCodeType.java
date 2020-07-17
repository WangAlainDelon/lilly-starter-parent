package org.lilly.core.enums;

public enum ValidateCodeType {
    IMAGE("image"),
    SMS("sms");
    private String value;

    ValidateCodeType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
