import {Component, OnInit} from '@angular/core';
import {Asxgram} from "./model/asxgram";
import {FormBuilder, FormControl, FormGroup} from "@angular/forms";
import {DataimportService} from "./dataimport.service";
import {NzMessageService} from "ng-zorro-antd";
import {Asxresult} from "./model/asxresult";

@Component({
  templateUrl: './dataimport.component.html',
  styleUrls: ['./dataimport.component.scss']
})
export class DataimportComponent implements OnInit {

  public static path = 'dataimport';
  public static title = '数据导入';
  public static description = '导入数据源数据';

  public asxgrams: Asxgram[];

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
        this.addAll(this.asxgrams);
        break;
      }
      default: {
        console.error('error index');
      }
    }
  }

  constructor(private fb: FormBuilder, private maintService: DataimportService, private _message: NzMessageService) {
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
    this.maintService.add({content} as Asxgram).subscribe();
  }

  public addAll(asxgrams: Asxgram[]): void {
    if (!asxgrams) {
      return;
    }
    let result: Asxresult<Asxgram[]> = new Asxresult<Asxgram[]>();
    this.maintService.addAll(asxgrams)
    .subscribe((result: Asxresult<Asxgram[]>) => {
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
    this.asxgrams = [];
    while ((result = regex.exec(text)) !== null) {
      let asxgramDom: string = result[0];
      let length = asxgramDom.length;
      let asxgram: Asxgram = new Asxgram();

      // asxgram.orderNum = this.index;
      asxgram.header = asxgramDom.substr(0, 4);
      asxgram.content = asxgramDom.substr(4, length - 8);
      asxgram.tail = asxgramDom.substr(length - 4, 4);
      this.asxgrams.push(asxgram);
    }
  }
}
