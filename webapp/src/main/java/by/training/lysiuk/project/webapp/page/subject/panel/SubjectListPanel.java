package by.training.lysiuk.project.webapp.page.subject.panel;

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

import by.training.lysiuk.project.dataaccess.filters.SubjectFilter;
import by.training.lysiuk.project.datamodel.Subject;
import by.training.lysiuk.project.datamodel.Subject_;
import by.training.lysiuk.project.service.SubjectService;
import by.training.lysiuk.project.webapp.page.subjects.SubjectEditPage;
import by.training.lysiuk.project.webapp.page.subjects.SubjectsPage;

public class SubjectListPanel extends Panel {

	@Inject
	private SubjectService subjectService;

	public SubjectListPanel(String id) {
		super(id);

		SubjectsDataProvider subjectsDataProvider = new SubjectsDataProvider();
		DataView<Subject> dataView = new DataView<Subject>("rows", subjectsDataProvider, 15) {
			@Override
			protected void populateItem(Item<Subject> item) {
				Subject subject = item.getModelObject();

				item.add(new Label("id", subject.getId()));
				item.add(new Label("name", subject.getName()));
				// item.add(new Label("price", product.getBasePrice()));
				// item.add(DateLabel.forDatePattern("created",
				// Model.of(product.getCreated()), "dd-MM-yyyy"));

				// CheckBox checkbox = new CheckBox("active",
				// Model.of(product.getActive()));
				// checkbox.setEnabled(false);
				// item.add(checkbox);
				item.add(new Link<Void>("edit-link") {
					@Override
					public void onClick() {
						setResponsePage(new SubjectEditPage(subject));
					}
				});
				item.add(new Link<Void>("delete-link") {
					@Override
					public void onClick() {
						try {
							subjectService.delete(subject);
						} catch (PersistenceException e) {
							System.out.println("caughth persistance exception");
						}

						setResponsePage(new SubjectsPage());
					}
				});
			}
		};
		add(dataView);
		add(new PagingNavigator("paging", dataView));

		add(new OrderByBorder("sort-id", Subject_.id, subjectsDataProvider));
		add(new OrderByBorder("sort-name", Subject_.name, subjectsDataProvider));

		// add(new OrderByBorder("sort-price", Product_.basePrice,
		// facultiesDataProvider));

	}

	private class SubjectsDataProvider extends SortableDataProvider<Subject, Serializable> {

		private SubjectFilter subjectFilter;

		public SubjectsDataProvider() {
			super();
			subjectFilter = new SubjectFilter();
			setSort((Serializable) Subject_.name, SortOrder.ASCENDING);
		}

		@Override
		public Iterator<Subject> iterator(long first, long count) {
			Serializable property = getSort().getProperty();
			SortOrder propertySortOrder = getSortState().getPropertySortOrder(property);

			subjectFilter.setSortProperty((SingularAttribute) property);
			subjectFilter.setSortOrder(propertySortOrder.equals(SortOrder.ASCENDING) ? true : false);

			subjectFilter.setLimit((int) count);
			subjectFilter.setOffset((int) first);
			return subjectService.find(subjectFilter).iterator();
		}

		@Override
		public long size() {
			return subjectService.count(subjectFilter);
		}

		@Override
		public IModel<Subject> model(Subject object) {
			return new Model(object);
		}

	}

}
