package org.pahappa.systems.views;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.pahappa.systems.core.services.ReportingService;
import org.primefaces.model.chart.PieChartModel;
import org.sers.webutils.server.core.utils.ApplicationContextProvider;
import org.sers.webutils.server.shared.SharedAppData;
@SessionScoped
@ManagedBean
public class LandingView {
	private PieChartModel pieChartModel;
	private ReportingService reportingService;
	private Map<String, Integer> valuesMap;
	@PostConstruct
	public void init() {
		this.reportingService = ApplicationContextProvider.getApplicationContext().getBean(ReportingService.class);
		reloadGraph();
	}
	private void reloadGraph() {
		System.out.println("somehing");
		this.valuesMap = this.reportingService.getUserRequisitionSummary(SharedAppData.getLoggedInUser());
		this.pieChartModel = new PieChartModel();
		 for (String key : this.valuesMap.keySet()) {
			 this.pieChartModel.set(key, valuesMap.get(key));	 
		 }
		 this.pieChartModel.setTitle("Summary");
		 this.pieChartModel.setLegendPosition("e");
		 this.pieChartModel.setFill(true);
		 this.pieChartModel.setShowDataLabels(true);
		 this.pieChartModel.setDiameter(200);
		 this.pieChartModel .setShadow(true);
	}
	
	public Map<String, Integer> getValuesMap() {
		return valuesMap;
	}
	public void setValueMap(Map<String, Integer> valuesMap) {
		this.valuesMap = valuesMap;
	}
}