package com.playground.rnd.models;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class Customer {
    private String firstName;
    private String lastName;
    private LocalDateTime createdAt;
    private MembershipType MembershipType;
    private KycLevel kycLevel;

    @Override
    public String toString() {
        return "Customer{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", createdAt=" + createdAt +
                ", MembershipType=" + MembershipType +
                ", KycLevel=" + kycLevel +
                '}';
    }
}
