package sk.stuba.fei.uim.oop.entity.grant;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Agency implements AgencyInterface{
    private String agencyName;
    private HashMap<Integer, HashSet<GrantInterface>> grantsForYears;

    @Override
    public String getName() {
        return agencyName;
    }

    @Override
    public void setName(String name) {
        this.agencyName = name;
        this.grantsForYears  = new HashMap<>();
    }

    @Override
    public void addGrant(GrantInterface gi, int year) {
        grantsForYears.computeIfAbsent(year, grantsForYear -> new HashSet<>()).add(gi);
    }

    @Override
    public Set<GrantInterface> getAllGrants() {
        return grantsForYears.values().stream()
        .flatMap(Collection::stream)
        .collect(Collectors.toSet());
    }

    @Override
    public Set<GrantInterface> getGrantsIssuedInYear(int year) {
        return grantsForYears.get(year);
    }


    //////////////////////////////////////////////////////////////////////////////
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((agencyName == null) ? 0 : agencyName.hashCode());
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
        Agency other = (Agency) obj;
        if (agencyName == null) {
            if (other.agencyName != null)
                return false;
        } else if (!agencyName.equals(other.agencyName))
            return false;
        return true;
    }

    
}
