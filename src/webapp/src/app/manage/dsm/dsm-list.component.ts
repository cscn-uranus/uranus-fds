import {Component, OnInit} from '@angular/core';

@Component({
  templateUrl: './dsm-list.component.html',
  styleUrls: ['./dsm-list.component.scss']
})
export class DsmListComponent implements OnInit {

  public static path = 'dsm-list';
  public static title = '数据源';
  public static description = '数据源管理';

  data = [
    {
      title: '飞行数据交换',
    },
  ];
  constructor() {
  }

  ngOnInit() {

  }

}
