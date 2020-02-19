import { Component, OnInit } from '@angular/core';
import { Question } from 'src/app/model/question';
import { QuestionService } from 'src/app/service/question.service';

@Component({
  selector: 'app-question-list',
  templateUrl: './question-list.component.html',
  styleUrls: ['./question-list.component.less']
})
export class QuestionListComponent implements OnInit {

  questions: Question[];
  page: Question[];

  constructor(private questionService: QuestionService) { }

  ngOnInit() {
    this.getQuestions();
  }

  getQuestions() {
    this.questionService.findAll().subscribe(questionsData => this.questions = questionsData);
  }

  onChangePage(page: Array<Question>) {
    this.page = page;
  }

}
