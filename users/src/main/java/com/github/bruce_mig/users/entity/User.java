package com.github.bruce_mig.users.entity;


import com.vladmihalcea.hibernate.type.array.StringArrayType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.Type;

/**
 * Hibernate entity for the User Table
 */

@Entity
@Table(name = "users")
public class User {

    @Id
    private String email;
    private String lastName;
    private String firstName;
    // Phone numbers are represented as plain strings and are not
    // being validated either by the front end or the back end.
    // JPA/Hibernate cannot handle string arrays directly, so we 
    // define them as a UserType using Hibernate Types
    @Type(StringArrayType.class)
    @Column(columnDefinition = "text[]")
    private String[] phoneNumbers;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String[] getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(String[] phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }
}
