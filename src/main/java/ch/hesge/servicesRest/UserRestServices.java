package ch.hesge.servicesRest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ch.hesge.dao.UsersRepository;
import ch.hesge.metier.UserCourseMetier;
import ch.hesge.metier.UserMetier;
import ch.hesge.model.Completion;
import ch.hesge.model.CompletionPourcentage;
import ch.hesge.model.PourcentageStrategies;
import ch.hesge.model.Suivi;
import ch.hesge.model.User;

@RestController
public class UserRestServices {

	@Autowired
	private UserMetier userMetier;
	
	@Autowired
	private UserCourseMetier userCourseMetier;
	
	@RequestMapping(value = "/users", method = RequestMethod.GET )
	public List<User> findAll() {
		return userMetier.findAll();
	}
	
	@RequestMapping(value = "users/suivi", method =RequestMethod.GET )
	public List<Suivi> getSuivis(@RequestParam String maxDate){
		
		return userCourseMetier.getSuivis(maxDate);
	}

	@RequestMapping(value = "users/{id}/suivi", method =RequestMethod.GET )
	public Suivi getUserSuivis(@RequestParam String maxDate,@PathVariable final String id){
		
		
		return userCourseMetier.getUserSuivis( id,maxDate);
	}
	
	@RequestMapping(value = "users/suiviPourcentage", method =RequestMethod.GET )
	public List<PourcentageStrategies> getPoucentagesStrategies(@RequestParam String maxDate){
		
		return userCourseMetier.getPoucentageStrategies(maxDate);
	}
	
	@RequestMapping(value = "users/{id}/suiviPourcentage", method =RequestMethod.GET )
	public PourcentageStrategies getUserPoucentagesStrategies(@RequestParam String maxDate,@PathVariable final String id){
		
		return userCourseMetier.getUserPoucentageStrategies( id,maxDate);
	}
	@RequestMapping(value = "users/completionModules", method =RequestMethod.GET )
	public List<Completion> getCompletionsModules(@RequestParam String maxDate){
		
		return userCourseMetier.getCompletionModules(maxDate);
	}
	
	@RequestMapping(value = "users/{id}/completionModules", method =RequestMethod.GET )
	public Completion getUserCompletionsModules(@RequestParam String maxDate,@PathVariable final String id){
		
		return userCourseMetier.getCompletionModules( id,maxDate);
	}
	
	@RequestMapping(value = "users/completionPourcentagesModules", method =RequestMethod.GET )
	public List<CompletionPourcentage> getCompletionsPourcentageModules(@RequestParam String maxDate){
		
		return userCourseMetier.getCompletionPourcentageModules(maxDate);
	}
	@RequestMapping(value = "users/{id}/completionPourcentagesModules", method =RequestMethod.GET )
	public CompletionPourcentage getUserCompletionsPourcentageModules(@RequestParam String maxDate,@PathVariable final String id){
		
		return userCourseMetier.getCompletionPourcentageModules( id,maxDate);
	}
	}

