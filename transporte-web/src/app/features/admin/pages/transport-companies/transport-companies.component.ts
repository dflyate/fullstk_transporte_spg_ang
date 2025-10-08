import { Component, OnInit } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import { Router } from '@angular/router';
import { ConfirmationMessages, ErrorMessages } from 'src/app/core/constants/messages.constants';
import { ModalWindowsTypes } from 'src/app/core/constants/modal-windows-types';
import { TransportCompany } from 'src/app/core/models/transport-company';
import { TransportCompanyService } from 'src/app/core/services/transport-company.service';
import { DialogService } from 'src/app/shared/services/dialog.service';

@Component({
  selector: 'app-transport-companies',
  templateUrl: './transport-companies.component.html',
  styleUrls: ['./transport-companies.component.css']
})
export class TransportCompaniesComponent implements OnInit {

  displayedColumns = ['id', 'documentType', 'documentNumber', 'fullName', 'address', 'actions'];
  columnLabels = {
    id: 'ID',
    documentType: 'Tipo de Documento',
    documentNumber: 'Documento',
    fullName: 'nombre',
    address: 'Dirección',
    actions: 'Acciones'
  };

  data: TransportCompany[] = [];
  totalElements = 0;
  pageSize = 10;
  pageIndex = 0;
  isLoading = false;

  actionButtons = [
    { icon: 'edit', tooltip: 'Editar', action: 'edit' },
    { icon: 'delete', tooltip: 'Eliminar', action: 'delete' },
  ];

  constructor(private transportCompanyService: TransportCompanyService, 
              private router: Router,
              private dialogService: DialogService) { }

  ngOnInit(): void {
    this.loadTransportCompanies();
  }

  loadTransportCompanies(pageIndex: number = 0, pageSize: number = 10) {
    this.isLoading = true;

    this.transportCompanyService.getPagedTransportCompanies(pageIndex, pageSize).subscribe({

      next: (res) => {
        this.data = res.content;
        this.totalElements = res.totalElements;
        this.pageSize = res.size;
        this.pageIndex = res.number;
        this.isLoading = false;
      },
      error: () => {
        this.isLoading = false;
      }
    });
  }

  onPageChange(event: PageEvent) {
    this.loadTransportCompanies(event.pageIndex, event.pageSize);
  }

  goToNewTransportCompanyForm(): void {
    this.router.navigate(['/admin/new-transport-company']);
  }

  onTableAction(event: { action: string; row: any }) {
    const { action, row } = event;
    switch (action) {
      case 'edit':
        this.router.navigate(['/admin/edit-transport-company',row.id]);
        break;
      case 'delete':
        this.handleDeleteRow(row);
        break;
    }
  }

  handleDeleteRow(row: any) {
    this.dialogService.openDialog(ModalWindowsTypes.INFO, 
      ConfirmationMessages.CONFIRMATION, 
      ConfirmationMessages.DELETION_CONFIRMATION,
      {
        text: 'Eliminar empresa',
        onClick: () => this.deleteRow(row)
      });
  }

  deleteRow(row : any){
    this.transportCompanyService.deleteTransportCompany(row.id).subscribe({
      next: (res) => {
        this.dialogService.openDialog('error', ConfirmationMessages.CONFIRMATION, res.message);
        this.loadTransportCompanies();
      },
      error: (err) => {
        let errorMessage = err?.error?.message ? err.error.message : ErrorMessages.INTERNAL_SERVER_ERROR;
        this.dialogService.openDialog('error', ErrorMessages.ERROR , errorMessage);
      }
    })
  }

}
