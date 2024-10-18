import { inject, Injectable, NgModule } from '@angular/core';
import { enviroment } from '../enviroment/enviroment';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Product } from '../data/interfaces/product';

@Injectable({
  providedIn: 'root'
})
export class ProductService {


  private aprServerUrl = enviroment.apiBaseUrl;

  http : HttpClient = inject(HttpClient)

  // constructor(private http: HttpClient) { }

  public getAllProducts(): Observable<Product[]>{
    return this.http.get<Product[]>(`${this.aprServerUrl}/api/product/all`)
  }

  public search(text: string, color: string): Observable<Product[]>{
    const params = new HttpParams()
    .set('text', text)
    .set('color', color);
    return this.http.get<Product[]>(`${this.aprServerUrl}/api/search`,{
      params: params
    })
  }
}
