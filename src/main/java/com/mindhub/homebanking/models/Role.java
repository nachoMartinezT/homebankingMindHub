package com.mindhub.homebanking.models;

import java.util.Arrays;
import java.util.List;

public enum Role {
    ADMIN,
    CLIENT;

    public static List<Role> getRoles() {
        return Arrays.asList(Role.values());
    }
}
