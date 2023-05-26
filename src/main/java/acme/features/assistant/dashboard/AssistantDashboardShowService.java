
package acme.features.assistant.dashboard;

import java.util.Collection;
import java.util.HashMap;
import java.util.IntSummaryStatistics;
import java.util.Map;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.tutorial.TutorialSession;
import acme.forms.AssistantDashboard;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantDashboardShowService extends AbstractService<Assistant, AssistantDashboard> {

	@Autowired
	protected AssistantDashboardRepository repository;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Assistant.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		final AssistantDashboard dashboard = new AssistantDashboard();
		int id;
		Integer totalTheoryTutorials = 0;
		Integer totalHandsOnTutorials = 0;
		final Map<String, Double> statsTimeTutorials = new HashMap<>();
		final Map<String, Double> statsTimeSessions = new HashMap<>();
		final Collection<TutorialSession> sessions;
		final IntStream durations;
		final IntSummaryStatistics statsSession;

		id = super.getRequest().getPrincipal().getActiveRoleId();
		totalTheoryTutorials = this.repository.getTotalTheoryTutorialsByAssistantId(id);
		totalHandsOnTutorials = this.repository.getTotalHandsOnTutorialsByAssistantId(id);
		sessions = this.repository.getTutorialSessionsByAssistantId(id);
		durations = sessions.stream().map(ts -> MomentHelper.computeDuration(ts.getStartTime(), ts.getEndTime())).mapToInt(d -> (int) d.toHours());
		statsSession = durations.summaryStatistics();

		statsTimeTutorials.put("average", this.repository.getAverageTutorialTimeByAssistantId(id));
		statsTimeTutorials.put("deviation", this.repository.getDeviationTutorialTimeByAssistantId(id));
		statsTimeTutorials.put("min", this.repository.getMinTutorialTimeByAssistantId(id));
		statsTimeTutorials.put("max", this.repository.getMaxTutorialTimeByAssistantId(id));

		statsTimeSessions.put("average", statsSession.getAverage());
		statsTimeSessions.put("deviation",
			Math.sqrt(sessions.stream().map(ts -> MomentHelper.computeDuration(ts.getStartTime(), ts.getEndTime())).mapToInt(d -> (int) d.toHours()).mapToDouble(i -> i - statsSession.getAverage()).map(i -> i * i).average().getAsDouble()));
		statsTimeSessions.put("min", (double) statsSession.getMin());
		statsTimeSessions.put("max", (double) statsSession.getMax());

		dashboard.setNTutorialsTheoricalCourse(totalTheoryTutorials);
		dashboard.setNTutorialsHandsOnCourse(totalHandsOnTutorials);
		dashboard.setStatsSessionTime(statsTimeSessions);
		dashboard.setStatsTutorialTime(statsTimeTutorials);

		super.getBuffer().setData(dashboard);
	}

	@Override
	public void unbind(final AssistantDashboard object) {
		final Tuple tuple;

		tuple = super.unbind(object, "nTutorialsTheoricalCourse", "nTutorialsHandsOnCourse", "statsSessionTime", "statsTutorialTime");

		super.getResponse().setData(tuple);

	}

}
