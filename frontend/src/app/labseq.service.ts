import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, map } from 'rxjs';

export interface LabseqResponse {
    index: number;
    value: string;
}

@Injectable({
  providedIn: 'root'
})
export class LabseqService {
  private baseUrl = '/labseq';

  constructor(private http: HttpClient) {}

  getValue(n: number): Observable<string> {
    return this.http.get<LabseqResponse>(`${this.baseUrl}/${n}`)
      .pipe(map(response => response.value));
  }
}

