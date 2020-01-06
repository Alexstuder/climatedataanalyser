import { Component, OnInit } from '@angular/core';
import {ApiService} from "../shared/api.service";
import{Bundeslaender} from "./model/bundeslaender";
import {ViewModelAnalytics} from "./model/view-model-analytics";


@Component({
  selector: 'app-analytics',
  templateUrl: './analytics.component.html',
  styleUrls: ['./analytics.component.css']
})
export class AnalyticsComponent implements OnInit {

  bundeslaender: Bundeslaender;
  selectedBundesland: string;
  vievModelAnalytics: ViewModelAnalytics;

  constructor(private apiService: ApiService) { }

  ngOnInit() {
    this.initAnalytics();
  }

  initAnalytics() {

    this.apiService.initAnalytics().subscribe(
      value => {
        this.bundeslaender = value;
        alert("Bundeslande :" + this.bundeslaender);
      },
      error => {
        alert("An error occurred while init Analytics, trying to get all Bundeslaender from Backend !");
      }

    );

  }

  onBundeslaenderDropDownListSelected(selectedBundesland:any){
    this.apiService.getAnalyticsByBundesland(selectedBundesland).subscribe(
      value => {

        this.vievModelAnalytics = value;
      },
      error => {
        alert("An error occurred ,while getting analytics by Bundesland: " + selectedBundesland);
      }
    );
     this.selectedBundesland = "The value "+ selectedBundesland+" was selected !";

  }


}
