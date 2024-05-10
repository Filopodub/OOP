package sk.stuba.fei.uim.oop.entity.organization;

import sk.stuba.fei.uim.oop.entity.grant.ProjectInterface;
import sk.stuba.fei.uim.oop.entity.people.PersonInterface;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


public class University implements OrganizationInterface{
    String universityName;
    private HashMap<PersonInterface, Integer> universityEmployers = new HashMap<>();
    private HashMap<Integer, HashSet<ProjectInterface>> projectsForYears = new HashMap<>();

    @Override
    public String getName() {
        return this.universityName;
    }

    @Override
    public void setName(String name) {
        this.universityName = name;
    }

    @Override
    public void addEmployee(PersonInterface p, int employment) {
        universityEmployers.put(p ,employment);
    }

    @Override
    public Set<PersonInterface> getEmployees() {
        return universityEmployers.keySet();
    }

    @Override
    public int getEmploymentForEmployee(PersonInterface p) {
        return universityEmployers.get(p);
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
        projectsForYears.computeIfAbsent(project.getStartingYear(), projectsForYear -> new HashSet<>()).add(project);;
    }

    @Override
    public int getProjectBudget(ProjectInterface pi) {
        return pi.getTotalBudget();
    }

    @Override
    public int getBudgetForAllProjects() {
        return getAllProjects().stream()
            .mapToInt(project -> getProjectBudget(project)) // map budget to project
            .sum(); 
    }

    @Override
    public void projectBudgetUpdateNotification(ProjectInterface pi, int year, int budgetForYear) {
        pi.setBudgetForYear(year, budgetForYear);
    }


    //////////////////////////////////////////////////////////////////////////////
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((universityName == null) ? 0 : universityName.hashCode());
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
        University other = (University) obj;
        if (universityName == null) {
            if (other.universityName != null)
                return false;
        } else if (!universityName.equals(other.universityName))
            return false;
        return true;
    }


    
}

