import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PostModel } from 'src/app/shared/post-model';
import { PostService } from 'src/app/shared/post.service';


@Component({
  selector: 'app-view-section',
  templateUrl: './view-section.component.html',
  styleUrls: ['./view-section.component.css']
})
export class ViewSectionComponent implements OnInit {
posts: Array<PostModel> = [];

  constructor(private activatedRoute: ActivatedRoute,private postService: PostService) {
    let id = this.activatedRoute.snapshot.params.sectionId

    this.postService.getAllPostsBySection(id).subscribe(post => {
      this.posts = post;
    });
}

  ngOnInit(): void {
  }

}
