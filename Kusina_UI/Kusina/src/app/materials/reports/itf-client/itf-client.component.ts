import { Component, OnInit, ViewChild, AfterViewInit, Output, EventEmitter } from '@angular/core';
import { MatTabChangeEvent } from '@angular/material';
import { AppConfig } from '../../../globals/app.config';

@Component({
  selector: 'app-itf-client',
  templateUrl: './itf-client.component.html',
  styleUrls: ['./itf-client.component.css']
})
export class ItfClientComponent implements AfterViewInit, OnInit {

  selectedCategory;

  @ViewChild('tabGroup') tabGroup;
 
  constructor(private _appConfig: AppConfig) { }

  ngOnInit() { }

  ngAfterViewInit() {
    if (this.tabGroup.selectedIndex === 0) {
        this.selectedCategory = this.tabGroup._tabs.first.textLabel;
    }
}

  tabChanged = (tabChangeEvent: MatTabChangeEvent): void => {
    this.selectedCategory = tabChangeEvent.tab.textLabel;
  }
}
