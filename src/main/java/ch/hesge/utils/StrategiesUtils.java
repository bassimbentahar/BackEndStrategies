package ch.hesge.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Date;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;


import ch.hesge.enums.CompletionStatus;
import ch.hesge.enums.ModuleType;
import ch.hesge.model.CompletionPourcentage;
import ch.hesge.model.UserCourse;
import ch.hesge.model.UserModule;

public class StrategiesUtils {
	//strategies
	private static final String MOT="6";
	private static final String OT="5";
	private static final String MO="4";
	private static final String O="3";
	private static final String M="2";
	private static final String x="1";
	
	//for Timing
	 private static final Map<String, Integer> mapTiming = new HashMap<>();
	    static {
	    	mapTiming.put("INITIAL-CONTENT", 0);
	    	mapTiming.put("INITIAL-RESUME", 1);
	    	mapTiming.put("INITIAL-TEST", 2);
	    	mapTiming.put("CONTENT-RESUME", 1);
	    	mapTiming.put("CONTENT-TEST", 2);
	    	mapTiming.put("RESUME-TEST", 1);
	    	
	    	// noremalement y'a pas 2 contenus qui se suivent , sauf dans le cas où on le définit, ici c'est un bug de l'API
	    	mapTiming.put("CONTENT-CONTENT", 999);
	    }
	private final static int NB_HOURS_LATE=24;
	//private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	
	
public static List<Map<String,String>> getStrategies(List<UserCourse> listeUserCourses ,String maxDate){
	// pour filter par date
	if(maxDate.length()>0) {
		listeUserCourses= filterByMaxDate(listeUserCourses,maxDate);

	}
	List<Map<String,String>> listStrategies=new ArrayList<>();

	listeUserCourses.stream()
	.forEach(uc->{

		Map<String,String> mapStrategies=new HashMap<>();

		mapStrategies.put("strategy", getStrategy(uc));
		
		mapStrategies.put("testStatus", getTestStatus(uc));
		listStrategies.add(mapStrategies);
	});

	return listStrategies;
	
}


public static Map<String, Double> getpourcentageStrategies(List<UserCourse> listeUserCourses,String maxDate) {
	
	Map<String,Double> mapPourcentageStrategies=new HashMap<>();
	mapPourcentageStrategies.put(MOT, 0.0);
	mapPourcentageStrategies.put(OT, 0.0);
	mapPourcentageStrategies.put(MO, 0.0);
	mapPourcentageStrategies.put(O, 0.0);
	mapPourcentageStrategies.put(M, 0.0);
	mapPourcentageStrategies.put(x, 0.0);
	
	Map<String,Integer> mapSumPourcentageStrategies=new HashMap<>();
	mapSumPourcentageStrategies.put(MOT, 0);
	mapSumPourcentageStrategies.put(OT, 0);
	mapSumPourcentageStrategies.put(MO, 0);
	mapSumPourcentageStrategies.put(O, 0);
	mapSumPourcentageStrategies.put(M, 0);
	mapSumPourcentageStrategies.put(x, 0);
	
	List<Map<String,String>> listStrategies=getStrategies(listeUserCourses,maxDate);
	listStrategies.stream()
	.forEach(mapSuivi->{
		mapSumPourcentageStrategies.put(mapSuivi.get("strategy"), mapSumPourcentageStrategies.get(mapSuivi.get("strategy"))+1);
	});
	int sum = mapSumPourcentageStrategies.values().stream().reduce(0, Integer::sum);
	if(sum!=0) {
	    mapSumPourcentageStrategies.forEach((k, v) -> {
	    	
			mapPourcentageStrategies.put(k,  100*(mapSumPourcentageStrategies.get(k)/(double)sum));
	
	    });
	}

	return mapPourcentageStrategies;
}

public static SortedMap<Date, Integer> getCompletionssModules(List<UserCourse> listUserCourses, String maxDate) {
	
	// pour filter par date
	if(maxDate.length()>0) {
		listUserCourses= filterByMaxDate(listUserCourses,maxDate);

	}
	SortedMap<Date, List<UserModule>>  mapModulesbyDate=new TreeMap<>();
	SortedMap<Date, Integer>  mapdelaysBefore=new TreeMap<>();
	
	listUserCourses.stream()
	.forEach(uc->{
		
		uc.getUserModules().stream()
		.filter(um->um!=null && um.getSendDate()!=null)
		.forEach(um->{
			
			try {
				
				String pattern = "yyyy-MM-dd";
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
				synchronized (simpleDateFormat) {
					
				
				String date = simpleDateFormat.format(um.getSendDate());
				
				Date endDate = simpleDateFormat.parse(date);
				List<UserModule> listUms=mapModulesbyDate.get(endDate);
				if(listUms == null) listUms=new ArrayList<>();
				listUms.add(um);
				mapModulesbyDate.put(endDate,listUms);
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		});
		
	});

	SortedMap<Date, List<UserModule>>  mapAllModulesBefore=new TreeMap<>();
	List<UserModule> uMLLBefore = new ArrayList<>();

	mapModulesbyDate.forEach((k,v)->{
		List<UserModule> uMList = mapModulesbyDate.get(k);
		if (uMList== null) uMList=new ArrayList();
		uMLLBefore.addAll(uMList);

		mapAllModulesBefore.put(k,new ArrayList<UserModule>(uMLLBefore));
		
	});


	mapAllModulesBefore.forEach((k,v)->{
		
		v.stream()
		.forEach(um->{
			Integer nbdelaysModules=mapdelaysBefore.get(k);
			if(nbdelaysModules == null) nbdelaysModules=0;	
			if(um.getEndDate()==null || 
					k.toInstant().plus(NB_HOURS_LATE,ChronoUnit.HOURS).isBefore(um.getEndDate().toInstant()) ) {

				nbdelaysModules++;
				
			}
			
			
			mapdelaysBefore.put(k,nbdelaysModules);

		});

	});
	
	
	return mapdelaysBefore;
}


public static CompletionPourcentage getCompletionsPourcentageModules(List<UserCourse> listeUserCourses, String maxDate) {
	// pour filter par date
		if(maxDate.length()>0) {
			listeUserCourses= filterByMaxDate(listeUserCourses,maxDate);

		}
	
	CompletionPourcentage completionPourcentage=new CompletionPourcentage();
	
	Map<String,Integer> mapCompletedByType=new HashMap<>();
	mapCompletedByType.put(ModuleType.INITIAL.getType(),0 );
	mapCompletedByType.put(ModuleType.CONTENT.getType(),0 );
	mapCompletedByType.put(ModuleType.RESUME.getType(),0 );
	mapCompletedByType.put(ModuleType.TEST.getType(),0 );
	
	Integer[] sum= {0,0,0,0};
	listeUserCourses.stream()
	.forEach(uc->{
		uc.getUserModules().stream()
		.forEach(um->{
			if(um.getModule().getModuleType().equals(ModuleType.INITIAL)) {
				sum[0]=sum[0]+1;
				if(um.getCompletionStatus().equals(CompletionStatus.COMPLETE)) {
				mapCompletedByType.put(ModuleType.INITIAL.getType(),mapCompletedByType.get(ModuleType.INITIAL.getType())+1 );
				}
			}else if(um.getModule().getModuleType().equals(ModuleType.CONTENT)) {
				sum[1]=sum[1]+1;

				if(um.getCompletionStatus().equals(CompletionStatus.COMPLETE)) {
				mapCompletedByType.put(ModuleType.CONTENT.getType(),mapCompletedByType.get(ModuleType.CONTENT.getType())+1 );
				}
			}else if(um.getModule().getModuleType().equals(ModuleType.RESUME)) {
				sum[2]=sum[2]+1;

				if(um.getCompletionStatus().equals(CompletionStatus.COMPLETE)) {
				mapCompletedByType.put(ModuleType.RESUME.getType(),mapCompletedByType.get(ModuleType.RESUME.getType())+1 );
				}
			}else if(um.getModule().getModuleType().equals(ModuleType.TEST)) {
				sum[3]=sum[3]+1;
				if(um.getCompletionStatus().equals(CompletionStatus.COMPLETE)) {
				mapCompletedByType.put(ModuleType.TEST.getType(),mapCompletedByType.get(ModuleType.TEST.getType())+1 );
				}
			}
		});
		
	});
	
	
	completionPourcentage.setInitial(mapCompletedByType.get(ModuleType.INITIAL.getType())/(double)sum[0]);
	completionPourcentage.setContent(mapCompletedByType.get(ModuleType.CONTENT.getType())/(double)sum[1]);
	completionPourcentage.setResume(mapCompletedByType.get(ModuleType.RESUME.getType())/(double)sum[2]);
	completionPourcentage.setTest(mapCompletedByType.get(ModuleType.TEST.getType())/(double)sum[3]);
	return completionPourcentage;
}
private static List<UserCourse> filterByMaxDate(List<UserCourse> listeUserCourses, String maxDate) {
	try {
		Date mDate=new SimpleDateFormat("dd/MM/yyyy").parse(maxDate);

		listeUserCourses=listeUserCourses.stream()
				.filter(uc->uc.getUserModules().get(0).getSendDate().toInstant().isBefore(mDate.toInstant()))
				.collect(Collectors.toList());
	} catch (ParseException e) {
	}	
	
	return listeUserCourses;
}

private static String getTestStatus(UserCourse uc) {
	List<UserModule> listUserModules=uc.getUserModules().stream()
	.filter(um->um.getModule().getModuleType().equals(ModuleType.TEST))
	.collect(Collectors.toList());
	
	UserModule um=listUserModules.get(0);

	return (um.isSuccess())?"SUCCESS_OK":"SUCCESS_KO" ;
}

private static String getStrategy(UserCourse uC) {
	
	boolean isM=isM(uC);
	boolean isO=isO(uC);
	boolean isT=isT(uC);
	if(isM) {
		if(isO ) {
			if(isT) {
				return MOT;
			}else {
				return MO;
			}
		}else {
			return M;
		}
	}else if(isO){
		if(isT) {
			return OT;
		}else {
			return O;
		}
	}else {
		return x;
	}
	
}

private static boolean isT(UserCourse uC) {
	List<UserModule> listUsersModules=uC.getUserModules();
	 
	listUsersModules.sort((o1, o2) -> o1.supposedOrder().compareTo(o2.supposedOrder()));
	
	Deque<UserModule> stack = new ArrayDeque<>();
	stack.addAll(listUsersModules.stream().filter(um->um.getEndDate()!=null ).collect(Collectors.toList()));
	//System.out.println("-----");
	//listUsersModules.forEach(um->System.out.println(um));
	Iterator<UserModule> iterUM = stack.iterator();
	if(stack.isEmpty()) { return false;}
		
		UserModule um1=iterUM.next();
		Date convertedDate=(Date) um1.getEndDate().clone();
		convertedDate.setHours(0);
		convertedDate.setMinutes(0);
		convertedDate.setSeconds(0);
		Instant i1=convertedDate.toInstant();

	while (iterUM.hasNext()){
		UserModule um2=iterUM.next();
		Date convertedDate2=(Date) um2.getEndDate().clone();
		convertedDate2.setHours(0);
		convertedDate2.setMinutes(0);
		convertedDate2.setSeconds(0);
		
		Instant i2=convertedDate2.toInstant();
		//System.out.println(um1.getModule().getModuleType()+"-"+um2.getModule().getModuleType()+"  "+ChronoUnit.DAYS.between(i1,i2));
		//System.out.println(mapTiming.get(um1.getModule().getModuleType()+"-"+um2.getModule().getModuleType()));
		//System.out.println(i1);
		//System.out.println(i2);
		if(ChronoUnit.DAYS.between(i1,i2)!= mapTiming.get(um1.getModule().getModuleType()+"-"+um2.getModule().getModuleType())) {
			return false;
		}
		um1=um2;
		i1=i2;
	}
	return true;
}

private static boolean isO(UserCourse uC) {
	List<UserModule> listUsersModules=uC.getUserModules();
	 
	listUsersModules.sort((o1, o2) -> o1.supposedOrder().compareTo(o2.supposedOrder()));
	
	Deque<UserModule> stack = new ArrayDeque<>();
	stack.addAll(listUsersModules.stream().filter(um->um.getEndDate()!=null ).collect(Collectors.toList()));

			//System.out.println(listUsersModules.stream().filter(um->um.getEndDate()!=null ).collect(Collectors.toList()));
	Iterator<UserModule> iterUM = stack.iterator();
	if(stack.isEmpty()) { return false;}
		
		Instant i1=iterUM.next().getEndDate().toInstant();

	while (iterUM.hasNext()){
		Instant i2=iterUM.next().getEndDate().toInstant();
		if(!i1.isBefore(i2)) {
			return false;
		}
		i1=i2;
	}
	
	
	return true;
	

}

/**
 * est ce que les cours sont completed ?
 * @param uC
 * @return
 */
private static boolean isM(UserCourse uC) {

	for (UserModule um : uC.getUserModules()) {
		if(!um.getCompletionStatus().equals(CompletionStatus.COMPLETE)) {
			return false;
		};
	}
	
	return true;
}

}

