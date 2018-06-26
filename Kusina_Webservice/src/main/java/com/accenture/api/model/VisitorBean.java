package com.accenture.api.model;

import java.io.Serializable;


/**
 * @author rocky.chucas
 *
 */
public class VisitorBean  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1282998592687622021L;
	private String eid;
	
	public VisitorBean(String eid){
		this.eid = eid;
	}

	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	@Override
	public String toString() {
		return "VistorBean [eid=" + eid + "]";
	}
}
