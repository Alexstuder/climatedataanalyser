import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup} from "@angular/forms";
import {ApiService} from "../shared/api.service";
import {ClimateResponseDto} from "./model/ClimateResponseDto";
import {HttpEventType} from "@angular/common/http";
import {Bundeslaender} from "./model/bundeslaender";
import {timeout} from "rxjs/operators";

@Component({
  selector: 'app-climates',
  templateUrl: './climates.component.html',
  styleUrls: ['./climates.component.css']
})
export class ClimatesComponent implements OnInit {

  bundeslaender: Array<String>;
  private selectedBundesland: string;

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

  reset(){
    this.bundeslaender= null;
    setTimeout(this.initClimates,1000);
    alert("Timeout");
  }

  private createForm() {

    //Weisweil   79367 : 48.181837104192695, 7.6906623449884695
    //Stühlingen 79780 : 47.73683613454628, 8.360463439669749
    this.angForm = this.fb.group({
      gps1lat: new FormControl('48.181837104192695'),
      gps1long: new FormControl('7.6906623449884695'),
      gps2lat: new FormControl('47.73683613454628'),
      gps2long: new FormControl('8.360463439669749'),
      startYear: new FormControl('1900'),
      distanceYear: new FormControl('30')
    });
  }

  onClickSubmit() {
    this.reset();

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
        console.log("Bundesland versucht zu laden !");
        console.log(this.bundeslaender);
        //alert("Bundeslande :" + this.bundeslaender);
      },
      error => {
        alert('An error occurred while init Analytics, trying to get all Bundeslaender from Backend !');
      }
    );

  }

  onBundeslaenderDropDownListSelected(selectedBundesland: any) {

   // this.angForm.controls['gps1lat'].disable();
    // this.angForm.value.valueOf().gps1lat('');
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
