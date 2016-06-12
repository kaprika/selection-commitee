package by.training.lysiuk.project.webapp.page.students.panel;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.datetime.markup.html.basic.DateLabel;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import by.training.lysiuk.project.dataaccess.filters.PlanSetFilter;
import by.training.lysiuk.project.datamodel.Competition;
import by.training.lysiuk.project.datamodel.Enrolee;
import by.training.lysiuk.project.datamodel.Faculty;
import by.training.lysiuk.project.service.CompetitionService;

public class StudentListPanel extends Panel {

	@Inject
	private CompetitionService competitionService;

	private PlanSetFilter filter;

	public StudentListPanel(String id, PlanSetFilter filter) {
		super(id);
		this.filter = filter;

		CompetitionDataProvider competitionDataProvider = new CompetitionDataProvider();

		DataView<Competition> dataView = new DataView<Competition>("rows", competitionDataProvider) {
			@Override
			protected void populateItem(Item<Competition> item) {
				Competition competition = item.getModelObject();

				Faculty faculty = competition.getFaculty();
				item.add(new Label("faculty", new PropertyModel<>(faculty, "name")));
				String listEnrolees = "";
				List<Enrolee> enrolees = competition.getEnrolees();
				int i = 1;
				for (Enrolee enrolee : enrolees) {
					listEnrolees += i + " " + enrolee.getFirstName() + " " + enrolee.getLastName() + " ("
							+ enrolee.getTotalScore() + ")<br/>";
					i++;
				}

				item.add(new Label("list-enrolees", listEnrolees).setEscapeModelStrings(false));
				item.add(DateLabel.forDatePattern("start date", Model.of(competition.getStartDateSet()), "dd-MM-yyyy"));
				item.add(DateLabel.forDatePattern("end date", Model.of(competition.getEndDateSet()), "dd-MM-yyyy"));
				item.add(new Label("plan", competition.getPlan()));
				item.add(new Label("competition", competition.getCompetition()));
				item.add(new Label("passing score", competition.getPassingScore()));
			}
		};
		add(dataView);
//		add(new PagingNavigator("paging", dataView));
		// добавить пагинацию
		/*
		 * add(new OrderByBorder("sort-id", PlanSet_.id, planSetDataProvider));
		 * add(new OrderByBorder("sort-start date set", PlanSet_.startDateSet,
		 * planSetDataProvider)); add(new OrderByBorder("sort-plan",
		 * PlanSet_.plan, planSetDataProvider)); add(new OrderByBorder(
		 * "sort-end date set", PlanSet_.endDateSet, planSetDataProvider));
		 * add(new OrderByBorder("sort-price", Product_.basePrice,
		 * facultiesDataProvider));
		 */
	}

	private class CompetitionDataProvider extends SortableDataProvider<Competition, Serializable> {

		// private PlanSetFilter planSetFilter;

		public CompetitionDataProvider() {
			super();
			// planSetFilter = new PlanSetFilter();
			// planSetFilter.setFetchFaculty(true);
			// planSetFilter.setFetchSubjects(true);
			// setSort((Serializable) PlanSet_.startDateSet,
			// SortOrder.DESCENDING);
		}

		@Override
		public Iterator<Competition> iterator(long first, long count) {
			return competitionService.closedCompetitionList(filter).iterator();
		}

		@Override
		public long size() {
			return competitionService.closedCompetitionList(filter).size();
		}

		@Override
		public IModel<Competition> model(Competition object) {
			return new Model(object);
		}
	}

}