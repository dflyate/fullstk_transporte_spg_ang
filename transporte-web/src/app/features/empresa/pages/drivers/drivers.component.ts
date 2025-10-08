import { Component, OnInit } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import { Router } from '@angular/router';
import { ConfirmationMessages, ErrorMessages } from 'src/app/core/constants/messages.constants';
import { ModalWindowsTypes } from 'src/app/core/constants/modal-windows-types';
import { Driver } from 'src/app/core/models/driver';
import { DriverService } from 'src/app/core/services/driver.service';
import { UserContextService } from 'src/app/core/services/user-context.service';
import { DialogService } from 'src/app/shared/services/dialog.service';

@Component({
  selector: 'app-drivers',
  templateUrl: './drivers.component.html',
  styleUrls: ['./drivers.component.css']
})
export class DriversComponent implements OnInit {

  displayedColumns = ['id', 'documentType', 'documentNumber', 'fullName', 'address', 'actions'];
  columnLabels = {
    id: 'ID',
    documentType: 'Tipo de Documento',
    documentNumber: 'Documento',
    fullName: 'Nombre',
    address: 'Dirección',
    actions: 'Acciones'
  };

  data: Driver[] = [];
  totalElements = 0;
  pageSize = 10;
  pageIndex = 0;
  isLoading = false;

  actionButtons = [
    { icon: 'edit', tooltip: 'Editar', action: 'edit' },
    { icon: 'delete', tooltip: 'Eliminar', action: 'delete' },
  ];

  constructor(private driverService: DriverService,
    private contextService: UserContextService,
    private router: Router,
    private dialogService: DialogService) { }

  ngOnInit(): void {
    this.loadDrivers();
  }

  loadDrivers(pageIndex: number = 0, pageSize: number = 10) {
    this.isLoading = true;

    this.driverService.getPagedDrivers(pageIndex, pageSize, this.contextService.getTransportCompanyId()).subscribe({

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
    this.loadDrivers(event.pageIndex, event.pageSize);
  }

  goToNewDriverForm(): void {
    this.router.navigate(['/company/drivers/new-driver']);
  }

  onTableAction(event: { action: string; row: any }) {
    const { action, row } = event;
    switch (action) {
      case 'edit':
        this.router.navigate(['/company/drivers/edit-driver', row.id]);
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
        text: 'Eliminar conductor',
        onClick: () => this.deleteRow(row)
      });
  }

  deleteRow(row: any) {
    this.driverService.deleteDriver(row.id).subscribe({
      next: (res) => {
        this.dialogService.openDialog('error', ConfirmationMessages.CONFIRMATION, res.message);
        this.loadDrivers();
      },
      error: (err) => {
        let errorMessage = err?.error?.message ? err.error.message : ErrorMessages.INTERNAL_SERVER_ERROR;
        this.dialogService.openDialog('error', ErrorMessages.ERROR, errorMessage);
      }
    })
  }
} 
