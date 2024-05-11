package sk.stuba.fei.uim.oop.entity.organization;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import sk.stuba.fei.uim.oop.entity.grant.ProjectInterface;
import sk.stuba.fei.uim.oop.entity.people.PersonInterface;

public class Organization implements OrganizationInterface {
    private String organizationName;
    private HashMap<PersonInterface, Integer> organizationEmployers = new HashMap<>();
    private HashMap<Integer, HashSet<ProjectInterface>> projectsForYears = new HashMap<>();

    @Override
    public String getName() {
        return this.organizationName;
    }

    @Override
    public void setName(String name) {
        this.organizationName = name;
    }

    @Override
    public void addEmployee(PersonInterface p, int employment) {
        organizationEmployers.put(p ,employment);
    }

    @Override
    public Set<PersonInterface> getEmployees() {
        return organizationEmployers.keySet();
    }

    @Override
    public int getEmploymentForEmployee(PersonInterface p) {
        return organizationEmployers.get(p);
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
        result = prime * result + ((organizationName == null) ? 0 : organizationName.hashCode());
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
        Organization other = (Organization) obj;
        if (organizationName == null) {
            if (other.organizationName != null)
                return false;
        } else if (!organizationName.equals(other.organizationName))
            return false;
        return true;
    }
}
