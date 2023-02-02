import { Component, OnInit } from '@angular/core';
import { SectionService } from 'src/app/section/section.service';
import { SectionModel } from 'src/app/section/section-response';

@Component({
  selector: 'app-section-side-bar',
  templateUrl: './section-side-bar.component.html',
  styleUrls: ['./section-side-bar.component.css']
})
export class SectionSideBarComponent implements OnInit {
  sections: Array<SectionModel> = [];
  displayViewAll: boolean;

  constructor(private sectionService: SectionService) {
    this.sectionService.getAllSections().subscribe(data => {
      if (data.length > 3) {
        this.sections = data.splice(0, 3);
        this.displayViewAll = true;
      } else {
        this.sections = data;
      }
    });
  }

  ngOnInit(): void { }

}
