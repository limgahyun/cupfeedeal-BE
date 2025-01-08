package com.cupfeedeal.domain.cafeSubscriptionType.enumerate;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum SubscriptionMenu {

    AMERICANO("AMERICANO"),
    SIGNITURE("SIGNITURE");

    private final String description;

    SubscriptionMenu(String description) {
        this.description = description;
    }
}
