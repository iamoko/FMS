package org.pahappa.systems.core.utils;

import java.util.*;

import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.pahappa.systems.core.services.RequisitionSearchUtils;
import org.sers.webutils.model.RecordStatus;
import org.sers.webutils.model.utils.SearchField;
import org.sers.webutils.model.utils.SortField;
import org.sers.webutils.server.core.utils.DateUtils;
import org.sers.webutils.server.shared.SharedAppData;

public class CustomSearchUtils {

	private static final int MINIMUM_CHARACTERS_FOR_SEARCH_TERM = 2;

	public static boolean searchTermSatisfiesQueryCriteria(String query) {
		if (StringUtils.isBlank(query)) {
			return false;
		}
		return query.length() >= MINIMUM_CHARACTERS_FOR_SEARCH_TERM;
	}

	private static Search generateSearchTerms(String query, List<String> searchFields) {
		Search search = new Search();
		search.addFilterEqual("recordStatus", RecordStatus.ACTIVE);

		if (StringUtils.isNotBlank(query) && CustomSearchUtils.searchTermSatisfiesQueryCriteria(query)) {
			ArrayList<Filter> filters = new ArrayList<Filter>();
			CustomSearchUtils.generateSearchTerms(searchFields, query, filters);
			search.addFilterAnd(filters.toArray(new Filter[filters.size()]));
		}
		return search;
	}

	private static boolean generateSearchTerms(List<String> searchFields, String query, List<Filter> filters) {
		if (searchFields != null && !searchFields.isEmpty()) {
			for (String token : query.replaceAll("  ", " ").split(" ")) {
				String searchTerm = "%" + StringEscapeUtils.escapeSql(token) + "%";
				Filter[] orFilters = new Filter[searchFields.size()];
				int counter = 0;
				for (String searchField : searchFields) {
					orFilters[counter] = Filter.like(searchField, searchTerm);
					counter++;
				}
				filters.add(Filter.or(orFilters));
			}
			return true;
		}
		return false;
	}

	public static Search generateSearchObjectForNames(String searchTerm, SortField sortField) {
		Search search = generateSearchTerms(searchTerm, Arrays.asList("firstName", "lastName"));

		if (sortField != null) {
			search.addSort(sortField.getSort());
		}

		return search;
	}

	public static Search genereateSearchObjectForWithdraws(String searchTerm, Date startDate, Date endDate,
			SortField sortField) {
		Search search = generateSearchTerms(searchTerm, Arrays.asList("user.firstName","user.lastName","transactionNumber"));
		if (sortField != null) {
			search.addSort(sortField.getSort());
		}
		if (startDate != null) {
			search.addFilterGreaterOrEqual("dateCreated", startDate);
		}
		if (endDate != null) {
			search.addFilterLessOrEqual("dateCreated", endDate);
		}
		return search;
	}

	public static Search genereateSearchObjectForDeposits(String searchTerm, Date startDate, Date endDate,
														   SortField sortField) {
		Search search = generateSearchTerms(searchTerm, Arrays.asList("user.firstName","user.lastName", "transactionNumber"));
		if (sortField != null) {
			search.addSort(sortField.getSort());
		}
		if (startDate != null) {
			search.addFilterGreaterOrEqual("depositDate", startDate);
		}
		if (endDate != null) {
			search.addFilterLessOrEqual("depositDate", endDate);
		}
		return search;
	}

	public static Search genereateSearchObjectForLogs(String searchTerm, Date startDate, Date endDate,
			SortField sortField) {
		Search search = generateSearchTerms(searchTerm, Arrays.asList("user.firstName", "user.lastName","action"));
		if (sortField != null) {
			search.addSort(sortField.getSort());
		}
		if (startDate != null) {
			search.addFilterGreaterOrEqual("dateCreated", startDate);
		}
		if (endDate != null) {
			search.addFilterLessOrEqual("dateCreated", endDate);
		}
		return search;
	}

	public static Search genereateSearchObjectForHolidays(String searchTerm, Date startDate, Date endDate,
			SortField sortField) {
		Search search = generateSearchTerms(searchTerm, Arrays.asList("comment"));
		if (sortField != null) {
			search.addSort(sortField.getSort());
		}
		if (startDate != null) {
			search.addFilterGreaterOrEqual("dateCreated", startDate);
		}
		if (endDate != null) {
			search.addFilterLessOrEqual("dateCreated", endDate);
		}
		return search;
	}

	public static Search composeRequisitionFilterForRequisitions(String query, Date dateFrom, Date dateTo,
			SortField sortField) {

		Search search = generateSearchTerms(query,
				Arrays.asList("user.firstName", "user.lastName", "requisitionNumber"));
		search.addFilterEqual("recordStatus", RecordStatus.ACTIVE);
		if (dateFrom != null)
			search.addFilterGreaterOrEqual("dateCreated", DateUtils.getMinimumDate(dateFrom));

		if (dateTo != null)
			search.addFilterLessOrEqual("dateCreated", DateUtils.getMaximumDate(dateTo));

		if (sortField != null) {
			search.addSort(sortField.getSort());
		}
		return search;
	}
	
	public static Search composeRequisitionFilterForAccounts(String query, Date dateFrom, Date dateTo,
			SortField sortField) {
		Search search = generateSearchTerms(query,
				Arrays.asList("user.firstName", "user.lastName", "user.fullName"));
		search.addFilterEqual("recordStatus", RecordStatus.ACTIVE);

		if (dateFrom != null)
			search.addFilterGreaterOrEqual("dateCreated", DateUtils.getMinimumDate(dateFrom));

		if (dateTo != null)
			search.addFilterLessOrEqual("dateCreated", DateUtils.getMaximumDate(dateTo));

		if (sortField != null) {
			search.addSort(sortField.getSort());
		}
		return search;
	}

	public static Search composeRequisitionFilterForTransactions(String query, Date dateFrom, Date dateTo,
															 SortField sortField) {
		Search search = generateSearchTerms(query,
				Arrays.asList("user.firstName", "user.lastName", "user.fullName"));
		search.addFilterEqual("recordStatus", RecordStatus.ACTIVE);

		if (dateFrom != null)
			search.addFilterGreaterOrEqual("dateCreated", DateUtils.getMinimumDate(dateFrom));

		if (dateTo != null)
			search.addFilterLessOrEqual("dateCreated", DateUtils.getMaximumDate(dateTo));

		if (sortField != null) {
			search.addSort(sortField.getSort());
		}
		return search;
	}

}
