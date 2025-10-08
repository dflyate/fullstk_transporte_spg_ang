import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UserContextService {

  private transportCompanyId: number | null = null;

  setTransportCompanyId(transportCompanyId: number): void {
    this.transportCompanyId = transportCompanyId;
  }

  getTransportCompanyId(): number {
    return this.transportCompanyId ?? 0;
  }

  clear(): void {
    this.transportCompanyId = null;
  }

  hasTransportCompanyId(): boolean {
    return this.transportCompanyId !== null;
  }
}
