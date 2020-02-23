import { Component, OnInit } from '@angular/core';
import {ApiService} from '../shared/api.service';
import {Bundeslaender} from './model/bundeslaender';
import {GpsPoint} from './model/GpsPoint';
import {ClimateAnalyserResponseDto} from './model/ClimateAnalyserResponseDto';
import {HttpEvent, HttpEventType, HttpResponse} from '@angular/common/http';
import {ClimateAnalyserRequest} from './model/ClimateAnalyserRequest';
import {FormBuilder, FormControl, FormGroup, FormsModule, ReactiveFormsModule} from '@angular/forms';


@Component({
  selector: 'app-analytics',
  templateUrl: './analytics.component.html',
  styleUrls: ['./analytics.component.css']
})
export class AnalyticsComponent implements OnInit {

  bundeslaender: Bundeslaender;
  selectedBundesland: string;
  climateAnalyserResponseDto: ClimateAnalyserResponseDto;
  climateAnalyserRequestDto: ClimateAnalyserRequest;

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
      gps1lat: new FormControl('49'),
      gps1long: new FormControl('7.3'),
      gps2lat: new FormControl('47'),
      gps2long: new FormControl('9.2'),
      yearO: new FormControl('1989'),
      yearC: new FormControl('2015')
    });
  }
  onClickSubmit() {

    this.climateAnalyserRequest = new ClimateAnalyserRequest(''
      , new GpsPoint( this.angForm.value.valueOf().gps1long, this.angForm.value.valueOf().gps1lat)
      , new GpsPoint( this.angForm.value.valueOf().gps2long, this.angForm.value.valueOf().gps2lat)
      , this.angForm.value.valueOf().yearO
      , this.angForm.value.valueOf().yearC);


    this.apiService.getAnalyticsByRequest(



      ''
      , new GpsPoint( this.angForm.value.valueOf().gps1long, this.angForm.value.valueOf().gps1lat)
      , new GpsPoint( this.angForm.value.valueOf().gps2long, this.angForm.value.valueOf().gps2lat)
      , this.angForm.value.valueOf().yearO
      , this.angForm.value.valueOf().yearC
    ).subscribe(
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
    this.yearOrigine = this.angForm.value.valueOf().yearO;
    this.yearToCompare = this.angForm.value.valueOf().yearC;
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
