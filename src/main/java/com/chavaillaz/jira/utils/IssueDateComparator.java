package com.chavaillaz.jira.utils;

import java.time.OffsetDateTime;
import java.util.Comparator;
import java.util.Optional;

import com.chavaillaz.jira.domain.Issue;

public class IssueDateComparator implements Comparator<Issue> {

    @Override
    public int compare(Issue issue1, Issue issue2) {
        OffsetDateTime date1 = Optional.ofNullable(issue1.getFields().getUpdated())
                .orElse(issue1.getFields().getCreated());
        OffsetDateTime date2 = Optional.ofNullable(issue2.getFields().getUpdated())
                .orElse(issue2.getFields().getCreated());

        if (date1 == null) {
            return -1;
        } else if (date2 == null) {
            return 1;
        } else {
            return date1.compareTo(date2);
        }
    }

}
