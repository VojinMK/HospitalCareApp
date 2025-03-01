import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Bolnica } from '../models/bolnica';
import { BOLNICA_URL } from '../constants';

@Injectable({
  providedIn: 'root'
})
export class BolnicaService {

  constructor(private httpClient:HttpClient) { }

  public getAllBolnice():Observable<any>{
    return this.httpClient.get(`${BOLNICA_URL}`)
  }

  public addBolnica(bolnica:Bolnica):Observable<any>{
    return this.httpClient.post(`${BOLNICA_URL}`,bolnica);
  }

  public updateBolnica(bolnica:Bolnica):Observable<any>{
    return this.httpClient.put(`${BOLNICA_URL}/id/${bolnica.id}`,bolnica)
  }

  public deleteBolnica(bolnicaId:number):Observable<any>{
    return this.httpClient.delete(`${BOLNICA_URL}/id/${bolnicaId}`,{responseType: 'text'});
  }
}
