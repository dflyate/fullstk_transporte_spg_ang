import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ErrorMessages } from 'src/app/core/constants/messages.constants';
import { Driver } from 'src/app/core/models/driver';
import { CatalogService, SelectOption } from 'src/app/core/services/catalog.service';
import { DriverService } from 'src/app/core/services/driver.service';
import { UserContextService } from 'src/app/core/services/user-context.service';
import { DialogService } from 'src/app/shared/services/dialog.service';

@Component({
  selector: 'app-driver',
  templateUrl: './driver.component.html',
  styleUrls: ['./driver.component.css']
})
export class DriverComponent implements OnInit{

  driverForm!: FormGroup;
  cities: SelectOption[] = [];
  departments: SelectOption[] = [];
  countries: SelectOption[] = [];
  documentTypes: SelectOption[] = [];

  isEditMode = false;
  driverId: number = 0;

  constructor(
    private route: ActivatedRoute,
    private catalogService: CatalogService,
    private fb: FormBuilder, 
    private dialogService: DialogService,
    private router: Router,
    private driverService: DriverService,
    private contextService: UserContextService,
  ) {
    this.driverForm = this.fb.group({
      documentType: ['', [Validators.required]],
      documentNumber: ['', [Validators.required, Validators.maxLength(50)]],
      fullName: ['', [Validators.required,Validators.maxLength(255)]],
      address: ['',[Validators.maxLength(255)]],
      city: ['',[Validators.maxLength(100)]],
      department: ['',[Validators.maxLength(100)]],
      country: ['',[Validators.maxLength(100)]],
      phone: ['',[Validators.maxLength(30)]],
    });
  }
  ngOnInit(): void {
    this.cities = this.catalogService.getCities();
    this.departments = this.catalogService.getDepartments();
    this.countries = this.catalogService.getCountries();
    this.documentTypes = this.catalogService.getDocumentTypes();

    this.driverId = Number(this.route.snapshot.paramMap.get('id'));

    if(this.driverId != 0) {
      this.isEditMode = true;
      this.driverService.getDriverById(this.driverId).subscribe({
        next: (res) => {
          this.driverForm.patchValue(res);
        },
        error: (err) => {
        let errorMessage = err?.error?.message ? err.error.message : ErrorMessages.INTERNAL_SERVER_ERROR;
        this.dialogService.openDialog('error', ErrorMessages.ERROR, errorMessage);
      }
      });
    } 
  }

  onSubmit(): void {
      if (this.driverForm.invalid) return;
  
      const driver: Driver = this.driverForm.value;
      driver.transportCompanyId = this.contextService.getTransportCompanyId();
      let save$;
      if(this.isEditMode){
        driver.id = this.driverId;
        save$ = this.driverService.updateDriver(driver)
      } else {
        save$ = this.driverService.createDriver(driver);
      }

      save$.subscribe({
        next: (response: any) => {
          try {
            this.router.navigate(['/company/drivers']);
            this.dialogService.openDialog('success', 'Éxito', response?.message);
          } catch (e) {
            this.dialogService.openDialog('error', 'Error', "Error al guardar un registro");
          }
        },
        error: (err) => {
          let errorMessage = err?.error?.message ? err.error.message : ErrorMessages.INTERNAL_SERVER_ERROR;
          this.dialogService.openDialog('error', 'Error', errorMessage);
  
        }
      });
    }


  getDriverFormControl(name: string): FormControl {
    return this.driverForm.get(name) as FormControl;
  }

}
