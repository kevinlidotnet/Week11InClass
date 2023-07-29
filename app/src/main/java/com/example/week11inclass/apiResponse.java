package com.example.week11inclass;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class apiResponse {
    public ArrayList<Employee> getEmployees() {
        return Employees;
    }

    public void setEmployees(ArrayList<Employee> employees) {
        Employees = employees;
    }

    private ArrayList<Employee> Employees;
}
