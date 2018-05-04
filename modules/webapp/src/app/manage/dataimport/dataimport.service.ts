import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders} from '@angular/common/http';

import {Observable} from 'rxjs/Observable';
import {catchError, map, tap} from 'rxjs/operators';

import {Asxgram} from "./model/asxgram";
import {NzMessageService} from "ng-zorro-antd";
import {Asxresult} from "./model/asxresult";
import {ErrorObservable} from "rxjs/observable/ErrorObservable";

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable()
export class DataimportService {

  private asxgramsUrl = 'http://localhost:8080/asxgram';  // URL to web api
  constructor(
    private http: HttpClient,
    private _message: NzMessageService) {
  }


  add(asxgram: Asxgram): Observable<Asxresult<Asxgram>> {
    return this.http.post<Asxgram>(this.asxgramsUrl + "/add", asxgram, httpOptions).pipe(
      catchError(this.handleError)
    );

  }

  addAll(asxgrams: Asxgram[]): Observable<Asxresult<Asxgram[]>> {
    return this.http.post<Asxgram[]>(this.asxgramsUrl + "/addAll", asxgrams, httpOptions).pipe(
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
    return new ErrorObservable(
      'Something bad happened; please try again later.');
  };

  /** Log a DataimportService message with the MessageService */
  private log(message: string) {
    this._message.info('DataimportService: ' + message);
  }
}
