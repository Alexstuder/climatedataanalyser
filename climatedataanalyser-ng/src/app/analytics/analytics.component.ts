import { Component, OnInit } from '@angular/core';
import {ApiService} from '../shared/api.service';
import {Bundeslaender} from './model/bundeslaender';
import {ViewModelAnalytics} from './model/view-model-analytics';
import {GpsPoint} from './model/GpsPoint';
import {ClimateAnalyserResponseDto} from './model/ClimateAnalyserResponseDto';
import {HttpEvent, HttpEventType, HttpResponse} from '@angular/common/http';
import {ClimateAnalyserRequest} from './model/ClimateAnalyserRequest';


@Component({
  selector: 'app-analytics',
  templateUrl: './analytics.component.html',
  styleUrls: ['./analytics.component.css']
})
export class AnalyticsComponent implements OnInit {

  bundeslaender: Bundeslaender;
  selectedBundesland: string;
  vievModelAnalytics: ViewModelAnalytics;
  climateAnalyserResponseDto: ClimateAnalyserResponseDto;
  climateAnalyserRequest: ClimateAnalyserRequest;

  private bundesland: string;
  private gps1: GpsPoint;
  private gps2: GpsPoint;
  private yearOrigine: string;
  private yearToCompare: string;

  constructor(private apiService: ApiService) { }

  ngOnInit() {
    this.initAnalytics();
    this.climateAnalyserRequest = new ClimateAnalyserRequest();
  }

  initAnalytics() {

    this.apiService.initAnalytics().subscribe(
      value => {
        this.bundeslaender = value;
        // alert("Bundeslande :" + this.bundeslaender);
      },
      error => {
        alert('An error occurred while init Analytics, trying to get all Bundeslaender from Backend !');
      }

    );

  }

  onBundeslaenderDropDownListSelected(selectedBundesland: any) {
    this.yearOrigine = '2000';
    this.yearToCompare = '2015';
    this.apiService.getAnalyticsByRequest(selectedBundesland, this.gps1, this.gps2, this.yearOrigine, this.yearToCompare).subscribe(

      value => {

        switch (value.type) {
          case HttpEventType.Response:
             this.climateAnalyserResponseDto = value.body;

        }
      },
      error => {
        alert('An error occurred ,while getting analytics by Bundesland: ' + selectedBundesland);
      }
    );
    selectedBundesland = 'The value ' + selectedBundesland + ' was selected !';

  }

}
