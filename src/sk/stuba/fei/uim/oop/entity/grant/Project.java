package sk.stuba.fei.uim.oop.entity.grant;

import sk.stuba.fei.uim.oop.entity.organization.OrganizationInterface;
import sk.stuba.fei.uim.oop.entity.people.PersonInterface;
import sk.stuba.fei.uim.oop.utility.Constants;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Project implements ProjectInterface{
    int projectYear;
    String projectName;
    HashMap<Integer, Integer> budgetForYears = new HashMap<>();
    HashSet<PersonInterface> projectParticipants = new HashSet<>();
    OrganizationInterface projectApplicant;

    @Override
    public String getProjectName() {
        return this.projectName;
    }

    @Override
    public void setProjectName(String name) {
        this.projectName = name;
    }

    @Override
    public int getStartingYear() {
        return this.projectYear;
    }

    @Override
    public void setStartingYear(int year) {
        this.projectYear = year;
    }

    @Override
    public int getEndingYear() {
        return getStartingYear() + Constants.PROJECT_DURATION_IN_YEARS -1;
    }

    @Override
    public int getBudgetForYear(int year) {
        // return budgetForYears.getOrDefault(year,0);
        return budgetForYears.get(year);
    }

    @Override
    public void setBudgetForYear(int year, int budget) {
        budgetForYears.put(year, budget);
    }

    @Override
    public int getTotalBudget() {
        return budgetForYears.values().stream()
            .mapToInt(Integer::valueOf) 
            .sum();
    }

    @Override
    public void addParticipant(PersonInterface participant) {
        if(projectApplicant != null && projectApplicant.getEmployees().contains(participant)){
            projectParticipants.add(participant);
        }  
    }

    @Override
    public Set<PersonInterface> getAllParticipants() {
        return projectParticipants;
    }

    @Override
    public OrganizationInterface getApplicant() {
        return this.projectApplicant;
    }

    @Override
    public void setApplicant(OrganizationInterface applicant) {
        this.projectApplicant = applicant;
    }




    //////////////////////////////////////////////////////////////////////////////
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((projectName == null) ? 0 : projectName.hashCode());
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
        Project other = (Project) obj;
        if (projectName == null) {
            if (other.projectName != null)
                return false;
        } else if (!projectName.equals(other.projectName))
            return false;
        return true;
    }
}
