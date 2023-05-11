
package acme.features.auditor.audit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.ConfigurationRepository;
import acme.entities.audit.Audit;
import acme.entities.course.Course;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditCreateService extends AbstractService<Auditor, Audit> {

	@Autowired
	protected AuditorAuditRepository	repository;

	@Autowired
	protected ConfigurationRepository	configuration;


	@Override
	public void check() {

		boolean status;

		status = super.getRequest().hasData("courseId", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {

		boolean status;
		int courseId;
		Course course;

		courseId = super.getRequest().getData("courseId", int.class);
		course = this.repository.findOneCourseById(courseId);
		status = course != null;

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Audit object;
		Auditor auditor;
		int courseId;
		Course course;

		object = new Audit();
		auditor = this.repository.findOneAuditorById(super.getRequest().getPrincipal().getActiveRoleId());
		courseId = super.getRequest().getData("courseId", int.class);
		course = this.repository.findOneCourseById(courseId);
		object.setDraftMode(true);
		object.setAuditor(auditor);
		object.setCourse(course);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Audit object) {
		assert object != null;

		int auditorId;
		Auditor auditor;
		int courseId;
		Course course;

		auditorId = super.getRequest().getPrincipal().getActiveRoleId();
		auditor = this.repository.findOneAuditorById(auditorId);
		courseId = super.getRequest().getData("courseId", int.class);
		course = this.repository.findOneCourseById(courseId);

		super.bind(object, "code", "conclusion", "strongPoints", "weakPoints", "draftMode");
		object.setAuditor(auditor);
		object.setDraftMode(true);
		object.setCourse(course);
	}

	@Override
	public void validate(final Audit object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Audit audit;
			String code;

			code = object.getCode();
			audit = this.repository.findOneAuditByCode(code);
			super.state(audit == null, "code", "auditor.audit.form.error.duplicated-code");

		}

		if (!super.getBuffer().getErrors().hasErrors("conclusion")) {
			boolean status;
			String message;

			message = object.getConclusion();
			status = this.configuration.hasSpam(message);

			super.state(!status, "conclusion", "auditor.audit.error.spam");
		}

		if (!super.getBuffer().getErrors().hasErrors("strongPoints")) {
			boolean status;
			String message;

			message = object.getStrongPoints();
			status = this.configuration.hasSpam(message);

			super.state(!status, "strongPoints", "auditor.audit.error.spam");
		}

		if (!super.getBuffer().getErrors().hasErrors("weakPoints")) {
			boolean status;
			String message;

			message = object.getConclusion();
			status = this.configuration.hasSpam(message);

			super.state(!status, "weakPoints", "auditor.audit.error.spam");
		}

	}

	@Override
	public void perform(final Audit object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Audit object) {
		assert object != null;

		Tuple tuple;
		int auditorId;
		Auditor auditor;
		int courseId;
		Course course;

		auditorId = super.getRequest().getPrincipal().getActiveRoleId();
		auditor = this.repository.findOneAuditorById(auditorId);
		courseId = super.getRequest().getData("courseId", int.class);
		course = this.repository.findOneCourseById(courseId);
		tuple = super.unbind(object, "code", "conclusion", "strongPoints", "weakPoints", "draftMode");
		tuple.put("estimatedTime", 0);
		tuple.put("courseId", super.getRequest().getData("courseId", int.class));
		tuple.put("auditor", auditor);
		tuple.put("course", course);

		super.getResponse().setData(tuple);
	}

}
