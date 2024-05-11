package sk.stuba.fei.uim.oop.entity.grant;

import sk.stuba.fei.uim.oop.entity.people.PersonInterface;
import sk.stuba.fei.uim.oop.utility.Constants;

import java.util.*;
import java.util.stream.Collectors;


public class Grant implements GrantInterface {
    private String grantIdentifier;
    private int grantYear, grantBudget, grantRemainingBudget;
    private AgencyInterface agency;
    private Set<ProjectInterface> registeredProjects = new LinkedHashSet<>();
    private Set<ProjectInterface> passedProjects = new LinkedHashSet<>();
    private GrantState grantState;

    @Override
    public String getIdentifier() {
        return grantIdentifier;
    }

    @Override
    public void setIdentifier(String identifier) {
        this.grantIdentifier = identifier;
    }

    @Override
    public int getYear() {
        return grantYear;
    }

    @Override
    public void setYear(int year) {
        this.grantYear = year;
    }

    @Override
    public AgencyInterface getAgency() {
        return agency;
    }

    @Override
    public void setAgency(AgencyInterface agency) {
        this.agency = agency;
    }

    @Override
    public int getBudget() {
        return grantBudget;
    }

    @Override
    public void setBudget(int budget) {
        this.grantBudget = budget;
        this.grantRemainingBudget = budget;
    }

    @Override
    public int getRemainingBudget() {
        return grantRemainingBudget;
    }

    @Override
    public int getBudgetForProject(ProjectInterface project) {
        return project.getTotalBudget();
    }

    @Override
    public boolean registerProject(ProjectInterface project) {
        if (grantState == GrantState.STARTED && project.getStartingYear() == grantYear && !project.getAllParticipants().isEmpty()) {
            registeredProjects.add(project);
            return true;
        }
        return false;
    }

    @Override
    public Set<ProjectInterface> getRegisteredProjects() {
        return registeredProjects;
    }

    @Override
    public GrantState getState() {
        return grantState;
    }

    
    @Override
    public void callForProjects() {
        this.grantState = GrantState.STARTED;
    }


    /////////// EVALUATION ////////////
    private HashMap<PersonInterface, HashMap<Integer, Integer>> yearEmploymentForPerson(ProjectInterface project,HashMap<PersonInterface, HashMap<Integer, Integer>> personEmployment){
        for (PersonInterface person : project.getAllParticipants()) {
            HashMap<Integer, Integer> yearEmployment = personEmployment.getOrDefault(person, new HashMap<>());
            
            for (Integer year = project.getStartingYear(); year <= project.getEndingYear(); year++) {
                yearEmployment.put(year, yearEmployment.getOrDefault(year,0) + project.getApplicant().getEmploymentForEmployee(person));
            }
            personEmployment.put(person, yearEmployment);
        }
        return personEmployment;
    }
    


    private boolean capacityCheck(ProjectInterface project,HashMap<PersonInterface, HashMap<Integer, Integer>> personEmployment) {
        for (PersonInterface person : personEmployment.keySet()) {
            if (!project.getAllParticipants().contains(person)) {
                continue; 
            }

            for (Integer year : personEmployment.get(person).keySet()) {
                if (personEmployment.get(person).get(year) > Constants.MAX_EMPLOYMENT_PER_AGENCY) {
                    return false;
                }
            }
        }
        return true;
    }
    

    @Override
    public void evaluateProjects() {
        grantState = GrantState.EVALUATING;
        HashMap<PersonInterface, HashMap<Integer, Integer>> personEmployment = new HashMap<>();

        for(GrantInterface grant : agency.getAllGrants()){
            for(ProjectInterface project : grant.getRegisteredProjects()){
                personEmployment = yearEmploymentForPerson(project, personEmployment);

                if(capacityCheck(project, personEmployment)){
                    passedProjects.add(project);
                }
            }
        }
        
    }


    private void divideBudget(Set<ProjectInterface> projects) {
        List<ProjectInterface> eligibleProjects = projects.stream()
                                                        .filter(project -> project.getTotalBudget() == 0)
                                                        .collect(Collectors.toList());
      
        int numToFund = Math.max(1, eligibleProjects.size() / 2); 
      
        List<ProjectInterface> projectsToFund;
        if (numToFund < eligibleProjects.size()) {
            projectsToFund = eligibleProjects.subList(0, numToFund); 
        } else {
            projectsToFund = eligibleProjects; 
        }
      
        int budgetPerYear = grantBudget / (numToFund * Constants.PROJECT_DURATION_IN_YEARS);
      
        for (ProjectInterface project : projectsToFund) {
          for (int year = grantYear; year < grantYear + Constants.PROJECT_DURATION_IN_YEARS; year++) {
            project.getApplicant().projectBudgetUpdateNotification(project, year, budgetPerYear);
            grantRemainingBudget -= budgetPerYear;
          }
        }
      }
    
    
    @Override
    public void closeGrant() {
        grantState = GrantState.CLOSED;
        if(grantRemainingBudget>0){
            divideBudget(passedProjects);
        }
    }



    //////////////////////////////////////////////////////

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((grantIdentifier == null) ? 0 : grantIdentifier.hashCode());
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
        Grant other = (Grant) obj;
        if (grantIdentifier == null) {
            if (other.grantIdentifier != null)
                return false;
        } else if (!grantIdentifier.equals(other.grantIdentifier))
            return false;
        return true;
    }
}