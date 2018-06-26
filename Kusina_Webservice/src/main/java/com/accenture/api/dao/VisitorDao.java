/**
 * 
 */
package com.accenture.api.dao;

import org.springframework.stereotype.Repository;

import com.accenture.api.model.VisitorBean;

/**
 * @author rocky.chucas
 *
 */

public interface VisitorDao {
	 VisitorBean getVisitorSession(String eid);
}
