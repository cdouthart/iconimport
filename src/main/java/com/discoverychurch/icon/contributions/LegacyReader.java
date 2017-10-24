package com.discoverychurch.icon.contributions;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class LegacyReader {
    JdbcTemplate template;

    // SELECT `GivingCurrentYearT`.`Contribution#`,

    Map<Integer,List<GivingRecord>> recordsByMember = new LinkedHashMap<>();

    List<GivingRecord> getRecordsForMember(int memberId) {
        List<GivingRecord> mList = recordsByMember.get(memberId);
        if (mList == null) {
            mList = new ArrayList<>();
            recordsByMember.put(memberId,mList);
        }

        return mList;
    }

    public void loadMemberRecords() {
        String query = "select MemberID,LastName,FirstNameH,FirstNameW from MemberInfoT where MemberID in ("
                + StringUtils.join(recordsByMember.keySet(),',')
                + ")";
        template.query(query, new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet rs) throws SQLException {
                GivingRecord r = GivingRecord.builder()
                        .id(rs.getInt(1))
                        .memberId(rs.getInt(2))
                        .date(rs.getDate(3))
                        .amount(rs.getFloat(4))
                        .designation(rs.getString(5))
                        .comment(rs.getString(6))
                        .build();

                getRecordsForMember(r.getMemberId()).add(r);

            }
        });
    }


    public void loadRecords() {
        template.query("select * from GivingCurrentYearT", new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet rs) throws SQLException {
                GivingRecord r = GivingRecord.builder()
                        .id(rs.getInt(1))
                        .memberId(rs.getInt(2))
                        .date(rs.getDate(3))
                        .amount(rs.getFloat(4))
                        .designation(rs.getString(5))
                        .comment(rs.getString(6))
                        .build();

                getRecordsForMember(r.getMemberId()).add(r);

            }
        });
    }
}
