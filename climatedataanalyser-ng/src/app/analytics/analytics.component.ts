import { Component, OnInit } from '@angular/core';
import {ApiService} from "../shared/api.service";
import{Bundeslaender} from "../analytics/model/bundeslaender";


@Component({
  selector: 'app-analytics',
  templateUrl: './analytics.component.html',
  styleUrls: ['./analytics.component.css']
})
export class AnalyticsComponent implements OnInit {

  bundeslaender: Bundeslaender;
  selectedBundesland: string;

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
     this.selectedBundesland = "The value "+ selectedBundesland+" was selected !";

  }


}
