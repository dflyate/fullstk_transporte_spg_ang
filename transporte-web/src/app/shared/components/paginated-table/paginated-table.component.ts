import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';

@Component({
  selector: 'app-paginated-table',
  templateUrl: './paginated-table.component.html',
  styleUrls: ['./paginated-table.component.css']
})
export class PaginatedTableComponent<T> {


  @Input() displayedColumns: string[] = [];
  @Input() columnLabels: {[key: string]: string} = {};
  @Input() dataSource: T[] = [];
  @Input() totalElements = 0;
  @Input() pageSize = 10;
  @Input() pageIndex = 0;
  @Input() isLoading = false;
  @Input() actionsEnabled: boolean = false;;
  @Input() actionButtons: {icon: string; tooltip: string; action:string }[] = [];
  @Output() actionClick = new EventEmitter<{action: string; row: any}>();

  @Output() pageChange = new EventEmitter<PageEvent>();

  handleActionClick(action: string, row: any ): void{
    
    this.actionClick.emit({action, row});
  }
}
