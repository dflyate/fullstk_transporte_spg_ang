import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { TransportCompany } from '../models/transport-company';
import { PageResponse } from '../models/page-response';
import { MessageResponse } from '../models/message-response';
import { TransportCompanyRegistration } from '../models/transport-company-registration';

@Injectable({
  providedIn: 'root'
})
export class TransportCompanyService {

  private apiUrl = `${environment.apiUrl}/transport-company`;

  constructor(private http: HttpClient) {}

  getPagedTransportCompanies(page: number, size: number): Observable<PageResponse<TransportCompany>> {
    const params = new HttpParams()
      .set('page', page)
      .set('size', size);

      return this.http.get<PageResponse<TransportCompany>>(this.apiUrl, { params });
  }

  getTransportCompanyById(id: number): Observable<TransportCompanyRegistration> {
    return this.http.get<TransportCompanyRegistration>(`${this.apiUrl}/${id}`);
  }

  createTransportCompany(transportCompanyRegistration: TransportCompanyRegistration): Observable<MessageResponse> {
    return this.http.post<MessageResponse>(this.apiUrl, transportCompanyRegistration);
  }

  updateTransportCompany(transportCompanyRegistration: TransportCompanyRegistration): Observable<MessageResponse> {
    return this.http.put<MessageResponse>(this.apiUrl, transportCompanyRegistration);
  }

  deleteTransportCompany(id: number): Observable<MessageResponse> {
    return this.http.delete<MessageResponse>(`${this.apiUrl}/${id}`);
  }
}
