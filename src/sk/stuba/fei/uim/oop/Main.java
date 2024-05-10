package sk.stuba.fei.uim.oop;

import java.util.LinkedList;

import sk.stuba.fei.uim.oop.entity.grant.AgencyInterface;
import sk.stuba.fei.uim.oop.factory.DataFactory;

public class Main {

    public static void main(String[] args) {
        LinkedList<AgencyInterface> agencies;
        agencies = DataFactory.getAgencies(2);
        agencies.get(0).setName("VEGA");
        agencies.get(1).setName("VEGA");
        System.out.println(agencies.get(0).getName());
        System.out.println(agencies.get(1).getName());
        System.out.println(agencies.get(0).hashCode());
        System.out.println(agencies.get(1).hashCode());
    }
}
