package sk.stuba.fei.uim.oop.entity.organization;

import sk.stuba.fei.uim.oop.utility.Constants;
import sk.stuba.fei.uim.oop.entity.grant.ProjectInterface;
import sk.stuba.fei.uim.oop.entity.people.PersonInterface;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


public class Company implements OrganizationInterface{
    String companyName;
    private HashMap<PersonInterface, Integer> companyEmployees = new HashMap<>();
    private HashMap<Integer, HashSet<ProjectInterface>> projectsForYears = new HashMap<>(); 
    private HashMap<ProjectInterface, Integer> projectsBudget = new HashMap<>(); 
    int ownResources = Constants.COMPANY_INIT_OWN_RESOURCES;

    
    @Override
    public String getName() {
        return this.companyName;
    }

    @Override
    public void setName(String name) {
        this.companyName = name;
    }

    @Override
    public void addEmployee(PersonInterface p, int employment) {
        companyEmployees.put(p ,employment);
    }

    @Override
    public Set<PersonInterface> getEmployees() {
        return companyEmployees.keySet();
    }

    @Override
    public int getEmploymentForEmployee(PersonInterface p) {
        return companyEmployees.get(p);
    }

    @Override
    public Set<ProjectInterface> getAllProjects() { 
        return projectsForYears.values().stream()
            .flatMap(Collection::stream)
            .collect(Collectors.toSet());
    }
    

    @Override
    public Set<ProjectInterface> getRunningProjects(int year) {
        return projectsForYears.get(year);
    }

    
    @Override
    public void registerProjectInOrganization(ProjectInterface project) {
        projectsForYears.computeIfAbsent(project.getStartingYear(), projectsForYear -> new HashSet<>()).add(project);
    }


    @Override
    public int getProjectBudget(ProjectInterface pi) {
        return pi.getTotalBudget() + projectsBudget.getOrDefault(pi,0);
    }


    @Override
    public int getBudgetForAllProjects() {
        return getAllProjects().stream()
            .mapToInt(project -> getProjectBudget(project)) 
            .sum(); 
    }


    @Override
    public void projectBudgetUpdateNotification(ProjectInterface pi, int year, int budgetForYear) {
        pi.setBudgetForYear(year, budgetForYear);
        dotation(pi, budgetForYear);
    }

    public void dotation(ProjectInterface pi, int budgetForYear) {    
        int dotation = Math.min(ownResources, budgetForYear); 
        projectsBudget.put(pi, dotation + projectsBudget.getOrDefault(pi, 0)); 
        ownResources -= dotation;
    }



    //////////////////////////////////////////////////////////////////////////////
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((companyName == null) ? 0 : companyName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Company other = (Company) obj;
        if (companyName == null) {
            if (other.companyName != null)
                return false;
        } else if (!companyName.equals(other.companyName))
            return false;
        return true;
    }


    
}
