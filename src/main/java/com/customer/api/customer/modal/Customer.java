package com.customer.api.customer.modal;

import com.customer.api.customer.validation.PastOrPresent;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Data
@Entity
public class Customer {

  @Id
  @GeneratedValue
  private long id;

  @Column
  @NotNull(message = "Name cannot be null")
  private String name;

  @Column
  @NotNull(message = "Age cannot be null")
  @Min(value = 18, message="Age must be at least 18")
  @Max(value = 100, message="Age must be at most 100")
  private int age;

  @Column
  @NotNull(message = "Date of birth cannot be null")
  @PastOrPresent(message = "Date must be in the past or present")
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