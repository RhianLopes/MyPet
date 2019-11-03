package com.example.mypet;


public class Domain {

  private Long id;

  private boolean isActive;

  public Domain() {
  }

  public Domain(Long id, boolean isActive) {
    this.id = id;
    this.isActive = isActive;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public boolean isActive() {
    return isActive;
  }

  public void setActive(boolean active) {
    isActive = active;
  }
}
