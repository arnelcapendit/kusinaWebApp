/**
 * 
 */
package com.accenture.api.dao.impl;

import org.springframework.stereotype.Repository;

import com.accenture.api.dao.VisitorDao;
import com.accenture.api.model.VisitorBean;

/**
 * @author rocky.chucas
 *
 */
@Repository
public class VisitorDaoImpl  implements VisitorDao{

	@Override
	public VisitorBean getVisitorSession(String eid) {
		return new VisitorBean(eid);
	}

}
