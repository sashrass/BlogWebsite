import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { SectionModel } from '../section-response';
import { Router } from '@angular/router';
import { SectionService } from '../section.service';
import { throwError } from 'rxjs';

@Component({
  selector: 'app-create-section',
  templateUrl: './create-section.component.html',
  styleUrls: ['./create-section.component.css']
})
export class CreateSectionComponent implements OnInit {
  createSectionForm: FormGroup;
  sectionModel: SectionModel;
  title = new FormControl('');
  description = new FormControl('');

  constructor(private router: Router, private sectionService: SectionService) {
    this.createSectionForm = new FormGroup({
      title: new FormControl('', Validators.required),
      description: new FormControl('', Validators.required)
    });
    this.sectionModel = {
      name: '',
      description: ''
    }
  }

  ngOnInit() {
  }

  discard() {
    this.router.navigateByUrl('/');
  }

  createSection() {
    this.sectionModel.name = this.createSectionForm.get('title')
    .value;
    this.sectionModel.description = this.createSectionForm.get('description')
    .value;
    this.sectionService.createSection(this.sectionModel).subscribe(data => {
      this.router.navigateByUrl('/list-sections');
    }, error => {
      throwError(error);
    })
  }
}