package com.discoverychurch.icon;

import com.discoverychurch.icon.domain.Date;
import com.discoverychurch.icon.domain.*;
import com.discoverychurch.icon.icon.api.Icon;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.lang.StringUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;
import java.util.function.Consumer;

import static com.discoverychurch.icon.domain.IconAPIs.createHousehold;
import static com.discoverychurch.icon.domain.IconAPIs.createMember;
import static com.discoverychurch.icon.util.ArrayUtills.of;
import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;

@Slf4j
@SpringBootApplication
public class Membership
    implements CommandLineRunner {
    String[] columns = {
            "cp",   //A
            "member", //B
            "lastName", //C
            "firstName",//D
            "wifeName",//E
            "children",//F
            "address",//G
            "city",//H
            "zip",//I
            "phone",//J
            "mobile",//K
            "email",//L
            "wifeMobile",//M
            "birthday",//N
            "wifeEmail",//O
            "wifebirthday",//P
            "childrenbirthday",//Q
            "anniversary"};//R

    private Properties properties;
    private IconAuth auth;

    public Membership()
        throws IOException{
        properties = new Properties();

        properties.load(new FileReader("/Users/charles.douthart/dev/personal/iconimport/src/main/resources/application.properties"));
//        properties.load(this.getClass().getClassLoader().getResourceAsStream("/application.properties"));

        auth = new IconAuth();

        Icon.setIconAuth(auth);
    }

    public void parse(String filename) throws IOException{
        List<Map<String,String>> data = new ArrayList<>();
        Reader in = new FileReader(filename);
        CSVFormat.DEFAULT.parse(in).getRecords().stream()
                .skip(1)
                .forEach(r -> {
                    Map<String,String> a = new LinkedHashMap<>();
                    for (int i=0; i < r.size();i++) {

                        String k = columns[i];
                        String v = clean(k,r.get(i));
                        if (StringUtils.isNotEmpty(v)) {
                            a.put(k, v);
                        }
                    }
                    data.add(a);
                });

        data.forEach(row -> processRow(row));
    }


    String ac1 ="\\((\\d\\d\\d)\\) (.*)";
    String ac2 ="\\(\\d\\d\\d\\)\\.(.*)";

    public String cleanDate(String date) {
        String[] parts = StringUtils.split(date,"/");

        int month = Integer.parseInt(parts[0]);
        int day = Integer.parseInt(parts[1]);
        int year = parts.length == 3 ? Integer.parseInt(parts[2]) : 1900;
        if (year < 17) {
            year += 2000;
        } else if (year < 100) {
            year += 1900;
        }

        return String.format("%1$04d-%2$02d-%3$02d",year,month,day);
    }

    public String clean(String key, String value) {

        if (StringUtils.isEmpty(value)) {
            return null;
        }

        String result = StringUtils.trim(value);

        if (containsIgnoreCase(key,"phone") || containsIgnoreCase(key,"mobile")) {
            result = result.replaceFirst(ac1,"$1-$2");
            result = result.replaceFirst(ac2,"$1-$2");
            result = StringUtils.replace(result,".","-");
            return result;
        }

        if (containsIgnoreCase(key,"day") && !containsIgnoreCase(key,"children")) {
            return cleanDate(result);
        }

        return result;
    }

    void ifPresent(Map<String,String> row, String key, Consumer<String> c) {
        Optional.ofNullable(row.get(key))
                .filter(StringUtils::isNotEmpty)
                .ifPresent(c::accept);

    }

    public void processRow(Map<String,String> row)
     {

        // Genders: Male, Female, Unknown
        // Relationships: Spouse, Child
        // Dates: Anniversary, Birthday
        // Phones: Home, Mobile


        /*
            Basic Structure

                Household
                    - lastname
                    - firstname (primary)
                    - address
                    - home phone
                    - anniversary date
                +- Husband (if present)
                    - firstname
                    - mobile
                    - gender
                    - birthday
                +- Wfie (if present)
                    - firstname
                    - mobile
                    - gender
                    - birthday
                +- Children (if present)
                    - firstname
                    - gender. Will be Unknown. No data in source
         */
        String module = "membership";
        String section="household";
        String action="create";

        // Household info
        String lastName = row.get("lastName");

        String hName = row.get("firstName");
        String wName = row.get ("wifeName");

        boolean wifePresent = StringUtils.isNotEmpty(wName);
        boolean husbandPresent = StringUtils.isNotEmpty(hName);

        String householdName = StringUtils.defaultIfEmpty(hName,wName);
        boolean wifePrimary = StringUtils.isNotEmpty(wName) && StringUtils.isEmpty(hName);


        // min data is first,last,city, state
        Household h = new Household();
        h.setFirstName(householdName);
        h.setLastName(lastName);
        h.setCity(row.get("city"));
        h.setState("NC");
        h.setAddress(row.get("address"));
        h.setZip(row.get("zip"));
        h.setEmail(husbandPresent ? row.get("email") : row.get("wifeEmail"));
        h.setPhone(row.get("phone"));


//        ifPresent(row,"phone", p -> h.setPhones(of(new Phone("Home", p))));
        ifPresent(row,"anniversary", d -> h.setSpecialDates(of(new Date("Marriage", d))));


        String householdId = createHousehold(h);

        if (StringUtils.isNotEmpty(hName)) {
            Member husband = new Member();
            husband.setHouseholdId(householdId);
            husband.setFirstName(hName);
            husband.setLastName(lastName);
            husband.setGender("Male");
            husband.setRelationship(wifePresent ? "Husband" : "Single Male");
            husband.setPrimary(true);
            ifPresent(row,"mobile", m -> husband.setPhones(of(new Phone("Mobile", m))));
            ifPresent(row,"birthday", d -> husband.setSpecialDates(of(new Date("Birth", d))));
            ifPresent(row,"email", d -> husband.setEmails(of(new Email("Personal Email",d,false))));



            String memberId = createMember(husband);
        }


         if (StringUtils.isNotEmpty(wName)) {
             Member wife = new Member();
             wife.setHouseholdId(householdId);
             wife.setFirstName(wName);
             wife.setLastName(lastName);
             wife.setRelationship(husbandPresent ? "Wife" : "Single Female");
             wife.setGender("Female");
             if (!husbandPresent) {
                 wife.setPrimary(true);
             }


             ifPresent(row,"wifeMobile", m -> wife.setPhones(of(new Phone("Mobile", m))));
             ifPresent(row,"wifeBirthday", d -> wife.setSpecialDates(of(new Date("Birth", d))));
             ifPresent(row,"wifeEmail", d -> wife.setEmails(of(new Email("Personal Email",d,false))));

             String memberId = createMember(wife);
         }


        String[] children = StringUtils.split(row.get("children"), ',');
        if (children != null && children.length >0) {

            Map<String,String> cbdays = new HashMap<>();

            String childrenBirthday = row.get("childrenbirthday");
            if (StringUtils.isNotEmpty(childrenBirthday)) {
                String[] bdays = StringUtils.split(childrenBirthday,';');
                Arrays.asList(bdays).forEach(bday->{
                    String[] parts = StringUtils.split(bday,' ');
                    if (parts.length == 2) {
                        String name = StringUtils.trim(parts[0]).toLowerCase();
                        String date = cleanDate(StringUtils.trim(parts[1]));
                        cbdays.put(name,date);
                    }
                });
            }

            Arrays.asList(children).forEach(childName -> {
                String[] name = StringUtils.split(childName,' ');

                Member child = new Member();
                child.setHouseholdId(householdId);
                child.setFirstName(name[0]);
                child.setLastName(name.length == 2 ? name[1] : lastName);
                child.setRelationship("Child");
                child.setGender("Unknown");

                ifPresent(cbdays,name[0].toLowerCase(), bd -> child.setSpecialDates(of(new Date("Birth", bd))));

                String memberId = createMember(child);

            });
        }

        System.out.println("----------");
    }



    public static void main(String[] args) throws Exception {
        Membership m = new Membership();
        m.run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        Membership m = new Membership();
        m.parse("/Users/charles.douthart/dev/personal/iconimport/src/main/resources/directory.csv");
    }
}
