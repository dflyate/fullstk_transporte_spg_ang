import { Injectable } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { DialogComponent } from '../components/dialog/dialog.component';

export type DialogType = 'error' | 'success' | 'warning' | 'info';

@Injectable({
  providedIn: 'root'
})
export class DialogService {

  constructor(private dialog: MatDialog) { }

  openDialog(type: DialogType, title: string, message: string,
    actionBtn?: { text: string, onClick: () => void}
  ): void {
    const dialogRef = this.dialog.open(DialogComponent, {
      data: { type, title, message, actionBtn }
    });

    dialogRef.afterClosed().subscribe(result => {
      if(result === 'action' && actionBtn) actionBtn.onClick();
    });
    
  }
}
