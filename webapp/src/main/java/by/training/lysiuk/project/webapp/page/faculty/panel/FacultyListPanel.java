package by.training.lysiuk.project.webapp.page.faculty.panel;

import java.io.Serializable;
import java.util.Iterator;

import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.persistence.metamodel.SingularAttribute;

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

import by.training.lysiuk.project.dataaccess.filters.FacultyFilter;
import by.training.lysiuk.project.datamodel.Faculty;
import by.training.lysiuk.project.datamodel.Faculty_;
import by.training.lysiuk.project.service.FacultyService;
import by.training.lysiuk.project.webapp.page.faculties.FacultiesPage;
import by.training.lysiuk.project.webapp.page.faculties.FacultyEditPage;

public class FacultyListPanel extends Panel {

	@Inject
	private FacultyService facultyService;

	public FacultyListPanel(String id) {
		super(id);

		FacultiesDataProvider facultiesDataProvider = new FacultiesDataProvider();
		DataView<Faculty> dataView = new DataView<Faculty>("rows", facultiesDataProvider, 10) {
			@Override
			protected void populateItem(Item<Faculty> item) {
				Faculty faculty = item.getModelObject();

				item.add(new Label("id", faculty.getId()));
				item.add(new Label("name", faculty.getName()));
				item.add(new Link<Void>("edit-link") {
					@Override
					public void onClick() {
						setResponsePage(new FacultyEditPage(faculty));
					}
				});
				item.add(new Link<Void>("delete-link") {
					@Override
					public void onClick() {
						FacultiesPage page = new FacultiesPage();
						try {
							facultyService.delete(faculty);
						} catch (PersistenceException e) {
							page.error(getString("faculty.error"));
						} finally {
							setResponsePage(page);
						}
					}
				});
			}
		};
		add(dataView);
		add(new PagingNavigator("paging", dataView));

		add(new OrderByBorder("sort-id", Faculty_.id, facultiesDataProvider));
		add(new OrderByBorder("sort-name", Faculty_.name, facultiesDataProvider));

	}

	private class FacultiesDataProvider extends SortableDataProvider<Faculty, Serializable> {

		private FacultyFilter facultyFilter;

		public FacultiesDataProvider() {
			super();
			facultyFilter = new FacultyFilter();
			setSort((Serializable) Faculty_.name, SortOrder.ASCENDING);
		}

		@Override
		public Iterator<Faculty> iterator(long first, long count) {
			Serializable property = getSort().getProperty();
			SortOrder propertySortOrder = getSortState().getPropertySortOrder(property);

			facultyFilter.setSortProperty((SingularAttribute) property);
			facultyFilter.setSortOrder(propertySortOrder.equals(SortOrder.ASCENDING) ? true : false);

			facultyFilter.setLimit((int) count);
			facultyFilter.setOffset((int) first);
			return facultyService.find(facultyFilter).iterator();
		}

		@Override
		public long size() {
			return facultyService.count(facultyFilter);
		}

		@Override
		public IModel<Faculty> model(Faculty object) {
			return new Model(object);
		}
	}

}
