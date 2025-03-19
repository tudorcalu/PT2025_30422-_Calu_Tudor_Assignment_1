package com.example.businessLogic;

import com.example.dataModel.Employee;
import com.example.dataModel.Task;
import com.example.businessLogic.TasksManagement;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utility implements Serializable {


    public static List<String> getEmployeesOver40Hours(TasksManagement management) {
        List<Employee> filtered = new ArrayList<>();
        for (Employee employee : management.getEmployees()) {
            double duration = management.calculateEmployeeWorkDuration(employee);
            if (duration > 40) {
                filtered.add(employee);
            }
        }
        filtered.sort((e1, e2) -> {
            double d1 = 0, d2 = 0;
            for (Task task : e1.getTasks()) {
                if ("Completed".equals(task.getStatus())) {
                    d1 += task.estimateDuration();
                }
            }
            for (Task task : e2.getTasks()) {
                if ("Completed".equals(task.getStatus())) {
                    d2 += task.estimateDuration();
                }
            }
            return Double.compare(d1, d2);
        });

        List<String> result = new ArrayList<>();
        for (Employee emp : filtered) {
            double totalDuration = management.calculateEmployeeWorkDuration(emp);
           /* for (Task task : emp.getTasks()) {
                if ("Completed".equals(task.getStatus())) {
                    totalDuration += task.estimateDuration();
                }
            }*/

            result.add(emp.getIdEmployee() + ": " + emp.getName() + " (" + totalDuration + " hrs)");
        }
        return result;
    }



    public static Map<Integer, Map<String, Integer>> getTaskStatusStatistics(TasksManagement management) {
        Map<Integer, Map<String, Integer>> statistics = new HashMap<>();
        for (Employee employee : management.getEmployees()) {
            int completed = 0;
            int uncompleted = 0;
            for (Task task : employee.getTasks()) {
                if ("Completed".equals(task.getStatus())) {
                    completed++;
                } else {
                    uncompleted++;
                }
            }
            Map<String, Integer> stat = new HashMap<>();
            stat.put("Completed", completed);
            stat.put("Uncompleted", uncompleted);
            statistics.put(employee.getIdEmployee(), stat);
        }
        return statistics;
    }


}
