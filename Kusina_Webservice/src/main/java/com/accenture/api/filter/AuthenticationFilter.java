package com.accenture.api.filter;

import com.accenture.api.service.KusinaService;
import com.accenture.api.utils.KusinaStringUtils;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Enumeration;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 *
 * @author marlon.naraja
 */
@Component
public class AuthenticationFilter implements Filter {

    @Autowired
    private KusinaStringUtils kusinaStringUtils;

    @Autowired
    private KusinaService kusinaService;

    @Override
    public void init(FilterConfig fc) throws ServletException {
        // Enable Annotation(s) in Filter
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                fc.getServletContext());
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain fc) throws IOException, ServletException {
        HttpServletRequest httprequest = (HttpServletRequest) req;
        HttpServletResponse httpresponse = (HttpServletResponse) res;

        httpresponse.setContentType("application/json");
        httprequest.setCharacterEncoding("UTF-8");

        Enumeration<String> params = req.getParameterNames();
        
        JSONObject paramMap = new JSONObject();

        JSONObject ar = new JSONObject();

        try {
            while (params.hasMoreElements()) {
                String name = params.nextElement(); 
                String value = req.getParameter(name);
                paramMap.put(name, kusinaStringUtils.sanitizeString(value));
            }
            JSONObject user = kusinaService.getUserByEid(paramMap.get("eid").toString());
            if (user != null) {
                if (kusinaService.hasSession(paramMap.get("eid").toString(), paramMap.get("nonce").toString())) {
                    JSONObject userSessionObj = new JSONObject();
                    userSessionObj.put("eid", paramMap.get("eid"));
                    userSessionObj.put("nonce", paramMap.get("nonce"));
                    userSessionObj.put("now", paramMap.get("now"));
                    JSONObject currrentSession = kusinaService.getUserSession(userSessionObj);
                    String strCurrrentSession = currrentSession.get("session").toString();

                    if (strCurrrentSession.equalsIgnoreCase("active")) {
                        req.setAttribute("user", user);
                        req.setAttribute("params", paramMap);
                        fc.doFilter(req,res);
                    } else {
                        String _id = kusinaService.getSessionTypeId(paramMap.get("eid").toString(), paramMap.get("nonce").toString());
                        kusinaService.deleteSession(_id);

                        ar.put("status", "session expired");
                        httpresponse.getWriter().write(ar.toJSONString());
                    }
                } else {
                    ar.put("status", "no session found");
                    httpresponse.getWriter().write(ar.toJSONString());
                }
            } else {
                ar.put("status", "unauthorized user.");
                httpresponse.getWriter().write(ar.toJSONString());
            }
        } catch (IOException | ServletException | NullPointerException e) {
            ar.put("status", e.getMessage());
            httpresponse.getWriter().write(ar.toJSONString());
        }

    }

    @Override
    public void destroy() {
    }
}
