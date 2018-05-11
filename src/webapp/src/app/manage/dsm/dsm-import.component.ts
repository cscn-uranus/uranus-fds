import {Component, OnInit} from '@angular/core';
import {FieGram} from "./fie-gram";
import {FormBuilder, FormControl, FormGroup} from "@angular/forms";
import {DsmImportService} from "./dsm-import.service";
import {NzMessageService} from "ng-zorro-antd";
import {FdsResult} from "../result/fds-result";

@Component({
  templateUrl: './dsm-import.component.html',
  styleUrls: ['./dsm-import.component.scss']
})
export class DsmImportComponent implements OnInit {

  public static path = 'dsm-import';
  public static title = '数据导入';
  public static description = '导入数据源数据';

  public fieGrams: FieGram[];

  gramsForm: FormGroup;

  current = 0;


  gramsValidator = (control: FormControl): { [s: string]: boolean } => {
    const GRAMS_REGEXP = /^[\s]*ZCZC[\s\S]*?NNNN[\s]*$/;
    if (!control.value) {
      return {required: true}
    } else if (!GRAMS_REGEXP.test(control.value)) {
      return {error: true, wrongGramsText: true};
    }
  };

  pre() {
    this.current -= 1;
    this.changeContent();
  }

  next() {
    this.current += 1;
    this.changeContent();

  }

  done() {
    this._message.success('done');
  }

  changeContent() {
    switch (this.current) {
      case 0: {
        break;
      }
      case 1: {
        this.resolveAsxgramDom(this.getFormControl('gramsText').value);
        break;
      }
      case 2: {
        this.addAll(this.fieGrams);
        break;
      }
      default: {
        console.error('error index');
      }
    }
  }

  constructor(private fb: FormBuilder, private dsmImportService: DsmImportService, private _message: NzMessageService) {
    this.createForm();
  }

  createForm() {
    this.gramsForm = this.fb.group({
      gramsText: ['', [this.gramsValidator]],
    });
  }

  getFormControl(name) {
    return this.gramsForm.controls[name];
  }

  public add(content: string): void {
    content = content.trim();
    if (!content) {
      return;
    }
    this.dsmImportService.add({content} as FieGram).subscribe();
  }

  public addAll(asxgrams: FieGram[]): void {
    if (!asxgrams) {
      return;
    }
    let result: FdsResult<FieGram[]> = new FdsResult<FieGram[]>();
    this.dsmImportService.addAll(asxgrams)
    .subscribe((result: FdsResult<FieGram[]>) => {
      result = {...result}
      this._message.info(result.description);
      }
    );
  }

  ngOnInit() {
  }

  public resolveAsxgramDom(text: string): void {
    let regex = new RegExp('ZCZC[\\s\\S]*?NNNN', 'g');
    let result;
    this.fieGrams = [];
    while ((result = regex.exec(text)) !== null) {
      let asxgramDom: string = result[0];
      let length = asxgramDom.length;
      let asxgram: FieGram = new FieGram();

      // asxgram.orderNum = this.index;
      asxgram.header = asxgramDom.substr(0, 4);
      asxgram.content = asxgramDom.substr(4, length - 8);
      asxgram.tail = asxgramDom.substr(length - 4, 4);
      this.fieGrams.push(asxgram);
    }
  }
}
