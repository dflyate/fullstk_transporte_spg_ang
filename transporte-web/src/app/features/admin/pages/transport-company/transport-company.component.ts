import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ErrorMessages } from 'src/app/core/constants/messages.constants';
import { LegalRepresentative } from 'src/app/core/models/legal-representative';
import { TransportCompany } from 'src/app/core/models/transport-company';
import { User } from 'src/app/core/models/user';
import { CatalogService, SelectOption } from 'src/app/core/services/catalog.service';
import { TransportCompanyService } from 'src/app/core/services/transport-company.service';
import { DialogService } from 'src/app/shared/services/dialog.service';

@Component({
  selector: 'app-transport-company',
  templateUrl: './transport-company.component.html',
  styleUrls: ['./transport-company.component.css']
})
export class TransportCompanyComponent implements OnInit{

  transportForm!: FormGroup;
  legalRepresentativeForm!: FormGroup;
  userForm!: FormGroup;
  mainForm!: FormGroup;
  cities: SelectOption[] = [];
  departments: SelectOption[] = [];
  countries: SelectOption[] = [];
  documentTypes: SelectOption[] = [];

  isEditMode = false;
  transportCompanyId: number = 0;

  constructor(
    private route: ActivatedRoute,
    private catalogService: CatalogService,
    private fb: FormBuilder, 
    private dialogService: DialogService,
    private router: Router,
    private transportCompanyService: TransportCompanyService
  ) {
    this.transportForm = this.fb.group({
      documentType: ['', [Validators.required]],
      documentNumber: ['', [Validators.required, Validators.maxLength(50)]],
      fullName: ['', [Validators.required,Validators.maxLength(255)]],
      address: ['',[Validators.maxLength(255)]],
      city: ['',[Validators.maxLength(100)]],
      department: ['',[Validators.maxLength(100)]],
      country: ['',[Validators.maxLength(100)]],
      phone: ['',[Validators.maxLength(30)]],
    });

    this.legalRepresentativeForm = this.fb.group({
      documentType: ['', [Validators.required]],
      documentNumber: ['', [Validators.required, Validators.maxLength(50)]],
      fullName: ['', [Validators.required,Validators.maxLength(255)]],
      address: ['',[Validators.maxLength(255)]],
      city: ['',[Validators.maxLength(100)]],
      department: ['',[Validators.maxLength(100)]],
      country: ['',[Validators.maxLength(100)]],
      phone: ['',[Validators.maxLength(30)]],
    });

    this.userForm = this.fb.group({
      username: ['', [Validators.required, Validators.maxLength(50)]],
      password: [''],
    });

    this.mainForm = this.fb.group({
      transportForm: this.transportForm,
      legalRepresentativeForm: this.legalRepresentativeForm,
      userForm: this.userForm
    });
  }
  ngOnInit(): void {
    this.cities = this.catalogService.getCities();
    this.departments = this.catalogService.getDepartments();
    this.countries = this.catalogService.getCountries();
    this.documentTypes = this.catalogService.getDocumentTypes();

    this.transportCompanyId = Number(this.route.snapshot.paramMap.get('id'));

    if(this.transportCompanyId != 0) {
      this.isEditMode = true;
      this.setEditModeValidators();
      this.transportCompanyService.getTransportCompanyById(this.transportCompanyId).subscribe({
        next: (res) => {
          this.transportForm.patchValue(res.transportCompany);
          this.legalRepresentativeForm.patchValue(res.legalRepresentative);
          this.userForm.patchValue(res.user);
        },
        error: (err) => {
        let errorMessage = err?.error?.message ? err.error.message : ErrorMessages.INTERNAL_SERVER_ERROR;
        this.dialogService.openDialog('error', ErrorMessages.ERROR, errorMessage);
      }
      });
    } else {
      this.setCreateModeValidators();
    }
  }

  setCreateModeValidators() {
    this.userForm.get('username')?.setValidators([Validators.required, Validators.maxLength(50)]);
    this.userForm.get('password')?.setValidators([Validators.required, Validators.maxLength(200)]);
    this.userForm.get('username')?.updateValueAndValidity();
    this.userForm.get('password')?.updateValueAndValidity();
  }

  setEditModeValidators() {
    this.userForm.get('username')?.setValidators([Validators.required, Validators.maxLength(50)]);
    this.userForm.get('password')?.setValidators([Validators.maxLength(200)]);
    this.userForm.get('username')?.updateValueAndValidity();
    this.userForm.get('password')?.updateValueAndValidity();
  }

  onSubmit(): void {
      if (this.transportForm.invalid || this.legalRepresentativeForm.invalid || this.userForm.invalid) return;
  
      const transportCompany: TransportCompany = this.transportForm.value;
      const legalRepresentative: LegalRepresentative = this.legalRepresentativeForm.value;
      const user: User = this.userForm.value;
      user.role = 'EMPRESA';
      const transportCompanyRegistration = {transportCompany, legalRepresentative, user};

      let save$;
      if(this.isEditMode){
        transportCompany.id = this.transportCompanyId;
        save$ = this.transportCompanyService.updateTransportCompany(transportCompanyRegistration)
      } else {
        save$ = this.transportCompanyService.createTransportCompany(transportCompanyRegistration);
      }

      save$.subscribe({
        next: (response: any) => {
          try {
            this.router.navigate(['/admin']);
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


  getTransportFormControl(name: string): FormControl {
    return this.transportForm.get(name) as FormControl;
  }

  getLegalRepControl(name: string): FormControl {
    return this.legalRepresentativeForm.get(name) as FormControl;
  }

  getUserControl(name: string): FormControl {
    return this.userForm.get(name) as FormControl;
  }

}
