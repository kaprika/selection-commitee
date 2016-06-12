package by.training.lysiuk.project.webapp.page.plan.panel;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Iterator;

import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.persistence.metamodel.SingularAttribute;

import org.apache.wicket.authorization.Action;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeAction;
import org.apache.wicket.datetime.markup.html.basic.DateLabel;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.OrderByBorder;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import by.training.lysiuk.project.dataaccess.filters.PlanSetFilter;
import by.training.lysiuk.project.datamodel.Faculty;
import by.training.lysiuk.project.datamodel.Faculty_;
import by.training.lysiuk.project.datamodel.PlanSet;
import by.training.lysiuk.project.datamodel.PlanSet_;
import by.training.lysiuk.project.datamodel.Subject;
import by.training.lysiuk.project.service.PlanSetService;
import by.training.lysiuk.project.webapp.page.plan.PlanSetEditPage;
import by.training.lysiuk.project.webapp.page.plan.PlanSetPage;

public class PlanSetThisYearListPanel extends Panel {

	@Inject
	private PlanSetService planSetService;

	public PlanSetThisYearListPanel(String id) {
		super(id);

		PlanSetDataProvider planSetDataProvider = new PlanSetDataProvider();
		DataView<PlanSet> dataView = new DataView<PlanSet>("rows", planSetDataProvider) {
			@Override
			protected void populateItem(Item<PlanSet> item) {
				PlanSet planSet = item.getModelObject();

				planSet.getSubjects().sort(new Comparator<Subject>(){
					@Override
					public int compare(Subject o1, Subject o2) {
						return o1.getName().compareTo(o2.getName());
					}
				});
				
				item.add(new Label("id", planSet.getId()));
				
				item.add(DateLabel.forDatePattern("start date set", Model.of(planSet.getStartDateSet()), "dd-MM-yyyy"));
				item.add(DateLabel.forDatePattern("end date set", Model.of(planSet.getEndDateSet()), "dd-MM-yyyy"));

				Faculty faculty = planSet.getFaculty();
				item.add(new Label("faculty", new PropertyModel<>(faculty, "name")));

				Subject firstSubject = planSet.getSubjects().get(0);
				item.add(new Label("first subject", new PropertyModel<>(firstSubject, "name")));

				Subject secondSubject = planSet.getSubjects().get(1);
				item.add(new Label("second subject", new PropertyModel<>(secondSubject, "name")));

				Subject thirdSubject = planSet.getSubjects().get(2);
				item.add(new Label("third subject", new PropertyModel<>(thirdSubject, "name")));

				item.add(new Label("plan", planSet.getPlan()));
				
				item.add(new Link<Void>("edit-link") {
					@Override
					public void onClick() {
						setResponsePage(new PlanSetEditPage(planSet, false));
					}
				});
				item.add(new Link<Void>("delete-link") {
					@Override
					public void onClick() {
						try {
							planSetService.delete(planSet);
						} catch (PersistenceException e) {
							System.out.println("caughth persistance exception");
						}
						setResponsePage(new PlanSetPage());
					}
				});

			}
		};
		add(dataView);
		add(new OrderByBorder("sort-id", PlanSet_.id, planSetDataProvider));

		add(new OrderByBorder("sort-start date set", PlanSet_.startDateSet, planSetDataProvider));
		add(new OrderByBorder("sort-plan", PlanSet_.plan, planSetDataProvider));
		add(new OrderByBorder("sort-end date set", PlanSet_.endDateSet, planSetDataProvider));

	}

	private class PlanSetDataProvider extends SortableDataProvider<PlanSet, Serializable> {

		private PlanSetFilter planSetFilter;
		
		public PlanSetDataProvider() {
			super();
			planSetFilter = new PlanSetFilter();
			setSort((Serializable) PlanSet_.startDateSet, SortOrder.DESCENDING);
		}

		@Override
		public Iterator<PlanSet> iterator(long first, long count) {
			Serializable property = getSort().getProperty();
			SortOrder propertySortOrder = getSortState().getPropertySortOrder(property);
			planSetFilter.setSortProperty((SingularAttribute) property);
			planSetFilter.setSortOrder(propertySortOrder.equals(SortOrder.ASCENDING) ? true : false);
			return planSetService.getByCurrentYear(planSetFilter).iterator();
		}

		@Override
		public long size() {
			return planSetService.getByCurrentYear(planSetFilter).size();
		}

		@Override
		public IModel<PlanSet> model(PlanSet object) {
			return new Model(object);
		}
	}

}
