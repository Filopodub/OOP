package sk.stuba.fei.uim.oop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sk.stuba.fei.uim.oop.entity.grant.AgencyInterface;
import sk.stuba.fei.uim.oop.entity.grant.GrantInterface;
import sk.stuba.fei.uim.oop.entity.grant.ProjectInterface;
import sk.stuba.fei.uim.oop.entity.organization.OrganizationInterface;
import sk.stuba.fei.uim.oop.entity.people.PersonInterface;
import sk.stuba.fei.uim.oop.factory.DataFactory;
import sk.stuba.fei.uim.oop.utility.Constants;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SiteExampleTest {

    private LinkedList<AgencyInterface> agencies;
    private LinkedList<GrantInterface> grants;
    private LinkedList<OrganizationInterface> organizations;
    private LinkedList<ProjectInterface> projects;
    private LinkedList<PersonInterface> persons;

    @BeforeEach
    void setUp() {
        agencies = DataFactory.getAgencies(2);
        agencies.get(0).setName("VEGA");
        agencies.get(1).setName("APVV");

        grants = DataFactory.getGrants(4);
        List<Integer> grantBudgets = Arrays.asList(100000, 8000, 100000, 8000);
        List<AgencyInterface> grantAgencies = Arrays.asList(agencies.get(1), agencies.get(0), agencies.get(1), agencies.get(0));
        List<Integer> grantYears = Arrays.asList(2022, 2023, 2024, 2024);
        for (int i = 0; i < grants.size(); i++) {
            GrantInterface grant = grants.get(i);
            grant.setIdentifier(String.valueOf(i + 1));
            grant.setAgency(grantAgencies.get(i));
            grantAgencies.get(i).addGrant(grant, grantYears.get(i));
            grant.setBudget(grantBudgets.get(i));
            grant.setYear(grantYears.get(i));
        }

        List<String> organizationNames = Arrays.asList("STU", "UK", "Eset");
        Constants.COMPANY_INIT_OWN_RESOURCES = 100000;
        organizations = new LinkedList<>();
        organizations.addAll(DataFactory.getUniversities(2));
        organizations.addAll(DataFactory.getCompanies(1));
        for (int i = 0; i < organizations.size(); i++) {
            organizations.get(i).setName(organizationNames.get(i));
        }

        List<String> personNames = Arrays.asList("Peter", "Jozef", "Anna", "Karol");
        persons = DataFactory.getPersons(personNames.size());
        for (int i = 0; i < persons.size(); i++) {
            PersonInterface person = persons.get(i);
            person.setName(personNames.get(i));
            person.setAddress("Address " + (i + 1));
        }
        // STU Peter, Jozef
        organizations.get(0).addEmployee(persons.get(0), 2);
        persons.get(0).addEmployer(organizations.get(0));
        organizations.get(0).addEmployee(persons.get(1), 3);
        persons.get(1).addEmployer(organizations.get(0));
        // UK Anna, Karol
        organizations.get(1).addEmployee(persons.get(2), 3);
        persons.get(2).addEmployer(organizations.get(1));
        organizations.get(1).addEmployee(persons.get(3), 1);
        persons.get(3).addEmployer(organizations.get(1));
        // Eset Peter, Anna, Karol
        organizations.get(2).addEmployee(persons.get(0), 4);
        persons.get(0).addEmployer(organizations.get(2));
        organizations.get(2).addEmployee(persons.get(2), 2);
        persons.get(2).addEmployer(organizations.get(2));
        organizations.get(2).addEmployee(persons.get(3), 1);
        persons.get(3).addEmployer(organizations.get(2));

        List<OrganizationInterface> projectOrganizations = Arrays.asList(organizations.get(0), organizations.get(1), organizations.get(2), organizations.get(2), organizations.get(0), organizations.get(1), organizations.get(1));
        List<Integer> projectYears = Arrays.asList(2022, 2023, 2024, 2024, 2024, 2024, 2024);

        projects = DataFactory.getProjects(7);
        for (int i = 0; i < projects.size(); i++) {
            ProjectInterface proj = projects.get(i);
            proj.setProjectName("P" + (i + 1));
            OrganizationInterface applicant = projectOrganizations.get(i);
            proj.setApplicant(applicant);
            applicant.registerProjectInOrganization(projects.get(i));
            proj.setStartingYear(projectYears.get(i));
        }
        projects.get(0).addParticipant(persons.get(0)); // Peter
        projects.get(0).addParticipant(persons.get(1)); // Jozef
        projects.get(1).addParticipant(persons.get(2)); // Anna
        projects.get(1).addParticipant(persons.get(3)); // Karol
        projects.get(2).addParticipant(persons.get(0)); // Peter
        projects.get(3).addParticipant(persons.get(2)); // Anna
        projects.get(3).addParticipant(persons.get(3)); // Karol
        projects.get(4).addParticipant(persons.get(0)); // Peter
        projects.get(4).addParticipant(persons.get(1)); // Jozef
        projects.get(5).addParticipant(persons.get(2)); // Anna
        projects.get(6).addParticipant(persons.get(3)); // Karol
    }

    @Test
    void testAllExampleProjectsPassed() {
        //Constants.PROJECT_DURATION_IN_YEARS = 4;
        //Constants.MAX_EMPLOYMENT_PER_AGENCY = 4;
        // non overlapping projects, both projects will pass the test

        ProjectInterface P1 = projects.get(0);
        GrantInterface grant1 = grants.get(0);
        grant1.callForProjects();
        grant1.registerProject(P1);
        grant1.evaluateProjects();
        grant1.closeGrant();
        // the registered project must obtain the full grant budget
        assertEquals(100000, grant1.getBudgetForProject(P1));
        assertEquals(100000, P1.getApplicant().getProjectBudget(P1));

        ProjectInterface P2 =  projects.get(1);
        GrantInterface grant2 = grants.get(1);
        grant2.callForProjects();
        grant2.registerProject(P2);
        grant2.evaluateProjects();
        grant2.closeGrant();
        // the registered project must obtain the full grant budget
        // the grant doesn't know about the company dotation
        assertEquals(8000, grant2.getBudgetForProject(P2));
        assertEquals(8000, P2.getApplicant().getProjectBudget(P2));


        ProjectInterface P3 =  projects.get(2);
        GrantInterface grant3 = grants.get(2);
        grant3.callForProjects();
        grant3.registerProject(P3);
        grant3.evaluateProjects();
        grant3.closeGrant();
        // the registered project must obtain the full grant budget
        // the grant doesn't know about the company dotation
        assertEquals(0, grant3.getBudgetForProject(P3));
        assertEquals(0, P3.getApplicant().getProjectBudget(P3));


        ProjectInterface P4 =  projects.get(3);
        ProjectInterface P5 =  projects.get(4);
        ProjectInterface P6 =  projects.get(5);
        ProjectInterface P7 =  projects.get(6);

        GrantInterface grant4 = grants.get(3);
        grant4.callForProjects();
        grant4.registerProject(P4);
        grant4.registerProject(P5);
        grant4.registerProject(P6);
        grant4.registerProject(P7);
        grant4.evaluateProjects();
        grant4.closeGrant();
        // the registered project must obtain the full grant budget
        // the grant doesn't know about the company dotation
        // P4.getApplicant().getProjectBudget(P4);
        assertEquals(8000, grant4.getBudgetForProject(P4));
        assertEquals(0, grant4.getBudgetForProject(P5));
        assertEquals(0, grant4.getBudgetForProject(P6));
        assertEquals(0, grant4.getBudgetForProject(P7));

        assertEquals(16000, P4.getApplicant().getProjectBudget(P4));
        assertEquals(0, P4.getApplicant().getProjectBudget(P5));
        assertEquals(0, P4.getApplicant().getProjectBudget(P6));
        assertEquals(0, P4.getApplicant().getProjectBudget(P7));


    }
}
