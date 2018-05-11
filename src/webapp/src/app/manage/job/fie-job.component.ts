import {Component, OnInit} from '@angular/core';
import {FdsResult} from "../result/fds-result";
import {FieJob} from "./fie-job";
import {FieJobService} from "./fie-job.service";

@Component({
  templateUrl: 'fie-job.component.html',
  styleUrls: ['fie-job.component.scss']
})

export class FieJobComponent implements OnInit {
  result: FdsResult<FieJob[]> = new FdsResult<FieJob[]>();

  constructor(private fieJobService: FieJobService) {
  }

  ngOnInit() {
    this.getAll();

  }

  getAll() {
    this.fieJobService.getAll()
    .subscribe((result: FdsResult<FieJob[]>) => {
        this.result = {...result};
        console.log(this.result);
      }
    );
  }
}
