import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { AnswerService } from 'src/app/service/answer.service';
import { Location } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { AlertService } from 'src/app/alert';

@Component({
  selector: 'app-answer-form',
  templateUrl: './answer-form.component.html',
  styleUrls: ['./answer-form.component.less']
})
export class AnswerFormComponent implements OnInit {

  answerForm: FormGroup;
  submitted = false;

  constructor(private formBuilder: FormBuilder,
              private answerService: AnswerService,
              private location: Location,
              private route: ActivatedRoute,
              private alertService: AlertService) { }

  ngOnInit() {
    this.getAnswer();
    this.answerForm = this.formBuilder.group({
      id: '',
      text: new FormControl('', Validators.required),
      correct: false
    });
  }

  getAnswer() {
    const answerId = this.route.snapshot.paramMap.get('answerId');
    if (answerId !== '0') {
      this.answerService.findById(answerId).subscribe(resultData => {
        this.answerForm.patchValue(resultData);
      });
    }
  }

  get f() { return this.answerForm.controls; }

  onSubmit() {
    this.submitted = true;
    const questionId = this.route.snapshot.paramMap.get('questionId');

    if (!this.answerForm.valid) {
      return;
    }

    this.answerService.save(questionId, this.answerForm.value).subscribe(resultData => {
      this.alertService.successKeepOnRouteChange('Antwort #' + resultData.id + ' erfolgreich gespeichert.');
      this.gotBack();
    });
  }

  gotBack() {
    this.location.back();
  }

}
