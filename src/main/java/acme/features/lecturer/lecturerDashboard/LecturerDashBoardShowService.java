
package acme.features.lecturer.lecturerDashboard;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.forms.LecturerDashboard;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerDashBoardShowService extends AbstractService<Lecturer, LecturerDashboard> {

	@Autowired
	protected LecturerDashBoardRepository repository;


	@Override
	public void check() {

		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Lecturer.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		final LecturerDashboard dashBoard = new LecturerDashboard();
		int id;
		Integer totalTheoryLectures = 0;
		Integer totalHandsOnLectures = 0;
		final Lecturer lecturer;
		final Map<String, Double> statsLearningTimeLectures = new HashMap<>();
		final Map<String, Double> statsLearningTimeCourses = new HashMap<>();

		id = super.getRequest().getPrincipal().getActiveRoleId();
		totalTheoryLectures = this.repository.getTotalTheoryLecturesByLecturerId(id);
		totalHandsOnLectures = this.repository.getTotalHandsOnLecturesByLecturerId(id);

		statsLearningTimeLectures.put("averageLecture", this.repository.getAverageLectureLearningTimeByLecturerId(id));
		statsLearningTimeLectures.put("deviationLecture", this.repository.getDeviationLectureLearningTimeByLecturerId(id));
		statsLearningTimeLectures.put("minLecture", this.repository.getMinLectureLearningTimeByLecturerId(id));
		statsLearningTimeLectures.put("maxLecture", this.repository.getMaxLectureLearningTimeByLecturerId(id));

		statsLearningTimeCourses.put("averageCourse", this.repository.getAverageCourseLearningTimeByLecturerId(id));
		statsLearningTimeCourses.put("deviationCourse", this.repository.getDeviationCourseLearningTimeByLecturerId(id));
		statsLearningTimeCourses.put("minCourse", this.repository.getMinCourseLearningTimeByLecturerId(id));
		statsLearningTimeCourses.put("maxCourse", this.repository.getMaxCourseLearningTimeByLecturerId(id));

		dashBoard.setTotalThoeryLectures(totalTheoryLectures);
		dashBoard.setTotalHansOndLectures(totalHandsOnLectures);
		dashBoard.setStatsLearningTimeLectures(statsLearningTimeLectures);
		dashBoard.setStatsLearningTimeCourses(statsLearningTimeCourses);

		super.getBuffer().setData(dashBoard);
	}

	@Override
	public void unbind(final LecturerDashboard object) {
		final Tuple tuple;

		tuple = super.unbind(object, "totalThoeryLectures", "totalHansOndLectures", "statsLearningTimeLectures", "statsLearningTimeCourses");

		super.getResponse().setData(tuple);

	}

}
