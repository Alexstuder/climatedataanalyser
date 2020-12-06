import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavigationComponent } from './navigation/navigation.component';
import { AnalyticsComponent } from './analytics/analytics.component';
import { DatabaseComponent } from './database/database.component';
import { NotFoundComponent } from './not-found/not-found.component';
import {HttpClientModule} from "@angular/common/http";
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { ClimatesComponent } from './climates/climates.component';
import { WolfgangComponent } from './wolfgang/wolfgang.component';

@NgModule({
  declarations: [
    AppComponent,
    NavigationComponent,
    AnalyticsComponent,
    DatabaseComponent,
    NotFoundComponent,
    ClimatesComponent,
    WolfgangComponent
  ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        HttpClientModule,
        FormsModule,
        ReactiveFormsModule
    ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
