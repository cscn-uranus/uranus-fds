import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders} from '@angular/common/http';

import {Observable} from 'rxjs/Observable';
import {catchError, map, tap} from 'rxjs/operators';

import {NzMessageService} from "ng-zorro-antd";
import {ErrorObservable} from "rxjs/observable/ErrorObservable";
import {FdsResult} from "../result/fds-result";
import {FieJob} from "./fie-job";
import {throwError} from "rxjs/internal/observable/throwError";

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable()
export class FieJobService {

  private jobUrl = 'http://localhost:8080/job';  // URL to web api
  constructor(
    private http: HttpClient,
    private _message: NzMessageService) {
  }

  getAll(): Observable<{}> {
    return this.http.get<FdsResult<FieJob[]>>(this.jobUrl + "/getAll", httpOptions).pipe(
      catchError(this.handleError)
    );
  }

  private handleError(error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent) {
      // A client-side or endpoint error occurred. Handle it accordingly.
      console.error('An error occurred:', error.error.message);
    } else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong,
      console.error(
        `Backend returned code ${error.status}, ` +
        `body was: ${error.error}`);
    }
    // return an observable with a user-facing error message
    return throwError(
      'Something bad happened; please try again later.');
  };

  /** Log a DsmImportService message with the MessageService */
  private log(message: string) {
    this._message.info('DsmImportService: ' + message);
  }
}
