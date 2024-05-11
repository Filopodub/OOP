package sk.stuba.fei.uim.oop.entity.organization;

import sk.stuba.fei.uim.oop.utility.Constants;
import sk.stuba.fei.uim.oop.entity.grant.ProjectInterface;
import java.util.HashMap;



public class Company extends Organization{
    private HashMap<ProjectInterface, Integer> projectsBudget = new HashMap<>(); 
    private int ownResources = Constants.COMPANY_INIT_OWN_RESOURCES;

    @Override
    public int getProjectBudget(ProjectInterface pi) {
        return pi.getTotalBudget() + projectsBudget.getOrDefault(pi,0);
    }
    
    @Override
    public void projectBudgetUpdateNotification(ProjectInterface pi, int year, int budgetForYear) {
        pi.setBudgetForYear(year, budgetForYear);
        dotation(pi, budgetForYear);
    }

    private void dotation(ProjectInterface pi, int budgetForYear) {    
        int dotation = Math.min(ownResources, budgetForYear); 
        projectsBudget.put(pi, dotation + projectsBudget.getOrDefault(pi, 0)); 
        ownResources -= dotation;
    }

}
