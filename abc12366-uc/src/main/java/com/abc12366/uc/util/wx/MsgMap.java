package com.abc12366.uc.util.wx;

import java.util.HashMap;
import java.util.Map;
/**
 * 
 * @author zhushuai 2017-7-27
 *
 */
public class MsgMap {
	    public MsgMap()
	    {
	    }

	    public static int getMsgType(String key)
	    {
	        return ((Integer)msgmapping.get(key)).intValue();
	    }
	    
	    public static int getEventType(String key){
	    	return ((Integer)eventmapping.get(key)).intValue();
	    }

	    public static final Map<String, Integer> msgmapping = new HashMap<String,Integer>() {      
	        /**
			 * 
			 */
			private static final long serialVersionUID = 1L;
            {
	            put("text", Integer.valueOf(0));
	            put("image", Integer.valueOf(1));
	            put("voice", Integer.valueOf(2));
	            put("video", Integer.valueOf(3));
	            put("shortvideo", Integer.valueOf(4));
	            put("location", Integer.valueOf(5));
	            put("link", Integer.valueOf(6));
	            put("event", Integer.valueOf(7));
	        }
	    };
	    
	    public static final Map<String, Integer> eventmapping = new HashMap<String,Integer>() {      
	        /**
			 * 
			 */
			private static final long serialVersionUID = 1L;
            {
	            put("subscribe", Integer.valueOf(0));
	            put("unsubscribe", Integer.valueOf(1));
	            put("SCAN", Integer.valueOf(2));
	            put("LOCATION", Integer.valueOf(3));
	            put("CLICK", Integer.valueOf(4));
	            put("VIEW", Integer.valueOf(5));
	        }
	    };
}
