package com.customer.api.customer.modal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;

@Data
@Entity
public class Customer {

  @Id
  @GeneratedValue
  private long id;

  @Column

  private String name;

  @Column

  private Integer age;

  @Column
  private LocalDate dob;

  public String toString() {
    return "Customer["
            + "id: " + getId()
            + ", name: " + getName()
            + ", age: " + getAge()
            +", dob: " + getDob()
            + "]";
  }
}