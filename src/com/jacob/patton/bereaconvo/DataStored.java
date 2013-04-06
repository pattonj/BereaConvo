package com.jacob.patton.bereaconvo;

import java.util.ArrayList;
import java.util.List;


public class DataStored {
	//Master database
	public List<String[]> database;
	
	
	public List<String[]> createdata(){
		
		 database = new ArrayList<String[]>();
			for(int i=0; i<5; i++){
				database.add(new String[]{""+i,"Fall",i+"/8/12","3:00pm","Fall Convo "+i ,"This is a description of the data that will be used"});
				database.add(new String[]{""+i+5,"Spring",i+"/2/13","3:00pm","spring convo "+i, "This is a description of the data that will be used"});
			}
			for(int i=0; i<2; i++){
				database.add(new String[]{""+i+10,"Fall",i+"/2/13","8:00pm","Fall Convo "+i,"This is a description of the data that will be used"});
				database.add(new String[]{""+i+12,"Spring",i+"/2/14","8:00pm","Spring Convo "+i,"This is a description of the data that will be used"});
			}
			for(int i=0; i<3; i++){
				database.add(new String[]{""+i+14,"Fall",i+"/2/13","6:00pm","fall convo "+i,"This is a description of the data that will be used"});
				database.add(new String[]{""+i+16,"Spring",i+"/2/14","6:00pm","spring convo "+i,"This is a description of the data that will be used"});
			}
			
			
			return database;
	}
	
	


}
