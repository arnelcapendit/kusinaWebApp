package com.accenture.api.service;

import org.json.simple.JSONObject;

/**
 *
 * @author marlon.naraja
 */
public interface CustomReportService {

    /**
     *
     * @param object
     * @return
     */
    public JSONObject getCustomReports(JSONObject object);

    /**
     * @author arnel.m.capendit
     * @date 11/23/2017
     * @param object
     * @return
     */
    public JSONObject getCustomReportsOverviewPageMetrics(JSONObject object);

    /**
     * @author arnel.m.capendit
     * @date 11/23/2017
     * @param object
     * @return
     */
    public JSONObject getCustomReportsOverviewUserMetrics(JSONObject object);

    /**
     * @author arnel.m.capendit
     * @date 12/19/2017
     * @param object
     * @return
     */
    public JSONObject getCustomReportsOverviewReferrersMetrics(JSONObject object);

    /**
     * @author arnel.m.capendit
     * @date 12/19/2017
     * @param object
     * @return
     */
    public JSONObject getCustomReportsOverviewDownloadMetrics(JSONObject object);

    /**
     *
     * @return
     */
    public JSONObject getMyTEUniquePageUrlList();

    /**
     *
     * @param object
     * @return
     */
    public JSONObject getMyTEUniquePageUrl(JSONObject object);

    /**
     *
     * @param object
     * @return
     */
    public JSONObject getUsageByUser(JSONObject object);

    /**
     *
     * @param object
     * @return
     */
    public JSONObject getCustomReportPages(JSONObject object);
    
    /**
    *
    * @param object
    * @return
    * @For revise version of getCustomReportPages allowing parent views to reflect.
    */
   public JSONObject getCustomReportPagesV2(JSONObject object);
   
   /**
   *
   * @param object
   * @return
   * @For revise version of getCustomReportPages allowing child views to reflect only.
   */
  public JSONObject getCustomReportPagesDetails(JSONObject object);
  
  /**
  *
  * @param object
  * @return
  * @For event metrics
  */
  public JSONObject getCustomReportsEventMetrics(JSONObject object);
  
  /**
  *
  * @param object
  * @return
  * @For revise usage metrics allowing parent data to show
  */
  public JSONObject getCustomReportUsageV2(JSONObject object);
  
  /**
  *
  * @param object
  * @return
  * @For revise usage metrics allowing child views to reflect only
  */
  public JSONObject getCustomReportUsageDetails(JSONObject object);
  /**
  *
  * @param object
  * @return
  * @For getting total values for custom report data.
  */
  public JSONObject getCustomReportTotal(JSONObject object);
  
  /**
  *
  * @param object
  * @return
  * @For revise version elastic pagination for parent...
  */
 public JSONObject getCustomReportUsageV3(JSONObject object); 
 
 /**
 *
 * @param object
 * @return
 * @For revise version elastic pagination for child...
 */
 public JSONObject getCustomReportUsageChild(JSONObject object);
 /**
 *
 * @param object
 * @return
 * @For revise version elastic pagination for parent...
 */
 public JSONObject getCustomReportPagesV3(JSONObject object);
 
 /**
 *
 * @param object
 * @return
 * @For revise version elastic pagination for parent...
 */
 public JSONObject getCustomReportPagesChild(JSONObject object);
 
 /**
 *
 * @param object
 * @return
 * @For revise version elastic pagination for parent...
 */
 public JSONObject getCustomReportsAIBSPBVI(JSONObject object);

 /**
 *
 * @param object
 * @return
 * @For revise version elastic pagination for overview metric reports...
 */ 
 JSONObject getCustomReportsOverview(JSONObject object);
 
 /**
 *
 * @param object
 * @return
 * @For revise version elastic pagination for event metric reports...
 */ 
 JSONObject getCustomReportsEvents(JSONObject object);
 
}
