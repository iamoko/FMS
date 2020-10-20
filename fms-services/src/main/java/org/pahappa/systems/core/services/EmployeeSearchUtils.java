package org.pahappa.systems.core.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.sers.webutils.model.Gender;
import org.sers.webutils.model.RecordStatus;
import org.sers.webutils.model.utils.SearchField;
import org.sers.webutils.server.core.utils.DateUtils;

import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;

public class EmployeeSearchUtils {

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

	public static Search composeEmployeeSearch(List<SearchField> searchFields, String query, Gender gender,
			Date dateOfBirthFrom, Date dateOfBirthTo) {
		Search search = new Search();
		search.addFilterEqual("recordStatus", RecordStatus.ACTIVE);

		if (gender != null)
			search.addFilterEqual("gender", gender);

		if (dateOfBirthFrom != null)
			search.addFilterGreaterOrEqual("dateOfBirth", DateUtils.getMinimumDate(dateOfBirthFrom));

		if (dateOfBirthTo != null)
			search.addFilterLessOrEqual("dateOfBirth", DateUtils.getMaximumDate(dateOfBirthTo));

		if (StringUtils.isNotBlank(query) && EmployeeSearchUtils.searchTermSatisfiesQueryCriteria(query)) {
			ArrayList<Filter> filters = new ArrayList<Filter>();
			EmployeeSearchUtils.generateSearchTerms(searchFields, query, filters);
			search.addFilterAnd(filters.toArray(new Filter[filters.size()]));
		}
		return search;
	}
	
	public static Search composeUserSearch(List<SearchField> searchFields, String query, Gender gender,
			Date dateOfBirthFrom, Date dateOfBirthTo) {
		Search search = new Search();
		search.addFilterEqual("recordStatus", RecordStatus.ACTIVE);

		if (gender != null)
			search.addFilterEqual("gender", gender);

		if (dateOfBirthFrom != null)
			search.addFilterGreaterOrEqual("dateOfBirth", DateUtils.getMinimumDate(dateOfBirthFrom));

		if (dateOfBirthTo != null)
			search.addFilterLessOrEqual("dateOfBirth", DateUtils.getMaximumDate(dateOfBirthTo));

		if (StringUtils.isNotBlank(query) && EmployeeSearchUtils.searchTermSatisfiesQueryCriteria(query)) {
			ArrayList<Filter> filters = new ArrayList<Filter>();
			EmployeeSearchUtils.generateSearchTerms(searchFields, query, filters);
			search.addFilterAnd(filters.toArray(new Filter[filters.size()]));
		}
		return search;
	}
	
	
	public static Search composeRequisitionSearch(List<SearchField> searchFields, String query) {
		Search search = new Search();
		search.addFilterEqual("recordStatus", RecordStatus.ACTIVE);

		
		if (StringUtils.isNotBlank(query) && EmployeeSearchUtils.searchTermSatisfiesQueryCriteria(query)) {
			ArrayList<Filter> filters = new ArrayList<Filter>();
			EmployeeSearchUtils.generateSearchTerms(searchFields, query, filters);
			search.addFilterAnd(filters.toArray(new Filter[filters.size()]));
		}
		return search;
	}
	
	
	public static Search composeLogSearch(List<SearchField> searchFields, String query,
			Date dateFrom, Date dateTo) {
		Search search = new Search();
		search.addFilterEqual("recordStatus", RecordStatus.ACTIVE);

		if (dateFrom != null)
			search.addFilterGreaterOrEqual("dateCreated", DateUtils.getMinimumDate(dateFrom));

		if (dateTo != null)
			search.addFilterLessOrEqual("dateCreated", DateUtils.getMaximumDate(dateTo));

		if (StringUtils.isNotBlank(query) && EmployeeSearchUtils.searchTermSatisfiesQueryCriteria(query)) {
			ArrayList<Filter> filters = new ArrayList<Filter>();
			EmployeeSearchUtils.generateSearchTerms(searchFields, query, filters);
			search.addFilterAnd(filters.toArray(new Filter[filters.size()]));
		}
		return search;
	}
}
