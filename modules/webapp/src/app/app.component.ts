import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, NavigationEnd, Router} from "@angular/router";
import {NavItem} from "./nav-item";
import "rxjs/add/operator/filter";
import "rxjs/add/operator/distinctUntilChanged";
import "rxjs/add/operator/map";
import {distinctUntilChanged, filter} from "rxjs/operators";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent implements OnInit {

  menus = [
    {
      url: 'home',
      title: '首页',
    },
    {
      url: 'manage',
      title: '管理中心',
    }
  ];
  public static title = '首页';
  public static description = "飞行数据源的首页";
  public static url = '';


  constructor(private activatedRoute: ActivatedRoute,
              private router: Router) {

  }

  ngOnInit() {

  }



  OnClick() {
    console.info(this.activatedRoute)
  }
}
