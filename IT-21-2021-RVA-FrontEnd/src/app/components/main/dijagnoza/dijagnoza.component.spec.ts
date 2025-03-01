import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DijagnozaComponent } from './dijagnoza.component';

describe('DijagnozaComponent', () => {
  let component: DijagnozaComponent;
  let fixture: ComponentFixture<DijagnozaComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DijagnozaComponent]
    });
    fixture = TestBed.createComponent(DijagnozaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
