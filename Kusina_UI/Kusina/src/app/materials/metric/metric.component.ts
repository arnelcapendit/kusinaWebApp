import { Component, OnInit } from '@angular/core';
import { MsgHandlerService } from  '../../services/msg-handler.service';

@Component({
  selector: 'app-metric',
  templateUrl: './metric.component.html',
  styleUrls: ['./metric.component.css']
})
export class MetricComponent implements OnInit {
  cardTitle ="Overview";
  
    totalVisits = "0";
    totalVisitsTxt = "Unique Visits";
    uniqueUsers = "0";
    uniqueUsersTxt = "Unique Users";
    avePageLoad = "0";
    avePageLoadTxt = "Avg Generation Time";
    hits = "0";
    hitsTxt = "Actions";

  constructor(private _msgHandlerService:MsgHandlerService) { }

  ngOnInit() {
  }

  public refreshMetricValues(metricJson:any){
    if( metricJson!=undefined){
      this.totalVisits = metricJson.visitsCount;
      this.uniqueUsers = metricJson.visitorCount;
      try {
        this.avePageLoad = (metricJson.generationTime).toFixed(2);
    }
    catch(err) {
      this.avePageLoad = "0";
    }
      
      this.hits =metricJson.hitsCount;
    } else{
      const msg = "setMetricValues() Error data obtained ->"+metricJson;
      this._msgHandlerService.handleComponentError(msg);
    }
  }

}
