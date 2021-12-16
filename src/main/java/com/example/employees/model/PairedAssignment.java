package com.example.employees.model;

import java.util.Objects;
import java.util.stream.Stream;

public class PairedAssignment {
  private String employeeId1;
  private String employeeId2;
  private String projectId;

  public static PairedAssignment createWithOrderIds(String e1, String e2, String projectId) {
    PairedAssignment result = new PairedAssignment();
    var employees = Stream.of(e1, e2).sorted().toArray(String[]::new);
    result.employeeId1 = employees[0];
    result.employeeId2 = employees[1];
    result.projectId = projectId;
    return result;
  }

  private PairedAssignment() {}

  public String getEmployeeId1() {
    return employeeId1;
  }

  public void setEmployeeId1(String employeeId1) {
    this.employeeId1 = employeeId1;
  }

  public String getEmployeeId2() {
    return employeeId2;
  }

  public void setEmployeeId2(String employeeId2) {
    this.employeeId2 = employeeId2;
  }

  public String getProjectId() {
    return projectId;
  }

  public void setProjectId(String projectId) {
    this.projectId = projectId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PairedAssignment that = (PairedAssignment) o;
    return employeeId1.equals(that.employeeId1)
        && employeeId2.equals(that.employeeId2)
        && projectId.equals(that.projectId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(employeeId1, employeeId2, projectId);
  }
}
