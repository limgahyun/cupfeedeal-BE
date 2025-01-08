package com.cupfeedeal.domain.cafeSubscriptionType.enumerate;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum SubscriptionMenu {

    AMERICANO("AMERICANO"),
    SIGNATURE("SIGNATURE");

    private final String description;

    SubscriptionMenu(String description) {
        this.description = description;
    }
}
