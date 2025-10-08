import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { PageResponse } from '../models/page-response';
import { Vehicle } from '../models/vehicle';
import { MessageResponse } from '../models/message-response';
import { VehicleAffiliation } from '../models/vehicle-affiliation';

@Injectable({
  providedIn: 'root'
})
export class VehicleService {

  private apiUrl = `${environment.apiUrl}/vehicle`;

  constructor(private http: HttpClient) { }

  getPagedVehicles(page: number, size: number): Observable<PageResponse<Vehicle>> {
    const params = new HttpParams()
      .set('page', page)
      .set('size', size);

    return this.http.get<PageResponse<Vehicle>>(this.apiUrl, { params });
  }

  getAvailableVehicles(page: number, size: number): Observable<PageResponse<Vehicle>> {
    const params = new HttpParams()
      .set('page', page)
      .set('size', size);

    return this.http.get<PageResponse<Vehicle>>(`${this.apiUrl}/available-vehicles`, { params });
  }

  getAllByTransportCompany(page: number, size: number, transporCompanyId: number): Observable<PageResponse<Vehicle>> {
    const params = new HttpParams()
      .set('page', page)
      .set('size', size)
      .set('transportCompanyId', transporCompanyId);

    return this.http.get<PageResponse<Vehicle>>(`${this.apiUrl}/by_transport_company`, { params });
  }

  getVehicleById(id: number): Observable<Vehicle> {
    return this.http.get<Vehicle>(`${this.apiUrl}/${id}`);
  }

  createVehicle(vehicle: Vehicle): Observable<MessageResponse> {
    return this.http.post<MessageResponse>(this.apiUrl, vehicle);
  }

  updateVehicle(vehicle: Vehicle): Observable<MessageResponse> {
    return this.http.put<MessageResponse>(this.apiUrl, vehicle);
  }

  deleteVehicle(id: number): Observable<MessageResponse> {
    return this.http.delete<MessageResponse>(`${this.apiUrl}/${id}`);
  }

  disaffiliate(vehicleAffiliation: VehicleAffiliation): Observable<MessageResponse> {
    return this.http.put<MessageResponse>(`${this.apiUrl}/disaffiliate`,vehicleAffiliation);
  }

  affiliate(vehicleAffiliation: VehicleAffiliation): Observable<MessageResponse> {
    return this.http.post<MessageResponse>(`${this.apiUrl}/affiliate`,vehicleAffiliation);
  }
}
