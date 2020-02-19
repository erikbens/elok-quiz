import { Component, OnInit } from '@angular/core';
import { Difficulty, Difficulty2Label } from 'src/app/model/difficulty.enum';
import { Domain } from 'src/app/model/domain';
import { DomainService } from 'src/app/service/domain.service';
import { QuizFilter } from 'src/app/model/quizfilter';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-quiz-overview',
  templateUrl: './quiz-overview.component.html',
  styleUrls: ['./quiz-overview.component.less']
})
export class QuizOverviewComponent implements OnInit {

  difficulties = Object.values(Difficulty);
  difficulty2Label = Difficulty2Label;
  domains: Domain[];

  quizForm: FormGroup;

  constructor(private domainService: DomainService,
              private formBuilder: FormBuilder,
              private router: Router) { }

  ngOnInit() {
    this.getDomains();
    this.quizForm = this.formBuilder.group({
      filterDomain: null,
      filterDifficulty: null
    });
  }

  get f() { return this.quizForm.controls; }

  getDomains() {
    this.domainService.findAll().subscribe(resultData => {
      this.domains = resultData;
    });
  }

  goToQuiz() {
    const quizFilter = new QuizFilter();
    quizFilter.difficulty = this.quizForm.controls.filterDifficulty.value;
    quizFilter.domainId = this.quizForm.controls.filterDomain.value;

    this.router.navigate(['quiz/play'], {
      state: { filter: quizFilter }
    });
  }

}
