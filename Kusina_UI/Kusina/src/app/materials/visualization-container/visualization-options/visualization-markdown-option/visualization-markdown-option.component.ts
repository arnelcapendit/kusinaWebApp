import { Component, OnInit, Input } from '@angular/core';
import { MarkdownVisualizationModel } from '../../../../models/custom-dashboard-visualizations/visualizations.model';

@Component({
  selector: 'app-visualization-markdown-option',
  templateUrl: './visualization-markdown-option.component.html',
  styleUrls: ['./visualization-markdown-option.component.css']
})
export class VisualizationMarkdownOptionComponent implements OnInit {

  @Input() data:MarkdownVisualizationModel = new MarkdownVisualizationModel();

  constructor() { }

  ngOnInit() {

  }

}
