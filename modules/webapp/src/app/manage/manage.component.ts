import 'rxjs/add/operator/filter';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/mergeMap';

import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, NavigationEnd, Router} from "@angular/router";
import {NavItem} from "../nav-item";
import {distinctUntilChanged, filter} from "rxjs/operators";


@Component({
  templateUrl: './manage.component.html',
  styleUrls: ['./manage.component.scss']
})
export class ManageComponent implements OnInit {

  public static url = 'manage';
  public static title = '管理控制台';
  public static description = '集中管理配置';

  public breadcrumbs: NavItem[];

  constructor(private activatedRoute: ActivatedRoute, private router: Router) {
    this.router.events.pipe(
      filter(event => event instanceof NavigationEnd),
      distinctUntilChanged(),
    ).subscribe(() => {
      this.breadcrumbs = this.buildNavItem(this.activatedRoute.root);
    });
  }

  ngOnInit() {

  }

  public buildNavItem(route: ActivatedRoute, parentPath: string = '',
                      navItems: Array<NavItem> = []): NavItem[] {

    const path = route.routeConfig ? route.routeConfig.path : '';
    const title = route.routeConfig ? route.routeConfig.data['title'] : 'Home';
    const description = route.routeConfig ? route.routeConfig.data['description'] : '';

    const nextPath = `${parentPath}${path}/`;
    const navItem = {
      path: nextPath,
      title: title,
      description: description,
    };
    const newNavItems = [...navItems, navItem];
    if (route.firstChild) {
      return this.buildNavItem(route.firstChild, nextPath, newNavItems);
    }
    return newNavItems;
  }


}
