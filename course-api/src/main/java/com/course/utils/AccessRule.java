package com.course.utils;

import com.course.enumerators.NavigationMode;

import java.util.Set;

public final class AccessRule {

    private AccessRule() {
    }

    public static boolean isOpen(NavigationMode mode, int globalOrder, Set<Integer> completedOrders) {
        if (mode == NavigationMode.FREE) {
            return true;
        }
        for (int order = 1; order < globalOrder; order++) {
            if (!completedOrders.contains(order)) {
                return false;
            }
        }
        return true;
    }
}
