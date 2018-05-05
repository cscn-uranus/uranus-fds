import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';

import {ManageRoutingModule} from "./manage-routing.module";


import {ManageComponent} from "./manage.component";
import {DataimportComponent} from "./dataimport/dataimport.component";
import {EndpointComponent} from "./endpoint/endpoint.component";
import {DataimportService} from "./dataimport/dataimport.service";
import {HttpClientModule} from "@angular/common/http";
import {NgZorroAntdModule} from "ng-zorro-antd";
import {JobComponent} from "./job/job.component";


@NgModule({
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,

    NgZorroAntdModule.forRoot(),


    HttpClientModule,

    ManageRoutingModule,

  ],
  exports: [],
  declarations: [
    ManageComponent,
    JobComponent,
    EndpointComponent,
    DataimportComponent,
  ],
  providers: [
    DataimportService,
  ],
})
export class ManageModule {
}
