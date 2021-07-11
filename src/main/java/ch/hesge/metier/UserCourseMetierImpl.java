package ch.hesge.metier;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.hesge.dao.UserCourseRepository;
import ch.hesge.dao.UsersRepository;
import ch.hesge.model.Completion;
import ch.hesge.model.CompletionPourcentage;
import ch.hesge.model.PourcentageStrategies;
import ch.hesge.model.Suivi;
import ch.hesge.model.User;
import ch.hesge.model.UserCourse;
import ch.hesge.utils.StrategiesUtils;

@Service
public class UserCourseMetierImpl implements UserCourseMetier {

	
	
	@Autowired
	private UsersRepository usersRepository;
	
	@Override
	public List<Suivi> getSuivis(String maxDate) {
		List<Suivi> suivis=new ArrayList<>();
		List<User> users=usersRepository.findAll();
		users.stream()
		.forEach(u->{
			Suivi suivi=new Suivi();

			suivi.setUserName(u.getLastname()+" "+u.getFirstname());
			suivi.setStrategiesList(StrategiesUtils.getStrategies(u.getUserCourses(),maxDate));
			suivis.add(suivi);
		}
				);

		return suivis;
	}

	@Override
	public Suivi getUserSuivis(String id,String maxDate) {
		Optional<User> optionalUser=usersRepository.findById(id);
		Suivi suivi=new Suivi();
		if(optionalUser.isPresent()) {
			User u=optionalUser.get();
			suivi.setUserName(u.getLastname()+" "+u.getFirstname());
			suivi.setStrategiesList(StrategiesUtils.getStrategies(u.getUserCourses(),maxDate));
			return suivi;
		}
		return null;
	}

	@Override
	public List<PourcentageStrategies> getPoucentageStrategies(String maxDate) {
		List<PourcentageStrategies> pourcentageStrategies=new ArrayList<>();
		List<User> users=usersRepository.findAll();
		users.stream()
		.forEach(u->{
			PourcentageStrategies pStrategies=new PourcentageStrategies();

			pStrategies.setUserName(u.getLastname()+" "+u.getFirstname());
			pStrategies.setPourcentagesStrategy(StrategiesUtils.getpourcentageStrategies(u.getUserCourses(),maxDate));
			pourcentageStrategies.add(pStrategies);
		}
				);

		return pourcentageStrategies;
	}

	@Override
	public PourcentageStrategies getUserPoucentageStrategies(String id,String maxDate) {
		Optional<User> optionalUser=usersRepository.findById(id);
		PourcentageStrategies pStrategies=new PourcentageStrategies();
		if(optionalUser.isPresent()) {
			User u=optionalUser.get();
			pStrategies.setUserName(u.getLastname()+" "+u.getFirstname());
			pStrategies.setPourcentagesStrategy(StrategiesUtils.getpourcentageStrategies(u.getUserCourses(),maxDate));
			return pStrategies;
		}
		return null;
	}

	@Override
	public List<Completion> getCompletionModules(String maxDate) {
		List<Completion> completions=new ArrayList<>();
		List<User> users=usersRepository.findAll();
		users.stream()
		.forEach(u->{
			Completion completion=new Completion();

			completion.setUserName(u.getLastname()+" "+u.getFirstname());
			completion.setDelaysBefore(StrategiesUtils.getCompletionssModules(u.getUserCourses(),maxDate));
			completions.add(completion);
		}
		);
		return completions;
	}


	@Override
	public Completion getCompletionModules(String id,String maxDate) {
		Optional<User> optionalUser=usersRepository.findById(id);
		Completion completion=new Completion();
		if(optionalUser.isPresent()) {
			User u=optionalUser.get();
			completion.setUserName(u.getLastname()+" "+u.getFirstname());
			completion.setDelaysBefore(StrategiesUtils.getCompletionssModules(u.getUserCourses(),maxDate));
			return completion;
		}
		return null;
	}

	@Override
	public List<CompletionPourcentage> getCompletionPourcentageModules(String maxDate) {
		List<CompletionPourcentage> completionsPourcentage=new ArrayList<>();
		List<User> users=usersRepository.findAll();
		users.stream()
		.forEach(u->{
			CompletionPourcentage completionP=StrategiesUtils.getCompletionsPourcentageModules(u.getUserCourses(),maxDate);

			completionP.setUserName(u.getLastname()+" "+u.getFirstname());
			
			completionsPourcentage.add(completionP);
		}
		);
		return completionsPourcentage;
	}

	@Override
	public CompletionPourcentage getCompletionPourcentageModules(String id,String maxDate) {
		Optional<User> optionalUser=usersRepository.findById(id);
		if(optionalUser.isPresent()) {
			User u=optionalUser.get();
			CompletionPourcentage completionP=StrategiesUtils.getCompletionsPourcentageModules(u.getUserCourses(),maxDate);

			completionP.setUserName(u.getLastname()+" "+u.getFirstname());
			return completionP;
		}
		return null;
	}

}
