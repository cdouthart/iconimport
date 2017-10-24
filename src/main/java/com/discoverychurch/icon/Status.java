package com.discoverychurch.icon;

import com.discoverychurch.icon.domain.IconAPIs;
import com.discoverychurch.icon.domain.IconResponse;

import java.util.stream.IntStream;

import static com.discoverychurch.icon.domain.IconAPIs.MODULE_MEMBERSHIPS;
import static com.discoverychurch.icon.domain.IconAPIs.SECTION_HOUSEHOLDS;

public class Status {

    // creates the prerequiite categories, etc
    public void createSupportingData() {
//        create(MODULE_MEMBERSHIPS,SECTION_RELATIONSHIPS,new SimpleId("Spouse"));
//        create(MODULE_MEMBERSHIPS,SECTION_RELATIONSHIPS,new SimpleId("Child"));
//
//        create(MODULE_MEMBERSHIPS,SECTION_GENDER,new SimpleId("Unknown"));
//
//        create(MODULE_MEMBERSHIPS,SECTION_SPECIAL_DATES,new SimpleId("Birthday"));
//        create(MODULE_MEMBERSHIPS,SECTION_SPECIAL_DATES,new SimpleId("Anniversary"));

//        create(MODULE_MEMBERSHIPS,SECTION_PHONE_NAMES,new SimpleId("Home"));


//        IconResponse r = get(MODULE_MEMBERSHIPS,SECTION_HOUSEHOLDINDEX,null);
//
//        Arrays.asList(r.getHouseholdIndices()).forEach( hi -> {
//            System.out.println(hi.getId());
//        });


//        SimpleId[] ids = new ObjectMapper().readValue(SimpleId.class);
//
//        System.out.println(Arrays.toString(r.getIds()));

        IntStream.rangeClosed(1,56).forEach( i -> {

            try {
                IconResponse r = IconAPIs.delete(MODULE_MEMBERSHIPS,SECTION_HOUSEHOLDS,i);
            } catch (Exception e) {
                //System.out.println( i + "Failed");
                System.out.println(e.getMessage());
            }
        });
    }


    public static void main(String[] argv) {
        Status status = new Status();
        status.createSupportingData();;
    }

}
