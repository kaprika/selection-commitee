package by.training.lysiuk.project.webapp.page.enrollee.panel;

import java.io.Serializable;
import java.util.Iterator;

import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.persistence.metamodel.SingularAttribute;

import org.apache.wicket.datetime.markup.html.basic.DateLabel;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.OrderByBorder;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import by.training.lysiuk.project.dataaccess.filters.EnrolleeFilter;
import by.training.lysiuk.project.datamodel.Enrolee;
import by.training.lysiuk.project.datamodel.Enrolee_;
import by.training.lysiuk.project.datamodel.Faculty;
import by.training.lysiuk.project.datamodel.ScoresInSubjects;
import by.training.lysiuk.project.datamodel.Subject;
import by.training.lysiuk.project.service.EnroleeService;
import by.training.lysiuk.project.service.ScoresInSubjectsService;
import by.training.lysiuk.project.webapp.page.enrollees.EnrolleesPage;
import by.training.lysiuk.project.webapp.page.registration.RegistrationPage;

public class EnrolleeListPanel extends Panel {

	@Inject
	private EnroleeService enroleeService;
	
	@Inject
	private ScoresInSubjectsService scoresInSubjectsService;

	public EnrolleeListPanel(String id) {
		super(id);

		EnrolleesDataProvider enrolleesDataProvider = new EnrolleesDataProvider();
		DataView<Enrolee> dataView = new DataView<Enrolee>("rows", enrolleesDataProvider, 15) {
			@Override
			protected void populateItem(Item<Enrolee> item) {
				Enrolee enrolee = item.getModelObject();

				item.add(new Label("id", enrolee.getId()));
				item.add(new Label("lname", enrolee.getLastName()));
				item.add(new Label("fname", enrolee.getFirstName()));
				item.add(new Label("email", enrolee.getEmail()));
				item.add(
						DateLabel.forDatePattern("registred", Model.of(enrolee.getDateOfRegistration()), "dd-MM-yyyy"));
				item.add(new Label("identification number", enrolee.getIdentificationNumber()));
				item.add(new Label("phone number", enrolee.getPhoneNumber()));
				Faculty faculty = enrolee.getPlanSet().getFaculty();
				item.add(new Label("faculty", faculty.getName()));
				
				ScoresInSubjects firstScore = scoresInSubjectsService.getScoresInSubjectsByEnrolee(enrolee).get(0);
				Subject firstSubject = firstScore.getSubject();
				item.add(new Label("subject1", firstSubject.getName()));
				item.add(new Label("points1", firstScore.getPoints()));
				
				ScoresInSubjects secondScore = scoresInSubjectsService.getScoresInSubjectsByEnrolee(enrolee).get(1);
				Subject secondSubject = secondScore.getSubject();
				item.add(new Label("subject2", secondSubject.getName()));
				item.add(new Label("points2", secondScore.getPoints()));
				
				ScoresInSubjects thirdScore = scoresInSubjectsService.getScoresInSubjectsByEnrolee(enrolee).get(2);
				Subject thirdSubject = thirdScore.getSubject();
				item.add(new Label("subject3", thirdSubject.getName()));
				item.add(new Label("points3", thirdScore.getPoints()));
				
				
				
				/*Subject firstSubject = enrolee.getPlanSet().getSubjects().get(0);
				item.add(new Label("subject1", firstSubject.getName()));
				Subject secondSubject = enrolee.getPlanSet().getSubjects().get(1);
				item.add(new Label("subject2", secondSubject.getName()));
				Subject thirdSubject = enrolee.getPlanSet().getSubjects().get(2);
				item.add(new Label("subject3", thirdSubject.getName()));
				*/
				item.add(new Label("certificate", enrolee.getCertificate()));

		
				item.add(new Link<Void>("edit-link") {
					@Override
					public void onClick() {
						setResponsePage(new RegistrationPage(enrolee));
					}
				});
				item.add(new Link<Void>("delete-link") {
					@Override
					public void onClick() {
						try {
							scoresInSubjectsService.deleteByEnrolleeId(enrolee.getId());
							enroleeService.delete(enrolee);
						} catch (PersistenceException e) {
							System.out.println("caughth persistance exception");
						}

						setResponsePage(new EnrolleesPage());
					}
				});
			}
		};
		add(dataView);
		add(new PagingNavigator("paging", dataView));

		add(new OrderByBorder("sort-id", Enrolee_.id, enrolleesDataProvider));
		add(new OrderByBorder("sort-lname", Enrolee_.lastName, enrolleesDataProvider));
		add(new OrderByBorder("sort-fname", Enrolee_.firstName, enrolleesDataProvider));
		add(new OrderByBorder("sort-registred", Enrolee_.dateOfRegistration, enrolleesDataProvider));

	}

	private class EnrolleesDataProvider extends SortableDataProvider<Enrolee, Serializable> {

		private EnrolleeFilter enrolleFilter;

		public EnrolleesDataProvider() {
			super();
			enrolleFilter = new EnrolleeFilter();
			enrolleFilter.setFetchFaculty(true);
			enrolleFilter.setFetchSubjects(true);
			setSort((Serializable) Enrolee_.lastName, SortOrder.ASCENDING);
		}

		@Override
		public Iterator<Enrolee> iterator(long first, long count) {
			Serializable property = getSort().getProperty();
			SortOrder propertySortOrder = getSortState().getPropertySortOrder(property);

			enrolleFilter.setSortProperty((SingularAttribute) property);
			enrolleFilter.setSortOrder(propertySortOrder.equals(SortOrder.ASCENDING) ? true : false);

			enrolleFilter.setLimit((int) count);
			enrolleFilter.setOffset((int) first);
			return enroleeService.find(enrolleFilter).iterator();
		}

		@Override
		public long size() {
			return enroleeService.count(enrolleFilter);
		}

		@Override
		public IModel<Enrolee> model(Enrolee object) {
			return new Model(object);
		}
	}
}
