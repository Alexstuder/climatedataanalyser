import { Component, OnInit } from '@angular/core';
import {Bundeslaender} from "../analytics/model/bundeslaender";
import {FormBuilder, FormGroup} from "@angular/forms";
import {ApiService} from "../shared/api.service";
import {ClimateResponseDto} from "./model/ClimateResponseDto";

@Component({
  selector: 'app-climates',
  templateUrl: './climates.component.html',
  styleUrls: ['./climates.component.css']
})
export class ClimatesComponent implements OnInit {

  bundeslaender: Bundeslaender;
  selectedBundesland: string;
  angForm: FormGroup;
  private fb: FormBuilder;
  climateResponseDto: ClimateResponseDto;

  constructor(private apiService: ApiService, fb: FormBuilder) {
    this.fb = fb;
    this.createForm();
  }

  ngOnInit() {
  }


  private createForm() {
    this.angForm = this.fb.group()
  }
}
