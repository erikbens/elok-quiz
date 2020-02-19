import { Component, OnInit } from '@angular/core';
import { QuizFilter } from 'src/app/model/quizfilter';
import { Question } from 'src/app/model/question';
import { FormGroup, FormBuilder, FormArray, Validators, FormControl, AbstractControl } from '@angular/forms';
import { Answer } from 'src/app/model/answer';
import { QuizService } from 'src/app/service/quiz.service';
import { QuestionService } from 'src/app/service/question.service';
import { AuthenticationService } from 'src/app/service/auth.service';
import { CheckboxRequireOneValidator } from 'src/app/validator/checkbox-require-one.validator';
import { AlertService } from 'src/app/alert';
import { Router } from '@angular/router';
import { QuizResult } from 'src/app/model/quizresult';

@Component({
  selector: 'app-quiz-play',
  templateUrl: './quiz-play.component.html',
  styleUrls: ['./quiz-play.component.less']
})
export class QuizPlayComponent implements OnInit {

  quizFilter: QuizFilter;

  answersForm: FormGroup;

  submitted = false;
  correctAnswers = 0;
  incorrectAnswers = 0;

  questionList: Question[];
  currentQuestion: Question;

  constructor(private quizService: QuizService,
              private questionService: QuestionService,
              private alertService: AlertService,
              private formBuilder: FormBuilder,
              private router: Router,
private authService: AuthenticationService) {
  }

  ngOnInit() {
    this.currentQuestion = null;
    this.quizFilter = history.state.filter as QuizFilter;
    this.answersForm = this.formBuilder.group({
      foo: ''
    });
    this.getQuestions();
  }

  getQuestions() {
    this.quizService.getQuizByQuestionFilter(this.quizFilter).subscribe(resultData => {
      this.questionList = resultData;
      this.nextQuestion();
    });
  }

  a(index: number) { return this.answersForm.controls.answers[index]; }

  nextQuestion() {
	this.submitted = false;
    this.currentQuestion = this.questionList[this.questionList.indexOf(this.currentQuestion) + 1];
    if (this.currentQuestion != null) {
      this.addAnswers(this.currentQuestion.answers);
    } else {
      this.gotoQuizResult();
    }
  }

  addAnswers(answersToAdd: Answer[]) {
    this.answersForm.removeControl('answers');
    this.answersForm.addControl('answers', this.buildAnswersFormArray(answersToAdd));
  }

  buildAnswersFormArray(answersToAdd: Answer[]) {
    const controlArr = answersToAdd.map(() => {
      return this.formBuilder.control(false);
    });
    return this.formBuilder.array(controlArr, CheckboxRequireOneValidator.multipleCheckboxRequireOne);
  }

  onSubmit() {
    this.alertService.clear();
    this.submitted = true;

    if (!this.answersForm.valid) {
      return;
    }

    const givenAnswers = this.currentQuestion.answers
      .filter((answer, answerIdx) => this.answersArray.controls.some((control, controlIdx) => answerIdx === controlIdx && control.value))
      .map(answer => answer.id);
    this.questionService.checkAnswersForQuestionId(this.currentQuestion.id, givenAnswers).subscribe(resultData => {
      this.handleAnswerCheck(resultData);
    });
  }

  handleAnswerCheck(isCorrect: boolean) {
    if (isCorrect) {
        this.correctAnswers++;
        this.alertService.success('Das ist richtig!');

        this.nextQuestion();
      } else {
        this.incorrectAnswers++;
        this.alertService.error('Leider falsch');

        if (this.incorrectAnswers > 3) {
          this.gotoQuizResult();
        }
      }
  }

  gotoQuizResult() {
    const quizResult = new QuizResult();
    quizResult.difficulty = this.quizFilter.difficulty;
    quizResult.domainId = +this.quizFilter.domainId;
    quizResult.correctAnswers = this.correctAnswers;
    quizResult.incorrectAnswers = this.incorrectAnswers;
	quizResult.name = this.authService.currentUserValue ? this.authService.currentUserValue.name : 'Gast';

    this.router.navigate(['quiz/result'], {
      state: { quizResult }
    });
  }

  get answersArray(): FormArray {
    return this.answersForm && this.answersForm.controls.answers as FormArray;
  }

}
