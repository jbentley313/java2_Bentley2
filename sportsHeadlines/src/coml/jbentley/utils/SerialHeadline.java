package coml.jbentley.utils;

import java.io.Serializable;
import java.net.URL;

public class SerialHeadline implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String titleHL;
	private String descripHL;
	private URL urlHL;
	
	@Override
    public String toString() {
        return "passHeadline [title=" + titleHL + 
        		", description=" + descripHL + 
        		", urlP=" + urlHL +
        		"]";
    }

}
