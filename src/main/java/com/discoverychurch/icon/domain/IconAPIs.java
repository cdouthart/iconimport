package com.discoverychurch.icon.domain;

import com.discoverychurch.icon.icon.api.Icon;
import com.google.common.base.Throwables;

import java.io.IOException;
import java.util.function.Supplier;

public class IconAPIs {
    public static String MODULE_MEMBERSHIPS="membership";

    public static String SECTION_HOUSEHOLDS="households";
    public static String SECTION_HOUSEHOLDINDEX="householdindex";
    public static String SECTION_MEMBERS="members";
    public static String SECTION_RELATIONSHIPS="relationships";
    public static String SECTION_GENDER="gender";
    public static String SECTION_SPECIAL_DATES="special_dates";
    public static String SECTION_PHONE_NAMES="phone_names";


    private static String ACTION_CREATE="create";

    public static IconResponse apiCall(String module, String section, String action,Supplier<Object> p) {
        return apiCall(module,section,action,p,null);
    }

        public static IconResponse apiCall(String module, String section, String action,Supplier<Object> p, Object filters) {
        try {
            IconResponse r = Icon.request(
                    IconRequest.builder()
                            .module(module)
                            .section(section)
                            .action(action)
                            .data(p == null ? null : p.get())
                            .filters(filters)
                            .build());


            return r;
        } catch (IOException e) {
            Throwables.propagate(e);
        }
        return null;
    }


    public static String createHousehold(Household h) {
        return create(MODULE_MEMBERSHIPS,SECTION_HOUSEHOLDS,h);
//        IconResponse r = apiCall(MODULE_MEMBERSHIPS,SECTION_HOUSEHOLDS,ACTION_CREATE,()->h);
//        return r.getStatistics().lastId;
    }

    public static String createMember(Member m){
        return create(MODULE_MEMBERSHIPS,SECTION_MEMBERS,m);
//        IconResponse r =  apiCall(MODULE_MEMBERSHIPS,SECTION_MEMBERS,ACTION_CREATE,()->m);
//        return r.getStatistics().lastId;
    }

    public static String create(String module,String section, Object payload) {
        IconResponse r =  apiCall(module,section,ACTION_CREATE,() -> payload,null);
//        return "123";
        return r.getStatistics().getLastId();

    }

    public static IconResponse get(String module, String section, Filter f) {
        return apiCall(module,section,null,null,f == null ? null : f);
    }

    public static IconResponse delete(String module, String section, int id) {
        return apiCall(module,section,"delete",null,new SimpleId<Integer>(id));
    }


}
