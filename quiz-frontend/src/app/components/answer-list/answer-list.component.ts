import { Component, OnInit, Input } from '@angular/core';
import { Answer } from 'src/app/model/answer';
import { AnswerService } from 'src/app/service/answer.service';
import { AlertService } from 'src/app/alert';

@Component({
  selector: 'app-answer-list',
  templateUrl: './answer-list.component.html',
  styleUrls: ['./answer-list.component.less']
})
export class AnswerListComponent implements OnInit {

  @Input() questionId: string;
  answers: Answer[];

  constructor(private answerService: AnswerService,
              private alertService: AlertService) { }

  ngOnInit() {
    if (this.questionId !== '' && this.questionId !== '0') {
      this.answerService.findAnswersByQuestionId(this.questionId).subscribe(resultData => this.answers = resultData);
    }
  }

  deleteAnswer(answer: Answer) {
    if (confirm('Antwort ' + answer.id + ' löschen?')) {
      this.answerService.deleteAnswer(answer.id).subscribe(() => {
        this.answers.splice(this.answers.indexOf(answer), 1);
        this.alertService.success('Antwort ' + answer.id + ' wurde gelöscht.');
      });
    }
  }

}
