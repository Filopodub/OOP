package sk.stuba.fei.uim.oop.entity.people;

import sk.stuba.fei.uim.oop.entity.organization.OrganizationInterface;

import java.util.HashSet;
import java.util.Set;

public class Person implements PersonInterface{
    String personName, personAddress;
    HashSet<OrganizationInterface> personOrganizations = new HashSet<>();

    @Override
    public String getName() {
        return this.personName;
    }

    @Override
    public void setName(String name) {
        this.personName = name;
    }

    @Override
    public String getAddress() {
        return this.personAddress;
    }

    @Override
    public void setAddress(String address) {
        this.personAddress = address;
    }

    @Override
    public void addEmployer(OrganizationInterface organization) {
        personOrganizations.add(organization);
    }

    @Override
    public Set<OrganizationInterface> getEmployers() {
        return personOrganizations;
    }

    // @Override
    // public int hashCode() {
    //     final int prime = 31;
    //     int result = 1;
    //     result = prime * result + ((personName == null) ? 0 : personName.hashCode());
    //     return result;
    // }

    // @Override
    // public boolean equals(Object obj) {
    //     if (this == obj)
    //         return true;
    //     if (obj == null)
    //         return false;
    //     if (getClass() != obj.getClass())
    //         return false;
    //     Person other = (Person) obj;
    //     if (personName == null) {
    //         if (other.personName != null)
    //             return false;
    //     } else if (!personName.equals(other.personName))
    //         return false;
    //     return true;
    // }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((personName == null) ? 0 : personName.hashCode());
        result = prime * result + ((personAddress == null) ? 0 : personAddress.hashCode());
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
        Person other = (Person) obj;
        if (personName == null) {
            if (other.personName != null)
                return false;
        } else if (!personName.equals(other.personName))
            return false;
        if (personAddress == null) {
            if (other.personAddress != null)
                return false;
        } else if (!personAddress.equals(other.personAddress))
            return false;
        return true;
    }

    
}
