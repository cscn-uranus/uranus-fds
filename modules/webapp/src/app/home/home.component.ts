import {Component, OnInit} from '@angular/core';

@Component({
  templateUrl: 'home.component.html',
  styleUrls: ['home.component.scss'],
})

export class HomeComponent implements OnInit {
  public static title = '首页';
  public static description = "飞行数据源的首页";
  public static url = 'home';

  constructor() {
  }

  ngOnInit() {
  }
}
