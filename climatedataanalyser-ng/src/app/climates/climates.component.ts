import {Component, OnInit} from '@angular/core';
import {Bundeslaender} from "../analytics/model/bundeslaender";
import {FormBuilder, FormControl, FormGroup} from "@angular/forms";
import {ApiService} from "../shared/api.service";
import {ClimateResponseDto} from "./model/ClimateResponseDto";
import {HttpEventType} from "@angular/common/http";

@Component({
  selector: 'app-climates',
  templateUrl: './climates.component.html',
  styleUrls: ['./climates.component.css']
})
export class ClimatesComponent implements OnInit {

  bundeslaender: Bundeslaender;
  selectedBundesland: string;

  private startYear;
  private distanceYear;

  angForm: FormGroup;
  private fb: FormBuilder;

  climateResponseDto: ClimateResponseDto;

  private zero: string;

  constructor(private apiService: ApiService, fb: FormBuilder) {
    this.fb = fb;
    this.createForm();
  }

  ngOnInit() {
    this.initClimates();
    this.zero = "0";
  }


  private createForm() {
    this.angForm = this.fb.group({
      gps1lat: new FormControl('49'),
      gps1long: new FormControl('7.3'),
      gps2lat: new FormControl('47'),
      gps2long: new FormControl('9.2'),
      startYear: new FormControl('1900'),
      distanceYear: new FormControl('5')
    });
  }

  onClickSubmit() {

    this.apiService.getClimateRecords(""
      , this.angForm.value.valueOf().gps1lat
      , this.angForm.value.valueOf().gps1long
      , this.angForm.value.valueOf().gps2lat
      , this.angForm.value.valueOf().gps2long
      , this.angForm.value.valueOf().startYear
      , this.angForm.value.valueOf().distanceYear
    ).subscribe(
      value => {
        switch (value.type) {
          case HttpEventType.Response:
            this.climateResponseDto = value.body;
        }
      }, error => {
        alert('An error occurred ,while getting climateRecords from Backend');
      }
    )


  }

  initClimates() {

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
    this.startYear = this.angForm.value.valueOf().startYear;
    this.distanceYear = this.angForm.value.valueOf().distanceYear;

    this.apiService.getClimateRecords(selectedBundesland, this.zero, this.zero, this.zero, this.zero, this.startYear, this.distanceYear).subscribe(
      value => {

        switch (value.type) {
          case HttpEventType.Response:
            this.climateResponseDto = value.body;
        }


      },
      error => {
        alert('An error occurred ,while getting analytics by Bundesland: ' + selectedBundesland);
      }
    );
    selectedBundesland = 'The value ' + selectedBundesland + ' was selected !';

  }

}
