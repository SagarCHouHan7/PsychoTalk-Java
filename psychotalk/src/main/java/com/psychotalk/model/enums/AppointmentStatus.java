package com.psychotalk.model.enums;

public enum AppointmentStatus {
    REQUESTED,
    CONFIRMED,
    PAYMENT_PENDING,
    SCHEDULED,
    COMPLETED,
    CANCELLED;

    public boolean canUserCancel(){
        return this == REQUESTED || this == PAYMENT_PENDING;
    }
}
