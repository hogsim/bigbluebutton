/**
* BigBlueButton open source conferencing system - http://www.bigbluebutton.org/
* 
* Copyright (c) 2012 BigBlueButton Inc. and by respective authors (see below).
*
* This program is free software; you can redistribute it and/or modify it under the
* terms of the GNU Lesser General Public License as published by the Free Software
* Foundation; either version 3.0 of the License, or (at your option) any later
* version.
* 
* BigBlueButton is distributed in the hope that it will be useful, but WITHOUT ANY
* WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
* PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
*
* You should have received a copy of the GNU Lesser General Public License along
* with BigBlueButton; if not, see <http://www.gnu.org/licenses/>.
*
*/
package org.bigbluebutton.conference.service.whiteboard;

import java.util.ArrayList;
import java.util.Map;

import org.bigbluebutton.conference.BigBlueButtonSession;
import org.bigbluebutton.conference.Constants;
import org.bigbluebutton.conference.service.whiteboard.shapes.Annotation;
import org.red5.logging.Red5LoggerFactory;
import org.red5.server.api.Red5;
import org.slf4j.Logger;

public class WhiteboardService {

	private static Logger log = Red5LoggerFactory.getLogger(WhiteboardService.class, "bigbluebutton");
	
	private WhiteboardApplication application;
	private WhiteboardBridge whiteboardBridge;
	
	public void setWhiteboardApplication(WhiteboardApplication a){
		log.debug("Setting whiteboard application instance");
		this.application = a;
	}
	
	public void sendAnnotation(Map<String, Object> annotation) {
//		for (Map.Entry<String, Object> entry : annotation.entrySet()) {
//		    String key = entry.getKey();
//		    Object value = entry.getValue();
		    
//		    if (key.equals("points")) {
//		    	String points = "points=[";
//		    	ArrayList<Double> v = (ArrayList<Double>) value;
//		    	log.debug(points + pointsToString(v) + "]");
//		    } else {
//		    	log.debug(key + "=[" + value + "]");
//		    }
//		}
			
		String meetingID = getMeetingId();
		String requesterID = getBbbSession().getInternalUserID();
		
		application.sendWhiteboardAnnotation(meetingID, requesterID, annotation);
		
		application.sendAnnotation(a);
		whiteboardBridge.sendAnnotation(application.getMeetingId(), a);
		whiteboardBridge.storeAnnotation(application.getMeetingId(), a);
	}
	
	private String pointsToString(ArrayList<Double> points){
    	String datapoints = "";
    	for (Double i : points) {
    		datapoints += i + ",";
    	}
    	// Trim the trailing comma
//    	log.debug("Data Point = " + datapoints);
    	return datapoints.substring(0, datapoints.length() - 1);

//		application.sendShape(shape, type, color, thickness, fill, fillColor, transparency, id, status);

	}
	
	public void setActivePage(Map<String, Object> message){		
		log.info("WhiteboardApplication - Getting number of shapes for page: " + (Integer) message.get("pageNum"));

		String meetingID = getMeetingId();
		String requesterID = getBbbSession().getInternalUserID();
		Integer page = (Integer) message.get("pageNum");
		
		application.changeWhiteboardPage(meetingID, requesterID, page);
	}
	
	public void requestAnnotationHistory(Map<String, Object> message) {
		log.info("WhiteboardApplication - requestAnnotationHistory");
		
		String meetingID = getMeetingId();
		String requesterID = getBbbSession().getInternalUserID();
		String presentationID = (String) message.get("presentationID");
		Integer pageNum = (Integer) message.get("pageNumber");
		
		application.requestAnnotationHistory(meetingID, requesterID, presentationID, pageNum);
		application.sendAnnotationHistory(getBbbSession().getInternalUserID(), 
		(String) message.get("presentationID"), (Integer) message.get("pageNumber"));
	}
		
	public void clear() {
		log.info("WhiteboardApplication - Clearing board");

		String meetingID = getMeetingId();
		String requesterID = getBbbSession().getInternalUserID();
		application.clearWhiteboard(meetingID, requesterID);		
		application.clear();
		whiteboardBridge.clear(application.getMeetingId()); // send "clrPaper" event to html5-client
	}
	
	public void undo() {
		log.info("WhiteboardApplication - Deleting last graphic");
<<<<<<< HEAD
		
		String meetingID = getMeetingId();
		String requesterID = getBbbSession().getInternalUserID();
		application.undoWhiteboard(meetingID, requesterID);
=======
		application.undo();
		whiteboardBridge.undo(application.getMeetingId()); // send "undo" event to html5-client
>>>>>>> html5-bridge-new-events
	}
	
	public void toggleGrid() {
		log.info("WhiteboardApplication - Toggling grid mode");
		//application.toggleGrid();
	}
	
	public void setActivePresentation(Map<String, Object> message) {		
		log.info("WhiteboardApplication - Setting active presentation: " + (String)message.get("presentationID"));

		String meetingID = getMeetingId();
		String requesterID = getBbbSession().getInternalUserID();
		String presentationID = (String) message.get("presentationID");
		Integer numPages = (Integer) message.get("numberOfSlides");		
		application.setWhiteboardActivePresentation(meetingID, requesterID, presentationID, numPages);
	}
	
	public void enableWhiteboard(Map<String, Object> message) {
		log.info("WhiteboardApplication - Setting whiteboard enabled: " + (Boolean)message.get("enabled"));

		String meetingID = getMeetingId();
		String requesterID = getBbbSession().getInternalUserID();
		Boolean enable = (Boolean)message.get("enabled");
		
		application.setWhiteboardEnable(meetingID, requesterID, enable);
	}
	
	public void isWhiteboardEnabled() {
		String meetingID = getMeetingId();
		String requesterID = getBbbSession().getInternalUserID();		
		application.setIsWhiteboardEnabled(meetingID, requesterID);
	}
	
	private BigBlueButtonSession getBbbSession() {
		return (BigBlueButtonSession) Red5.getConnectionLocal().getAttribute(Constants.SESSION);
	}
	
<<<<<<< HEAD
	private String getMeetingId(){
		return Red5.getConnectionLocal().getScope().getName();
	}
	
=======
	public void setWhiteboardBridge(WhiteboardBridge br){
		this.whiteboardBridge = br;
	}
>>>>>>> html5-bridge-new-events
}
