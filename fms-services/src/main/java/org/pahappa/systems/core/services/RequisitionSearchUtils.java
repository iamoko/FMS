package org.pahappa.systems.core.services;

import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.sers.webutils.model.RecordStatus;
import org.sers.webutils.model.utils.SearchField;
import org.sers.webutils.model.utils.SortField;
import org.sers.webutils.server.core.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RequisitionSearchUtils {

    private static final int MINIMUM_CHARACTERS_FOR_SEARCH_TERM = 1;

    public static boolean searchTermSatisfiesQueryCriteria(String query) {
        if (StringUtils.isBlank(query))
            return false;
        return query.length() >= MINIMUM_CHARACTERS_FOR_SEARCH_TERM;
    }

    public static boolean generateSearchTerms(List<SearchField> searchFields, String query, List<Filter> filters) {
        if (searchFields != null && !searchFields.isEmpty()) {
            for (String token : query.replaceAll("  ", " ").split(" ")) {
                String searchTerm = "%" + StringEscapeUtils.escapeSql(token) + "%";
                Filter[] orFilters = new Filter[searchFields.size()];
                int counter = 0;
                for (SearchField searchField : searchFields) {
                    orFilters[counter] = Filter.like(searchField.getPath(), searchTerm);
                    counter++;
                }
                filters.add(Filter.or(orFilters));
            }
            return true;
        }
        return false;
    }

    public static Search composeRequisitionSearch(List<SearchField> searchFields, String query,
                                                  Date dateFrom, Date dateTo) {
        Search search = new Search();
        search.addFilterEqual("recordStatus", RecordStatus.ACTIVE);

        if (dateFrom != null)
            search.addFilterGreaterOrEqual("dateCreated", DateUtils.getMinimumDate(dateFrom));

        if (dateTo != null)
            search.addFilterLessOrEqual("dateCreated", DateUtils.getMaximumDate(dateTo));

        if (StringUtils.isNotBlank(query) && RequisitionSearchUtils.searchTermSatisfiesQueryCriteria(query)) {
            ArrayList<Filter> filters = new ArrayList<Filter>();
            RequisitionSearchUtils.generateSearchTerms(searchFields, query, filters);
            search.addFilterAnd(filters.toArray(new Filter[filters.size()]));
        }
        return search;
    }

    public static Search composeRequisitionFilter(List<SearchField> searchFields, String query,Date dateFrom, Date dateTo, SortField sortField) {

        Search search = new Search();
        search.addFilterEqual("recordStatus", RecordStatus.ACTIVE);

        if (dateFrom != null)
            search.addFilterGreaterOrEqual("dateCreated", DateUtils.getMinimumDate(dateFrom));

        if (dateTo != null)
            search.addFilterLessOrEqual("dateCreated", DateUtils.getMaximumDate(dateTo));

        if (sortField != null) {
            search.addSort(sortField.getSort());
        }

        if (StringUtils.isNotBlank(query) && RequisitionSearchUtils.searchTermSatisfiesQueryCriteria(query)) {
            ArrayList<Filter> filters = new ArrayList<Filter>();
            RequisitionSearchUtils.generateSearchTerms(searchFields, query, filters);
            search.addFilterAnd(filters.toArray(new Filter[filters.size()]));
        }
        return search;
    }
}
