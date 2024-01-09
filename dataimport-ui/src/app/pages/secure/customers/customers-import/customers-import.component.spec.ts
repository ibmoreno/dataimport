import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomersImportComponent } from './customers-import.component';

describe('CustomersImportComponent', () => {
  let component: CustomersImportComponent;
  let fixture: ComponentFixture<CustomersImportComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CustomersImportComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CustomersImportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
