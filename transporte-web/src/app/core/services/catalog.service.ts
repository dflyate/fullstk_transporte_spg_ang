import { Injectable } from '@angular/core';

export interface SelectOption {
  value: any;
  viewValue: string;
}

@Injectable({
  providedIn: 'root'
})
export class CatalogService {

  getCities() {
    return [
      { value: 'bogota', viewValue: 'Bogotá' },
      { value: 'medellin', viewValue: 'Medellín' },
      { value: 'cali', viewValue: 'Cali' },
    ]
  }

  getDepartments() {
    return [
      { value: 'cundinamarca', viewValue: 'Cundinamarca' },
      { value: 'antioquia', viewValue: 'Antioquia' },
      { value: 'valle', viewValue: 'Valle del Cauca' },
    ];
  }

  getCountries() {
    return [
      { value: 'co', viewValue: 'Colombia' }
    ]
  }

  getDocumentTypes() {
    return [
      { value: 'cc', viewValue: 'Cédula de Ciudadanía' },
      { value: 'pasaporte', viewValue: 'Pasaporte' },
      { value: 'ce', viewValue: 'Cédula de Extranjería' },
      { value: 'nit', viewValue: 'NIT' },
    ]
  }


}
