package ch.hesge.metier;

import java.util.List;

import ch.hesge.model.Completion;
import ch.hesge.model.CompletionPourcentage;
import ch.hesge.model.PourcentageStrategies;
import ch.hesge.model.Suivi;

public interface UserCourseMetier {

	public List<Suivi> getSuivis(String maxDate);
	public Suivi getUserSuivis(String id,String maxDate);
	public List<PourcentageStrategies> getPoucentageStrategies(String maxDate);
	public PourcentageStrategies getUserPoucentageStrategies(String id,String maxDate);
	public List<Completion> getCompletionModules(String maxDate);
	public Completion getCompletionModules(String id,String maxDate);
	public List<CompletionPourcentage> getCompletionPourcentageModules(String maxDate);
	public CompletionPourcentage getCompletionPourcentageModules(String id,String maxDate);

}
