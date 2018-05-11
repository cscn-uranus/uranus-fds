import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';


import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {NgZorroAntdModule} from "ng-zorro-antd";
import {ManageModule} from "./manage/manage.module";
import {HomeComponent} from "./home/home.component";


@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,

    NgZorroAntdModule.forRoot(),

    ManageModule,
    AppRoutingModule,

  ],
  providers: [
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}

