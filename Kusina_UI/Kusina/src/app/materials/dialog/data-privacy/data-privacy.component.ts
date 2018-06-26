import { Component, OnInit, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-data-privacy',
  templateUrl: './data-privacy.component.html',
  styleUrls: ['./data-privacy.component.css']
})
export class DataPrivacyComponent implements OnInit {
  selection:boolean=false;

  @Output() onConfirmed:EventEmitter<any> = new EventEmitter<any>();

  constructor() { }

  ngOnInit() {
  }

  confirmed(data:any){
   this.onConfirmed.emit(data);
  }

}
