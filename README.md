**BereaConvo**
==========

An application for viewing information about Berea College convocations. 

https://play.google.com/store/apps/details?id=com.jacob.patton.bereaconvo

**Instructions**
 
 Viewing Convos
 	To view a certain convocation simply click on the title of the convo.This will either open it in a new window or in the display panel.  
 
 Sorting Convos
	To toggle between Fall and Spring convocations, click the button in the top right corner. 
    If you would like to sort the convocations by time, either slide your finger to the right, or click the top left button to show the menu. 
    
Attendance
	To mark a convocation as attended, long press on the list title until a green arrow appears. This does NOT mark it as attended in the Berea College database. It is simply a way to help you keep track of how many you have attended. You still are required to turn in your convocation card.

**Code Comments**
- There are plenty of comments in the code which shoudl explain what things are for. It is importnat to realize that currenlty there is no way to update the app mid-year and keep the list of attended convos. (This is something that needs fixed in the future). When updating the list of convos you must update the "app_version" from the String.xml file. This alerts the app of a change in convo information and clears the storedattendance string. If you do not do this, the program will attempt to merge the data and the string into the array list and want to access a position in the string that doesn't exist! 
 

**Known Issuese**
- Occasionally the image for the side menu does not tile as the XML layout specifies, but rather streches. This is a bug with Android and not the code. 

**Resources**
Berea Convo uses the ActionBarSherlock and SlidingMenu Libraries 
- http://actionbarsherlock.com/
- https://github.com/jfeinstein10/SlidingMenu

Make sure that your support v4 library is up to date on the libraries and app to run properly 

      - Right click on target (library,app,etc)> android tools> add support library. 
      
      - After installing either clean the projects or restart eclipse if it is still not working. 
      
Also, make sure that both libraries are included in your project and open in eclipse.

