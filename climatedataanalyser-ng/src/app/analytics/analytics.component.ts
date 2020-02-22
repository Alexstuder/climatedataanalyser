import { Component, OnInit } from '@angular/core';
import {ApiService} from '../shared/api.service';
import {Bundeslaender} from './model/bundeslaender';
import {ViewModelAnalytics} from './model/view-model-analytics';
import {GpsPoint} from './model/GpsPoint';
import {ClimateAnalyserResponseDto} from './model/ClimateAnalyserResponseDto';
import {HttpEvent, HttpEventType, HttpResponse} from '@angular/common/http';
import {ClimateAnalyserRequest} from './model/ClimateAnalyserRequest';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule} from '@angular/forms';


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
  climateAnalyserRequestDto: ClimateAnalyserRequest;

  private bundesland: string;
  private gps1: GpsPoint;
  private gps2: GpsPoint;
  private yearOrigine: string;
  private yearToCompare: string;
  private fb: FormBuilder;

  angForm: FormGroup;
  private climateAnalyserRequest: ClimateAnalyserRequest;

  constructor(private apiService: ApiService, fb: FormBuilder) {
    this.fb = fb;
    this.createForm();
  }

  createForm() {
    this.angForm = this.fb.group({
      gps1lat: [0],
      gps1long: [0],
      gps2lat: [0],
      gps2long: [0],
      yearO: ['2017'],
      yearC: ['2000']
    });
  }
  onClickSubmit(gps1lat, gps1long, gps2lat, gps2long, yearO, yearC) {

    this.climateAnalyserRequest = new ClimateAnalyserRequest(''
                                  , new GpsPoint( gps1long, gps1lat)
                                  , new GpsPoint( gps2long, gps2lat)
                                  , yearO
                                  , yearC);

    this.apiService.getAnalyticsByRequest('', new GpsPoint( gps1long, gps1lat), new GpsPoint( gps2long, gps2lat), yearO, yearC).subscribe(
   // this.apiService.getAnalyticsByRequest2(this.climateAnalyserRequest).subscribe(

      value => {

        switch (value.type) {
          case HttpEventType.Response:
            this.climateAnalyserResponseDto = value.body;

        }
      },
      error => {
        alert('An error occurred ,while getting analytics by GPS coordinates');
      }
    );
  }

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
