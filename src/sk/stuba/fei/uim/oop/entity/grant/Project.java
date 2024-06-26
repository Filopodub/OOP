package sk.stuba.fei.uim.oop.entity.grant;

import sk.stuba.fei.uim.oop.entity.organization.OrganizationInterface;
import sk.stuba.fei.uim.oop.entity.people.PersonInterface;
import sk.stuba.fei.uim.oop.utility.Constants;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Project implements ProjectInterface{
    private int projectYear;
    private String projectName;
    private HashMap<Integer, Integer> budgetForYears;
    private HashSet<PersonInterface> projectParticipants;
    private OrganizationInterface projectApplicant;

    @Override
    public String getProjectName() {
        return this.projectName;
    }

    @Override
    public void setProjectName(String name) {
        this.projectName = name;
        this.budgetForYears = new HashMap<>();
        this.projectParticipants = new HashSet<>();
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
        return budgetForYears.getOrDefault(year,0);
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
        if (this.projectParticipants == null) {
            this.projectParticipants = new HashSet<>();
        }
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
