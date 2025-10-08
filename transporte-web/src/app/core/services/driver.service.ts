import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { PageResponse } from '../models/page-response';
import { Observable } from 'rxjs';
import { Driver } from '../models/driver';
import { MessageResponse } from '../models/message-response';
import { VehicleDriver } from '../models/vehicle-driver';

@Injectable({
  providedIn: 'root'
})
export class DriverService {

  private apiUrl = `${environment.apiUrl}/driver`;

  constructor(private http: HttpClient) { }

  getPagedDrivers(page: number, size: number, transportCompanyId: number): Observable<PageResponse<Driver>> {
    const params = new HttpParams()
      .set('page', page)
      .set('size', size)
      .set('transportCompanyId', transportCompanyId);

    return this.http.get<PageResponse<Driver>>(this.apiUrl, { params });
  }

  getAvailableDrivers(page: number, size: number, vehicleId: number, transportCompanyId: number): Observable<PageResponse<Driver>> {
    const params = new HttpParams()
      .set('page', page)
      .set('size', size)
      .set('vehicleId', vehicleId)
      .set('transportCompanyId', transportCompanyId);

    return this.http.get<PageResponse<Driver>>(`${this.apiUrl}/available-drivers`, { params });
  }

  getAllByVehicle(page: number, size: number, vehicleId: number): Observable<PageResponse<Driver>> {
    const params = new HttpParams()
      .set('page', page)
      .set('size', size)
      .set('vehicleId', vehicleId);

    return this.http.get<PageResponse<Driver>>(`${this.apiUrl}/by_vehicle`, { params });
  }

  getDriverById(id: number): Observable<Driver> {
    return this.http.get<Driver>(`${this.apiUrl}/${id}`);
  }

  createDriver(driver: Driver): Observable<MessageResponse> {
    return this.http.post<MessageResponse>(this.apiUrl, driver);
  }

  updateDriver(driver: Driver): Observable<MessageResponse> {
    return this.http.put<MessageResponse>(this.apiUrl, driver);
  }

  deleteDriver(id: number): Observable<MessageResponse> {
    return this.http.delete<MessageResponse>(`${this.apiUrl}/${id}`);
  }

  disaffiliate(driverAffiliation: VehicleDriver): Observable<MessageResponse> {
    return this.http.put<MessageResponse>(`${this.apiUrl}/disaffiliate`, driverAffiliation);
  }

  affiliate(driverAffiliation: VehicleDriver): Observable<MessageResponse> {
    return this.http.post<MessageResponse>(`${this.apiUrl}/affiliate`, driverAffiliation);
  }
}
