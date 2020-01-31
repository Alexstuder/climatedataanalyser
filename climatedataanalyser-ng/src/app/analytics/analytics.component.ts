import { Component, OnInit } from '@angular/core';
import {ApiService} from "../shared/api.service";
import{Bundeslaender} from "./model/bundeslaender";
import {ViewModelAnalytics} from "./model/view-model-analytics";
import {GpsPoint} from "./model/GpsPoint";
import {ClimateAnalyserResponseDto} from "./model/ClimateAnalyserResponseDto";
import {HttpEvent} from "@angular/common/http";
import {ClimateAnalyserRequestDto} from "./model/ClimateAnalyserRequestDto";


@Component({
  selector: 'app-analytics',
  templateUrl: './analytics.component.html',
  styleUrls: ['./analytics.component.css']
})
export class AnalyticsComponent implements OnInit {

  bundeslaender: Bundeslaender;
  selectedBundesland: string;
  vievModelAnalytics: ViewModelAnalytics;
  climateAnalyserResponseDto: HttpEvent<ClimateAnalyserRequestDto>;
  private bundesland: string;
  private gps1: GpsPoint;
  private gps2: GpsPoint;
  private yearOrigine: string;
  private yearToCompare: string;

  constructor(private apiService: ApiService) { }

  ngOnInit() {
    this.initAnalytics();
  }

  initAnalytics() {

    this.apiService.initAnalytics().subscribe(
      value => {
        this.bundeslaender = value;
        // alert("Bundeslande :" + this.bundeslaender);
      },
      error => {
        alert("An error occurred while init Analytics, trying to get all Bundeslaender from Backend !");
      }

    );

  }

  onBundeslaenderDropDownListSelected(selectedBundesland:any){
    this.apiService.getAnalyticsByRequest(this.bundesland,this.gps1,this.gps2,this.yearOrigine,this.yearToCompare).subscribe(
      value => {

        this.climateAnalyserResponseDto = value;
      },
      error => {
        alert("An error occurred ,while getting analytics by Bundesland: " + selectedBundesland);
      }
    );
     this.selectedBundesland = "The value "+ selectedBundesland+" was selected !";

  }

/* onBundeslaenderDropDownListSelected(selectedBundesland:any){
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
*/

}
