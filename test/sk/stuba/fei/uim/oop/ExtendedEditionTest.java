package sk.stuba.fei.uim.oop;


import org.junit.jupiter.api.Test;
import sk.stuba.fei.uim.oop.entity.grant.AgencyInterface;
import sk.stuba.fei.uim.oop.entity.grant.GrantInterface;
import sk.stuba.fei.uim.oop.entity.grant.ProjectInterface;
import sk.stuba.fei.uim.oop.entity.organization.OrganizationInterface;

import sk.stuba.fei.uim.oop.entity.people.PersonInterface;
import sk.stuba.fei.uim.oop.factory.DataFactory;



import java.util.LinkedList;


import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExtendedEditionTest {

    private LinkedList<AgencyInterface> agencies;
    private LinkedList<GrantInterface> grants;
    private LinkedList<OrganizationInterface> organizations;
    private LinkedList<ProjectInterface> projects;
    private LinkedList<PersonInterface> persons;

    @Test
    void testEquals() {
        agencies = DataFactory.getAgencies(2);
        agencies.get(0).setName("VEGA");
        agencies.get(1).setName("VEGA");
        assertTrue(agencies.get(0).equals(agencies.get(1)));
        assertEquals(agencies.get(0).hashCode(),agencies.get(1).hashCode());

        grants = DataFactory.getGrants(2);
        grants.get(0).setIdentifier("IDENTIFIER");
        grants.get(1).setIdentifier("IDENTIFIER");
        assertTrue(grants.get(0).equals(grants.get(1)));
        assertEquals(grants.get(0).hashCode(),grants.get(1).hashCode());

        organizations = DataFactory.getUniversities(2);
        organizations.get(0).setName("STU");
        organizations.get(1).setName("STU");
        assertTrue(organizations.get(0).equals(organizations.get(1)));
        assertEquals(organizations.get(0).hashCode(),organizations.get(1).hashCode());

        persons = DataFactory.getPersons(2);
        persons.get(0).setName("Jano");
        persons.get(1).setName("Jano");
        persons.get(0).setAddress("Ilkovicova");
        persons.get(1).setAddress("Ilkovicova");
        assertTrue(persons.get(0).equals(persons.get(1)));
        assertEquals(persons.get(0).hashCode(),persons.get(1).hashCode());
        persons.get(0).setAddress("Petrzalka");
        assertFalse(persons.get(0).equals(persons.get(1)));
        assertNotEquals(persons.get(0).hashCode(),persons.get(1).hashCode());


    }

    @Test
    void testCompanyDotation(){
        grants = DataFactory.getGrants(3);
        grants.get(0).setIdentifier("Technology");
        grants.get(0).setBudget(800000);
        grants.get(0).setYear(2022);
        grants.get(0).callForProjects();
        grants.get(1).setIdentifier("Medicine");
        grants.get(1).setBudget(800000);
        grants.get(1).setYear(2022);
        grants.get(1).callForProjects();
        grants.get(2).setIdentifier("Military");
        grants.get(2).setBudget(800000);
        grants.get(2).setYear(2023);
        grants.get(2).callForProjects();


        agencies = DataFactory.getAgencies(1);
        agencies.get(0).setName("VEGA");
        agencies.get(0).addGrant(grants.get(0),2022);
        agencies.get(0).addGrant(grants.get(1),2022);
        agencies.get(0).addGrant(grants.get(2),2023);
        grants.get(0).setAgency(agencies.get(0));
        grants.get(1).setAgency(agencies.get(0));
        grants.get(2).setAgency(agencies.get(0));


        persons = DataFactory.getPersons(1);
        persons.get(0).setName("Jano");
        persons.get(0).setName("Trencin");

        organizations = DataFactory.getCompanies(1);
        organizations.get(0).setName("Boeing");
        organizations.get(0).addEmployee(persons.get(0),1);
        persons.get(0).addEmployer(organizations.get(0));

        projects = DataFactory.getProjects(3);
        projects.get(0).setProjectName("Engine");
        projects.get(0).setStartingYear(2022);
        projects.get(0).setApplicant(organizations.get(0));
        organizations.get(0).registerProjectInOrganization(projects.get(0));
        projects.get(0).addParticipant(persons.get(0));

        projects.get(1).setProjectName("Vaccine");
        projects.get(1).setStartingYear(2022);
        projects.get(1).setApplicant(organizations.get(0));
        organizations.get(0).registerProjectInOrganization(projects.get(1));
        projects.get(1).addParticipant(persons.get(0));


        projects.get(2).setProjectName("SPAA");
        projects.get(2).setStartingYear(2022);
        projects.get(2).setApplicant(organizations.get(0));
        organizations.get(0).registerProjectInOrganization(projects.get(2));
        projects.get(2).addParticipant(persons.get(0));


        assertTrue(grants.get(0).registerProject(projects.get(0)));
        assertTrue(grants.get(1).registerProject(projects.get(1)));
        assertFalse(grants.get(2).registerProject(projects.get(2)));

        grants.get(0).evaluateProjects();
        grants.get(0).closeGrant();
        grants.get(1).evaluateProjects();
        grants.get(1).closeGrant();
        grants.get(2).evaluateProjects();
        grants.get(2).closeGrant();

        assertEquals(800000+800000,organizations.get(0).getProjectBudget(projects.get(0)));
        assertEquals(800000+200000,organizations.get(0).getProjectBudget(projects.get(1)));

    }
}
